package com.app.bet.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.app.bet.BaseActivity;
import com.app.bet.HomeScreen.HomeScreenActivity;
import com.app.bet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Registration extends BaseActivity implements View.OnKeyListener {

    @BindView(R.id.tv_login_text)
    TextView tvLoginText;
    @BindView(R.id.signup_name)
    EditText signupName;
    @BindView(R.id.textInput_name)
    TextInputLayout textInputName;
    @BindView(R.id.signup_email)
    EditText signupEmail;
    @BindView(R.id.textInput_email)
    TextInputLayout textInputEmail;
    @BindView(R.id.signup_password)
    EditText signupPassword;
    @BindView(R.id.textInput_password)
    TextInputLayout textInputPassword;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.constrainlayout)
    ConstraintLayout constrainlayout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();


        signupPassword.setOnTouchListener((view, motionEvent) -> showoOrHidePassword(motionEvent, signupPassword));
        signupPassword.setOnKeyListener(this);
    }

    @OnClick({R.id.signup_name, R.id.signup_email, R.id.signup_password, R.id.btn_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signup_name:
                break;
            case R.id.signup_email:
                break;
            case R.id.signup_password:
                break;
            case R.id.btn_signup:
                String txt_username = signupName.getText().toString();
                String txt_email = signupEmail.getText().toString();
                String txt_password = signupPassword.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    snackBar(constrainlayout, "All fileds are required");

                } else if (!Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()) {
                    snackBar(constrainlayout, "please enter valid email address");
                } else if (txt_password.length() < 6) {

                    snackBar(constrainlayout, "password must be at least 6 characters");
                } else {

                    registration(txt_username, txt_email, txt_password);
                    snackBar(constrainlayout, "Register Successfully..!");
                }

                break;
        }
    }

    private void registration(final String username, final String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            snackBar(constrainlayout, "Success");

                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();
                            User user =new User(firebaseUser.getUid(),username,email,username.toLowerCase(),"","","");
                            UsersInstance.child(userid).setValue(user);
                            startActivity(new Intent(Registration.this, HomeScreenActivity.class));

                        } else {

                            snackBar(constrainlayout, Objects.requireNonNull(task.getException()).getMessage());
                            dismissProgressDialog();
                            updateUI(null);
                        }

                    }

                });
    }
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            retrieveUserDetail(currentUser);

        }
    }




    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }
}
