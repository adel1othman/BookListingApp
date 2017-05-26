package com.example.android.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private String REQUEST_URL;

    private static final int Book_LOADER_ID = 1;

    private BookAdapter mAdapter;

    private TextView mEmptyStateTextView;
    EditText search;
    ListView bookListView;
    ProgressBar loadingIndicator;
    LoaderManager.LoaderCallbacks<List<Book>> myCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        bookListView = (ListView) findViewById(R.id.bookList);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        myCallbacks = new LoaderManager.LoaderCallbacks<List<Book>>() {
            @Override
            public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
                loadingIndicator.setVisibility(View.VISIBLE);
                return new BookLoader(getBaseContext(), REQUEST_URL);
            }

            @Override
            public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
                loadingIndicator.setVisibility(View.GONE);

                mAdapter.clear();

                if (books != null && !books.isEmpty()) {
                    mEmptyStateTextView.setVisibility(View.GONE);
                    mAdapter.addAll(books);
                }else {
                    mEmptyStateTextView.setText(R.string.no_books);
                    bookListView.setEmptyView(mEmptyStateTextView);
                }
            }

            @Override
            public void onLoaderReset(Loader<List<Book>> loader) {
                loadingIndicator.setVisibility(View.GONE);
                mAdapter.clear();
            }
        };
        //bookListView.setEmptyView(mEmptyStateTextView);

        /*bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Book currentBook = mAdapter.getItem(position);
            }
        });*/

        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        search = (EditText)findViewById(R.id.etSearch);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*if (s.toString().isEmpty()){
                    mAdapter.clear();
                    mEmptyStateTextView.setText(R.string.bookSearching);
                    bookListView.setEmptyView(mEmptyStateTextView);
                }else {
                    if (mAdapter != null){
                        mAdapter.clear();
                    }
                    REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?maxResults=20&q=" + s.toString().toLowerCase();
                    mAdapter = new BookAdapter(getBaseContext(), new ArrayList<Book>());

                    bookListView.setAdapter(mAdapter);

                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {
                        mEmptyStateTextView.setVisibility(View.GONE);
                        LoaderManager loaderManager = getLoaderManager();
                        loaderManager.restartLoader(Book_LOADER_ID, null, myCallbacks);
                    } else {
                        loadingIndicator.setVisibility(View.GONE);

                        mEmptyStateTextView.setText(R.string.no_internet_connection);
                        bookListView.setEmptyView(mEmptyStateTextView);
                    }
                }*/
            }

            private Timer timer = new Timer();
            private final long DELAY = 1000;

            @Override
            public void afterTextChanged(final Editable s) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (s.toString().isEmpty()){
                                            mAdapter.clear();
                                            mEmptyStateTextView.setText(R.string.bookSearching);
                                            bookListView.setEmptyView(mEmptyStateTextView);
                                        }else {
                                            /*if (mAdapter != null){
                                                mAdapter.clear();
                                            }*/
                                            REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?maxResults=20&q=" + s.toString().toLowerCase();
                                            mAdapter = new BookAdapter(getBaseContext(), new ArrayList<Book>());

                                            bookListView.setAdapter(mAdapter);

                                            ConnectivityManager connMgr = (ConnectivityManager)
                                                    getSystemService(Context.CONNECTIVITY_SERVICE);

                                            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                                            if (networkInfo != null && networkInfo.isConnected()) {
                                                mEmptyStateTextView.setVisibility(View.GONE);
                                                LoaderManager loaderManager = getLoaderManager();
                                                loaderManager.restartLoader(Book_LOADER_ID, null, myCallbacks);
                                            } else {
                                                loadingIndicator.setVisibility(View.GONE);

                                                mEmptyStateTextView.setText(R.string.no_internet_connection);
                                                bookListView.setEmptyView(mEmptyStateTextView);
                                            }
                                        }
                                    }
                                });
                            }
                        },
                        DELAY
                );
            }
        });
    }
}