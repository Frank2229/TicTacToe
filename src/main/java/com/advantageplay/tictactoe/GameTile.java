package com.advantageplay.tictactoe;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class GameTile extends StackPane {

    private final Button button = new Button();
    private final Label label = new Label();
    private boolean isAI = false;
    private boolean isO = false;
    private boolean isPlayer = false;
    private boolean isX = false;

    public GameTile() {
        button.setMinSize(110,110);
        button.setOpacity(0);
        label.getStyleClass().add("tile-text");

        getChildren().addAll(label, button);
    }

    public Button getButton() {
        return button;
    }

    public boolean isAI() {
        return isAI;
    }

    public boolean isO() {
        return isO;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public boolean isX() {
        return isX;
    }

    public void reset() {
        isO = false;
        isX = false;
        label.setText("");
        button.setDisable(false);
        isPlayer = false;
        isAI = false;
    }

    public void select(boolean isPlayer) {
        if (isPlayer) {
            label.setText("X");
            isX = true;
            this.isPlayer = true;
        }
        else {
            label.setText("O");
            isO = true;
            this.isAI = true;
        }
        button.setDisable(true);
    }
}
