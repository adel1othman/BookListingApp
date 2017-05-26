package com.example.android.booklistingapp;

/**
 * Created by Adel on 5/24/2017.
 */

public class Book {

    private String mTitle;
    private String mAuthor;
    private String mImageURL;

    public Book(String title, String author, String imageURL) {
        mTitle = title;
        mAuthor = author;
        mImageURL = imageURL;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmImageURL() {
        return mImageURL;
    }
}