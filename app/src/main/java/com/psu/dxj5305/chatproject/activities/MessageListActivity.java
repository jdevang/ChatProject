package com.psu.dxj5305.chatproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.psu.dxj5305.chatproject.utils.CustomSharedPreferences;
import com.psu.dxj5305.chatproject.adapters.MessageAdapter;
import com.psu.dxj5305.chatproject.R;
import com.psu.dxj5305.chatproject.models.Message;

import java.util.ArrayList;
import java.util.Date;

public class MessageListActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;

    RecyclerView recyclerView;

    ArrayList<Message> messageArrayList;
    MessageAdapter adapter;

    String oppositeUser;
    private CustomSharedPreferences sharedPreferences ;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth= FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user=mAuth.getCurrentUser();
        sharedPreferences = new CustomSharedPreferences(this);
        oppositeUser = getIntent().getExtras().getString("oppositeUser");
        if(user==null)
        {
            Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_message_list);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        recyclerView = findViewById(R.id.messageRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageArrayList = new ArrayList<Message>();
        adapter = new MessageAdapter(MessageListActivity.this, messageArrayList);
        recyclerView.setAdapter(adapter);

        EventChangeListener();
        EditText messageData = findViewById(R.id.messageEditText);
        ImageView sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener((a) -> {

            Message message = new Message(messageData.getText().toString(), user.getEmail().substring(0, user.getEmail().indexOf('@')), oppositeUser, new Date());

            db.collection("messages")
                    .add(message);
            messageData.setText("");
        });

    }
    private void EventChangeListener() {
        db.collection("messages")
                .where(Filter.or(Filter.and(Filter.equalTo("sentBy", user.getEmail().substring(0, user.getEmail().indexOf('@'))), Filter.equalTo("sentTo", oppositeUser)), Filter.and(Filter.equalTo("sentBy", oppositeUser), Filter.equalTo("sentTo", user.getEmail().substring(0, user.getEmail().indexOf('@'))))))
                .orderBy("sentAt", Query.Direction.ASCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e("Firestore error", error.getMessage());
                    }
                    for (DocumentChange dc : value.getDocumentChanges()) {

                        if(dc.getType() == DocumentChange.Type.ADDED) {
                            messageArrayList.add(dc.getDocument().toObject(Message.class));
                        }

                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
