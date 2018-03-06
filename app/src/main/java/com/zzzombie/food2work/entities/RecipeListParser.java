package com.zzzombie.food2work.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeListParser implements Parser<List<RecipeShort>> {

    @Override
    public List<RecipeShort> parse(String s) throws JSONException {
        List<RecipeShort> result = new ArrayList<>();
        JSONObject rootJSON = new JSONObject(s);
        JSONArray array = rootJSON.getJSONArray("recipes");
        for (int i = 0; i < array.length(); i++) {
            result.add(parseRecipe(array.getJSONObject(i)));
        }
        return result;
    }

    private RecipeShort parseRecipe(JSONObject jsonObject) throws JSONException {
        RecipeShort recipeShort = new RecipeShort();
        recipeShort.setPublisher(jsonObject.getString("publisher"));
        recipeShort.setF2fUrl(jsonObject.getString("f2f_url"));
        recipeShort.setTitle(jsonObject.getString("title"));
        recipeShort.setSourceUrl(jsonObject.getString("source_url"));
        recipeShort.setId(jsonObject.getString("recipe_id"));
        recipeShort.setImageUrl(jsonObject.getString("image_url"));
        recipeShort.setSocialRank(jsonObject.getDouble("social_rank"));

        return recipeShort;
    }
}
