package xyz.hollywoodhub.hollywoodhub.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import xyz.hollywoodhub.hollywoodhub.helper.CloudflareWebViewPageSourceHelper;
import xyz.hollywoodhub.hollywoodhub.model.ContentDataModel;
import xyz.hollywoodhub.hollywoodhub.ui.adapter.ContentListAdapter;
import xyz.hollywoodhub.hollywoodhub.utilities.PaginationScrollListener;

/**
 * Created by rpandey.ppe on 03/09/17.
 */

public abstract class ContentListFragment extends Fragment implements CloudflareWebViewPageSourceHelper.Callbacks{

    abstract RecyclerView getRecyclerView();

    abstract void fetchContents(int pageNo);

    abstract void parseContentHTML(String html);

    public interface OnFragmentInteractionListener {

    }

    private OnFragmentInteractionListener mListener;


    public ContentListFragment() {
        // Required empty public constructor
    }

    private Unbinder unbinder;
    protected EventBus eventBus;
    protected CloudflareWebViewPageSourceHelper viewPageSourceHelper;

    private int pageNo = 0;
    private boolean isLoading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus = EventBus.getDefault();
        viewPageSourceHelper = CloudflareWebViewPageSourceHelper.newInstance(getContext(), this).initialize();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    protected void initializeRecyclerView() {
        ContentListAdapter contentListAdapter = new ContentListAdapter(getContext(), new ArrayList<ContentDataModel>());
        getRecyclerView().setLayoutManager(new GridLayoutManager(getContext(), 3));
        getRecyclerView().setAdapter(contentListAdapter);
        setRecyclerViewListener();
        fetchContents(0); // start loading
    }

    void updateAdapter(List<ContentDataModel> contentDataModelList) {
        ContentListAdapter adapter = (ContentListAdapter) getRecyclerView().getAdapter();
        adapter.updateAdapter(contentDataModelList);
        increasePageNo(pageNo);
    }

    protected int getPageNo() {
        return pageNo;
    }

    protected void setRecyclerViewListener() {

        getRecyclerView().addOnScrollListener(new PaginationScrollListener() {
            @Override
            public void loadMoreItems() {
                fetchContents(pageNo);
                isLoading = true;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    protected void tryFetchSourceFromWebView(String contextId, String url) {
        viewPageSourceHelper.loadURL(contextId, url);
    }

    @Override
    public void onPageSourceLoad(String contextId, final String html) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parseContentHTML(html);
            }
        });
    }

    private void increasePageNo(int previousPageNo) {
        pageNo = ++previousPageNo;
        isLoading = false;
    }

    @Subscribe
    protected void onReceivedEvent(Object obj) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
