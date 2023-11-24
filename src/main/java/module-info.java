module com.advantageplay.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.advantageplay.tictactoe to javafx.fxml;
    exports com.advantageplay.tictactoe;
}