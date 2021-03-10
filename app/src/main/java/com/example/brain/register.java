package com.example.brain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Button callLogin, register_btn;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout email, username, contact, password, confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

        callLogin = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logo_image);
        contact = findViewById(R.id.contact);
        email = findViewById(R.id.email);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm_password);
        register_btn = findViewById(R.id.register_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = email.getEditText().getText().toString();
                String name = username.getEditText().getText().toString();
                String number = contact.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();
                String con = confirm.getEditText().getText().toString();

                if (!validateName() | !validatePassword() | !validatePhoneNo() | !validateConfirmPassword() | !validateEmail()) {
                    return;
                } else {
                    UserHelperClass helperClass = new UserHelperClass(id, name, number, pass);
                    reference.child(number).setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(register.this, "Registration is successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(register.this, userDashboard.class));
                            } else {
                                Toast.makeText(register.this, "Registration is unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private Boolean validateEmail() {
        String val = email.getEditText().getText().toString();
        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            email.requestFocus();
            return true;
        }
    }

    private Boolean validateName() {
        String val = username.getEditText().getText().toString();
        common objCommon = new common();
        boolean checkUser = objCommon.checkUserExists(true, val);
        if(!checkUser) {
            username.setError("Username already exists");
            return false;
        }
        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            username.requestFocus();
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = contact.getEditText().getText().toString();

        if (val.isEmpty()) {
            contact.setError("Field cannot be empty");
            return false;
        } else {
            contact.setError(null);
            contact.setErrorEnabled(false);
            contact.requestFocus();
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            password.setError("Password is too weak");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            password.requestFocus();
            return true;
        }
    }

    private Boolean validateConfirmPassword() {
        String val = password.getEditText().getText().toString();
        String val1 = confirm.getEditText().getText().toString();

        if (val1.isEmpty()) {
            confirm.setError("Field cannot be empty");
            return false;
        } else if (!val1.matches(val)) {
            confirm.setError("Password does not match");
            return false;
        } else {
            confirm.setError(null);
            confirm.setErrorEnabled(false);
            confirm.requestFocus();
            return true;
        }
    }

    public void triggerBackButton(View v){
        super.onBackPressed();
    }

}
