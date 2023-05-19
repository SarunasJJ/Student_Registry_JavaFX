package com.stages.laboratorinis4;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class GroupsTable extends TableClass{
    public GroupsTable() {
        super();
    }
    @Override
    public void createColumns() {
        TableColumn<StudentGroups, String> groupNameColumn = new TableColumn<>("Group name");
        groupNameColumn.setCellValueFactory(new PropertyValueFactory<>("groupName"));
    }
}
