package xyz.hollywoodhub.hollywoodhub.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import xyz.hollywoodhub.hollywoodhub.ui.enums.ContentType;
import xyz.hollywoodhub.hollywoodhub.ui.fragments.FilterContentListFragment;
import xyz.hollywoodhub.hollywoodhub.ui.fragments.MovieListFragment;
import xyz.hollywoodhub.hollywoodhub.ui.fragments.TVSeriesListFragment;
import xyz.hollywoodhub.hollywoodhub.utilities.Utils;

/**
 * Created by rpandey.ppe on 02/09/17.
 */

public class MovieViewPagerAdapter extends FragmentStatePagerAdapter {

    private ContentType contentType;

    public MovieViewPagerAdapter(FragmentManager fm, ContentType contentType) {
        super(fm);
        this.contentType = contentType;
    }

    @Override
    public Fragment getItem(int position) {
        if (contentType == ContentType.MOVIE) {
            return MovieListFragment.newInstance(
                    Utils.getMoviesCategorizationIdByPosition(position));
        }
        else if (contentType == ContentType.TV_SERIES){
            return TVSeriesListFragment.newInstance(
                    Utils.getMoviesCategorizationIdByPosition(position));
        }
        else {
            return FilterContentListFragment.newInstance(
                    Utils.getMoviesCategorizationIdByPosition(position));
        }
    }

    @Override
    public int getCount() {
        return Utils.getMoviesCategorization().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String categoryId = Utils.getMoviesCategorizationIdByPosition(position);
        return Utils.getMoviesCategorization().get(categoryId);
    }
}
