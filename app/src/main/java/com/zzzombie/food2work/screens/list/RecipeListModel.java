package com.zzzombie.food2work.screens.list;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.zzzombie.food2work.entities.RecipeShort;

import java.util.List;

public class RecipeListModel implements Parcelable {
    private List<RecipeShort> data;
    private int scrollPosition;
    private String query;
    private int page = 1;
    private Exception exception;
    private boolean fullyLoaded;

    public boolean isFullyLoaded() {
        return fullyLoaded;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public List<RecipeShort> getData() {
        return data;
    }

    public void setData(List<RecipeShort> data) {
        this.data = data;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    RecipeListModel() {
    }

    private RecipeListModel(Parcel in) {
        data = in.readArrayList(RecipeShort.class.getClassLoader());
        scrollPosition = in.readInt();
        query = in.readString();
        page = in.readInt();
        exception = (Exception) in.readSerializable();
        fullyLoaded = in.readInt() == 1;
    }

    public void clear() {
        data = null;
        scrollPosition = 0;
        query = null;
        page = 1;
        exception = null;
        fullyLoaded = false;
    }

    public void appendData(List<RecipeShort> newData) {
        if (isEmpty()) {
            data = newData;
        } else {
            data.addAll(newData);
        }
        if (data.size() < 30) {
            fullyLoaded = true;
        }
    }

    public boolean isEmpty() {
        return data == null || data.size() == 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeList(data);
        dest.writeInt(scrollPosition);
        dest.writeString(query);
        dest.writeInt(page);
        dest.writeSerializable(exception);
        dest.writeInt(fullyLoaded ? 1 : 0);
    }

    public static final Creator<RecipeListModel> CREATOR = new Creator<RecipeListModel>() {
        @Override
        public RecipeListModel createFromParcel(Parcel in) {
            return new RecipeListModel(in);
        }

        @Override
        public RecipeListModel[] newArray(int size) {
            return new RecipeListModel[size];
        }
    };
}
