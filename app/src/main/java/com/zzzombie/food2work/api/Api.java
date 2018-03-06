package com.zzzombie.food2work.api;

import android.support.annotation.Nullable;

import java.util.List;

import com.zzzombie.food2work.entities.Recipe;
import com.zzzombie.food2work.entities.RecipeShort;

public interface Api {

    ApiResponse<List<RecipeShort>> getRecipesList(@Nullable String query, int page);

    ApiResponse<Recipe> getRecipe(String recipeId);
}
