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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.bet.HomeScreen.HomeScreenActivity;
import com.app.bet.R;
import com.app.bet.Util.SendMail;

public class SendFeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    EditText Et_Name,Et_Email,Et_Feedback;
    Button Btn_Submit;
    RadioGroup Rg_Suggest;
    RatingBar Rb_Stars;
    TextView TvStatus;
    Toolbar Tb_App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);
        Tb_App = findViewById(R.id.Tb_App);
        setSupportActionBar(Tb_App);
        Tb_App.setTitle("Send Feedback");

        Et_Name = findViewById(R.id.Et_Name);
        Et_Email = findViewById(R.id.Et_Email);
        Et_Feedback = findViewById(R.id.Et_Feedback);
        Btn_Submit = findViewById(R.id.Btn_Submit);
        Rg_Suggest = findViewById(R.id.Rg_Suggest);
        Rb_Stars = findViewById(R.id.Rb_Stars);
        TvStatus = findViewById(R.id.TvStatus);

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
        String Name, Email, Rating, Suggest, Feeds;
        if (TextUtils.isEmpty(Et_Name.getText())){
            TvStatus.setVisibility(View.VISIBLE);
            TvStatus.setText("******** Name Required ********");
        } else if (TextUtils.isEmpty(Et_Email.getText())){
            TvStatus.setVisibility(View.VISIBLE);
            TvStatus.setText("******** E-Mail Required ********");
        }else if (Rb_Stars.getRating() == 0.0){
            TvStatus.setVisibility(View.VISIBLE);
            TvStatus.setText("******** Rating Required ********");
        }else {
            int id = Rg_Suggest.getCheckedRadioButtonId();
            RadioButton rb = findViewById(id);
            if (TextUtils.isEmpty(Et_Feedback.getText())){
                Feeds = "NA";
            }else {
                Feeds = Et_Feedback.getText().toString();
            }

            String Message;

            if (rb.getText().toString().equalsIgnoreCase("Yes")){
                Message = "Hi Team,\n\nMy Name is "+Et_Name.getText()+" I am an Android user of your SkyLiveLine Application,\n\n" +
                        "I would like to Rate this application with "+Rb_Stars.getRating()+" Stars out of 5.\n\n"+
                        "Yes,I would recommend this App to other users.\n\n"+"Feedback : "+Feeds+"\n\n"+"Thank You,\n"+Et_Name.getText();
            }else {
                Message = "Hi Team,\n\nMy Name is "+Et_Name.getText()+" I am an Android user of your SkyLiveLine Application,\n\n" +
                        "I would like to Rate this application with "+Rb_Stars.getRating()+" Stars out of 5.\n\n"+
                        "No, I would not recommend this App to other users.\n\n"+"Feedback : "+Feeds+"\n\n"+"Thank You,\n"+Et_Name.getText();
            }

            sendEmail(Message);
        }
    }

    private void sendEmail(String msg) {
        String email = "rahul.awffl@gmail.com";
        String subject = "Feedback Android SkyLiveLine";

        SendMail sm = new SendMail(this, email, subject, msg);

        sm.execute();
    }
}
