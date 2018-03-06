package com.zzzombie.food2work.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeParser implements Parser<Recipe> {

    @Override
    public Recipe parse(String s) throws JSONException {
        Recipe recipe = new Recipe();
        JSONObject rootJson = new JSONObject(s);
        JSONObject recipeJson = rootJson.getJSONObject("recipe");
        recipe.setF2fUrl(recipeJson.getString("f2f_url"));
        recipe.setSourceUrl(recipeJson.getString("source_url"));
        recipe.setId(recipeJson.getString("recipe_id"));
        recipe.setImageUrl(recipeJson.getString("image_url"));
        recipe.setPublisherUrl(recipeJson.getString("publisher_url"));
        recipe.setPublisher(recipeJson.getString("publisher"));
        recipe.setTitle(recipeJson.getString("title"));
        recipe.setSocialRank(recipeJson.getDouble("social_rank"));

        JSONArray ingredients = recipeJson.getJSONArray("ingredients");
        String[] strings = new String[ingredients.length()];
        for (int i = 0; i < ingredients.length(); i++) {
            strings[i] = ingredients.getString(i);
        }
        recipe.setIngredients(strings);

        return recipe;
    }
}
