package com.stages.laboratorinis4;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class StudentGroups {
    ObservableList<Student> studentList = FXCollections.observableArrayList();
    private final SimpleStringProperty groupName;

    StudentGroups(String groupName){
        this.groupName = new SimpleStringProperty(groupName);
    }

    public String getGroupName() {
        return groupName.get();
    }

    public void setGroupName(String groupName) {
        this.groupName.set(groupName);
    }

    public ObservableList<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(ObservableList<Student> studentList) {
        this.studentList = studentList;
    }

}
