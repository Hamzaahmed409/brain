package com.example.brain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class forget extends AppCompatActivity {
    FirebaseDatabase rootNode;
    Button reset_btn;
    ImageView image;
    TextView logoText, sloganText, txtMessage;
    TextInputLayout username, contact, password, confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_name);
        sloganText = findViewById(R.id.slogan_name);
        username = findViewById(R.id.user_name);
        contact = findViewById(R.id.user_contact);
        password = findViewById(R.id.user_password);
        confirm = findViewById(R.id.user_confirm_password);
        reset_btn = findViewById(R.id.login_btn);
        txtMessage = findViewById(R.id.txtMessage);

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateName() | !validatePassword() | !validateConfirmPassword()) {
                    return;
                } else {
                    reset();

                }
            }
        });
    }

    String message = "";
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    String enteredContact = "";
    private void reset() {
        final String enteredUsername = username.getEditText().getText().toString().trim();
        enteredContact = contact.getEditText().getText().toString().trim();
        final String enteredPassword = password.getEditText().getText().toString().trim();
        final String enteredConfirmPassword = confirm.getEditText().getText().toString().trim();

        Query checkUser = reference.orderByChild("username").equalTo(enteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    username.setError(null);
                    username.setErrorEnabled(false);
                    String contactFromDB = null;
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        contactFromDB = childSnapshot.child("contact").getValue(String.class);
                    }
                    if (contactFromDB.equals(enteredContact)) {
                        reference.child(enteredContact).child("password").setValue(enteredPassword);
                        txtMessage.setText("Successfully updated");
                        triggerBackButton();
                    }
                    else {
                        txtMessage.setText("Contact number is incorrect");
                    }
                } else {
                    txtMessage.setText("Something went wrong");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void triggerBackButton(){
        super.onBackPressed();
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
    public void ClearAllFields() {
        common objCommon = new common();
        objCommon.ClearField(username.getEditText());
        objCommon.ClearField(password.getEditText());
    }
}
