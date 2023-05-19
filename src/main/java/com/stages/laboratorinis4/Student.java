package com.stages.laboratorinis4;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.HashSet;

public class Student {
    SimpleStringProperty firstName, lastName, id;
    HashSet<LocalDate> attendanceMap;
    int attendanceRate = 0;

    public Student(String firstName, String lastName, String id, int attendanceRate) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.id = new SimpleStringProperty(id);
        this.attendanceRate = attendanceRate;
        this.attendanceMap = new HashSet<>();
    }
    public void setAttendance(LocalDate date){
        if(!attendanceMap.contains(date)) {
            attendanceMap.add(date);
            attendanceRate++;
        }
    }
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public int getAttendanceRate(){
        return attendanceRate;
    }
    public HashSet<LocalDate> getAttendanceMap() {
        return attendanceMap;
    }

    public void setAttendanceMap(HashSet<LocalDate> attendanceMap) {
        this.attendanceMap = attendanceMap;
    }

}
