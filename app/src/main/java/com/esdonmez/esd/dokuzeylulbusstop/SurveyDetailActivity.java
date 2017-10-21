package com.esdonmez.esd.dokuzeylulbusstop;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.esdonmez.esd.dokuzeylulbusstop.Models.SurveyModel;

public class SurveyDetailActivity extends AppCompatActivity {

    private TextView busStopText, likeDegreeText, adviceText, emailText;
    private Button okButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_detail);

        busStopText = (TextView)findViewById(R.id.busStopText);
        likeDegreeText = (TextView)findViewById(R.id.likeDegreeText);
        adviceText = (TextView)findViewById(R.id.adviceText);
        emailText = (TextView)findViewById(R.id.emailText);
        okButton = (Button) findViewById(R.id.okButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        Intent intent = getIntent();
        SurveyModel model = (SurveyModel) intent.getSerializableExtra("survey");

        busStopText.setText(model.getBusStop());
        likeDegreeText.setText(model.getLikeDegree());
        adviceText.setText(model.getAdvice());
        emailText.setText(model.getEmail());

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result","Ankete katıldığınız için teşekkürler.");
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }
}
