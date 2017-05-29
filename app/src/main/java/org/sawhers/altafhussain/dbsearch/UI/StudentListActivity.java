package org.sawhers.altafhussain.dbsearch.UI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.sawhers.altafhussain.dbsearch.ADOPTERS.StudentCoustomAdopter;
import org.sawhers.altafhussain.dbsearch.DATABASE.DBHelper;
import org.sawhers.altafhussain.dbsearch.MODEL.Student;
import org.sawhers.altafhussain.dbsearch.R;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lvStudent;
   // ArrayAdapter<Student> studentArrayAdapter;
    StudentCoustomAdopter studentArrayAdapter;
    ArrayList<Student> studentArrayList;
    DBHelper dbHelper;
    Button btnShowAllList,btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //***********
        dbHelper=new DBHelper(StudentListActivity.this);
        //************
        setContentView(R.layout.activity_student_list);
        lvStudent= (ListView) findViewById(R.id.lv_student);
        btnShowAllList= (Button) findViewById(R.id.btnShowAllList);
        btnSearch= (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        btnShowAllList.setOnClickListener(this);

//        dbHelper=new DBHelper(StudentListActivity.this);
//        studentArrayList=dbHelper.showAllStudent();
//        studentArrayAdapter=new ArrayAdapter<>(StudentListActivity.this,android.R.layout.simple_list_item_1,studentArrayList);
//        lvStudent.setAdapter(studentArrayAdapter);
  //**************************************************************************
//        lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Student student=studentArrayList.get(position);
//                Bundle bundle=new Bundle();
//                bundle.putSerializable("STUDENT",student);
//                Intent intent=new Intent(StudentListActivity.this,StudentDetailActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
  //**************************************************************************
//        lvStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                final Student s=studentArrayList.get(position);
//
//
//                final AlertDialog.Builder builder=new AlertDialog.Builder(StudentListActivity.this);
//                builder.setMessage("Are You Want To Delete : "+s.getName());
//                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        int studentId=s.getId();
//                        int result=dbHelper.deleteStudent(String.valueOf(studentId));
//                        if (result>0){
//                            studentArrayList.remove(s);
//                            studentArrayAdapter.notifyDataSetChanged();
//                            Toast.makeText(StudentListActivity.this, "Student Deleted", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Toast.makeText(StudentListActivity.this, "Student NOT Deleted", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//
//                });
//                AlertDialog alertDialog=builder.create();
//                alertDialog.show();
//
//
//                return true;
//            }
//        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        showAllStudentList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSearch:
              //to search a dilog from "btnSearch" we have method name is Dialog
                final Dialog searchDilog=new Dialog(StudentListActivity.this);
              //here we use setContentView() for showing the Xml of dilog we create with the name of dilog_search
                searchDilog.setContentView(R.layout.dialog_search);
                searchDilog.setTitle("Search");
              //now we refrence the dilog spiner and edt etc to get ftn from it
                //But we are in searchDilog so we need to give the referencr of searchDilog,other vise this give error
                final Spinner spinner = (Spinner) searchDilog.findViewById(R.id.sp_Criteria);
                final EditText etValue= (EditText) searchDilog.findViewById(R.id.et_Search);
                Button btnCancel= (Button)searchDilog.findViewById(R.id.btn_Cancel);
                Button btnOkay= (Button) searchDilog.findViewById(R.id.btn_Okay);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     String field=getResources().getStringArray(R.array.search_field)[position];
                   //getResources().getStringArray(R.array.search_field) is give us the search_field which is the name ,course
                   //Total Fee ,Fee Not Paid  ,[position] is used to when some on select from spiner the value mean the value
                   //so we get the position of that value and give it to field
                        if (field.equals("Name")){
                            if (!etValue.isEnabled()){
                                etValue.setEnabled(true);
                            }

                            etValue.setHint("Name");
                        }
                        else if (field.equals("Course")){

                            if (!etValue.isEnabled()){
                                etValue.setEnabled(true);
                            }
                       etValue.setHint("Course");
                        }
                        else if (field.equals("Fee Paid") || (field.equals("Fee Not Paid"))){
                            etValue.setHint("Not Required");
                            etValue.setEnabled(false);
                        }




                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchDilog.dismiss();
                }
            });
                btnOkay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//***********************************************************************
                        //now we work on Okaybtn to send requiest database
                        String field= (String) spinner.getSelectedItem();//this take value from spiner
                        //mean the user select name,course,total fee, fee not paid etc
                        String value=etValue.getText().toString().trim();//this is used when the user give
                        //name like altaf, ajmal etc
                        studentArrayList=dbHelper.selectStudentPerCriteria(field ,value);
                        if (studentArrayList.size()>0){
                            studentArrayAdapter=new StudentCoustomAdopter(StudentListActivity.this,studentArrayList);
                            lvStudent.setAdapter(studentArrayAdapter);
                            studentArrayAdapter.notifyDataSetChanged();
                            Toast.makeText(StudentListActivity.this, "Record Found  ="+studentArrayList.size(), Toast.LENGTH_SHORT).show();
                       searchDilog.dismiss();
                        }
                        else {
                            Toast.makeText(StudentListActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                            searchDilog.dismiss();
                        }

                    }

//***********************************************************************
                });

                searchDilog.show();
                break;


            case R.id.btnShowAllList:
                showAllStudentList();
                break;
        }

    }
    private void showAllStudentList(){

        studentArrayList=new ArrayList<>();
        studentArrayList=dbHelper.showAllStudent();
        studentArrayAdapter=new StudentCoustomAdopter(StudentListActivity.this,studentArrayList);
        lvStudent.setAdapter(studentArrayAdapter);
    }
}

