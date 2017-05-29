package org.sawhers.altafhussain.dbsearch.ADOPTERS;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.sawhers.altafhussain.dbsearch.DATABASE.DBHelper;
import org.sawhers.altafhussain.dbsearch.MODEL.Student;
import org.sawhers.altafhussain.dbsearch.R;
import org.sawhers.altafhussain.dbsearch.UI.StudentDetailActivity;

import java.util.ArrayList;

/**
 * Created by ALTAFHUSSAIN on 1/6/2017.
 */

public class StudentCoustomAdopter extends ArrayAdapter<Student> {
    Context ctx;
    ArrayList<Student> studentArrayList;
    DBHelper dbHelper;
    Student student;

    public StudentCoustomAdopter(Context context, ArrayList<Student> list) {
        super(context, R.layout.coustom_adopter ,list);
        ctx=context;
        studentArrayList=list;
        dbHelper=new DBHelper(ctx);

        }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        student=studentArrayList.get(position);
        LayoutInflater layoutInflater=LayoutInflater.from(ctx);
        final View view=layoutInflater.inflate(R.layout.coustom_adopter,parent,false);

        TextView tvName,tvCourse,tvTotalFee,tvFeePaid;
        ImageView ivEdit,ivDelete;
        tvName= (TextView) view.findViewById(R.id.tvName_rowStudent);
        tvCourse= (TextView) view.findViewById(R.id.tvCourse_rowStudent);
        tvTotalFee= (TextView) view.findViewById(R.id.tvTotalFee_rowStudent);
        tvFeePaid= (TextView) view.findViewById(R.id.tvFeePaid_rowStudent);
        tvName.setText("Name :"+student.getName());
        tvCourse.setText("Course :"+student.getCourse());
        tvTotalFee.setText(String.valueOf(student.getTotalFee()));
        tvFeePaid.setText(String.valueOf(student.getFeePaid()));
        ivEdit= (ImageView) view.findViewById(R.id.ivEdit_rowStudent);
        ivDelete= (ImageView) view.findViewById(R.id.ivDelete_rowStudent);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we have the select student abouve so we used this student mean like previse we pass the slected
                // student from lvstudent to StudentDetailActivity with the help of bundle
                Bundle bundle=new Bundle();
                bundle.putSerializable("STUDENT",student);
                Intent intent=new Intent(ctx, StudentDetailActivity.class);
                intent.putExtras(bundle);
                ctx.startActivity(intent);

            }
        });
//************************************************************
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ctx);
                builder.setMessage("Are You Sure To Delete :"+student.getName());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText(ctx, "Student Will be deleted  :", Toast.LENGTH_SHORT).show();

                        //********************************************
                        int studentid=student.getId();
                        int result= dbHelper.deleteStudent(String.valueOf(studentid));

                        if (result>0){
                            //Toast.makeText(ctx, "Student Deleted", Toast.LENGTH_SHORT).show();
                            studentArrayList.remove(student);
                            //studentArrayAdapter.notifyDataSetChanged();
                            notifyDataSetChanged();
                        }

                        else{
                            Toast.makeText(ctx, "Student fail to delete", Toast.LENGTH_SHORT).show();
                        }


                    }
                    //****************************************************

                });
                builder.setNegativeButton("No",null);
                builder.show();


            }
        });

        return view;
    }
}
