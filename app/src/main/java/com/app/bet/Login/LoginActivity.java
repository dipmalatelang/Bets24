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
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.bet.BaseActivity;
import com.app.bet.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnTouchListener, View.OnKeyListener {


    @BindView(R.id.tv_login_text)
    TextView tvLoginText;
    @BindView(R.id.txt_email)
    EditText txtEmail;
    @BindView(R.id.textInput_email)
    TextInputLayout textInputEmail;
    @BindView(R.id.txt_password)
    EditText txtPassword;
    @BindView(R.id.textInput_password)
    TextInputLayout textInputPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_phonelogin)
    TextView btnPhonelogin;
    @BindView(R.id.link_forgetPassword)
    TextView linkForgetPassword;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.constrainlayout)
    ConstraintLayout constrainlayout;
    @BindView(R.id.btn_gmaillogin)
    TextView btnGmaillogin;
    @BindView(R.id.txt_singup)
    TextView txtSingup;
    @BindView(R.id.sign_In_Button)
    SignInButton signInButton;
    String TAG = " ";
    GoogleApiClient mGoogleApiClient;
    List<UserData> userData;
    String value;
    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.tv_rememberme)
    AppCompatCheckBox tvRememberme;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    int READ_PHONE_STATE_PERMISSION_CODE = 123;


    public String serialNumber, networkCountryIso, simCountryIso, meid, imei, networkOperatorName, simOperatorName, line1Number, model,
            device, brand_Id, manufacturer, product, type, host, hardware, base_OS, codename, release, security_Patch, incrimental;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        TAG = "RetrieveActivity";


        //gmail login....
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        txtPassword.setOnTouchListener((view, motionEvent) -> showoOrHidePassword(motionEvent, txtPassword));
        txtPassword.setOnKeyListener(this);


    }


    @OnClick({R.id.btn_login, R.id.btn_phonelogin, R.id.link_forgetPassword, R.id.tv_register, R.id.btn_gmaillogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_gmaillogin:
                signIn();
                retrieveDeviceData();
                break;

            case R.id.btn_login:
                showProgressDialog();
                String txt_email = txtEmail.getText().toString().trim();
                String txt_password = txtPassword.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    dismissProgressDialog();
                    snackBar(constrainlayout, "All fileds are required !");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(txt_email).matches()) {
                    dismissProgressDialog();
                    snackBar(constrainlayout, "please enter valid email address");
                } else {
                    Log.d(TAG, "onComplete1: " + txt_email + " " + txt_password);
                    emailLogin(txt_email, txt_password);
                }
                retrieveDeviceData();

                break;

            case R.id.btn_phonelogin:
                startActivity(new Intent(this, PhoneActivity.class));
                break;

            case R.id.link_forgetPassword:
                Intent resetIntent = new Intent(LoginActivity.this, ResetPassword.class);
                resetIntent.putExtra("nextActivity", value);
                startActivity(resetIntent);
                break;

            case R.id.tv_register:
                Intent intent_login = new Intent(LoginActivity.this, Registration.class);
                startActivity(intent_login);
                break;

        }
    }

    // for email_login
    private void emailLogin(String txt_email, String txt_password) {
        mAuth.signInWithEmailAndPassword(txt_email, txt_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dismissProgressDialog();
                            updateUI(mAuth.getCurrentUser());

                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            UserData userData = new UserData(serialNumber, networkCountryIso, simCountryIso, meid, imei, networkOperatorName, simOperatorName, line1Number, model, device, brand_Id, manufacturer, product, type, host, hardware, base_OS, codename, release, security_Patch, incrimental);
                            UsersDataInstance.child(firebaseUser.getUid()).setValue(userData);


                            saveLoginDetails(txt_email, txt_password);

                        } else {
                            snackBar(constrainlayout, Objects.requireNonNull(task.getException()).getMessage());
                            dismissProgressDialog();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            retrieveUserDetail(currentUser);
        }
    }

    // for gmail_login
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    // gmail
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        userData = new ArrayList<>();


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser fuser = mAuth.getCurrentUser();
                            // login firebaseAuthWithGoogle data
                            User user = new User(fuser.getUid(), fuser.getDisplayName(), fuser.getEmail().toLowerCase(), fuser.getEmail(), fuser.getDisplayName().toLowerCase(), fuser.getPhoneNumber(), fuser.getProviderId());
                            UsersInstance.child(fuser.getUid()).setValue(user);
                            // login userdata
                            UserData userData = new UserData(serialNumber, networkCountryIso, simCountryIso, meid, imei, networkOperatorName, simOperatorName, line1Number, model, device, brand_Id, manufacturer, product, type, host, hardware, base_OS, codename, release, security_Patch, incrimental);
                            UsersDataInstance.child(fuser.getUid()).setValue(userData);

                            Log.d(TAG, "onComplete: mobile data " + userData.getDevice());

                            updateUI(fuser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                        // ...
                    }
                });
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Log.d(TAG, "onResult: signout");
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: " + connectionResult);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseAuth.getInstance().signOut();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    // retrieveDeviceData userdata
    @SuppressLint("MissingPermission")
    private void retrieveDeviceData() {
        if (isPermissionGranted(this, Manifest.permission.READ_PHONE_STATE)) {

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "retrieveData: getMeid " + telephonyManager.getMeid());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "retrieveData: getImei " + telephonyManager.getImei());
            }

            Log.i(TAG, "retrieveData: isNetworkRoaming " + telephonyManager.isNetworkRoaming());
            serialNumber = telephonyManager.getSimSerialNumber();
            networkCountryIso = telephonyManager.getNetworkCountryIso();
            simCountryIso = telephonyManager.getSimCountryIso();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                meid = telephonyManager.getMeid();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imei = telephonyManager.getImei();
            }

            networkOperatorName = telephonyManager.getNetworkOperatorName();
            simOperatorName = telephonyManager.getSimOperatorName();
            line1Number = telephonyManager.getLine1Number();
            model = Build.MODEL;
            device = Build.DEVICE;
            brand_Id = Build.ID;
            manufacturer = Build.MANUFACTURER;
            product = Build.PRODUCT;
            type = Build.TYPE;
            host = Build.HOST;
            hardware = Build.HARDWARE;
            base_OS = Build.VERSION.BASE_OS;
            codename = Build.VERSION.CODENAME;
            release = Build.VERSION.RELEASE;
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

                snackBar(constrainlayout, "Permission Granted");
                retrieveDeviceData();
            } else {
                snackBar(constrainlayout, "Permission Denied");
            }
        }

    }


    @OnClick(R.id.tv_rememberme)
    public void onViewClicked() {


    }
}
