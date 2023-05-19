package com.stages.laboratorinis4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public abstract class TableClass extends TableView<StudentGroups> {
    ObservableList<StudentGroups> data;
    public TableClass() {
        setItems(FXCollections.observableArrayList());
        createColumns();
    }

    public abstract void createColumns();

    public void setData(ObservableList<StudentGroups> data) {
        this.data = data;
        setItems(data);
    }
}
