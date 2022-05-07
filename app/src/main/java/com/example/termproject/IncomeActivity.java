package com.example.termproject;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IncomeActivity extends AppCompatActivity {
    String TAG ="main";
    ArrayAdapter<String> arrayAdapter;
    private int spinner_value = 0;
    private int spinner_value2 = 0;
    EditText text1;
    TextView result;
    long minus = 0;

    private List<IncomeData> incomDataList = new ArrayList<IncomeData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button = (Button) findViewById(R.id.button1);
        result = findViewById(R.id.Result);
        text1 = findViewById(R.id.text1);
        readExcelFileFromAssets();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IncomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int idx = 0;
                    long n1 = Long.parseLong(text1.getText().toString());
                    //float text1 = float.valueOf(text1.getText().toString());
                    for (int i = 0; i < incomDataList.size(); i++) {
                        float min = incomDataList.get(i).min;
                        float max = incomDataList.get(i).max;
                        if (n1/1000 >= min && n1/1000 < max) {
                            idx = i;
                            break;
                        }
                    }
                int familyidx = (spinner_value + (spinner_value2 *2)) -1;
                if(familyidx < 0)
                    familyidx = 0;

                float val = incomDataList.get(idx).friendsCount.get(familyidx);

                result.setText(String.valueOf(val));
                }
        });

        final Spinner spinner = findViewById(R.id.spinner_help);

        ArrayAdapter helpAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_help, android.R.layout.simple_spinner_dropdown_item);
        //Android.R.layout.simple_spinner_dropdown_item은 기본으로 제공해주는 형식
        helpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(helpAdapter); //어댑터에 연결해줍니다.


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_value = position;
            } //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됬는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Spinner spinner2 = findViewById(R.id.spinner_help2);

        ArrayAdapter help2Adapter = ArrayAdapter.createFromResource(this, R.array.spinner_help2, android.R.layout.simple_spinner_dropdown_item);
        //R.array.spinner_help는 정의해놓은 1월~11 / android.R.layout.simple_spinner_dropdown_item은 기본으로 제공해주는 형식
        help2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(help2Adapter); //어댑터에 연결해줍니다.


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_value2 = position;
            } //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됬는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void readExcelFileFromAssets() {
        try {
            InputStream myInput;
            // initialize asset manager
            AssetManager assetManager = getAssets();
            //  open excel sheet
            myInput = assetManager.open("library.xls");
            // Create a POI File System object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // We now need something to iterate through the cells.
            Iterator<Row> rowIter = mySheet.rowIterator();
            int rowno =0;
            while (rowIter.hasNext()) {
                Log.e(TAG, " row no "+ rowno );
                HSSFRow myRow = (HSSFRow) rowIter.next();
                if(rowno !=0) {
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    int colno =0;
                    String sno="", date="", det="";

                    if(rowno >= 6)
                    {
                        IncomeData data = new IncomeData();

                        while (cellIter.hasNext()) {

                            HSSFCell myCell = (HSSFCell) cellIter.next();

                            if (colno==0){
                                data.min =  Float.valueOf(myCell.toString());
                            }else if (colno==1){
                                data.max =  Float.valueOf(myCell.toString());
                            }else if (colno<=12){
                                String val = myCell.toString();
                                if(val != "" && val != "-")
                                {
                                    val = val.replace("," , "");
                                    data.friendsCount.add(Float.valueOf(val));
                                }
                            }
                            colno++;
                        }
                        incomDataList.add(data);
                    }
                }
                rowno++;
                if(rowno > 650) {
                    break;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "error "+ e.toString());
        }
    }
}