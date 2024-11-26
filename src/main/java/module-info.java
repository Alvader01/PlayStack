module com.github.alvader01 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;

    opens com.github.alvader01 to javafx.fxml;
    exports com.github.alvader01;
    exports com.github.alvader01.View;
    opens com.github.alvader01.View to javafx.fxml;
}
