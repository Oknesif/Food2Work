package com.zzzombie.food2work.screens.list;

import android.support.annotation.Nullable;

import com.zzzombie.food2work.entities.RecipeShort;

public interface RecipeListViewListener {

    void onSearchQueryChanged(@Nullable String query);

    void onBottomScrolled();

    void scrolledTo(int position);

    void onItemClicked(RecipeShort item);

    void onRetryClick();
}
