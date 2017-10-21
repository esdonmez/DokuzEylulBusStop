package com.esdonmez.esd.dokuzeylulbusstop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.esdonmez.esd.dokuzeylulbusstop.Models.SurveyModel;

public class MainActivity extends AppCompatActivity {

    Spinner busStopSpinner;
    RadioGroup likeRadioGroup;
    RadioButton likeRadioButton;
    EditText adviceEditText, emailEditText;
    String advice, email, spinnerValue;
    Button okButton;
    String[] spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        busStopSpinner = (Spinner) findViewById(R.id.busStopSpinner);
        likeRadioGroup = (RadioGroup) findViewById(R.id.likeRadioGroup);
        adviceEditText = (EditText) findViewById(R.id.adviceEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        okButton = (Button) findViewById(R.id.okButton);
        spinnerValue = new String();

        spinner = new String[]{
                "Mühendislik Durağı", "Depark Durağı", "İşletme Durağı", "Mimarlık Durağı"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner);
        busStopSpinner.setAdapter(adapter);

        busStopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerValue = spinner[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                advice = adviceEditText.getText().toString();
                if (!advice.equals("")) {
                    email = emailEditText.getText().toString();
                    if (email.equals("")) {
                        email = "-";
                    }
                    int selectedId = likeRadioGroup.getCheckedRadioButtonId();
                    likeRadioButton = (RadioButton) findViewById(selectedId);

                    SurveyModel model = new SurveyModel(advice, email, likeRadioButton.getText().toString(), spinnerValue);

                    Intent intent = new Intent(MainActivity.this, SurveyDetailActivity.class);
                    intent.putExtra("survey", model);
                    startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(getApplicationContext(), "Lütfen yorum/öneri/şikayet bölümünü doldurunuz.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "İşlem iptal edildi.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
