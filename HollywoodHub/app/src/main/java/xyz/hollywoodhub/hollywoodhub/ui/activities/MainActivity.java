package xyz.hollywoodhub.hollywoodhub.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.nodes.Document;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.hollywoodhub.hollywoodhub.R;
import xyz.hollywoodhub.hollywoodhub.filter.CreateFilterConstraint;
import xyz.hollywoodhub.hollywoodhub.model.FilterModel;
import xyz.hollywoodhub.hollywoodhub.ui.adapter.MovieViewPagerAdapter;
import xyz.hollywoodhub.hollywoodhub.ui.enums.ContentType;
import xyz.hollywoodhub.hollywoodhub.ui.fragments.FiltersDialogFragment;
import xyz.hollywoodhub.hollywoodhub.ui.fragments.MovieListFragment;
import xyz.hollywoodhub.hollywoodhub.ui.fragments.SearchContentListFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        MovieListFragment.OnFragmentInteractionListener, FiltersDialogFragment.FiltersCallback{

    private final String TAG_FILTER_FRAGMENT = "filter_fragment";
    private final String TAG_SEARCH_CONTENT_FRAGMENT = "search_content_fragment";

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialize();
        updateData(ContentType.MOVIE); // initialize item with movies
    }

    @Override
    protected void registerEvent() {
        eventBus = EventBus.getDefault();
    }

    private void updateData(ContentType contentType) {
        MovieViewPagerAdapter viewPagerAdapter = new MovieViewPagerAdapter(getSupportFragmentManager(), contentType);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.iv_filter)
    void onFilterClick() {
        CreateFilterConstraint constraint = new CreateFilterConstraint(eventBus.getStickyEvent(Document.class));
        EventBus.getDefault().postSticky(constraint);
        FragmentManager manager = getSupportFragmentManager();
        Fragment frag = manager.findFragmentByTag(TAG_FILTER_FRAGMENT);
        if (frag != null) {
            manager.beginTransaction().add(frag, TAG_FILTER_FRAGMENT).commit();
        } else {
            FiltersDialogFragment filtersDialogFragment = new FiltersDialogFragment();
            filtersDialogFragment.show(getSupportFragmentManager(), TAG_FILTER_FRAGMENT);
        }
    }

    private void onSearchQuerySubmit(String query) {
        startActivity(new Intent(this, Temp.class));
//        FragmentManager manager = getSupportFragmentManager();
//        Fragment frag = manager.findFragmentByTag(TAG_SEARCH_CONTENT_FRAGMENT);
//        if (frag == null) {
//            frag = SearchContentListFragment.newInstance(query);
//        }
//        manager.beginTransaction().replace(R.id.frame_layout, frag, TAG_SEARCH_CONTENT_FRAGMENT).commit();
    }

    @Override
    public void onFilterApplied(SparseArray<FilterModel> filters) {
        eventBus.postSticky(filters);
        updateData(ContentType.MIXED);
    }

    private void initialize() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onSearchQuerySubmit(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            updateData(ContentType.MOVIE);

        } else if (id == R.id.nav_gallery) {
            updateData(ContentType.TV_SERIES);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
