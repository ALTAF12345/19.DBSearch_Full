package org.sawhers.altafhussain.dbsearch.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.sawhers.altafhussain.dbsearch.DATABASE.DBHelper;
import org.sawhers.altafhussain.dbsearch.MODEL.Student;
import org.sawhers.altafhussain.dbsearch.R;

public class MainActivity extends AppCompatActivity {
    EditText etName,etCourse,etTotalFee,etFeePaid;
    Button btnSave,btnShowAll;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName= (EditText) findViewById(R.id.et_name);
        etCourse= (EditText) findViewById(R.id.et_course);
        etTotalFee= (EditText) findViewById(R.id.et_total_fee);
        etFeePaid= (EditText) findViewById(R.id.et_fee_paid);
        btnSave= (Button) findViewById(R.id.btn_save);
        btnShowAll= (Button) findViewById(R.id.btn_show_all);
   //********************************************************
        dbHelper=new DBHelper(MainActivity.this);

 //*********************************************************************************
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Student student=getvaluesFromField();
            long result=dbHelper.saveStudent(student);
                if (result!=0){
                    Toast.makeText(MainActivity.this, "Record added sucsussfully :"+result, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Record addition failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
 //**********************************************************************************
        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,StudentListActivity.class));

            }
        });

    }
    private Student getvaluesFromField(){

        String totalFee=etTotalFee.getText().toString().trim();
        String feePaid=etFeePaid.getText().toString().trim();
        String name=etName.getText().toString().trim();
        String course=etCourse.getText().toString().trim();
   Student student=new Student(Integer.parseInt(totalFee),Integer.parseInt(feePaid),name,course);

        return student;
    }
}
