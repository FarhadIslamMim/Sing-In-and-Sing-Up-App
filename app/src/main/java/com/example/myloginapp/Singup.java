package com.example.myloginapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;


public class Singup extends AppCompatActivity implements View.OnClickListener {

    private EditText singUpEmail,singUppassword;
    private TextView singInTextview;
    private Button singUpButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

        this.setTitle("Sing Up Activity");

        mAuth = FirebaseAuth.getInstance();

        singUpEmail=findViewById(R.id.idSingUpEmailEdittext);
        singUppassword=findViewById(R.id.idSingUpPassEdittext);
        singUpButton=findViewById(R.id.idsingUpButton);
        singInTextview=findViewById(R.id.idSingInTextView);
        progressBar=findViewById(R.id.idProgressBar);

        singInTextview.setOnClickListener(this);
        singUpButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.idsingUpButton:
                useRegister();
                break;
            case R.id.idSingInTextView:
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void useRegister() {
        String email=singUpEmail.getText().toString().trim();
        String password=singUppassword.getText().toString().trim();

        //checking the validity of the email
        if(email.isEmpty())
        {
            singUpEmail.setError("Enter an email address");
            singUpEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            singUpEmail.setError("Enter a valid email address");
            singUpEmail.requestFocus();
            return;
        }

        //checking the validity of the password
        if(email.isEmpty())
        {
            singUppassword.setError("Enter a password");
            singUppassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            singUppassword.setError("Minimum Length of a password is 6");
            singUppassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Register Is Successful",Toast.LENGTH_LONG ).show();
                } else {
                    if(task.getException() instanceof FirebaseAuthActionCodeException)
                    {
                        Toast.makeText(getApplicationContext(),"Already Registered",Toast.LENGTH_LONG ).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error "+task.getException(),Toast.LENGTH_LONG ).show();
                    }
                }
            }
        });
    }
}