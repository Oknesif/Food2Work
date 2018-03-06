package com.zzzombie.food2work.screens.details;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.zzzombie.food2work.entities.Recipe;

public class RecipeDetailsModel implements Parcelable {

    private String recipeId;
    private Recipe data;
    private Exception exception;

    public RecipeDetailsModel(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public Recipe getData() {
        return data;
    }

    public void setData(Recipe data) {
        this.data = data;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    protected RecipeDetailsModel(Parcel in) {
        data = in.readParcelable(Recipe.class.getClassLoader());
        exception = (Exception) in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
        dest.writeSerializable(exception);
    }

    public static final Creator<RecipeDetailsModel> CREATOR = new Creator<RecipeDetailsModel>() {
        @Override
        public RecipeDetailsModel createFromParcel(Parcel in) {
            return new RecipeDetailsModel(in);
        }

        @Override
        public RecipeDetailsModel[] newArray(int size) {
            return new RecipeDetailsModel[size];
        }
    };
}
