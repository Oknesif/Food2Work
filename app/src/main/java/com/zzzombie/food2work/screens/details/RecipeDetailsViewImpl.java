package com.zzzombie.food2work.screens.details;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzzombie.food2work.R;
import com.zzzombie.food2work.api.ImageLoader;
import com.zzzombie.food2work.entities.Recipe;

public class RecipeDetailsViewImpl implements RecipeDetailsView {

    private RecipeDetailsViewListener listener;

    private final View rootView;
    private final View progressView;
    private final ImageView imageView;
    private final LinearLayout ingredientsContainer;
    private final TextView publisherName;
    private final TextView socialRank;
    private Snackbar snackBar;

    private final ImageLoader imageLoader;

    public RecipeDetailsViewImpl(View rooView) {
        this.rootView = rooView;
        this.progressView = rooView.findViewById(R.id.progress_view);
        this.imageView = rooView.findViewById(R.id.img_photo);
        this.ingredientsContainer = rooView.findViewById(R.id.ingredients_container);
        this.publisherName = rooView.findViewById(R.id.publisher_name);
        this.socialRank = rooView.findViewById(R.id.social_rank);

        rooView.findViewById(R.id.btn_instruction).setOnClickListener(v -> {
            if (listener != null) {
                listener.onInstructionClicked();
            }
        });
        rooView.findViewById(R.id.btn_original).setOnClickListener(v -> {
            if (listener != null) {
                listener.onOriginalClicked();
            }
        });

        this.imageLoader = new ImageLoader(new Handler());
    }

    @Override
    public void setListener(RecipeDetailsViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void showData(Recipe recipe) {
        imageLoader.loadImage(imageView, recipe.getImageUrl());
        publisherName.setText(recipe.getPublisher());
        socialRank.setText(rootView.getContext().getString(R.string.social_rank, recipe.getSocialRank()));
        LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
        for (int i = 0; i < recipe.getIngredients().length; i++) {
            String ingredient = recipe.getIngredients()[i];
            View view = inflater.inflate(R.layout.item_ingredient, ingredientsContainer, false);
            ((TextView) view.findViewById(R.id.txt_ingredient)).setText(ingredient);
            ingredientsContainer.addView(view);
            if (i < recipe.getIngredients().length - 1) {
                inflater.inflate(R.layout.item_divider, ingredientsContainer, true);
            }
        }
    }

    @Override
    public void setProgressVisibility(boolean visible) {
        progressView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showError() {
        snackBar = Snackbar.make(rootView,
                rootView.getResources().getString(R.string.network_error),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(
                        rootView.getResources().getString(R.string.retry), v -> {
                            if (listener != null) {
                                listener.onRetryClicked();
                            }
                        });
        snackBar.show();

    }

    @Override
    public void hideError() {
        if (snackBar != null) {
            snackBar.dismiss();
        }
    }
}
