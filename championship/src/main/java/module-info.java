module br.edu.ifsp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens br.edu.ifsp.application.main.controller to javafx.fxml;
    opens br.edu.ifsp.domain.entities.team to javafx.base;
    opens br.edu.ifsp.domain.entities.championship to javafx.base;

    opens br.edu.ifsp to javafx.fxml;
    exports br.edu.ifsp;
    exports br.edu.ifsp.application.main.controller;
    exports br.edu.ifsp.application.main.controller.team;
    opens br.edu.ifsp.application.main.controller.team to javafx.fxml;
}