module edu.cmis.zfit {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    opens edu.cmis.zfit.repository to java.sql;
    opens edu.cmis.zfit to javafx.fxml;
    opens edu.cmis.zfit.model to com.fasterxml.jackson.databind;

    exports edu.cmis.zfit;
    exports edu.cmis.zfit.model to com.fasterxml.jackson.databind;
}