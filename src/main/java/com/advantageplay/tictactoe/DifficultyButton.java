package com.advantageplay.tictactoe;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class DifficultyButton extends StackPane {

    private Button button = new Button();
    private ImageView buttonImageView;

    public DifficultyButton(String imageString) {

        button.setMinSize(250, 70);
        button.setOpacity(0);

        buttonImageView = new ImageView(new Image(imageString));
        buttonImageView.setFitWidth(260);
        buttonImageView.setPreserveRatio(true);

        getChildren().addAll(buttonImageView, button);
    }

    public Button getButton() {
        return button;
    }
}
