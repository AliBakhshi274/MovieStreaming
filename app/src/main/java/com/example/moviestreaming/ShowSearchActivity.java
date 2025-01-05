package com.example.moviestreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.Global.Global;
import com.example.moviestreaming.Model.AllInformation;

import java.util.ArrayList;
import java.util.List;

public class ShowSearchActivity extends AppCompatActivity {

    TextView title;
    SearchView searchView;
    RecyclerView recycler_show_search;

    RequestQueue requestQueue;
    Global global = new Global();
    List<AllInformation> list_search = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_search);

        requestQueue = Volley.newRequestQueue(this);

        findAll();

        title.setText(SearchActivity.TITLE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(ShowSearchActivity.this, query, Toast.LENGTH_SHORT).show();

                recycler_show_search.setLayoutManager(new GridLayoutManager(ShowSearchActivity.this, 3));
                recycler_show_search.setHasFixedSize(true);

                global.getSearch(ShowSearchActivity.this, requestQueue, SearchActivity.CATEGORY_NAME+"&&name="+query, recycler_show_search,list_search);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void findAll() {

        title = findViewById(R.id.show_search_title);
        searchView = findViewById(R.id.edt_search);
        recycler_show_search = findViewById(R.id.recycler_show_search);

    }
}