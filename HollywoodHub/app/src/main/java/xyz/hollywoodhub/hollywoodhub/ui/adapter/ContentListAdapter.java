package xyz.hollywoodhub.hollywoodhub.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.hollywoodhub.hollywoodhub.R;
import xyz.hollywoodhub.hollywoodhub.model.ContentDataModel;
import xyz.hollywoodhub.hollywoodhub.ui.activities.ContentDetailsActivity;
import xyz.hollywoodhub.hollywoodhub.ui.enums.ContentType;
import xyz.hollywoodhub.hollywoodhub.utilities.Utils;

import static xyz.hollywoodhub.hollywoodhub.constants.Constants.WebView.URL_POST_APPEND;

/**
 * Created by rpandey.ppe on 03/09/17.
 */

public class ContentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<ContentDataModel> contentDataModelList;

    public ContentListAdapter(Context context, List<ContentDataModel> contentDataModelList) {
        this.context = context;
        this.contentDataModelList = contentDataModelList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_list_item_view, parent, false);
        if (viewType == ContentType.MOVIE.getValue()) {
            return new MovieContentViewHolder(view);
        }
        else if (viewType == ContentType.TV_SERIES.getValue()){
            return new TVSeriesContentViewHolder(view);
        }
        else {
            return new MovieContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieContentViewHolder) {
            bindMovieData((MovieContentViewHolder) holder, contentDataModelList.get(position));
        }
        else if (holder instanceof TVSeriesContentViewHolder){
            bindTVSeriesData((TVSeriesContentViewHolder) holder, contentDataModelList.get(position));
        }
    }

    private void bindMovieData(MovieContentViewHolder holder, final ContentDataModel contentDataModel) {
        holder.contentTitle.setText(contentDataModel.getMovieName());
        Picasso
                .with(context)
                .load(contentDataModel.getImageUrl())
                .fit().centerCrop()
                .placeholder(R.drawable.ic_menu_gallery)
                .into(holder.contentImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(contentDataModel.getContentUrl(), ContentType.MOVIE);
            }
        });
    }

    private void bindTVSeriesData(TVSeriesContentViewHolder holder, final ContentDataModel contentDataModel) {
        holder.contentTitle.setText(contentDataModel.getMovieName());
        Picasso
                .with(context)
                .load(contentDataModel.getImageUrl())
                .fit().centerCrop()
                .placeholder(R.drawable.ic_menu_gallery)
                .into(holder.contentImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(contentDataModel.getContentUrl(), ContentType.TV_SERIES);
            }
        });
    }

    private void startActivity(String url, ContentType contentType) {
        EventBus.getDefault().post(url + URL_POST_APPEND);
        EventBus.getDefault().post(contentType);
        context.startActivity(new Intent(context, ContentDetailsActivity.class));
        Utils.showToast(url);
    }

    @Override
    public int getItemViewType(int position) {
        return contentDataModelList.get(position).getContentType().getValue();
    }

    @Override
    public int getItemCount() {
        return contentDataModelList.size();
    }

    public void updateAdapter(List<ContentDataModel> contentDataModelList) {
        this.contentDataModelList.addAll(contentDataModelList);
        notifyDataSetChanged();
    }

    class MovieContentViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_content_title) TextView contentTitle;
        @BindView(R.id.iv_content_image) ImageView contentImageView;

        MovieContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TVSeriesContentViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_content_title) TextView contentTitle;
        @BindView(R.id.iv_content_image) ImageView contentImageView;

        TVSeriesContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
