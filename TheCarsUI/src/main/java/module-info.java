module org.po2025.thecarsui {
    requires javafx.controls;
    requires javafx.fxml;
    requires TheCars;
    requires java.desktop;
    requires java.logging;


    opens org.po2025.thecarsui to javafx.fxml;
    exports org.po2025.thecarsui;
}