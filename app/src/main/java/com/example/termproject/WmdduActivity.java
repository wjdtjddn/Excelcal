package com.example.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class WmdduActivity extends AppCompatActivity {

    private int spinner_value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wmddu);

        Button button = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText text1 = (EditText)findViewById(R.id.text1);
                EditText text2 = (EditText)findViewById(R.id.text2);
                TextView result = (TextView)findViewById(R.id.Result);

                long n1 = Long.parseLong(text1.getText().toString());
                long n2 = Long.parseLong(text2.getText().toString());

                long relationValues[] = new long[] { 600000000, 50000000, 20000000, 10000000 };

                long resultVal = n1 + n2 - relationValues[spinner_value];
                    if(resultVal <= 0)
                    {
                        resultVal = 0;
                    }else if(resultVal <= 100000000)
                    {
                        resultVal = Math.round(resultVal * 0.1f);
                    }else if(resultVal <= 500000000)
                    {
                        resultVal = Math.round(resultVal * 0.2f);
                        resultVal -= 10000000;
                    }else if(resultVal <= 1000000000)
                    {
                    resultVal = Math.round(resultVal * 0.3f);
                    resultVal -= 60000000;
                }else if(resultVal <= Long.parseLong("3000000000"))
                {
                    resultVal = Math.round(resultVal * 0.4f);
                    resultVal -= 160000000;
                }else{
                    resultVal = Math.round(resultVal * 0.5f);
                    resultVal -= 460000000;
                }

                result.setText(Long.toString(resultVal));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WmdduActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final Spinner wmddu = findViewById(R.id.spinner_wmddu);

        ArrayAdapter wmdduAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_wmddu, android.R.layout.simple_spinner_dropdown_item);
        //R.array.spinner_help??? ??????????????? / android.R.layout.simple_spinner_dropdown_item??? ???????????? ??????????????? ??????
        wmdduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wmddu.setAdapter(wmdduAdapter); //???????????? ??????????????????.


        wmddu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_value = position;
            } //??? ??????????????? ??????????????? position??? ????????? ?????? ??????????????? ??? ??? ????????????.
            //getItemAtPosition(position)??? ????????? ?????? ?????? ????????????????????????.

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });
    }
    void show()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("????????? ?????????.");
        builder.setMessage("?????? ???????????? ???????????????.");
        builder.show();
    }
}