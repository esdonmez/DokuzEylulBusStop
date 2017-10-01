package com.esdonmez.esd.dokuzeylulbusstop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    Spinner busStopSpinner;
    RadioGroup likeRadioGroup;
    RadioButton likeRadioButton;
    EditText adviceEditText, emailEditText;
    Editable adviceEditable, emailEditable;
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

        spinner = new String[] {
                "Mühendislik Durağı", "Depark Durağı", "İşletme Durağı", "Mimarlık Durağı"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item , spinner);
        busStopSpinner.setAdapter(adapter);

        busStopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(), spinner[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adviceEditable = adviceEditText.getText();
                emailEditable = emailEditText.getText();
                int selectedId = likeRadioGroup.getCheckedRadioButtonId();
                likeRadioButton = (RadioButton) findViewById(selectedId);
                //Toast.makeText(getBaseContext(), likeRadioButton.getText(), Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), "Ankete katıldığınız için teşekkür ederiz.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
