package xyz.hollywoodhub.hollywoodhub.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.TreeMap;

import xyz.hollywoodhub.hollywoodhub.constants.Constants;
import xyz.hollywoodhub.hollywoodhub.HollywoodHubApplication;

/**
 * Created by rpandey.ppe on 23/07/17.
 */

public class Utils {

    public static boolean isNetworkAvailable() {
        ConnectivityManager
                cm = (ConnectivityManager) HollywoodHubApplication.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    public static int dpToPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }

    public static void showSnackBarMessage(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    public static TreeMap<String, String> getMoviesCategorization() {
        return Constants.categorizationIdMapping;
    }

    public static String getMoviesCategorizationIdByPosition(int position) {
        return Constants.categorizationIdList.get(position);
    }

    public static String getMoviesCategorizationIdByName(String name) {
        return Constants.categorizationIdMapping.get(name);
    }

    public static void showToast(String message) {
        Toast.makeText(HollywoodHubApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static Animation fadeOut(Context mContext){
        return AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out);
    }

    public static Animation fadeIn(Context mContext){
        return AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
    }
}
