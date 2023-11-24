package com.advantageplay.tictactoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

public class TicTacToe extends Application {
    private final GameTile[] gameTiles = new GameTile[9];
    private final Label drawLabel = new Label("It's a draw.");
    private final Label loseLabel = new Label("You lose...");
    private final Label winLabel = new Label("You win!");
    private final Random random = new Random();
    private boolean isAIWin = false;
    private boolean isDraw = false;
    private boolean isPlayerFirst = true;
    private boolean isPlayerWin = false;
    private HBox mainHBox;
    private int currentTurn = 1;
    private int difficulty = 0;
    private int totalDraws = 0;
    private int totalLosses = 0;
    private int totalWins = 0;
    private Label totalDrawsLabel;
    private Label totalLossesLabel;
    private Label totalWinsLabel;
    private StackPane root;

    @Override
    public void start(Stage stage){

        ImageView backgroundImageView = new ImageView(new Image(Objects.requireNonNull(TicTacToe.class.getResource("images/WoodBackground.png")).toString()));
        backgroundImageView.setFitWidth(1000);
        backgroundImageView.setPreserveRatio(true);

        //Difficulty menu setup;
        ImageView difficultyMenuImageView = new ImageView(new Image(Objects.requireNonNull(TicTacToe.class.getResource("images/DifficultyMenu.png")).toString()));
        difficultyMenuImageView.setFitWidth(400);
        difficultyMenuImageView.setPreserveRatio(true);
        DifficultyButton easyButton = new DifficultyButton(Objects.requireNonNull(TicTacToe.class.getResource("images/Easy.png")).toString());
        DifficultyButton mediumButton = new DifficultyButton(Objects.requireNonNull(TicTacToe.class.getResource("images/Medium.png")).toString());
        DifficultyButton hardButton = new DifficultyButton(Objects.requireNonNull(TicTacToe.class.getResource("images/Hard.png")).toString());
        VBox difficultyVBox = new VBox(easyButton, mediumButton, hardButton);
        difficultyVBox.setAlignment(Pos.CENTER);
        difficultyVBox.setTranslateY(50);
        difficultyVBox.setSpacing(5);
        StackPane difficultyStackPane = new StackPane(difficultyMenuImageView, difficultyVBox);

        // Setup game board.
        ImageView notebookImageView = new ImageView(new Image(Objects.requireNonNull(TicTacToe.class.getResource("images/Notebook.png")).toString()));
        notebookImageView.setFitHeight(550);
        notebookImageView.setFitWidth(425);
        notebookImageView.setTranslateX(-10);

        for (int i = 0; i < gameTiles.length; i++) gameTiles[i] = new GameTile();

        GridPane boardGridPane = new GridPane();
        int currentTile = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardGridPane.add(gameTiles[currentTile], j, i);
                currentTile++;
            }
        }
        boardGridPane.setAlignment(Pos.CENTER);
        StackPane boardPane = new StackPane(notebookImageView, boardGridPane);
        boardGridPane.setTranslateY(40);
        boardGridPane.setTranslateX(-7);

        // Setup right toolbar.
        winLabel.getStyleClass().add("win-text");
        winLabel.setVisible(false);
        loseLabel.getStyleClass().add("lose-text");
        loseLabel.setVisible(false);
        drawLabel.getStyleClass().add("draw-text");
        drawLabel.setVisible(false);
        StackPane resultStackPane = new StackPane(winLabel, loseLabel, drawLabel);
        ImageView postitImageView = new ImageView(new Image(Objects.requireNonNull(TicTacToe.class.getResource("images/Postit.png")).toString()));
        postitImageView.setFitWidth(250);
        postitImageView.setPreserveRatio(true);
        Label wLabel = new Label("W - ");
        wLabel.getStyleClass().add("results-text");
        totalWinsLabel = new Label(Integer.toString(totalWins));
        totalWinsLabel.getStyleClass().add("results-text");
        HBox winsHBox = new HBox(wLabel, totalWinsLabel);
        winsHBox.setAlignment(Pos.CENTER);
        Label lLabel = new Label("L - ");
        lLabel.getStyleClass().add("results-text");
        totalLossesLabel = new Label(Integer.toString(totalLosses));
        totalLossesLabel.getStyleClass().add("results-text");
        HBox lossesHBox = new HBox(lLabel, totalLossesLabel);
        lossesHBox.setAlignment(Pos.CENTER);
        Label dLabel = new Label("D - ");
        dLabel.getStyleClass().add("results-text");
        totalDrawsLabel = new Label(Integer.toString(totalDraws));
        totalDrawsLabel.getStyleClass().add("results-text");
        HBox drawsHBox = new HBox(dLabel, totalDrawsLabel);
        drawsHBox.setAlignment(Pos.CENTER);
        VBox postitVBox = new VBox(winsHBox, lossesHBox, drawsHBox);
        postitVBox.setAlignment(Pos.CENTER);
        StackPane postitStackPane = new StackPane(postitImageView, postitVBox);
        Button newGameButton = new Button("New Game");
        newGameButton.getStyleClass().add("new-game-button");
        VBox toolbarVBox = new VBox(resultStackPane, postitStackPane, newGameButton);
        toolbarVBox.setSpacing(20);
        toolbarVBox.setAlignment(Pos.CENTER);

        // Add elements to an HBox.
        mainHBox = new HBox(boardPane, toolbarVBox);
        mainHBox.setAlignment(Pos.CENTER);
        mainHBox.setSpacing(100);

        // Scene setup.
        root = new StackPane(backgroundImageView, difficultyStackPane);
        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(Objects.requireNonNull(TicTacToe.class.getResource("TicTacToe.css")).toString());
        stage.setTitle("Tic-Tac-Toe");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        // Difficulty button actions.
        easyButton.getButton().setOnAction(event -> setDifficulty(0));
        mediumButton.getButton().setOnAction(event -> setDifficulty(1));
        hardButton.getButton().setOnAction(event -> setDifficulty(2));

        // Tile button actions.
        gameTiles[0].getButton().setOnAction(event -> playerTurn(0));
        gameTiles[1].getButton().setOnAction(event -> playerTurn(1));
        gameTiles[2].getButton().setOnAction(event -> playerTurn(2));
        gameTiles[3].getButton().setOnAction(event -> playerTurn(3));
        gameTiles[4].getButton().setOnAction(event -> playerTurn(4));
        gameTiles[5].getButton().setOnAction(event -> playerTurn(5));
        gameTiles[6].getButton().setOnAction(event -> playerTurn(6));
        gameTiles[7].getButton().setOnAction(event -> playerTurn(7));
        gameTiles[8].getButton().setOnAction(event -> playerTurn(8));

        newGameButton.setOnAction(event -> {
            isDraw = false;
            isAIWin = false;
            isPlayerWin = false;
            currentTurn = 1;
            for (GameTile gameTile : gameTiles) gameTile.reset();
            winLabel.setVisible(false);
            loseLabel.setVisible(false);
            drawLabel.setVisible(false);

            if (isPlayerFirst) {
                isPlayerFirst = false;
                aiTurn();
            }
            else isPlayerFirst = true;
        });
    }

    private void aiTurn() {
        int aiChoice;
        int bestScore = -99;
        int miniMaxScore;
        LinkedList<Integer> bestChoices = new LinkedList<>();

        // If the game difficulty is set to easy, the AI pics a random tile.
        // If the game difficulty is set to hard, the AI will attempt to start with center.
        // The AI uses the mini-max function to assess all outcomes and pick the optimal tile.
        if (difficulty == 0) {
            aiChoice = random.nextInt(gameTiles.length);
            if (!gameTiles[aiChoice].isPlayer() && !gameTiles[aiChoice].isAI()) {
                gameTiles[aiChoice].select(false);
                evaluateTurn();
            }
            else aiTurn();
        }
        else {
            if (difficulty == 2 && (currentTurn <= 2) && !gameTiles[4].isAI() && !gameTiles[4].isPlayer()) gameTiles[4].select(false);
            else {
                for (int i = 0; i < gameTiles.length; i++) {
                    if (!gameTiles[i].isPlayer() && !gameTiles[i].isAI()) {
                        miniMaxScore = miniMax(true, gameTiles, i, currentTurn, 0);
                        if (miniMaxScore >= bestScore) {
                            if (miniMaxScore != bestScore) {
                                bestChoices.clear();
                                bestScore = miniMaxScore;
                            }
                            bestChoices.add(i);
                        }
                    }
                }
                aiChoice = bestChoices.get(random.nextInt(bestChoices.size()));
                gameTiles[aiChoice].select(false);
            }
            evaluateTurn();
        }
    }

    private void endGame() {
        for (GameTile gameTile : gameTiles) gameTile.getButton().setDisable(true);
        if (isDraw) {
            totalDraws++;
            totalDrawsLabel.setText(Integer.toString(totalDraws));
            drawLabel.setVisible(true);
        }
        else if (isAIWin) {
            totalLosses++;
            totalLossesLabel.setText(Integer.toString(totalLosses));
            loseLabel.setVisible(true);
        }
        else if (isPlayerWin) {
            totalWins++;
            totalWinsLabel.setText(Integer.toString(totalWins));
            winLabel.setVisible(true);
        }
    }

    private void evaluateTurn() {

        // Determine if either the player or computer won.
        if (gameTiles[0].isAI() && gameTiles[1].isAI() && gameTiles[2].isAI()) isAIWin = true;
        else if (gameTiles[0].isAI() && gameTiles[4].isAI() && gameTiles[8].isAI()) isAIWin = true;
        else if (gameTiles[0].isAI() && gameTiles[3].isAI() && gameTiles[6].isAI()) isAIWin = true;
        else if (gameTiles[8].isAI() && gameTiles[5].isAI() && gameTiles[2].isAI()) isAIWin = true;
        else if (gameTiles[8].isAI() && gameTiles[7].isAI() && gameTiles[6].isAI()) isAIWin = true;
        else if (gameTiles[2].isAI() && gameTiles[4].isAI() && gameTiles[6].isAI()) isAIWin = true;
        else if (gameTiles[1].isAI() && gameTiles[4].isAI() && gameTiles[7].isAI()) isAIWin = true;
        else if (gameTiles[3].isAI() && gameTiles[4].isAI() && gameTiles[5].isAI()) isAIWin = true;
        else if (gameTiles[0].isPlayer() && gameTiles[1].isPlayer() && gameTiles[2].isPlayer()) isPlayerWin = true;
        else if (gameTiles[0].isPlayer() && gameTiles[4].isPlayer() && gameTiles[8].isPlayer()) isPlayerWin = true;
        else if (gameTiles[0].isPlayer() && gameTiles[3].isPlayer() && gameTiles[6].isPlayer()) isPlayerWin = true;
        else if (gameTiles[8].isPlayer() && gameTiles[5].isPlayer() && gameTiles[2].isPlayer()) isPlayerWin = true;
        else if (gameTiles[8].isPlayer() && gameTiles[7].isPlayer() && gameTiles[6].isPlayer()) isPlayerWin = true;
        else if (gameTiles[2].isPlayer() && gameTiles[4].isPlayer() && gameTiles[6].isPlayer()) isPlayerWin = true;
        else if (gameTiles[1].isPlayer() && gameTiles[4].isPlayer() && gameTiles[7].isPlayer()) isPlayerWin = true;
        else if (gameTiles[3].isPlayer() && gameTiles[4].isPlayer() && gameTiles[5].isPlayer()) isPlayerWin = true;

        // Determine if game is a draw.
        currentTurn++;
        if (currentTurn == 10 && !isPlayerWin && !isAIWin) isDraw = true;

        // If the player or computer won or a draw occurred, end the game.
        if (isAIWin || isPlayerWin || isDraw) endGame();
    }

    private int miniMax(boolean isMaximizing, GameTile[] tiles, int currentTile, int cTurn, int simRound) {

        // AI being driven by the mini-max algorithm. If on medium, the AI will only look two turns ahead.
        GameTile[] gTiles = new GameTile[9];
        boolean isMiniMaxAIWin = false;
        boolean isMiniMaxPlayerWin = false;
        boolean isMiniMaxDraw = false;
        int bestScore;
        int miniMaxScore;
        int totalFreeTiles = 0;

        for (int i = 0; i < gTiles.length; i++) {
            gTiles[i] = new GameTile();
            if (tiles[i].isAI()) gTiles[i].select(false);
            else if (tiles[i].isPlayer()) gTiles[i].select(true);
            else totalFreeTiles++;
        }

        gTiles[currentTile].select(!isMaximizing);

        if (gTiles[0].isAI() && gTiles[1].isAI() && gTiles[2].isAI()) isMiniMaxAIWin = true;
        else if (gTiles[0].isAI() && gTiles[4].isAI() && gTiles[8].isAI()) isMiniMaxAIWin = true;
        else if (gTiles[0].isAI() && gTiles[3].isAI() && gTiles[6].isAI()) isMiniMaxAIWin = true;
        else if (gTiles[8].isAI() && gTiles[5].isAI() && gTiles[2].isAI()) isMiniMaxAIWin = true;
        else if (gTiles[8].isAI() && gTiles[7].isAI() && gTiles[6].isAI()) isMiniMaxAIWin = true;
        else if (gTiles[2].isAI() && gTiles[4].isAI() && gTiles[6].isAI()) isMiniMaxAIWin = true;
        else if (gTiles[1].isAI() && gTiles[4].isAI() && gTiles[7].isAI()) isMiniMaxAIWin = true;
        else if (gTiles[3].isAI() && gTiles[4].isAI() && gTiles[5].isAI()) isMiniMaxAIWin = true;
        else if (gTiles[0].isPlayer() && gTiles[1].isPlayer() && gTiles[2].isPlayer()) isMiniMaxPlayerWin = true;
        else if (gTiles[0].isPlayer() && gTiles[4].isPlayer() && gTiles[8].isPlayer()) isMiniMaxPlayerWin = true;
        else if (gTiles[0].isPlayer() && gTiles[3].isPlayer() && gTiles[6].isPlayer()) isMiniMaxPlayerWin = true;
        else if (gTiles[8].isPlayer() && gTiles[5].isPlayer() && gTiles[2].isPlayer()) isMiniMaxPlayerWin = true;
        else if (gTiles[8].isPlayer() && gTiles[7].isPlayer() && gTiles[6].isPlayer()) isMiniMaxPlayerWin = true;
        else if (gTiles[2].isPlayer() && gTiles[4].isPlayer() && gTiles[6].isPlayer()) isMiniMaxPlayerWin = true;
        else if (gTiles[1].isPlayer() && gTiles[4].isPlayer() && gTiles[7].isPlayer()) isMiniMaxPlayerWin = true;
        else if (gTiles[3].isPlayer() && gTiles[4].isPlayer() && gTiles[5].isPlayer()) isMiniMaxPlayerWin = true;

        cTurn++;
        if (cTurn == 10 && !isMiniMaxPlayerWin && !isMiniMaxAIWin) isMiniMaxDraw = true;

        if (isMiniMaxAIWin) bestScore = totalFreeTiles;
        else if (isMiniMaxPlayerWin) bestScore = totalFreeTiles * -1;
        else if (isMiniMaxDraw) bestScore = 0;
        else {
            if (isMaximizing) bestScore = 99;
            else bestScore = -99;
            for (int i = 0; i < gTiles.length; i++) {
                if (!gTiles[i].isAI() && !gTiles[i].isPlayer()) {
                    if (difficulty == 2 || simRound < 2) {
                        if (isMaximizing) {
                            miniMaxScore = miniMax(false, gTiles, i, cTurn, simRound + 1);
                            if (miniMaxScore <= bestScore) bestScore = miniMaxScore;
                        } else {
                            miniMaxScore = miniMax(true, gTiles, i, cTurn, simRound + 1);
                            if (miniMaxScore >= bestScore) bestScore = miniMaxScore;
                        }
                    }
                }
            }
        }

        return bestScore;
    }

    private void playerTurn(int tile) {
        gameTiles[tile].select(true);
        evaluateTurn();
        if (!isPlayerWin && !isAIWin && !isDraw) aiTurn();
    }

    private void setDifficulty(int i) {
        difficulty = i;
        root.getChildren().remove(1);
        root.getChildren().add(mainHBox);
    }

    public static void main(String[] args) {
        launch();
    }
}
