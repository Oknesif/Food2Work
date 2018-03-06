package com.zzzombie.food2work.screens.list;

import com.zzzombie.food2work.entities.RecipeShort;

import java.util.List;

public interface RecipeListView {

    void setListener(RecipeListViewListener listener);

    void refreshList(List<RecipeShort> data);

    void setProgressVisible(boolean visible);

    void setSearchQuery(String query);

    void showError();

    void hideError();

    void scrollTo(int scrollPosition);
}
