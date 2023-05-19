module com.stages.laboratorinis4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;


    opens com.stages.laboratorinis4 to javafx.fxml;
    exports com.stages.laboratorinis4;
}