package com.zzzombie.food2work.api;

import android.accounts.NetworkErrorException;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zzzombie.food2work.entities.Parser;
import com.zzzombie.food2work.entities.Recipe;
import com.zzzombie.food2work.entities.RecipeListParser;
import com.zzzombie.food2work.entities.RecipeParser;
import com.zzzombie.food2work.entities.RecipeShort;

import org.json.JSONException;

public class ApiImpl implements Api {

    @Override
    public ApiResponse<List<RecipeShort>> getRecipesList(@Nullable String query, int page) {
        Pair<String, String> pageParam = new Pair<>("page", String.valueOf(page));
        Map<String, String> params;
        if (query != null && query.length() > 0) {
            params = getParamMap(pageParam, new Pair<>("q", query));
        } else {
            params = getParamMap(pageParam);
        }
        return getResponse(params, "search", new RecipeListParser());
    }

    @Override
    public ApiResponse<Recipe> getRecipe(String recipeId) {
        Pair<String, String> pageParam = new Pair<>("rId", recipeId);
        return getResponse(getParamMap(pageParam), "get", new RecipeParser());
    }

    private <T> ApiResponse<T> getResponse(
            Map<String, String> params,
            String path,
            Parser<T> parser) {
        HttpURLConnection connection = null;
        try {
            connection = getUrlConnection(path, params);
            String responseString = readString(connection);
            return new ApiResponse<>(parser.parse(responseString));
        } catch (JSONException | IOException | NetworkErrorException e) {
            return new ApiResponse<>(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private Map<String, String> getParamMap(Pair<String, String>... params) {
        Map<String, String> paramMap = new HashMap<>();
        for (Pair<String, String> pair : params) {
            paramMap.put(pair.first, pair.second);
        }
        paramMap.put("key", "2a80cc53367ab4e7fa3b8dc88f26c6f7"); //I don't care about this api key, take it if you need
        return paramMap;
    }

    private String readString(HttpURLConnection connection) throws IOException, NetworkErrorException {
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
            return sb.toString();
        } else {
            throw new NetworkErrorException("Not HTTP_OK response; " + "response code - " + responseCode);
        }
    }

    private HttpURLConnection getUrlConnection(String path, Map<String, String> params) throws IOException {
        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme("http")
                .authority("food2fork.com")
                .appendPath("api")
                .appendPath(path);
        for (String key : params.keySet()) {
            uriBuilder.appendQueryParameter(key, params.get(key));
        }
        Uri uri = uriBuilder.build();

        HttpURLConnection connection = (HttpURLConnection) new URL(uri.toString()).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-length", "0");
        connection.setUseCaches(false);
        connection.setAllowUserInteraction(false);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        return connection;
    }
}
