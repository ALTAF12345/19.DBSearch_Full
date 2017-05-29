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

public class StudentDetailActivity extends AppCompatActivity {
    EditText etNameD,etCourseD,etTotalFeeD,etFeePaidD;
    Button btnUpdate;
    DBHelper dbHelper;
    int StudentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        etNameD= (EditText) findViewById(R.id.et_nameD);
        etCourseD= (EditText) findViewById(R.id.et_CourseD);
        etTotalFeeD= (EditText) findViewById(R.id.et_totalFeeD);
        etFeePaidD= (EditText) findViewById(R.id.et_feepaidD);
        btnUpdate= (Button) findViewById(R.id.btn_updata);

 //*************************************************************
        Bundle bundle=getIntent().getExtras();
        Student student= (Student) bundle.getSerializable("STUDENT");
        etNameD.setText(student.getName());
        etCourseD.setText(student.getCourse());
        etTotalFeeD.setText(String.valueOf(student.getTotalFee()));
        etFeePaidD.setText(String.valueOf(student.getFeePaid()));
        StudentID=student.getId();
//*************************************************************
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student=getvaluesFromFieldD();
                dbHelper=new DBHelper(StudentDetailActivity.this);
                int result=dbHelper.upDateStudent(student);
                if (result>0){
                    Toast.makeText(StudentDetailActivity.this, "Student upDated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(StudentDetailActivity.this, "Student not upDated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private Student getvaluesFromFieldD(){

        String totalFee=etTotalFeeD.getText().toString().trim();
        String feePaid=etFeePaidD.getText().toString().trim();
        String name=etNameD.getText().toString().trim();
        String course=etCourseD.getText().toString().trim();
        Student student=new Student(StudentID,Integer.parseInt(totalFee),Integer.parseInt(feePaid),name,course);

        return student;
    }


}
