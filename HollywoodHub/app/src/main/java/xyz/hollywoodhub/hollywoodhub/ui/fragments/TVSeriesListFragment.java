package xyz.hollywoodhub.hollywoodhub.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;
import xyz.hollywoodhub.hollywoodhub.R;
import xyz.hollywoodhub.hollywoodhub.constants.Constants;
import xyz.hollywoodhub.hollywoodhub.helper.ParserFactory;
import xyz.hollywoodhub.hollywoodhub.model.ContentDataModel;
import xyz.hollywoodhub.hollywoodhub.rest.IRestCallBacks;
import xyz.hollywoodhub.hollywoodhub.rest.gomovies.GetTVSeriesApi;
import xyz.hollywoodhub.hollywoodhub.ui.adapter.ContentListAdapter;
import xyz.hollywoodhub.hollywoodhub.utilities.Utils;

import static xyz.hollywoodhub.hollywoodhub.helper.JsoupHelper.parseHTML;

/**
 * Created by rpandey.ppe on 03/09/17.
 */

public class TVSeriesListFragment extends ContentListFragment{

    private static final String CATEGORY_ID = "category_id";

    @BindView(R.id.rv_tvseries_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.cv_progress_bar)
    CardView progressBarView;

    private String categoryId;

    public static TVSeriesListFragment newInstance(String categoryId) {
        TVSeriesListFragment fragment = new TVSeriesListFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getString(CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tvseries_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView();
    }

    @Override
    void fetchContents(final int pageNo) {
        showProgress();
        new GetTVSeriesApi(categoryId, pageNo + "", new IRestCallBacks<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        parseContentHTML(response.body());
                    }
                    else {
                        tryFetchSourceFromWebView(categoryId, call.request().url().toString());
                        return;
                    }
                }

                hideProgress();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                hideProgress();
                Utils.showSnackBarMessage(mRecyclerView, t.getMessage());
            }

        }).fetch();
    }

    @Override
    void parseContentHTML(String body) {
        Document document = parseHTML(body);
        try {
            List<ContentDataModel> contentDataModelList =
                    ParserFactory.getInstance().tvSeriesContentList(document);
            updateAdapter(contentDataModelList);
        } catch (Exception e) {
            Utils.showToast(Constants.PARSE_FAILED_MESSAGE);
        }
        hideProgress();
    }

    private void hideProgress() {
        if (progressBarView != null) {
            progressBarView.setAnimation(Utils.fadeOut(getContext()));
            progressBarView.setVisibility(View.GONE);
        }
    }

    private void showProgress() {
        if (progressBarView != null) {
            progressBarView.setAnimation(Utils.fadeIn(getContext()));
            progressBarView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
