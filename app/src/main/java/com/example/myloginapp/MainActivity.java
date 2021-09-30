package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private EditText singinEmail,singinpassword;
    private TextView singupTextview;
    private Button singinButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sing In Activity");


        mAuth = FirebaseAuth.getInstance();
        singinEmail=findViewById(R.id.idSingInEmailEdittext);
        singinpassword=findViewById(R.id.idSingInPassEdittext);
        singinButton=findViewById(R.id.idSingInButton);
        singupTextview=findViewById(R.id.idSingUpTextView);
        progressBar=findViewById(R.id.idProgressBar);

        singupTextview.setOnClickListener(this);
        singinButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.idSingInButton:
                userLogin();
                Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_LONG).show();

                break;
            case R.id.idSingUpTextView:
                Intent intent=new Intent(MainActivity.this,Singup.class);
                startActivity(intent);
                break;
        }

    }

    private void userLogin() {
        String email=singinEmail.getText().toString().trim();
        String password=singinpassword.getText().toString().trim();

        //checking the validity of the email
        if(email.isEmpty())
        {
            singinEmail.setError("Enter an email address");
            singinEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            singinEmail.setError("Enter a valid email address");
            singinEmail.requestFocus();
            return;
        }

        //checking the validity of the password
        if(email.isEmpty())
        {
            singinpassword.setError("Enter a password");
            singinpassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            singinpassword.setError("Minimum Length of a password is 6");
            singinpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    //finish();
                    Intent intent =new Intent(getApplicationContext(),Profile.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Sing In Unsuccessful",Toast.LENGTH_LONG ).show();
                }
            }
        });
    }

}