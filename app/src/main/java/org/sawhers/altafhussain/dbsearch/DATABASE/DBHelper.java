package org.sawhers.altafhussain.dbsearch.DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.sawhers.altafhussain.dbsearch.MODEL.Student;

import java.util.ArrayList;

import static android.R.attr.id;
import static android.R.attr.name;

/**
 * Created by ALTAFHUSSAIN on 1/5/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="mystudent.db";
    private static final int DB_VERSION=1;
    private static final String KEY_TABLE="tblstudent";
    private static final String KEY_NAME="name";
    private static final String KEY_COURSE="course";
    private static final String KEY_ID="id";
    private static final String KEY_TOTAL_FEE="totalfee";
    private static final String KEY_FEE_PAID="feepaid";

//*********************************************************************
private static final String CREATE_TABLE="CREATE TABLE " + KEY_TABLE + " ( "

                        +KEY_ID + " Integer Primary Key Autoincrement, "
                        +KEY_NAME + " Text, "
                        +KEY_COURSE + " Text, "
                        +KEY_TOTAL_FEE + " Integer, "
                        +KEY_FEE_PAID + " Integer "
                        + " ); ";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ CREATE_TABLE);
        onCreate(db);
    }

    public long saveStudent(Student student) {
        long result=-1;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,student.getName());
        values.put(KEY_COURSE,student.getCourse());
        values.put(KEY_TOTAL_FEE,student.getTotalFee());
        values.put(KEY_FEE_PAID,student.getFeePaid());
        result=sqLiteDatabase.insert(KEY_TABLE ,null,values);

        return result;

    }

    public ArrayList<Student> showAllStudent() {
        ArrayList<Student> studentArrayList=new ArrayList<>();

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor c=sqLiteDatabase.rawQuery("select * from " + KEY_TABLE ,null);
        if (c.moveToFirst()){
            do{
                int id =c.getInt(c.getColumnIndex(KEY_ID));
                String name=c.getString(c.getColumnIndex(KEY_NAME));
                String course=c.getString(c.getColumnIndex(KEY_COURSE));
                int totalFee=c.getInt(c.getColumnIndex(KEY_TOTAL_FEE));
                int feePaid=c.getInt(c.getColumnIndex(KEY_FEE_PAID));
                Student student=new Student(id,totalFee,feePaid,name,course);
                studentArrayList.add(student);
            }while (c.moveToNext());

        }
        return studentArrayList;
    }

    public int upDateStudent(Student student) {
        int result=0;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,student.getName());
        values.put(KEY_COURSE,student.getCourse());
        values.put(KEY_TOTAL_FEE,student.getTotalFee());
        values.put(KEY_FEE_PAID,student.getFeePaid());
        result=sqLiteDatabase.update(KEY_TABLE ,values ,KEY_ID + "=?" ,new String[]{String.valueOf(student.getId())});



        return result;
    }

    public int deleteStudent(String s) {
        int result=0;
        try {
            SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
            result=sqLiteDatabase.delete(KEY_TABLE ,KEY_ID + "=?" ,new String[]{s});
            return  result;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ALTAF_ERROR ","DBHelper -> deleteStudent "+result);
        }
        return result;
    }

    public ArrayList<Student> selectStudentPerCriteria(String field, String value) {
        ArrayList<Student> studentArrayList=new ArrayList<>();

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor c=sqLiteDatabase.rawQuery("select * from " + KEY_TABLE + " where " + field  + " LIKE ?",new String[]{value});
        if (c.moveToFirst()){
            do{
                int id =c.getInt(c.getColumnIndex(KEY_ID));
                String name=c.getString(c.getColumnIndex(KEY_NAME));
                String course=c.getString(c.getColumnIndex(KEY_COURSE));
                int totalFee=c.getInt(c.getColumnIndex(KEY_TOTAL_FEE));
                int feePaid=c.getInt(c.getColumnIndex(KEY_FEE_PAID));
                Student student=new Student(id,totalFee,feePaid,name,course);
                studentArrayList.add(student);
            }while (c.moveToNext());

        }
        return studentArrayList;

    }
}
