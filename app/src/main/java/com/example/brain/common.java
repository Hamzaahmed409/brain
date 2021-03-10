package com.example.brain;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class common {
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    boolean isExist = false;

    public boolean checkUserExists(boolean isUserChanged, String etUsername) {
        reference = FirebaseDatabase.getInstance().getReference("users");
        if (!isUserChanged)
            return isExist;
        Query checkUser = reference.orderByChild("username").equalTo(etUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    isExist = true;
                else
                    isExist = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        android.util.Log.i("data", "User Status: " + isExist);
        return isExist;
    }

    public void ClearField(TextInputEditText editText) {
        editText.setText("");
    }

    public void ClearField(EditText editText) {
        editText.setText("");
    }




}
