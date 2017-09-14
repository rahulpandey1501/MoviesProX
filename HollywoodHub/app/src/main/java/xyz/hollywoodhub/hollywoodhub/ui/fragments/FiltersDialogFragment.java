package xyz.hollywoodhub.hollywoodhub.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.google.android.flexbox.FlexboxLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.hollywoodhub.hollywoodhub.R;
import xyz.hollywoodhub.hollywoodhub.filter.CreateFilterConstraint;
import xyz.hollywoodhub.hollywoodhub.filter.filters.FilmTypeFilter;
import xyz.hollywoodhub.hollywoodhub.filter.filters.QualityFilter;
import xyz.hollywoodhub.hollywoodhub.filter.filters.ReleaseFilter;
import xyz.hollywoodhub.hollywoodhub.model.FilterModel;

/**
 * Created by rpandey.ppe on 03/09/17.
 */

public class FiltersDialogFragment extends DialogFragment {

    @BindView(R.id.filter_flex_layout)
    FlexboxLayout flexboxLayout;

    private RadioButton releaseSelectedButton;
    private RadioButton qualitySelectedButton;
    private RadioButton filmTypeSelectedButton;

    private SparseArray<FilterModel> selectedFilters = new SparseArray<>();
    private FiltersCallback filtersCallback;

    public interface FiltersCallback {
        void onFilterApplied(SparseArray<FilterModel> filters);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filters, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        CreateFilterConstraint constraint = EventBus.getDefault().getStickyEvent(CreateFilterConstraint.class);
        if (constraint == null) {
            dismiss();
        }
        addAllFilters(constraint);
    }

    public FiltersDialogFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FiltersCallback) {
            filtersCallback = (FiltersCallback) context;
        } else {
            throw new ClassCastException(getClass().getCanonicalName() +
            " must implements " + FiltersCallback.class.getCanonicalName());
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    private void addAllFilters(CreateFilterConstraint constraint) {
        addFiltersView(constraint.getGenreFilter().getFilters(), true);
        addFiltersView(constraint.getCountryFilter().getFilters(), true);
        addFiltersView(constraint.getFilmTypeFilter().getFilters(), false);
        addFiltersView(constraint.getReleaseFilter().getFilters(), false);
        addFiltersView(constraint.getQualityFilter().getFilters(), false);
    }

    private void addFiltersView(List<FilterModel> list, boolean isMultiple) {
        List<RadioButton> radioButtons = new ArrayList<>(list.size());
        int marginPx = getResources().getDimensionPixelSize(R.dimen.default_space_small);
        int paddingLR = getResources().getDimensionPixelSize(R.dimen.default_space);
        int paddingTB = getResources().getDimensionPixelSize(R.dimen.default_space_tiny);
        for(FilterModel filterModel: list) {
            RadioButton radioButton = new RadioButton(getContext());
            FlexboxLayout.LayoutParams flbLayoutParams = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            flbLayoutParams.setMargins(0, 0, marginPx, marginPx);
            radioButton.setTextColor(getResources().getColorStateList(R.color.colorPrimaryDark));
            radioButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_button_grey_round_corner_fill));
            radioButton.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setClickable(true);
            radioButton.setText(filterModel.getName());
            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            radioButton.setPadding(paddingLR, paddingTB, paddingLR, paddingTB);
            radioButton.setLayoutParams(flbLayoutParams);
            radioButton.setTag(filterModel);
            flexboxLayout.addView(radioButton);
            radioButtons.add(radioButton);
        }

        setCallBacks(radioButtons, isMultiple);
    }

    private void setCallBacks(final List<RadioButton> radioButtons, boolean isMultiple) {
        if (isMultiple) {
            setCallbacksForMultipleSelection(radioButtons);
        } else {
            setCallbacksForSingleSelection(radioButtons);
        }
    }

    private void setCallbacksForMultipleSelection(List<RadioButton> radioButtons) {
        for (RadioButton radioButton : radioButtons) {
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                    if (check) {
                        selectedFilters.put(compoundButton.getTag().hashCode(), (FilterModel) compoundButton.getTag());
                    } else {
                        selectedFilters.remove(compoundButton.getTag().hashCode());
                    }
                }
            });

            radioButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (((RadioButton)view).isChecked()) {
                        ((RadioButton)view).setChecked(false);
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    private void setCallbacksForSingleSelection(List<RadioButton> radioButtons) {
        for (final RadioButton radioButton : radioButtons) {
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                    FilterModel filterModel = (FilterModel) radioButton.getTag();
                    if (check) {
                        switch (filterModel.getTag()) {
                            case ReleaseFilter.TAG:
                                if (releaseSelectedButton != null) {
                                    releaseSelectedButton.setChecked(false);
                                    selectedFilters.remove(releaseSelectedButton.getTag().hashCode());
                                }
                                releaseSelectedButton = (RadioButton) compoundButton;
                                break;

                            case QualityFilter.TAG:
                                if (qualitySelectedButton != null) {
                                    qualitySelectedButton.setChecked(false);
                                    selectedFilters.remove(qualitySelectedButton.getTag().hashCode());
                                }
                                qualitySelectedButton = (RadioButton) compoundButton;
                                break;

                            case FilmTypeFilter.TAG:
                                if (filmTypeSelectedButton != null) {
                                    filmTypeSelectedButton.setChecked(false);
                                    selectedFilters.remove(filmTypeSelectedButton.getTag().hashCode());
                                }
                                filmTypeSelectedButton = (RadioButton) compoundButton;
                                break;

                        }

                        selectedFilters.put(compoundButton.getTag().hashCode(), (FilterModel) compoundButton.getTag());
                    }
                }
            });
        }
    }

    @OnClick(R.id.btn_submit)
    void submit() {
        filtersCallback.onFilterApplied(selectedFilters);
        dismiss();
    }
}
