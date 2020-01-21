package com.app.bet;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.app.bet.HomeScreen.HomeScreenActivity;
import com.app.bet.Login.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public abstract class BaseActivity extends AppCompatActivity {

    public String TAG = "Activity";


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public DatabaseReference UsersInstance = FirebaseDatabase.getInstance().getReference("Users");
    public DatabaseReference UsersDataInstance = FirebaseDatabase.getInstance().getReference("UserData");



    public void snackBar(View constrainlayout, String s) {
        Snackbar snackbar = Snackbar.make(constrainlayout, s, Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        snackbar.show();
    }
    public void showProgressDialog() {
        if (!isFinishing()) {
            ProgressActivity.showDialog(this);
        }
    }
    public void dismissProgressDialog() {
        if (!isFinishing()) {
            ProgressActivity.dismissDialog();
        }
    }

    public void retrieveUserDetail(FirebaseUser fUser) {
        UsersInstance.child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user!=null)
                saveDetailsLater(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void saveDetailsLater(User user) {
        startActivity(new Intent(this, HomeScreenActivity.class));
        finish();

        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("Id", user.getId());
        editor.putString("Name",  user.getName());
        editor.putString("Email",user.getEmail());
        editor.putString("Phone",user.getPhone());
        editor.putString("gmail",user.getGmail());
        editor.putString("mobileCode",user.getMobileCode());
        editor.apply();


    }

    public void appDetails(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getAppDetails(String key) {
        String name = "";
        SharedPreferences sharedPreferences = getSharedPreferences("AppDetails", Context.MODE_PRIVATE);
        if (sharedPreferences.contains(key)) {
            name = sharedPreferences.getString(key, "");
        }
        return name;
    }
    public void saveLoginDetails(String email, String password) {
        sharedPreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.putString("Password", password);

        editor.commit();
    }

    public boolean showoOrHidePassword(MotionEvent event, EditText signupPassword) {
        final int DRAWABLE_RIGHT = 2;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (signupPassword.getRight() - signupPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                if (!signupPassword.getTransformationMethod().toString().contains("Password")) {
                    signupPassword.setTransformationMethod(new PasswordTransformationMethod());
                    signupPassword.setSelection(signupPassword.getText().length());
                    signupPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_white_24dp, 0, R.drawable.ic_action_eye_off, 0);

                } else {
                    signupPassword.setTransformationMethod(new HideReturnsTransformationMethod());
                    signupPassword.setSelection(signupPassword.getText().length());
                    signupPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_white_24dp, 0, R.drawable.ic_action_eye, 0);
                }
                return true;
            }
        }
        return false;
    }


}
