package com.esdonmez.esd.dokuzeylulbusstop;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import com.esdonmez.esd.dokuzeylulbusstop.Helpers.DatabaseHandler;
import com.esdonmez.esd.dokuzeylulbusstop.Models.StopModel;
import com.esdonmez.esd.dokuzeylulbusstop.Models.SurveyModel;

public class MainActivity extends AppCompatActivity {

    RadioGroup likeRadioGroup;
    RadioButton likeRadioButton;
    EditText adviceEditText, emailEditText;
    String advice, email;
    Button okButton;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        likeRadioGroup = (RadioGroup) findViewById(R.id.likeRadioGroup);
        adviceEditText = (EditText) findViewById(R.id.adviceEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        okButton = (Button) findViewById(R.id.okButton);

        Intent intent = getIntent();
        final String name = (String) intent.getStringExtra("name");
        id = (String) intent.getStringExtra("id");

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

                    SurveyModel model = new SurveyModel(advice, email, likeRadioButton.getText().toString(), name);

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
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle(result);
                alertDialog.setIcon(R.mipmap.ic_launcher);

                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                StopModel model = db.getStop(Integer.parseInt(id));

                model.setVote(model.getVote() + 1);
                model.setLike(model.getLike() + Integer.parseInt(likeRadioButton.getText().toString()));
                db.updateStop(model);
                MapActivity.stopList = db.getAllStops();

                alertDialog.setMessage("Durak Adı: " + MapActivity.stopList.get(Integer.parseInt(id)).getName() + "\nAnkete katılan kişi sayısı: " + model.getVote() + "\nPuan Durumu: " + model.getLike());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Kapat",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                                startActivity(intent);
                            }
                        });
                alertDialog.show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "İşlem iptal edildi.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
