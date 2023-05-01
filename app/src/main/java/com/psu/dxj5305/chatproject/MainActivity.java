package com.psu.dxj5305.chatproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    private CustomSharedPreferences sharedPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        sharedPreferences = new CustomSharedPreferences(this);
        if(user==null)
        {
            Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
        super.onCreate(savedInstanceState);
//        Intent intent=new Intent(this,UserListActivity.class);
//        startActivity(intent);
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    }
}
