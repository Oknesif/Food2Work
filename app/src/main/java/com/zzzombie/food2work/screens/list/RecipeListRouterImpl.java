package com.zzzombie.food2work.screens.list;

import android.content.Context;

import com.zzzombie.food2work.screens.details.RecipeDetailsActivity;

public class RecipeListRouterImpl implements RecipeListRouter {

    private Context context;

    public RecipeListRouterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void openRecipeDetails(String itemId, String title) {
        RecipeDetailsActivity.start(context, itemId, title);
    }
}
