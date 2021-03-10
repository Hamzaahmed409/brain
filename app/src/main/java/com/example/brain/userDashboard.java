package com.example.brain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class userDashboard extends AppCompatActivity {
    Button update, location, logout;
    boolean _isExists = false;
    String strContact = null;
    String strUsername = null;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        location = findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(userDashboard.this, MapsActivity.class);
                in.putExtra("contact",strContact);
                in.putExtra("username",strUsername);
                startActivity(in);
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("users");

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        TextView txtUsername = findViewById(R.id.username);
        TextView txtEmail = findViewById(R.id.ID);
        TextView edID = findViewById(R.id.ID);
        TextInputEditText etFullUserName = findViewById(R.id.full_user_name);
        TextInputEditText etEmail = findViewById(R.id.email);
        TextInputEditText etFullPassword = findViewById(R.id.full_password);

        if (b != null) {
            strUsername = (String) b.get("username");
            strContact = (String) b.get("contact");
            String password = (String) b.get("password");
            String email = (String) b.get("email");

            etFullUserName.setText(strUsername);
            etFullPassword.setText(password);
            etEmail.setText(email);
            txtUsername.setText(strUsername);
            txtEmail.setText(email);
        }
    }

    private void updateMydata() {
        TextInputEditText etFullEmail = findViewById(R.id.email);
        TextInputEditText etFullUserName = findViewById(R.id.full_user_name);
        TextInputEditText etFullPassword = findViewById(R.id.full_password);

        TextView txtUsername = findViewById(R.id.username);
        TextView txtEmail = findViewById(R.id.ID);
        String usernameFromET = txtUsername.getText().toString().trim();
        boolean isUserChanged = strUsername.equals(usernameFromET);
        common objCommon = new common();
        if (!objCommon.checkUserExists(!isUserChanged, txtUsername.getText().toString().trim())) {
            String etUsername = etFullUserName.getText().toString();
            String etEmail = etFullEmail.getText().toString();
            String etPassword = etFullPassword.getText().toString();
            txtUsername.setText(etUsername);
            reference.child(strContact).child("username").setValue(etUsername);
            reference.child(strContact).child("email").setValue(etEmail);
            reference.child(strContact).child("password").setValue(etPassword);
            txtUsername.setText(etUsername);
            txtEmail.setText(etEmail);
            Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(View view) {
        updateMydata();
    }

    public void Logout(View view) {
        finish();
    }

}
