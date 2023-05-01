package com.psu.dxj5305.chatproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.preference.PreferenceManager;

public class UserListActivity extends AppCompatActivity {
//    private UserViewModel userViewModel;

    private CustomSharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
    }

    public void onStart(){
        super.onStart();
        if (sharedPreferences.loadNightModeState()) {
            setTheme(R.style.DarkTheme);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.darkColorPrimaryDark));
        } else {
            setTheme(R.style.LightTheme);
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.lightColorPrimaryDark));
        }
        setContentView(R.layout.activity_user_list);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        RecyclerView recyclerView = findViewById(R.id.users);
//        BooksListAdapter adapter = new BooksListAdapter(this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        booksViewModel = new ViewModelProvider(this).get(BooksViewModel.class);
//        booksViewModel.filterBooks();
//        booksViewModel.getAllBooks().observe(this, adapter::setBooks);

    }
}