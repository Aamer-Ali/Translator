package com.example.translator;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DbHelper myDb;
    Button btnSubmit, btnView, btnTranslate;
    EditText txtUrduWord, txtHindiWord, txtMarathiWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DbHelper(this);
        txtUrduWord = findViewById(R.id.txtWordInUrdu);
        txtHindiWord = findViewById(R.id.txtWordInHindi);
        txtMarathiWord = findViewById(R.id.txtWordInMarathi);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnView = findViewById(R.id.btnView);
        btnTranslate = findViewById(R.id.btnGotoTranslator);
        btnSubmit.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnTranslate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                boolean isInseted = myDb.insert_data(txtUrduWord.getText().toString().trim(), txtHindiWord.getText().toString().trim(), txtMarathiWord.getText().toString().trim());
                if (isInseted == true) {
                    Toast.makeText(MainActivity.this, "DATA ADDED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Something Goes Wrong", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnView:
                Cursor res = myDb.getAllData();

                if (res.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Nothing to Show", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append(myDb.COL_ID + " : " + res.getString(0) + "\n");
                    buffer.append(myDb.COL_URDU + " : " + res.getString(1) + "\n");
                    buffer.append(myDb.COL_HINDI + " : " + res.getString(2) + "\n");
                    buffer.append(myDb.COL_MARATHI + " : " + res.getString(3) + "\n");
                }
                Toast.makeText(MainActivity.this, buffer.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnGotoTranslator:
                Intent translateIntent = new Intent(MainActivity.this, Translation.class);
                startActivity(translateIntent);
                break;
        }
    }

}
