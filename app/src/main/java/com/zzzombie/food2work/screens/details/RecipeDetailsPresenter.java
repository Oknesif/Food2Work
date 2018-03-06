package com.zzzombie.food2work.screens.details;

import android.support.annotation.NonNull;

import com.zzzombie.food2work.api.Api;
import com.zzzombie.food2work.api.ApiResponse;
import com.zzzombie.food2work.api.Interactor;
import com.zzzombie.food2work.entities.Recipe;

public class RecipeDetailsPresenter implements RecipeDetailsViewListener, Interactor.Callback<Recipe> {

    private final Api api;
    private final Interactor<Recipe> interactor;
    private final RecipeDetailsModel model;
    private final RecipeDetailsView view;
    private final RecipeDetailsRouter router;

    public RecipeDetailsPresenter(
            @NonNull Api api,
            @NonNull Interactor<Recipe> interactor,
            @NonNull RecipeDetailsModel model,
            @NonNull RecipeDetailsView view,
            @NonNull RecipeDetailsRouter router) {
        this.api = api;
        this.interactor = interactor;
        this.model = model;
        this.view = view;
        this.router = router;
    }

    public void onStart() {
        this.view.setListener(this);
        this.interactor.setListener(this);
        if (model.getData() != null) {
            view.hideError();
            view.setProgressVisibility(false);
            view.showData(model.getData());
        } else {
            load();
        }
    }

    public void onStop() {
        view.setListener(null);
        interactor.cancelIfLoading();
        interactor.setListener(null);
    }

    private void load() {
        view.hideError();
        view.setProgressVisibility(true);
        interactor.loadData(() -> api.getRecipe(model.getRecipeId()));
    }

    @Override
    public void onRetryClicked() {
        load();
    }

    @Override
    public void onOriginalClicked() {
        router.openLink(model.getData().getSourceUrl());
    }

    @Override
    public void onInstructionClicked() {
        router.openLink(model.getData().getF2fUrl());
    }

    @Override
    public void onResponse(ApiResponse<Recipe> response) {
        if (response.successful) {
            model.setException(null);
            model.setData(response.data);
            view.showData(response.data);
            view.hideError();
            view.setProgressVisibility(false);
        } else {
            model.setException(response.exception);
            view.showError();
        }

    }
}
