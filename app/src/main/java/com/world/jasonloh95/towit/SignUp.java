package com.world.jasonloh95.towit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.world.jasonloh95.towit.data.Member;

public class SignUp extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText contactNumber;
    private EditText name;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.name);
        contactNumber = (EditText) findViewById(R.id.contact_Number);
        password = (EditText) findViewById(R.id.Password);
        email = (EditText) findViewById(R.id.email);
        Button register = (Button) findViewById(R.id.register);
        TextView loginconnector = (TextView) findViewById(R.id.loginconnector);

        progressDialog = new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                btnRegistrationUser_Click(view);
            }

        });

        loginconnector.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SignUp.this, CustomerLoginActivity.class);
                SignUp.this.startActivity(loginIntent);
            }

        });


    }

    public void btnRegistrationUser_Click(View v) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        final String password1  = password.getText().toString().trim();
        final String email1 = email.getText().toString().trim();
        final String contactNumber1  = contactNumber.getText().toString().trim();
        final String name1 = name.getText().toString().trim();

        if(TextUtils.isEmpty(email1)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password1)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(contactNumber1)){
            Toast.makeText(this,"Please enter Contact Number",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(name1)){
            Toast.makeText(this,"Please enter Name",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        (firebaseAuth.createUserWithEmailAndPassword(email1, password1))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            String user_id = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Member").child(user_id);
                            Member storeUser = new Member(name1,email1,contactNumber1);
                            current_user_db.setValue(storeUser);

                            Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(SignUp.this, CustomerLoginActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void addUser(String email){

    }

}
