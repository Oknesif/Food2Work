package com.zzzombie.food2work.screens.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.zzzombie.food2work.R;
import com.zzzombie.food2work.api.ApiImpl;
import com.zzzombie.food2work.api.InteractorImpl;

public class RecipeDetailsActivity extends AppCompatActivity {

    private static final String EX_RECIPE_ID = RecipeDetailsActivity.class.getCanonicalName().concat("EX_RECIPE_ID");
    private static final String EX_RECIPE_TITLE = RecipeDetailsActivity.class.getCanonicalName().concat("EX_RECIPE_TITLE");
    private static final String EX_MODEL = RecipeDetailsActivity.class.getCanonicalName().concat("EX_MODEL");

    public static void start(Context context, String recipeId, String recipeTitle) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(EX_RECIPE_ID, recipeId);
        intent.putExtra(EX_RECIPE_TITLE, recipeTitle);
        context.startActivity(intent);
    }

    private RecipeDetailsPresenter presenter;
    private RecipeDetailsModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getIntent().getStringExtra(EX_RECIPE_TITLE));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        if (savedInstanceState == null) {
            model = new RecipeDetailsModel(getIntent().getStringExtra(EX_RECIPE_ID));
        } else {
            model = savedInstanceState.getParcelable(EX_MODEL);
        }

        RecipeDetailsView view = new RecipeDetailsViewImpl(findViewById(R.id.root_view));
        presenter = new RecipeDetailsPresenter(
                new ApiImpl(),
                new InteractorImpl<>(),
                model,
                view,
                new RecipeDetailsRouterImpl(this));
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
