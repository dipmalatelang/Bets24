package com.app.bet.Login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.bet.BaseActivity;
import com.app.bet.HomeScreen.HomeScreenActivity;
import com.app.bet.R;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyPhoneActivity extends BaseActivity {

    @BindView(R.id.txt_verify_title)
    TextView txtVerifyTitle;
    @BindView(R.id.pinView)
    PinView pinView;
    @BindView(R.id.btn_verify)
    Button btnVerify;
    @BindView(R.id.resendCode)
    TextView resendCode;
    @BindView(R.id.tvcountDown)
    TextView tvcountDown;
    @BindView(R.id.cl_verify)
    ConstraintLayout clVerify;
    private FirebaseAuth mAuth;
    private FirebaseUser fuser;
    private String mVerificationId;
    String TAG = "";


    String mobile, mobileCode;
    int READ_PHONE_STATE_PERMISSION_CODE = 123;


    public String serialNumber, networkCountryIso, simCountryIso, meid, imei, networkOperatorName, simOperatorName, line1Number, model,
            device, brand_Id, manufacturer, product, type, host, hardware, base_OS, codename, release, security_Patch, incrimental;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        ButterKnife.bind(this);

        retrieveDeviceData();
        //initializing objects
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        mobileCode = intent.getStringExtra("mobileCode");
        sendVerificationCode(mobile, mobileCode);
        startCounter();


    }

    @OnClick({R.id.btn_verify, R.id.resendCode, R.id.tvcountDown})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_verify:
                String code = pinView.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    pinView.setError("Enter valid code");
                    pinView.requestFocus();
                    return;
                }



                //verifying the code entered manually
                verifyVerificationCode(code);
                break;

            case R.id.resendCode:
                sendVerificationCode(mobile, mobileCode);
                startCounter();
                break;

            case R.id.tvcountDown:
                break;
        }
    }

    private void startCounter() {
        new CountDownTimer(21000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvcountDown.setVisibility(View.VISIBLE);
                resendCode.setVisibility(View.GONE);
                tvcountDown.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tvcountDown.setVisibility(View.GONE);
                resendCode.setVisibility(View.VISIBLE);
            }

        }.start();
    }

    private void sendVerificationCode(String mobile, String mobileCode) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileCode + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                pinView.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void verifyVerificationCode(String code) {
        //creating the credential
//            showProgressDialog();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
                retrieveUserDetail(currentUser);

        }
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        if (fuser != null) {
            Log.i(TAG, "signInWithPhoneAuthCredential: if");
            fuser.linkWithCredential(credential)
                    .addOnCompleteListener(VerifyPhoneActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //verification successful we will start the profile activity
                                FirebaseUser user = task.getResult().getUser();

                                setPhoneNumber(fuser.getUid(), mobile, mobileCode);
                                updateUI(user);

                            } else {
                                //verification unsuccessful.. display an error message
                                Log.i(TAG, "signInWithPhoneAuthCredential: if else");
                                String message = "Somthing is wrong, we will fix it soon...";

                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    message = "Invalid code entered...";
                                }
//                                    snackBar(clVerify, task.getException().getMessage());
                                Log.i(TAG, "onComplete: " + fuser.getUid());
                                updateUI(null);

                            }
                        }
                    });
        } else {
            Log.i(TAG, "signInWithPhoneAuthCredential: else");
            phoneLogin(credential);
        }

    }

    private void phoneLogin(PhoneAuthCredential credential) {
        Log.i(TAG, "phoneLogin: ");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhoneActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            UserData userData = new UserData(serialNumber, networkCountryIso, simCountryIso, meid, imei, networkOperatorName, simOperatorName, line1Number, model, device, brand_Id, manufacturer, product, type, host, hardware, base_OS, codename, release, security_Patch, incrimental);
                            UsersDataInstance.child(firebaseUser.getUid()).setValue(userData);

                            startActivity(new Intent(VerifyPhoneActivity.this,HomeScreenActivity.class));
                            FirebaseUser user = task.getResult().getUser();
//                                snackBar(clVerify,user.getPhoneNumber()+" "+user.getUid());
                                dismissProgressDialog();

                            UsersInstance.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.exists()) {

                                        updateUI(user);
                                    } else {
                                        updateUI(user);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {


                                }
                            });


                        } else {
                            //verification unsuccessful.. display an error message
                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                                snackBar(clVerify, task.getException().getMessage());
                                dismissProgressDialog();
                            updateUI(null);

                        }
                    }
                });
    }


    public void setPhoneNumber(String id, String mobile, String mobileCode) {
        UsersInstance.child(id).child("phone").setValue(mobile);
        UsersInstance.child(id).child("mobileCode").setValue(mobileCode);
    }


    @SuppressLint("MissingPermission")
    private void retrieveDeviceData() {
        if (isPermissionGranted(this, Manifest.permission.READ_PHONE_STATE)) {

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Log.i(TAG, "retrieveData: getSimSerialNumber " + telephonyManager.getSimSerialNumber());
            Log.i(TAG, "retrieveData: getNetworkCountryIso " + telephonyManager.getNetworkCountryIso());
            Log.i(TAG, "retrieveData: getSimCountryIso " + telephonyManager.getSimCountryIso());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "retrieveData: getMeid "+telephonyManager.getMeid());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "retrieveData: getImei "+telephonyManager.getImei());
            }

            Log.i(TAG, "retrieveData: isNetworkRoaming " + telephonyManager.isNetworkRoaming());
            serialNumber = telephonyManager.getSimSerialNumber();
            networkCountryIso = telephonyManager.getNetworkCountryIso();
            simCountryIso = telephonyManager.getSimCountryIso();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                meid = telephonyManager.getMeid();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imei =telephonyManager.getImei();
            }

            networkOperatorName =telephonyManager.getNetworkOperatorName();
            simOperatorName = telephonyManager.getSimOperatorName();
            line1Number =telephonyManager.getLine1Number();
            model = Build.MODEL;
            device =Build.DEVICE;
            brand_Id = Build.ID;
            manufacturer =Build.MANUFACTURER;
            product = Build.PRODUCT;
            type = Build.TYPE;
            host =Build.HOST;
            hardware = Build.HARDWARE;
            base_OS =Build.VERSION.BASE_OS;
            codename = Build.VERSION.CODENAME;
            release =Build.VERSION.RELEASE;
            security_Patch = Build.VERSION.SECURITY_PATCH;
            incrimental = Build.VERSION.INCREMENTAL;

        } else {
            requestForPermission(this, Manifest.permission.READ_PHONE_STATE, READ_PHONE_STATE_PERMISSION_CODE);
        }
    }

    //BaseActivity

    public boolean isPermissionGranted(Context context, String strValue) {

        if (ContextCompat.checkSelfPermission(context, strValue) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void requestForPermission(Activity activity, String strValue, int permissionCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, strValue)) {
            permissionDialog(activity, strValue, permissionCode);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{strValue}, permissionCode);
        }
    }

    private void permissionDialog(final Activity activity, final String strValue, final int permissionCode) {
        new AlertDialog.Builder(activity).setTitle("Permission")
                .setMessage("This permission are needed")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(activity, new String[]{strValue}, permissionCode);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == READ_PHONE_STATE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                snackBar(clVerify, "Permission Granted");
                retrieveDeviceData();
            } else {
                snackBar(clVerify, "Permission Denied");
            }
        }

    }

}

