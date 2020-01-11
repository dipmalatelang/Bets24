package com.app.bet.HomeScreen.More;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.bet.HomeScreen.More.KhaiLagai.KhaiLagaiActivity;
import com.app.bet.R;

public class MoreFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        CardView CvKhaLagai, CvPrivacyPolicy, CvTermsCondition, CvSendFeedback, CvShareApp, CvRateUs, CvContactUs, CvHelp, CvAbout, CvLogout;

        CvKhaLagai = view.findViewById(R.id.CvKhaLagai);
        CvPrivacyPolicy = view.findViewById(R.id.CvPrivacyPolicy);
        CvTermsCondition = view.findViewById(R.id.CvTermsCondition);
        CvSendFeedback = view.findViewById(R.id.CvSendFeedback);
        CvShareApp = view.findViewById(R.id.CvShareApp);
        CvRateUs = view.findViewById(R.id.CvRateUs);
        CvContactUs = view.findViewById(R.id.CvContactUs);
        CvHelp = view.findViewById(R.id.CvHelp);
        CvAbout = view.findViewById(R.id.CvAbout);
        CvLogout = view.findViewById(R.id.CvLogout);

        CvKhaLagai.setOnClickListener(this);
        CvPrivacyPolicy.setOnClickListener(this);
        CvTermsCondition.setOnClickListener(this);
        CvSendFeedback.setOnClickListener(this);
        CvShareApp.setOnClickListener(this);
        CvRateUs.setOnClickListener(this);
        CvContactUs.setOnClickListener(this);
        CvHelp.setOnClickListener(this);
        CvAbout.setOnClickListener(this);
        CvLogout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.CvKhaLagai:
                intent = new Intent(getContext(), KhaiLagaiActivity.class);
                startActivity(intent);
                break;
            case R.id.CvPrivacyPolicy:
                intent = new Intent(getContext(),PrivacyTermsActivity.class);
                intent.putExtra("url","http://royalbook.org/Privacy_Policy.html");
                intent.putExtra("title", "Privacy Policy");
                startActivity(intent);
                break;
            case R.id.CvTermsCondition:
                intent = new Intent(getContext(),PrivacyTermsActivity.class);
                intent.putExtra("url","http://royalbook.org/terms_conditions.html");
                intent.putExtra("title", "Terms and Conditions");
                startActivity(intent);
                break;
            case R.id.CvSendFeedback:
                intent = new Intent(getContext(),SendFeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.CvShareApp:
                intent =  new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareapp = "http:sky live line ";
                intent.putExtra(Intent.EXTRA_TEXT,shareapp);
                startActivity(Intent.createChooser(intent,"share app"));
                break;
            case R.id.CvRateUs:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=in.myinnos.AppManager&hl=en_IN")));
                break;
            case R.id.CvContactUs:
                intent = new Intent(getContext(),ContactUsActivity.class);
                startActivity(intent);
                break;
            case R.id.CvHelp:
                intent = new Intent(getContext(),HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.CvAbout:
                intent  = new Intent(getContext(),AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.CvLogout:break;
        }
    }
}
