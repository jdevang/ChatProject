package com.psu.dxj5305.chatproject.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.psu.dxj5305.chatproject.R;

public class RegisterActivity extends AppCompatActivity {

    EditText username,password;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button register;
    TextView login;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();
        username=(EditText) findViewById(R.id.editTextEmailAddress);
        password=(EditText) findViewById(R.id.editTextPassword);
        register=(Button) findViewById(R.id.register_button);
        progressBar=(ProgressBar)findViewById(R.id.progress_horizontal);
        login=(TextView)findViewById(R.id.textView2) ;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email,pass;
                email=username.getText().toString();
                pass=password.getText().toString();

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(RegisterActivity.this, "Email can't be empty", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(pass))
                {
                    Toast.makeText(RegisterActivity.this, "Password can't be empty", Toast.LENGTH_SHORT).show();
                }

                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Logged in successfully.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Login failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}