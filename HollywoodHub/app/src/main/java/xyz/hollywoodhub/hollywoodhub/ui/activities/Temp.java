package xyz.hollywoodhub.hollywoodhub.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xyz.hollywoodhub.hollywoodhub.R;
import xyz.hollywoodhub.hollywoodhub.ui.fragments.ContentListFragment;
import xyz.hollywoodhub.hollywoodhub.ui.fragments.SearchContentListFragment;

public class Temp extends AppCompatActivity implements ContentListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        start("batman");
    }

    private void start(String query) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment frag = manager.findFragmentByTag("dsd");
        if (frag == null) {
            frag = SearchContentListFragment.newInstance(query);
        }
        manager.beginTransaction().replace(R.id.frame_layout, frag, "dsd").commit();
    }
}
