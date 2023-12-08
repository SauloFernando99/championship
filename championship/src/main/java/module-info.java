module br.edu.ifsp {
    requires javafx.controls;
    requires javafx.fxml;


                            
    opens br.edu.ifsp.application.main.controller to javafx.fxml;
    opens br.edu.ifsp to javafx.fxml;
    exports br.edu.ifsp;
    exports br.edu.ifsp.application.main.controller;
}