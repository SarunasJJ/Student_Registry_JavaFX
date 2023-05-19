package com.stages.laboratorinis4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable, ImportData, ExportData {
    @FXML
    private TextField groupField;
    @FXML
    private Button addGroupButton;
    //GroupsTable groupsTable = new GroupsTable();
    @FXML
    private TableView<StudentGroups> groupsTable = new TableView<>();
    ObservableList<StudentGroups> groupsObservableList = FXCollections.observableArrayList();
    TableColumn<StudentGroups, String> groupNameColumn = new TableColumn<>("Group name");

    public void addGroup(ActionEvent event){
        String groupName = null;
        try {
            groupName = groupField.getText();
        } catch (Exception e){
            System.out.println("Wrong input!\n");
        }
        groupField.clear();
        StudentGroups group = new StudentGroups(groupName);
        groupsObservableList.add(group);
        groupsTable.setItems(groupsObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupNameColumn.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        groupsTable.getColumns().addAll(groupNameColumn);

        groupsTable.setRowFactory(gt -> {
            TableRow<StudentGroups> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if(mouseEvent.getClickCount() == 2 && (!row.isEmpty())){
                    StudentGroups group = row.getItem();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("group.fxml"));
                    Parent root;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Stage groupStage = new Stage();
                    groupStage.setUserData(group);
                    groupStage.setScene(new Scene(root));
                    groupStage.setTitle(group.getGroupName());
                    GroupController groupController = loader.getController();
                    groupController.setGroup();
                    groupStage.show();
                }
            });
            return row;
        });
    }

    @Override
    public void exportDataToExcel(ActionEvent event) {

    }

    @Override
    public void exportDataToCSV(ActionEvent event) {
        StudentGroups selectedGroup = groupsTable.getSelectionModel().getSelectedItem();
        String groupName = selectedGroup.getGroupName();
        ObservableList<Student> students = selectedGroup.getStudentList();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(groupName + ".csv"))) {
            for (Student student : students) {
                writer.write(student.getFirstName() + "," + student.getLastName() + "," + student.getId()
                        + "," + student.getAttendanceRate() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void importDataFromExcel(ActionEvent event) {

    }

    @Override
    public void importDataFromCSV(ActionEvent event) {
        StudentGroups selectedGroup = groupsTable.getSelectionModel().getSelectedItem();
        String groupName = selectedGroup.getGroupName();
        ObservableList<Student> students = selectedGroup.getStudentList();
        try(BufferedReader reader = new BufferedReader(new FileReader(groupName + ".csv"))){
            String data;
            while((data = reader.readLine()) != null){
                String[] info = data.split(",");
                students.add(new Student(info[0], info[1], info[2], Integer.parseInt(info[3])));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        selectedGroup.setStudentList(students);
    }

}