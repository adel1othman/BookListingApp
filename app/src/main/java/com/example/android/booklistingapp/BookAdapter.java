package com.example.android.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Adel on 5/24/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.txtTitle);
        titleView.setText(currentBook.getmTitle());

        TextView authorView = (TextView) listItemView.findViewById(R.id.txtAuthor);
        authorView.setText(currentBook.getmAuthor());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.img);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(currentBook.getmImageURL(), imageView);

        return listItemView;
    }
}