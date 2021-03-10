package com.example.brain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Button callSignUp, login_btn, forget;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        hideProgress();
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        //Hooks
        callSignUp = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forget = findViewById(R.id.forget);
        login_btn = findViewById(R.id.login_btn);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(login.this, forget.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                pairs[3] = new Pair<View, String>(username, "username_tran");
                pairs[4] = new Pair<View, String>(password, "password_tran");
                pairs[5] = new Pair<View, String>(login_btn, "button_tran");
                pairs[6] = new Pair<View, String>(callSignUp, "login_signp_tran");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(login.this, pairs);
                    startActivity(in, options.toBundle());

                }
            }
        });
        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
                pairs[3] = new Pair<View, String>(username, "username_tran");
                pairs[4] = new Pair<View, String>(password, "password_tran");
                pairs[5] = new Pair<View, String>(login_btn, "button_tran");
                pairs[6] = new Pair<View, String>(callSignUp, "login_signp_tran");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(login.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String enteredUsername = username.getEditText().getText().toString().trim();
                final String enteredPassword = password.getEditText().getText().toString().trim();


                if (!validateName() | !validatePassword()) {
                    return;
                } else {
                    isUSer();

                }
            }
        });
    }

    private void showProgress() {
        ProgressBar prog = findViewById(R.id.loader);
        Button login = findViewById(R.id.login_btn);
        login.setVisibility(View.GONE);
        prog.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        ProgressBar prog = findViewById(R.id.loader);
        Button login = findViewById(R.id.login_btn);
        prog.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);
    }

    private Boolean validateName() {
        String val = username.getEditText().getText().toString();

        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private void isUSer() {
        showProgress();
        final String enteredUsername = username.getEditText().getText().toString().trim();
        final String enteredPassword = password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("username").equalTo(enteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = null;
                    String userIDFromDB = null;
                    String usernameFromDB = null;
                    String contactFromDB = null;
                    String emailFromDB = null;

                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        passwordFromDB = childSnapshot.child("password").getValue(String.class);
                        userIDFromDB = childSnapshot.child("userID").getValue(String.class);
                        usernameFromDB = childSnapshot.child("username").getValue(String.class);
                        contactFromDB = childSnapshot.child("contact").getValue(String.class);
                        emailFromDB = childSnapshot.child("email").getValue(String.class);
                    }

                    android.util.Log.i("data", "Pass is: " + passwordFromDB);
                    if (passwordFromDB.equals(enteredPassword)) {
                        username.setError(null);
                        username.setErrorEnabled(false);
                        ClearAllFields();
                        hideProgress();
                        Toast.makeText(login.this, "Login successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), userDashboard.class);
                        intent.putExtra("userID", userIDFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("contact", contactFromDB);
                        intent.putExtra("password", passwordFromDB);
                        intent.putExtra("email", emailFromDB);
                        startActivity(intent);
                    } else {
                        hideProgress();
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }


                } else {
                    username.setError("No such User exists");
                    username.requestFocus();
                    hideProgress();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void ClearAllFields() {
        common objCommon = new common();
        objCommon.ClearField(username.getEditText());
        objCommon.ClearField(password.getEditText());
    }


}
