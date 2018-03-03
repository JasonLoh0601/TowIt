package com.world.jasonloh95.towit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class DriverLoginActivity extends AppCompatActivity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener{

        EditText txtEmailLogin, password;
        Button login;
private FirebaseAuth firebaseAuth;
        String email;
        DatabaseReference mDriverDatabase;
private CheckBox rem_userpass;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        String email2;
private static final String PREF_NAME = "prefs";
private static final String KEY_REMEMBER = "remember";
private static final String KEY_USERNAME = "username";
private static final String KEY_PASS = "password";

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        txtEmailLogin = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.email_sign_in_button);
        firebaseAuth = FirebaseAuth.getInstance();
        rem_userpass = (CheckBox)findViewById(R.id.checkBox);

        email = txtEmailLogin.getText().toString().trim();

        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
        rem_userpass.setChecked(true);
        else
        rem_userpass.setChecked(false);

        txtEmailLogin.setText(sharedPreferences.getString(KEY_USERNAME,""));
        password.setText(sharedPreferences.getString(KEY_PASS,""));


        login.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        btnUserLogin_Click(v);
        }
        });

        txtEmailLogin.addTextChangedListener(this);
        password.addTextChangedListener(this);
        rem_userpass.setOnCheckedChangeListener(this);

        }


@Override
public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

@Override
public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
        }

@Override
public void afterTextChanged(Editable editable) {

        }

@Override
public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
        }

private void managePrefs(){
        if(rem_userpass.isChecked()){
        editor.putString(KEY_USERNAME, txtEmailLogin.getText().toString().trim());
        editor.putString(KEY_PASS, password.getText().toString().trim());
        editor.putBoolean(KEY_REMEMBER, true);
        editor.apply();
        }else{
        editor.putBoolean(KEY_REMEMBER, false);
        editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
        editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
        editor.apply();
        }
        }


// LOGIN
public void btnUserLogin_Click(View v) {

final String password1  = password.getText().toString().trim();
final String email1 = txtEmailLogin.getText().toString().trim();

        if(TextUtils.isEmpty(email1)){
        Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
        return;
        }

        if(TextUtils.isEmpty(password1)){
        Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
        return;
        }

final ProgressDialog progressDialog = ProgressDialog.show(DriverLoginActivity.this, "Please wait...", "Proccessing...", true);

        (firebaseAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(), password.getText().toString()))
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
@Override
public void onComplete(@NonNull Task<AuthResult> task) {
        progressDialog.dismiss();
        if (task.isSuccessful()) {

                        Toast.makeText(DriverLoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(DriverLoginActivity.this, driverMap.class);
                        i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                        startActivity(i);
        } else {
        Log.e("ERROR", task.getException().toString());
        Toast.makeText(DriverLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

        }
        }
        });
        }
        }
