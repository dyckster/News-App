package com.dyckster.newsapp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.dyckster.newsapp.R;
import com.dyckster.newsapp.model.Category;
import com.dyckster.newsapp.mvp.presenter.MainPresenter;
import com.dyckster.newsapp.mvp.view.MainView;
import com.dyckster.newsapp.util.ViewPagerAdapter;

import java.util.List;

public class MainActivity extends MvpAppCompatActivity implements
        MainView {

    @InjectPresenter
    MainPresenter presenter;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View progressView;

    private ViewPagerAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        progressView = findViewById(R.id.main_progress);

        tabLayout = findViewById(R.id.main_tabs);

        viewPager = findViewById(R.id.main_viewpager);
        viewPager.setOffscreenPageLimit(2);
        categoriesAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    }

    @Override
    public void onCategories(List<Category> categories) {
        for (Category category : categories) {
            categoriesAdapter.addFragment(NewsFragment.newInstance(category.getId()), category.getCategoryTitle());
        }
        viewPager.setAdapter(categoriesAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onCategoriesError() {
        Toast.makeText(this, R.string.err_categories_load, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void switchLoader(boolean show) {
        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        tabLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        viewPager.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
