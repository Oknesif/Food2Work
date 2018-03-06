package com.zzzombie.food2work.screens.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class RecipeDetailsRouterImpl implements RecipeDetailsRouter {

    private Context context;

    public RecipeDetailsRouterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void openLink(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        context.startActivity(intent);
    }
}
