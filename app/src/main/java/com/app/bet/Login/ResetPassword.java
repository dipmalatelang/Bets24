package com.app.bet.Login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.app.bet.BaseActivity;
import com.app.bet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPassword extends BaseActivity {

    @BindView(R.id.send_email)
    EditText sendEmail;
    @BindView(R.id.btn_reset)
    Button btnReset;
    @BindView(R.id.LinearLayout_ResetPassword)
    LinearLayout LinearLayoutResetPassword;

    FirebaseAuth firebaseAuth;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);

        value = Objects.requireNonNull(getIntent().getExtras()).getString("nextActivity");
        firebaseAuth = FirebaseAuth.getInstance();


    }

    @OnClick(R.id.btn_reset)
    public void onViewClicked() {
        showProgressDialog();
        String email = sendEmail.getText().toString();

        if (email.equals("")) {
            dismissProgressDialog();
            snackBar(LinearLayoutResetPassword, "All fileds are required!");
        } else {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        dismissProgressDialog();
                        snackBar(LinearLayoutResetPassword, "Please check you Email");
                        Intent resetIntent = new Intent(ResetPassword.this, LoginActivity.class);
                        resetIntent.putExtra("nextActivity", value);
                        startActivity(resetIntent);
                    } else {
                        dismissProgressDialog();
                        String error = Objects.requireNonNull(task.getException()).getMessage();
                        snackBar(LinearLayoutResetPassword, error);

                    }
                }
            });
        }


    }
}

