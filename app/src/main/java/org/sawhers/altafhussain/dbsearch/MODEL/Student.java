package org.sawhers.altafhussain.dbsearch.MODEL;

import java.io.Serializable;

/**
 * Created by ALTAFHUSSAIN on 1/5/2017.
 */

public class Student implements Serializable {

    int id;
    int totalFee;
    int feePaid;
    String name;
    String course;

    public Student() {
    }

    public Student(int totalFee, int feePaid, String name, String course) {
        this.totalFee = totalFee;
        this.feePaid = feePaid;
        this.name = name;
        this.course = course;
    }

    public Student(int id, int totalFee, int feePaid, String name, String course) {
        this.id = id;
        this.totalFee = totalFee;
        this.feePaid = feePaid;
        this.name = name;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public int getFeePaid() {
        return feePaid;
    }

    public void setFeePaid(int feePaid) {
        this.feePaid = feePaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return name;
    }
}
