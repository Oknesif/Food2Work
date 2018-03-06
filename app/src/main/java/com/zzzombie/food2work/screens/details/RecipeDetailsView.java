package com.zzzombie.food2work.screens.details;

import com.zzzombie.food2work.entities.Recipe;

public interface RecipeDetailsView {

    void setProgressVisibility(boolean visible);

    void showError();

    void hideError();

    void setListener(RecipeDetailsViewListener listener);

    void showData(Recipe recipe);
}
