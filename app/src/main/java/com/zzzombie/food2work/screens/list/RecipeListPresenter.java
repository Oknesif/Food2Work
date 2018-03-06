package com.zzzombie.food2work.screens.list;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zzzombie.food2work.api.Api;
import com.zzzombie.food2work.api.ApiResponse;
import com.zzzombie.food2work.api.Interactor;
import com.zzzombie.food2work.entities.RecipeShort;

import java.util.List;

public class RecipeListPresenter implements RecipeListViewListener, Interactor.Callback<List<RecipeShort>> {
    private final RecipeListModel model;
    private final RecipeListView view;
    private final RecipeListRouter router;
    private final Api api;
    private final Interactor<List<RecipeShort>> interactor;
    private boolean loading;

    public RecipeListPresenter(
            @NonNull Api api,
            @NonNull Interactor<List<RecipeShort>> interactor,
            @NonNull RecipeListModel model,
            @NonNull RecipeListView view,
            @NonNull RecipeListRouter router) {
        this.model = model;
        this.view = view;
        this.api = api;
        this.router = router;
        this.interactor = interactor;
    }

    public void onStart() {
        view.setListener(this);
        view.setSearchQuery(model.getQuery());
        view.setProgressVisible(!model.isFullyLoaded());
        interactor.setListener(this);
        if (!model.isEmpty()) {
            view.refreshList(model.getData());
            view.scrollTo(model.getScrollPosition());
        }
    }

    public void onStop() {
        view.setListener(null);
        interactor.cancelIfLoading();
        interactor.setListener(null);
        loading = false;
    }

    @SuppressLint("StaticFieldLeak")
    private void load() {
        view.setProgressVisible(true);
        loading = true;
        interactor.loadData(() -> api.getRecipesList(model.getQuery(), model.getPage()));
    }

    @Override
    public void onSearchQueryChanged(@Nullable String query) {
        model.clear();
        model.setQuery(query);
        view.refreshList(null);
        load();
    }

    @Override
    public void onBottomScrolled() {
        if (!loading && !model.isFullyLoaded()) {
            load();
        }
    }

    @Override
    public void scrolledTo(int position) {
        model.setScrollPosition(position);
    }

    @Override
    public void onItemClicked(RecipeShort item) {
        router.openRecipeDetails(item.getId(), item.getTitle());
    }

    @Override
    public void onRetryClick() {
        load();
    }

    @Override
    public void onResponse(ApiResponse<List<RecipeShort>> response) {
        loading = false;

        if (response.successful) {
            model.setPage(model.getPage() + 1);
            model.setException(null);
            model.appendData(response.data);
            view.refreshList(model.getData());
            view.hideError();
            if (response.data == null || response.data.size() < 30) {
                view.setProgressVisible(false);
            }
        } else {
            model.setException(response.exception);
            view.showError();
        }
    }
}
