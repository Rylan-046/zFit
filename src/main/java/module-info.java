module edu.cmis.zfit {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens edu.cmis.zfit to javafx.fxml;
    opens edu.cmis.zfit.model to com.fasterxml.jackson.databind;
    exports edu.cmis.zfit;
    exports edu.cmis.zfit.model to com.fasterxml.jackson.databind;
}