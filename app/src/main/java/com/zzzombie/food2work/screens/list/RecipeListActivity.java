package com.zzzombie.food2work.screens.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zzzombie.food2work.R;
import com.zzzombie.food2work.api.ApiImpl;
import com.zzzombie.food2work.api.InteractorImpl;


public class RecipeListActivity extends AppCompatActivity {

    private static final String EX_MODEL = RecipeListActivity.class.toString() + " EX_MODEL";
    private RecipeListPresenter presenter = null;
    private RecipeListModel model = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        if (savedInstanceState == null) {
            model = new RecipeListModel();
        } else {
            model = savedInstanceState.getParcelable(EX_MODEL);
        }
        View container = findViewById(R.id.root_view);
        RecipeListView recipeListView = new RecipeListViewImpl(container);
        presenter = new RecipeListPresenter(
                new ApiImpl(),
                new InteractorImpl<>(),
                model,
                recipeListView,
                new RecipeListRouterImpl(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EX_MODEL, model);
    }
}
