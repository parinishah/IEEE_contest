package com.example.krish.medical_app.UI;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Created by KRISH on 09-06-2017.
 */


    public class Search extends ListActivity {
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            handleIntent(getIntent());
        }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query =
                    intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    public void onNewIntent(Intent intent) {
            setIntent(intent);
            handleIntent(intent);
        }

        public void onListItemClick(ListView l,
                                    View v, int position, long id) {
            // call detail activity for clicked entry
        }



        private void doSearch(String queryStr) {
            // get a Cursor, prepare the ListAdapter
            // and set it
        }
    }

