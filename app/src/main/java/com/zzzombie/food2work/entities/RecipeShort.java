package com.zzzombie.food2work.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class RecipeShort implements Parcelable {
    private String title;
    private String sourceUrl;
    private String imageUrl;
    private String f2fUrl;
    private String publisherUrl;
    private String id;
    private String publisher;
    private double socialRank;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getF2fUrl() {
        return f2fUrl;
    }

    public void setF2fUrl(String f2fUrl) {
        this.f2fUrl = f2fUrl;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public void setPublisherUrl(String publisherUrl) {
        this.publisherUrl = publisherUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getSocialRank() {
        return socialRank;
    }

    public void setSocialRank(double socialRank) {
        this.socialRank = socialRank;
    }

    public RecipeShort() {
    }

    private RecipeShort(Parcel in) {
        this.title = in.readString();
        this.sourceUrl = in.readString();
        this.imageUrl = in.readString();
        this.f2fUrl = in.readString();
        this.publisherUrl = in.readString();
        this.id = in.readString();
        this.publisher = in.readString();
        this.socialRank = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(sourceUrl);
        dest.writeString(imageUrl);
        dest.writeString(f2fUrl);
        dest.writeString(publisherUrl);
        dest.writeString(id);
        dest.writeString(publisher);
        dest.writeDouble(socialRank);
    }

    public static final Parcelable.Creator<RecipeShort> CREATOR = new Parcelable.Creator<RecipeShort>() {
        @Override
        public RecipeShort createFromParcel(Parcel in) {
            return new RecipeShort(in);
        }

        @Override
        public RecipeShort[] newArray(int size) {
            return new RecipeShort[size];
        }
    };
}
