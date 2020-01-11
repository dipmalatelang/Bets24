package com.app.bet.HomeScreen.More;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.bet.HomeScreen.HomeScreenActivity;
import com.app.bet.R;
import com.app.bet.Util.SendMail;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText Et_Name,Et_Email,ET_Feedback;
    Button Btn_Submit;
    TextView TvStatus;
    Toolbar Tb_App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Et_Name = findViewById(R.id.Et_Name);
        Et_Email = findViewById(R.id.Et_Email);
        ET_Feedback = findViewById(R.id.ET_Feedback);
        Btn_Submit = findViewById(R.id.Btn_Submit);
        TvStatus = findViewById(R.id.TvStatus);
        Tb_App = findViewById(R.id.Tb_App);
        setSupportActionBar(Tb_App);
        Tb_App.setTitle("Contact Us");

        Btn_Submit.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:
                startActivity(new Intent(this, HomeScreenActivity.class));
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(Et_Name.getText())){
            TvStatus.setVisibility(View.VISIBLE);
            TvStatus.setText("******** Name Required ********");
        } else if (TextUtils.isEmpty(Et_Email.getText())){
            TvStatus.setVisibility(View.VISIBLE);
            TvStatus.setText("******** Email Required ********");
        } else if (TextUtils.isEmpty(ET_Feedback.getText())){
            TvStatus.setVisibility(View.VISIBLE);
            TvStatus.setText("******** Message Required ********");
        }else {
            TvStatus.setVisibility(View.GONE);
            String Msg = "Hi Team,\n\n" +
                    "My Name is "+Et_Name.getText().toString()+"\n\n" +
                    "My Email is "+Et_Email.getText().toString()+"\n\n"+
                    "Message : "+ET_Feedback.getText().toString();
            sendEmail(Msg);
        }
    }

    private void sendEmail(String msg) {
        String email = "rahul.awffl@gmail.com";
        String subject = "Contact Android SkyLiveLine";

        SendMail sm = new SendMail(this, email, subject, msg);

        sm.execute();
    }
}
