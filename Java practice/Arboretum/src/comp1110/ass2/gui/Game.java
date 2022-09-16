package Arboretum.out.test.comp1110;

import comp1110.ass2.Arboretum;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

import static java.lang.Math.sqrt;

public class Game extends Application {
    /* board layout */
    private static final int BOARD_WIDTH = 1200;
    private static final int BOARD_HEIGHT = 700;
    private final double WINDOW_XOFFSET = 15.0;
    private final double WINDOW_YOFFSET = 16.6;
    private final int CARD_WIDTH = 76;
    private final int CARD_HEIGHT = 120;
    private final Group root = new Group();
    private final Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
    private final Button deckButton = new Button("", new Card("00"));
    private final Button playerADiscardButton = new Button();
    private final Button playerBDiscardButton = new Button();
    private final HBox playerAHandPane = new HBox();
    private final HBox playerBHandPane = new HBox();
    private GridPane playerAArboretumPane = new GridPane();
    private GridPane playerBArboretumPane = new GridPane();
    private String[][] gameState = new String[][]{{"A", "A", "A", "B", "B"}, {"a1a2a3a4a5a6a7a8b1b2b3b4b5b6b7b8c1c2c3c4c5c6c7c8d1d2d3d4d5d6d7d8j1j2j3j4j5j6j7j8m1m2m3m4m5m6m7m8", "A", "B"}};
    private int drawCount;
    private boolean havePlaced;
    private boolean haveDiscarded;
    private boolean haveAI;
    private PositionCard highlightedCard;

    private double ax, ay, bx, by;
    private final Set<Node> highlightedPane = new HashSet<>();

    @Override
    public void start(Stage stage) {
        // initialize the position of each part
        displayStart();
        initializeBoardPosition();

        deckButton.setOnMouseClicked(event -> {
            if (drawCount < 2 && gameState[1][0].length() > 0) {
                drawFromDeck();
            }

        });
        playerADiscardButton.setOnMouseClicked(event -> {
            if (drawCount < 2 && gameState[0][2].length() > 1) {
                drawFromDiscard(2);
            }
        });
        playerBDiscardButton.setOnMouseClicked(event -> {
            if (drawCount < 2 && gameState[0][4].length() > 1) {
                drawFromDiscard(4);
            }
        });

        stage.setTitle("Arboretum");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * initialize the game state
     * fix the position of each part, including deck, hand, arboretum and discard pile
     *
     * @author Weiqiang PU
     * @author Ziyao Jin
     */
    private void initializeBoardPosition() {
        root.getChildren().clear();
        // initialize hand cards
        for (int count = 0; count < 7; count++) {
            ArrayList<String> cardList = new ArrayList<>(Arrays.asList(Arboretum.getValueFromHidden(gameState[1])[0]));
            StringBuilder tempDeck = new StringBuilder(gameState[1][0]);
            for (int player = 1; player < 3; player++) {
                String drawnCard = cardList.get((int) (Math.random() * cardList.size())); //draw card randomly
                cardList.remove(drawnCard);
                tempDeck.delete(tempDeck.indexOf(drawnCard), tempDeck.indexOf(drawnCard) + 2); //delete the drawn card from the deck
                gameState[1][player] += drawnCard;
            }
            gameState[1][0] = tempDeck.toString();
        }
        // alphabetize hand
        for (int player = 1; player < 3; player++) {
            ArrayList<String> handStrList = new ArrayList<>(Arrays.asList(Arboretum.getValueFromHidden(gameState[1])[player]));
            Collections.sort(handStrList);
            StringBuilder tempHand = new StringBuilder(String.valueOf((char) (player + 64)));
            for (String i : handStrList) {
                tempHand.append(i);
            }
            gameState[1][player] = tempHand.toString();
        }
        // set pane size and positions
        Label deckLabel = new Label("Deck");
        deckLabel.setLayoutX((double) (BOARD_WIDTH + CARD_WIDTH) / 2 + WINDOW_XOFFSET);
        deckLabel.setLayoutY((double) BOARD_HEIGHT / 2);
        deckButton.setLayoutX((double) (BOARD_WIDTH - CARD_WIDTH) / 2);
        deckButton.setLayoutY((double) (BOARD_HEIGHT - CARD_HEIGHT) / 2);

        playerAHandPane.setLayoutX(WINDOW_XOFFSET);
        playerAHandPane.setLayoutY(BOARD_HEIGHT - WINDOW_YOFFSET - CARD_HEIGHT);
        playerAHandPane.setPrefSize(CARD_WIDTH * 9, CARD_HEIGHT);
        playerAHandPane.setAlignment(Pos.CENTER);
        Label playerAHandLabel = new Label("PlayerA Hand");
        playerAHandLabel.setLayoutX(WINDOW_XOFFSET + CARD_WIDTH * 3.5);
        playerAHandLabel.setLayoutY(BOARD_HEIGHT - WINDOW_YOFFSET);

        playerBHandPane.setLayoutX(BOARD_WIDTH - WINDOW_XOFFSET - CARD_WIDTH * 9);
        playerBHandPane.setLayoutY(WINDOW_YOFFSET);
        playerBHandPane.setPrefSize(CARD_WIDTH * 9, CARD_HEIGHT);
        playerBHandPane.setAlignment(Pos.CENTER);
        Label playerBHandLabel = new Label("PlayerB Hand");
        playerBHandLabel.setLayoutX(BOARD_WIDTH - WINDOW_XOFFSET - CARD_WIDTH * 5);

        playerADiscardButton.setLayoutX((double) (BOARD_WIDTH - CARD_WIDTH) / 2);
        playerADiscardButton.setLayoutY((double) (BOARD_HEIGHT - CARD_HEIGHT) / 2 + CARD_HEIGHT + WINDOW_YOFFSET);
        playerADiscardButton.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        Label playerADiscardLabel = new Label("PlayerA Discard");
        playerADiscardLabel.setLayoutX((double) (BOARD_WIDTH - CARD_WIDTH) / 2);
        playerADiscardLabel.setLayoutY((double) BOARD_HEIGHT / 2 + WINDOW_YOFFSET + CARD_HEIGHT * 1.5);

        playerBDiscardButton.setLayoutX((double) (BOARD_WIDTH - CARD_WIDTH) / 2);
        playerBDiscardButton.setLayoutY((double) (BOARD_HEIGHT - CARD_HEIGHT) / 2 - CARD_HEIGHT - WINDOW_YOFFSET);
        playerBDiscardButton.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        Label playerBDiscardLabel = new Label("PlayerB Discard");
        playerBDiscardLabel.setLayoutX((double) (BOARD_WIDTH - CARD_WIDTH) / 2);
        playerBDiscardLabel.setLayoutY((double) BOARD_HEIGHT / 2 - WINDOW_YOFFSET * 2 - CARD_HEIGHT * 1.5);

        playerAArboretumPane.setLayoutX(WINDOW_XOFFSET);
        playerAArboretumPane.setLayoutY(WINDOW_YOFFSET);
        playerAArboretumPane.setPrefSize(BOARD_WIDTH - WINDOW_XOFFSET * 3 - CARD_WIDTH * 9, BOARD_HEIGHT - WINDOW_YOFFSET * 3 - CARD_HEIGHT);
        playerAArboretumPane.setAlignment(Pos.CENTER);
        Label playerAArboretumLabel = new Label("PlayerA Arboretum");
        playerAArboretumLabel.setLayoutX(WINDOW_XOFFSET + CARD_WIDTH * 2);

        playerBArboretumPane.setLayoutX(WINDOW_XOFFSET * 2 + CARD_WIDTH * 9);
        playerBArboretumPane.setLayoutY(WINDOW_YOFFSET * 2 + CARD_HEIGHT);
        playerBArboretumPane.setPrefSize(BOARD_WIDTH - WINDOW_XOFFSET * 3 - CARD_WIDTH * 9, BOARD_HEIGHT - WINDOW_YOFFSET * 3 - CARD_HEIGHT);
        playerBArboretumPane.setAlignment(Pos.CENTER);
        Label playerBArboretumLabel = new Label("PlayerB Arboretum");
        playerBArboretumLabel.setLayoutX(BOARD_WIDTH - WINDOW_XOFFSET - CARD_WIDTH * 4);
        playerBArboretumLabel.setLayoutY(BOARD_HEIGHT - WINDOW_YOFFSET);

        deckButton.setStyle("-fx-background-color: transparent");
        playerADiscardButton.setStyle("-fx-background-color: transparent");
        playerBDiscardButton.setStyle("-fx-background-color: transparent");
        playerAHandPane.setStyle("-fx-border-color: #6d8a8a");
        playerBHandPane.setStyle("-fx-border-color: #6d8a8a");
        playerAArboretumPane.setStyle("-fx-border-color: #6d8a8a");
        playerBArboretumPane.setStyle("-fx-border-color: #6d8a8a");

        root.getChildren().addAll(playerAArboretumPane, playerBArboretumPane,
                playerADiscardButton, playerBDiscardButton, deckButton, deckLabel, playerAHandLabel, playerBHandLabel,
                playerADiscardLabel, playerBDiscardLabel, playerAArboretumLabel, playerBArboretumLabel, playerAHandPane, playerBHandPane);

        ax = playerAArboretumPane.getBoundsInParent().getMinX();
        ay = playerAArboretumPane.getBoundsInParent().getMinY();
        bx = playerBArboretumPane.getBoundsInParent().getMinX();
        by = playerBArboretumPane.getBoundsInParent().getMinY();
        // set default shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        for (var i : root.getChildren()) {
            i.setEffect(dropShadow);
        }
        // create first move highlight
        Set<Node> newHighlight = new HashSet<>();
        newHighlight.add(deckButton);
        newHighlight.add(playerADiscardButton);
        newHighlight.add(playerBDiscardButton);
        highlightPane(newHighlight);

        drawGame(gameState);
    }

    /**
     * highlight the corresponding panes to show the current procedure of the game
     *
     * @param paneSet a set of pane waiting for highlighting
     * @author Ziyao Jin
     */
    private void highlightPane(Set<Node> paneSet) {
        // create default shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        // create a new shadow effect
        DropShadow highlight = new DropShadow();
        highlight.setRadius(20.0);
        highlight.setOffsetX(3.0);
        highlight.setOffsetY(3.0);
        highlight.setColor(Color.web("900021"));
        // set previous panes to default effect
        if (!highlightedPane.isEmpty()) {
            for (var i : highlightedPane) i.setEffect(dropShadow);
            highlightedPane.clear();
        }
        // set highlight panes
        highlightedPane.addAll(paneSet);
        for (var i : highlightedPane) i.setEffect(highlight);
    }

    /**
     * Draw the current state of the game after each interaction
     * First, clear the current board
     * Second, draw the new board
     * Finally, check canScore().
     *
     * @author Ziyao Jin
     * @author Weiqiang PU
     */
    private void drawGame(String[][] gameState) {
        //if (Arboretum.isStateValid(gameState)) {
        // Clear previous content
        playerAHandPane.getChildren().clear();
        playerBHandPane.getChildren().clear();
        playerAArboretumPane.getChildren().clear();
        playerBArboretumPane.getChildren().clear();

        if (gameState[0][2].length() > 1)
            playerADiscardButton.setGraphic(new Card(gameState[0][2].substring(gameState[0][2].length() - 2)));
        else playerADiscardButton.setGraphic(new Card("00"));
        if (gameState[0][4].length() > 1)
            playerBDiscardButton.setGraphic(new Card(gameState[0][4].substring(gameState[0][4].length() - 2)));
        else playerBDiscardButton.setGraphic(new Card("00"));

        // Hand A
        String handAStr = gameState[1][1];
        for (int i = 1; i < handAStr.length(); i += 2) {
            DraggableCard newCard = new DraggableCard(handAStr.substring(i, i + 2));
            playerAHandPane.getChildren().add(newCard);
        }
        // Hand B
        String handBStr = gameState[1][2];
        for (int i = 1; i < handBStr.length(); i += 2) {
            if (haveAI) {
                Card newCard = new Card("00"); //this line uses when computer opponent
                playerBHandPane.getChildren().add(newCard);
            } else {
                DraggableCard newCard = new DraggableCard(handBStr.substring(i, i + 2)); //this line uses when human player
                playerBHandPane.getChildren().add(newCard);
            }
        }
        // Arboretum A
        String arboretumAStr = gameState[0][1];
        drawArboretum(arboretumAStr, playerAArboretumPane);
        // Arboretum B
        String arboretumBStr = gameState[0][3];
        drawArboretum(arboretumBStr, playerBArboretumPane);
        //check whether match the condition to change round
        switchRound();
    }

    /**
     * Game mode selection interface
     *
     * @author Hanqin Liu
     */
    private void displayStart() {
        Stage window = new Stage();
        window.setTitle("Game Mode");

        window.initModality(Modality.APPLICATION_MODAL);
        window.setWidth(300);
        window.setMinHeight(150);

        Button buttonPlayer = new Button("Player A vs. Player B");
        Button buttonAI = new Button("Player vs. AI");
        buttonPlayer.setOnAction(e -> window.close());
        buttonAI.setOnAction(e -> {
            window.close();
            haveAI = true;
        });

        Label label = new Label();
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, buttonPlayer, buttonAI);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 150);
        window.setScene(scene);
        //Use showAndWait() to process the window first, while if not, the window in main cannot respond
        window.showAndWait();
    }

    /**
     * Display the ending and provide players with options.
     *
     * @param message Messages displayed in the window.
     * @author Ziyao Jin
     * @author Hanqin Liu
     */
    private void displayEnd(String message) {
        Stage window = new Stage();
        window.setTitle("Game End");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);
        window.setMinHeight(160);
        // quit button
        Button buttonQuit = new Button("Quit");
        buttonQuit.setOnAction(e -> {
            window.close();
            Stage stage = (Stage) playerAArboretumPane.getScene().getWindow();
            stage.close();
        });
        // restart button
        Button buttonRestart = new Button("Restart");
        buttonRestart.setOnAction(e -> {
            window.close();
            Stage stage = (Stage) playerAArboretumPane.getScene().getWindow();
            stage.close();
            gameState = new String[][]{{"A", "A", "A", "B", "B"}, {"a1a2a3a4a5a6a7a8b1b2b3b4b5b6b7b8c1c2c3c4c5c6c7c8d1d2d3d4d5d6d7d8j1j2j3j4j5j6j7j8m1m2m3m4m5m6m7m8", "A", "B"}};
            haveAI = false;
            playerAArboretumPane = new GridPane();
            playerBArboretumPane = new GridPane();
            start(new Stage());
        });

        Label label = new Label(message);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, buttonRestart, buttonQuit);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        //Use showAndWait() to process the window first, while if not, the window in main cannot respond
        window.showAndWait();
    }

    /**
     * draw arboretum in the corresponding grid pane
     * @param arboretumAStr a string contains arboretum card information
     * @param arboretumPane desired pane
     * @author Ziyao Jin
     */
    private void drawArboretum(String arboretumAStr, GridPane arboretumPane) {
        // clear previous translate
        arboretumPane.setTranslateX(0);
        arboretumPane.setTranslateY(0);
        // create placement strings
        String[][] gameState = new String[][]{
                {"A", arboretumAStr, "A", "B", "B"},
                {"", "", ""}};
        var highlightSet = Arboretum.getAllValidPlacements(gameState, "11");
        StringBuilder arboretumAStrBuilder = new StringBuilder(arboretumAStr);
        for (var i : highlightSet) {
            arboretumAStrBuilder.append(i);
        }
        arboretumAStr = arboretumAStrBuilder.toString();
        // find out height and width
        int biggestN = 0, biggestS = 0, biggestW = 0, biggestE = 0;
        for (int i = 1; i < arboretumAStr.length(); i += 8) {
            String v = arboretumAStr.substring(i + 3, i + 5);
            String h = arboretumAStr.substring(i + 6, i + 8);
            if (arboretumAStr.charAt(i + 2) == 'N' && Integer.parseInt(v) > biggestN) biggestN = Integer.parseInt(v);
            if (arboretumAStr.charAt(i + 2) == 'S' && Integer.parseInt(v) > biggestS) biggestS = Integer.parseInt(v);
            if (arboretumAStr.charAt(i + 5) == 'W' && Integer.parseInt(h) > biggestW) biggestW = Integer.parseInt(h);
            if (arboretumAStr.charAt(i + 5) == 'E' && Integer.parseInt(h) > biggestE) biggestE = Integer.parseInt(h);
        }
        // draw card in grid pane
        for (int i = 1; i < arboretumAStr.length(); i += 8) {
            int x = 0, y = 0;
            String v = arboretumAStr.substring(i + 3, i + 5);
            String h = arboretumAStr.substring(i + 6, i + 8);
            String species = arboretumAStr.substring(i, i + 2);
            if (arboretumAStr.charAt(i + 5) == 'W') x = biggestW - Integer.parseInt(h);
            if (arboretumAStr.charAt(i + 5) == 'E') x = biggestW + 1 + Integer.parseInt(h);
            if (arboretumAStr.charAt(i + 5) == 'C') x = biggestW + 1;
            if (arboretumAStr.charAt(i + 2) == 'N') y = biggestN - Integer.parseInt(v);
            if (arboretumAStr.charAt(i + 2) == 'S') y = biggestN + 1 + Integer.parseInt(v);
            if (arboretumAStr.charAt(i + 2) == 'C') y = biggestN + 1;
            if (species.equals("11")) {
                PositionCard newCard = new PositionCard(arboretumAStr.substring(i + 2, i + 5),
                        arboretumAStr.substring(i + 5, i + 8));
                newCard.setVisible(false);
                arboretumPane.add(newCard, x, y);
            } else {
                Card newCard = new Card(species);
                arboretumPane.add(newCard, x, y);
            }
        }
        // scale pane if height and width are larger than default setting
        var xScale = (BOARD_WIDTH - WINDOW_XOFFSET * 3 - CARD_WIDTH * 9) / ((biggestW + 1 + biggestE) * CARD_WIDTH);
        var yScale = (BOARD_HEIGHT - WINDOW_YOFFSET * 3 - CARD_HEIGHT) / ((biggestN + 1 + biggestS) * CARD_HEIGHT);
        if (xScale < 1) arboretumPane.setScaleX(xScale);
        if (yScale < 1) arboretumPane.setScaleY(yScale);
        // add translate to make pane stay at the same location after scaling
        double xTrans, yTrans;
        if (arboretumAStr.charAt(0) == 'A') {
            xTrans = ax - arboretumPane.getBoundsInParent().getMinX();
            yTrans = ay - arboretumPane.getBoundsInParent().getMinY();
        } else {
            xTrans = bx - arboretumPane.getBoundsInParent().getMinX();
            yTrans = by - arboretumPane.getBoundsInParent().getMinY();
        }
        if (xScale < 1) arboretumPane.setTranslateX(xTrans);
        if (yScale < 1) arboretumPane.setTranslateY(yTrans);
    }

    /**
     * Find the nearest placeable card position
     *
     * @param x    coordinate
     * @param y    coordinate
     * @param pane desired pane
     * @return the nearest card node
     * @author Ziyao Jin
     */
    private PositionCard findNearestCardPosition(GridPane pane, double x, double y) {
        double initial = Double.MAX_VALUE;
        PositionCard nearestCard = null;
        // find the nearest card in grid pane
        for (var i : pane.getChildren()) {
            if (i.getClass() == PositionCard.class) {
                var position = i.getLocalToSceneTransform().transform(
                        i.getLayoutBounds().getCenterX(),
                        i.getLayoutBounds().getCenterY()
                );
                // calculate distance
                var distance = sqrt((x - position.getX()) * (x - position.getX())
                        + (y - position.getY()) * (y - position.getY()));
                if (distance < initial) {
                    initial = distance;
                    nearestCard = (PositionCard) i;
                }
            }
        }
        return nearestCard;
    }

    /**
     * Highlight the nearest placeable card position
     *
     * @param pane desired pane
     * @param x    coordinate
     * @param y    coordinate
     * @author Ziyao Jin
     */
    private void highlightNearestCardPosition(GridPane pane, double x, double y) {
        // clear previous highlight
        if (highlightedCard != null) {
            highlightedCard.setVisible(false);
            highlightedCard.setFill(Color.WHITE);
        }
        // set new highlight
        highlightedCard = findNearestCardPosition(pane, x, y);
        highlightedCard.setFill(Color.web("82D8CF"));
        highlightedCard.setVisible(true);
    }

    /**
     * Draw a random card from the deck.
     * gameState[1] is the array of hidden state
     * gameState[2] is the array of shared state
     * Update game state: remove the drawn card from the deck, then add the drawn card into player's hand
     *
     * @author Weiqiang PU
     */
    private void drawFromDeck() {
        ArrayList<String> cardList = new ArrayList<>(Arrays.asList(Arboretum.getValueFromHidden(gameState[1])[0]));
        String drawnCard = cardList.get((int) (Math.random() * cardList.size())); //draw card randomly
        StringBuilder temp = new StringBuilder(gameState[1][0]);
        temp.delete(temp.indexOf(drawnCard), temp.indexOf(drawnCard) + 2); //delete the drawn card from the deck
        gameState[1][0] = temp.toString();
        addToHand(drawnCard);
        drawCount++;
        highlightPlace();
        drawGame(gameState);
    }

    /**
     * Draw the top card from the clicked discard.
     * gameState[1] is the array of hidden state
     * gameState[2] is the array of shared state
     * Update game state: remove the drawn card from the clicked discard pile, then add the drawn card into player's hand
     *
     * @param drawDiscardControl to control draw from which discard pile
     * @author Weiqiang PU
     */
    private void drawFromDiscard(int drawDiscardControl) {
        String drawnCard = gameState[0][drawDiscardControl].substring(gameState[0][drawDiscardControl].length() - 2);
        gameState[0][drawDiscardControl] = gameState[0][drawDiscardControl].substring(0, gameState[0][drawDiscardControl].length() - 2);
        addToHand(drawnCard);
        drawCount++;
        highlightPlace();
        drawGame(gameState);
    }

    private void highlightPlace() {
        if (drawCount == 2) {
            Set<Node> newHighlight = new HashSet<>();
            switch (gameState[0][0]) {
                case "A" -> {
                    newHighlight.add(playerAHandPane);
                    newHighlight.add(playerAArboretumPane);
                }
                case "B" -> {
                    newHighlight.add(playerBHandPane);
                    newHighlight.add(playerBArboretumPane);
                }
            }
            highlightPane(newHighlight);
        }
    }

    /**
     * add card to hand and alphabetize the hand
     *
     * @param drawnCard card needed to add
     * @author Weiqiang PU
     */
    private void addToHand(String drawnCard) {
        int turnID = 0;
        String playerID = "";
        switch (gameState[0][0]) {
            case "A" -> {
                turnID = 1;
                playerID = "A";
            }
            case "B" -> {
                turnID = 2;
                playerID = "B";
            }
        }
        ArrayList<String> handStrList = new ArrayList<>();
        for (int i = 1; i < gameState[1][turnID].length(); i += 2) {
            handStrList.add(gameState[1][turnID].substring(i, i + 2));
        }
        handStrList.add(drawnCard);
        Collections.sort(handStrList);
        StringBuilder tempHand = new StringBuilder(playerID);
        for (String i : handStrList) {
            tempHand.append(i);
        }
        gameState[1][turnID] = tempHand.toString();
    }

    /**
     * Discard a selected card from the hand.
     * gameState[1] is the array of hidden state
     * gameState[2] is the array of shared state
     * Update game state: remove the discard from the hand, then add the card into player's discard pile
     *
     * @param species discard
     * @author Weiqiang PU
     * @author Ziyao Jin
     * @author Hanqin Liu
     */
    private void discard(String species) {
        switch (gameState[0][0]) {
            case "A" -> {
                if (gameState[1][1].contains(species)) {
                    gameState[0][2] += species;
                    removeFromHand(species, "A");
                    haveDiscarded = true;
                }
            }
            case "B" -> {
                if (gameState[1][2].contains(species)) {
                    gameState[0][4] += species;
                    removeFromHand(species, "B");
                    haveDiscarded = true;
                }
            }
        }
        Set<Node> newHighlight = new HashSet<>();
        newHighlight.add(deckButton);
        newHighlight.add(playerADiscardButton);
        newHighlight.add(playerBDiscardButton);
        highlightPane(newHighlight);
        drawGame(gameState);

        //check whether match the end condition
        if (gameState[1][0].length() == 0) {
            displayEnd("Player A: " + Arboretum.getTotalViablePathScore(gameState, 'A') + "\n" + "Player B: "
                    + Arboretum.getTotalViablePathScore(gameState, 'B') + "\n" + Arboretum.getWinner(gameState));
        }
    }

    private class DraggableCard extends Card {
        private double mouseX;
        private double mouseY;

        /**
         * a draggable card constructor with dragged, released event
         *
         * @param species card value and species
         * @author Ziyao Jin
         * @author Weiqiang PU
         * @author Hanqin Liu
         */
        DraggableCard(String species) {
            super(species);
            setOnMousePressed(event -> {
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });
            setOnMouseDragged(event -> {
                if (!havePlaced && drawCount == 2) {
                    switch (gameState[0][0]) {
                        case "A" -> {
                            if (getParent() == playerAHandPane) highlightNearestCardPosition(playerAArboretumPane,
                                    event.getSceneX(), event.getSceneY());
                        }
                        case "B" -> {
                            if (getParent() == playerBHandPane) highlightNearestCardPosition(playerBArboretumPane,
                                    event.getSceneX(), event.getSceneY());
                        }
                    }
                }
                setTranslateX(event.getSceneX() - mouseX);
                setTranslateY(event.getSceneY() - mouseY);
            });
            //putting the card in the nearest placeable position or discard pile when releasing mouse and update game state
            setOnMouseReleased(event -> {
                if (!havePlaced && drawCount == 2) {
                    String newPlacement = species + highlightedCard.getPosV() + highlightedCard.getPosH();
                    try {
                        if (gameState[0][0].equals("A") && getParent() == playerAHandPane) {
                            Point2D pos = playerAArboretumPane.getLocalToSceneTransform().inverseTransform(event.getSceneX(), event.getSceneY());
                            if (playerAArboretumPane.contains(pos)) {
                                playerAHandPane.getChildren().remove(this);
                                gameState[0][1] += newPlacement;
                                removeFromHand(species, "A");
                                havePlaced = true;
                                Set<Node> newHighlight = new HashSet<>();
                                newHighlight.add(playerAHandPane);
                                newHighlight.add(playerADiscardButton);
                                highlightPane(newHighlight);
                            }
                        } else if (gameState[0][0].equals("B") && getParent() == playerBHandPane) {
                            Point2D pos = playerBArboretumPane.getLocalToSceneTransform().inverseTransform(event.getSceneX(), event.getSceneY());
                            if (playerBArboretumPane.contains(pos)) {
                                playerBHandPane.getChildren().remove(this);
                                gameState[0][3] += newPlacement;
                                removeFromHand(species, "B");
                                havePlaced = true;
                                Set<Node> newHighlight = new HashSet<>();
                                newHighlight.add(playerBHandPane);
                                newHighlight.add(playerBDiscardButton);
                                highlightPane(newHighlight);
                            }
                        }
                    } catch (NonInvertibleTransformException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        if (!haveDiscarded & drawCount == 2) {
                            if (Objects.equals(gameState[0][0], "A")) {
                                Point2D pos = playerADiscardButton.getLocalToSceneTransform().inverseTransform(
                                        event.getSceneX(), event.getSceneY());
                                if (playerADiscardButton.contains(pos)) discard(species);
                            } else {
                                Point2D pos = playerBDiscardButton.getLocalToSceneTransform().inverseTransform(
                                        event.getSceneX(), event.getSceneY());
                                if (playerBDiscardButton.contains(pos)) discard(species);
                            }
                        }
                    } catch (NonInvertibleTransformException e) {
                        throw new RuntimeException(e);
                    }
                }
                drawGame(gameState);
            });
        }
    }

    /**
     * remove card from hand, update and alphabetize the hand string
     *
     * @param species string of card
     * @param player  the sign of which player
     * @author Ziyao Jin
     */
    private void removeFromHand(String species, String player) {
        int turn;
        if (Objects.equals(player, "A")) {
            turn = 1;
        } else {
            turn = 2;
        }
        ArrayList<String> handStrList = new ArrayList<>();
        for (int i = 1; i < gameState[1][turn].length(); i += 2) {
            handStrList.add(gameState[1][turn].substring(i, i + 2));
        }
        handStrList.remove(species);
        Collections.sort(handStrList);
        StringBuilder updatedStr = new StringBuilder(player);
        for (var i : handStrList) {
            updatedStr.append(i);
        }
        gameState[1][turn] = updatedStr.toString();
        drawGame(gameState);
    }

    /**
     * Switch the round and initialize the state of each round
     *
     * @author Weiqiang PU
     */
    private void switchRound() {
        if (havePlaced && haveDiscarded && drawCount == 2) {
            havePlaced = false;
            haveDiscarded = false;
            drawCount = 0;
            switch (gameState[0][0]) {
                case "A" -> {
                    gameState[0][0] = "B";
                    if (haveAI) computerOpponentMove();
                }
                case "B" -> gameState[0][0] = "A";
            }
        }
    }

    /**
     * generate the computer opponent's move
     *
     * @author Weiqiang PU
     */
    private void computerOpponentMove() {
        if (gameState[1][0].length() != 0) {
            for (int a = 0; a < 2; a++) {
                String drawString = Arboretum.advancedAiLocation(gameState);
                if (Objects.equals(drawString, "D")) drawFromDeck();
                else {
                    if (gameState[0][2].contains(drawString))
                        drawFromDiscard(2);
                    else if (gameState[0][4].contains(drawString))
                        drawFromDiscard(4);
                }
            }
            String[] moveString = Arboretum.advancedAiMove(gameState);
            removeFromHand(moveString[0].substring(0, 2), "B");
            gameState[0][3] = gameState[0][3] + moveString[0];
            havePlaced = true;
            discard(moveString[1]);
        }
    }
}