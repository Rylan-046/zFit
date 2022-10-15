module edu.cmis.zfit {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.cmis.zfit to javafx.fxml;
    exports edu.cmis.zfit;
}