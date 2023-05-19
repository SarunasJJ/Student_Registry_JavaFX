package com.stages.laboratorinis4;

import com.itextpdf.text.DocumentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;

public class GroupController extends GroupManager implements Initializable{
    @FXML
    TextField firstNameField, lastNameField, idField;
    @FXML
    Label warningLabel;
    @FXML
    Button addButton;
    @FXML
    CheckBox filterBox;
    @FXML
    DatePicker attendanceDate, filterStartDate, filterEndDate;
    @FXML
    TableView<Student> studentTable = new TableView<>();
    TableColumn<Student, String> firstNameColumn = new TableColumn<>("First name");
    TableColumn<Student, String> lastNameColumn = new TableColumn<>("Last name");
    TableColumn<Student, String> idColumn = new TableColumn<>("Student ID");
    TableColumn<Student, String> attendanceColumn = new TableColumn<>("Attendance");
    ObservableList<Student> studentList = FXCollections.observableArrayList();
    StudentGroups group;
    public void setGroup(){
        Stage currentStage = (Stage) addButton.getScene().getWindow();
        group = (StudentGroups)currentStage.getUserData();
        studentList = group.getStudentList();
        studentTable.setItems(studentList);
        studentTable.setEditable(true);
    }
    @Override
    public void addStudent(ActionEvent event){
        String firstName = null, lastName = null, id = null;
        try{
            firstName = firstNameField.getText();
        } catch (Exception e){
            System.out.println("Wrong first name!");
        }
        try{
            lastName = lastNameField.getText();
        } catch (Exception e){
            System.out.println("Wrong last name!");
        }
        try {
            id = idField.getText();
        } catch (Exception e){
            System.out.println("Wrong id!");
        }
        firstNameField.clear();
        lastNameField.clear();
        idField.clear();
        studentList.add(new Student(firstName,lastName,id, 0));
        studentTable.setItems(studentList);
        group.setStudentList(studentList);
    }

    @Override
    public void removeStudent(ActionEvent event) {
            Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
            studentList.remove(selectedStudent);
    }
    @Override
    public void markAttendance(ActionEvent event){
        try {
            LocalDate date = attendanceDate.getValue();
            Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
            selectedStudent.setAttendance(date);
            studentTable.refresh();
        } catch (Exception e){
            warningLabel.setText("Please select student!");
        }
    }

    public void filterStudents(ActionEvent event){
        LocalDate startDate = filterStartDate.getValue();
        LocalDate endDate = filterEndDate.getValue();
        ObservableList<Student> listCopy = FXCollections.observableArrayList();
        if(filterBox.isSelected()){
            for(Student student : studentList) {
                LocalDate currentDate = startDate;
                HashSet<LocalDate> attendanceSet = student.getAttendanceMap();
                int filterAttendance = 0;
                while(!currentDate.isAfter(endDate)){
                    if(attendanceSet.contains(currentDate)) {
                        filterAttendance++;
                    }
                    currentDate = currentDate.plusDays(1);
                }
                if(filterAttendance>0){
                    listCopy.add(new Student(student.getFirstName(), student.getLastName(), student.getId(), filterAttendance));
                }
            }
            studentTable.setItems(listCopy);
            studentTable.refresh();
        } else{
            studentTable.setItems(studentList);
            studentTable.refresh();
        }
    }

    public void exportToPdf(ActionEvent event) throws DocumentException, FileNotFoundException {
        PDFExporter exporter = new PDFExporter();
        exporter.exportToPDF(studentList, group.getGroupName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        attendanceColumn.setCellValueFactory(new PropertyValueFactory<>("attendanceRate"));

        studentTable.setEditable(true);
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        studentTable.getColumns().addAll(firstNameColumn, lastNameColumn, idColumn, attendanceColumn);

        firstNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> studentStringCellEditEvent) {
                Student student = studentStringCellEditEvent.getRowValue();
                student.setFirstName(studentStringCellEditEvent.getNewValue());
            }
        });
        lastNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> studentStringCellEditEvent) {
                Student student = studentStringCellEditEvent.getRowValue();
                student.setLastName(studentStringCellEditEvent.getNewValue());
            }
        });
        idColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> studentStringCellEditEvent) {
                Student student = studentStringCellEditEvent.getRowValue();
                student.setId(studentStringCellEditEvent.getNewValue());
            }
        });
    }
}
