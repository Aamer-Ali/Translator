package com.example.translator;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Translation extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    DbHelper myHelper;
    Spinner spinnerFrom, spinnerTo;
    public String[] language = {"Urdu", "Hindi", "Marathi"};
    public String from, to;
    Button btnTranslateWord;
    EditText txtWordToTranslate;
    TextView tvResult;
    public String finalResult ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        spinnerFrom.setOnItemSelectedListener(this);
        spinnerTo.setOnItemSelectedListener(this);
        btnTranslateWord = findViewById(R.id.btnTranslate);
        txtWordToTranslate = findViewById(R.id.txtWordToTranslate);
        myHelper = new DbHelper(this);
        tvResult = findViewById(R.id.tvResult);

        btnTranslateWord.setOnClickListener(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, language);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerFrom) {
            Toast.makeText(this, language[position], Toast.LENGTH_SHORT).show();
            from = language[position];
        }

        if (parent.getId() == R.id.spinnerTo) {
            Toast.makeText(this, language[position], Toast.LENGTH_SHORT).show();
            to = language[position];
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v == btnTranslateWord) {

            Toast.makeText(this, "You have choosen From: " + from + "and To : " + to, Toast.LENGTH_SHORT).show();
            Cursor result= myHelper.getTranslation(txtWordToTranslate.getText().toString().trim(),from,to);
            while (result.moveToNext())
            {
                finalResult = result.getString(0);
            }
            tvResult.setText(finalResult);
        }
    }
}
