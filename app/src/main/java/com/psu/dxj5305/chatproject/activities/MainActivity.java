package com.psu.dxj5305.chatproject.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.psu.dxj5305.chatproject.db.AppDatabase;
import com.psu.dxj5305.chatproject.db.Thought;
import com.psu.dxj5305.chatproject.utils.CustomSharedPreferences;
import com.psu.dxj5305.chatproject.R;
import com.psu.dxj5305.chatproject.adapters.UserAdapter;
import com.psu.dxj5305.chatproject.models.Message;
import com.psu.dxj5305.chatproject.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    private Boolean snackbarDisplayed;
    private AppDatabase mDb;

    RecyclerView recyclerView;

    List<User> userArrayList;
    UserAdapter adapter;
    private CustomSharedPreferences sharedPreferences ;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mAuth=FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user=mAuth.getCurrentUser();
        sharedPreferences = new CustomSharedPreferences(this);
        if(user==null)
        {
            Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            snackbarDisplayed = savedInstanceState.getBoolean("snackbarDisplayed");
        }

        if(snackbarDisplayed == null){
            snackbarDisplayed = Boolean.FALSE;
        }

        if(!snackbarDisplayed){
            new Thread(() -> {
                mDb = AppDatabase.getDatabase(this);
                Thought thought = mDb.thoughtDao().getRandomThought();
                if (thought != null) {
                    String text = thought.text;
                    runOnUiThread(() -> {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG);
                        snackbar.setDuration(10000);
                        snackbar.show();
                    });
                }
            }).start();
            snackbarDisplayed = Boolean.TRUE;
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_home, menu);
        setTitle("Chat");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();

                return true;
            case R.id.settings:
                SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                Intent intent1 = new Intent(this, SettingsActivity.class);
                startActivity(intent1);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
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
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            final EditText input = new EditText(MainActivity.this);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Enter username to message")
                    .setView(input)
                    .setPositiveButton("Send", (dialog, whichButton) -> {
                        Intent messageIntent = new Intent(MainActivity.this, MessageListActivity.class);
                        messageIntent.putExtra("oppositeUser", input.getText().toString());
                        startActivity(messageIntent);
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        recyclerView = findViewById(R.id.users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userArrayList = new ArrayList<User>();
        adapter = new UserAdapter(MainActivity.this, userArrayList);
        recyclerView.setAdapter(adapter);
        EventChangeListener();

    }

    private void EventChangeListener() {
        db.collection("messages")
                .where(Filter.or(Filter.equalTo("sentBy", user.getEmail().substring(0, user.getEmail().indexOf('@'))), Filter.equalTo("sentTo", user.getEmail().substring(0, user.getEmail().indexOf('@')))))
                .orderBy("sentAt", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e("Firestore error", error.getMessage());
                    }
                    Set<String> users = new HashSet<>();
                    for (DocumentChange dc : value.getDocumentChanges()) {

                        if(dc.getType() == DocumentChange.Type.ADDED) {

                            Message message = dc.getDocument().toObject(Message.class);

                            users.add(message.getSentBy());
                            users.add(message.getSentTo());


                        }

                    }

                    Log.d("A", users.toString());
                    users.remove(user.getEmail().substring(0, user.getEmail().indexOf('@')));
//                    userArrayList.clear();
                    users.forEach((u)->{
                        User userNew = new User(u);
                        if(!userArrayList.contains(userNew)) {
                            userArrayList.add(new User(u));
                        }
                    });
                    adapter.notifyDataSetChanged();


                });
    }
    @Override
    protected void onSaveInstanceState(Bundle b){
        super.onSaveInstanceState(b);
        b.putBoolean("snackbarDisplayed", snackbarDisplayed);
    }

    @Override
    protected void onRestoreInstanceState(Bundle b){
        super.onRestoreInstanceState(b);
        snackbarDisplayed = b.getBoolean("snackbarDisplayed");
    }
}
