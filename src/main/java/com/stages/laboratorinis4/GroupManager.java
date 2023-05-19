package com.stages.laboratorinis4;

import javafx.event.ActionEvent;

public abstract class GroupManager {
    GroupManager a;
    public abstract void addStudent(ActionEvent event);
    public abstract void removeStudent(ActionEvent event);
    public abstract void markAttendance(ActionEvent event);
}
