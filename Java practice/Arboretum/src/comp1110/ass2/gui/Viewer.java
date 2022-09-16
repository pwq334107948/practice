package Arboretum.out.test.comp1110;


import comp1110.ass2.Arboretum;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;
    private static final int GRID_SIZE = 50;
    private static final int GRID_DIMENSION = 10;
    private static final int WINDOW_XOFFSET = 10;
    private static final int WINDOW_YOFFSET = 30;
    private static final int TEXTBOX_WIDTH = 120;

    private final Group root = new Group();
    private final Group controls = new Group();
    private final ScrollPane state = new ScrollPane();
    private final StackPane deck = new StackPane();
    private final HBox playerAHand = new HBox();
    private final HBox playerBHand = new HBox();
    private final HBox PlayerADiscard = new HBox(); //need to change to Stackpane in real game
    private final HBox PlayerBDiscard = new HBox(); //need to change to Stackpane in real game
    private final GridPane PlayerAArboretum = new GridPane();
    private final GridPane PlayerBArboretum = new GridPane();

    private TextField turnIDTextField;
    private TextField aArboretumTextField;
    private TextField bArboretumTextField;
    private TextField aDiscardTextField;
    private TextField bDiscardTextField;
    private TextField deckTextField;
    private TextField aHandTextField;
    private TextField bHandTextField;

    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param gameState TASK 6
     */
    void displayState(String[][] gameState) {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(1050, 680);

        // check whether the string is well-formed
        if (!Arboretum.isSharedStateWellFormed(gameState[0]) && !Arboretum.isHiddenStateWellFormed(gameState[1]) && !Arboretum.isStateValid(gameState)) { // if not, display error message
            Label errorMessage = new Label("Invalid input!\nPlease re-enter");
            errorMessage.setTextFill(Color.RED);
            errorMessage.setFont(Font.font("", 120));
            vbox.getChildren().add(errorMessage);
        } else { // if yes, display the state of each part
            String[][] sharedState = Arboretum.isSharedStateWellFormed(gameState[0]) ? Arboretum.getValueFromShared(gameState[0]) : null;
            String[][] hiddenState = Arboretum.isHiddenStateWellFormed(gameState[1]) ? Arboretum.getValueFromHidden(gameState[1]) : null;
            if (sharedState != null) { // display sharedState
                Label turnID = new Label("Player " + sharedState[0][0] + "'s Turn");
                vbox.getChildren().add(turnID);
                for (int a = 1; a < sharedState.length; a++) {
                    if (sharedState[a].length == 0) { //if this part is empty
                        Label noCard = new Label("There is no card left");
                        vbox.getChildren().add(noCard);
                    } else {
                        Label label = new Label(); // display label of each part
                        switch (a) {
                            case 1 -> {
                                label.setText("Player A Arboretum:");
                                for (int b = 0; b < sharedState[a].length; b += 3) {
                                    int x = 3;
                                    int y = 3;
                                    Card card = new Card(sharedState[a][b]);
                                    int subX = parseInt(sharedState[a][b + 1].substring(1));
                                    int subY = parseInt(sharedState[a][b + 2].substring(1));
                                    switch (sharedState[a][b + 1].charAt(0)) {
                                        case 'N' -> x -= subX;
                                        case 'S' -> x += subX;
                                    }
                                    switch (sharedState[a][b + 2].charAt(0)) {
                                        case 'W' -> y -= subY;
                                        case 'E' -> y += subY;
                                    }
                                    PlayerAArboretum.add(card, x, y);
                                }
                                PlayerAArboretum.setAlignment(Pos.CENTER);
                                vbox.getChildren().addAll(label, PlayerAArboretum);
                            }
                            case 2 -> {
                                label.setText("Player A Discard:");
                                for (int b = 0; b < sharedState[a].length; b++) {
                                    Card card = new Card(sharedState[a][b]);
                                    PlayerADiscard.getChildren().add(card);
                                }
                                vbox.getChildren().addAll(label, PlayerADiscard);
                            }
                            case 3 -> {
                                label.setText("Player B Arboretum:");
                                for (int b = 0; b < sharedState[a].length; b += 3) {
                                    int x = 3;
                                    int y = 3;
                                    Card card = new Card(sharedState[a][b]);
                                    int subY = parseInt(sharedState[a][b + 1].substring(1));
                                    int subX = parseInt(sharedState[a][b + 2].substring(1));
                                    switch (sharedState[a][b + 1].charAt(0)) {
                                        case 'N' -> y -= subY;
                                        case 'S' -> y += subY;
                                    }
                                    switch (sharedState[a][b + 2].charAt(0)) {
                                        case 'W' -> x -= subX;
                                        case 'E' -> x += subX;
                                    }
                                    PlayerBArboretum.setAlignment(Pos.CENTER);
                                    PlayerBArboretum.add(card, y, x);
                                }
                                vbox.getChildren().addAll(label, PlayerBArboretum);
                            }
                            case 4 -> {
                                label.setText("Player B Discard:");
                                for (int b = 0; b < sharedState[a].length; b++) {
                                    Card card = new Card(sharedState[a][b]);
                                    PlayerBDiscard.getChildren().add(card);
                                }
                                vbox.getChildren().addAll(label, PlayerBDiscard);
                            }
                            case 5 -> label.setText("Player C Arboretum:");
                            case 6 -> label.setText("Player C Discard:");
                            case 7 -> label.setText("Player D Arboretum:");
                            case 8 -> label.setText("Player D Discard:");
                        }
                    }
                }
            }
            if (hiddenState != null) { //display hidden state
                for (int a = 0; a < hiddenState.length; a++) {
                    Label label = new Label(); // display label of the part
                    switch (a) {
                        case 0 -> label.setText("Deck:");
                        case 1 -> label.setText("Player A's Hand:");
                        case 2 -> label.setText("Player B's Hand:");
                        case 3 -> label.setText("Player C's Hand:");
                        case 4 -> label.setText("Player D's Hand:");
                    }
                    vbox.getChildren().add(label);
                    if (hiddenState[a] == null) {
                        Label noCard = new Label("There is no card left");
                        vbox.getChildren().add(noCard);
                    } else {
                        HBox hBox = new HBox(5);
                        for (String b : hiddenState[a]) { // display card horizontally
                            Card card = new Card(b);
                            hBox.getChildren().add(card);
                        }
                        vbox.getChildren().add(hBox);
                    }
                }
            }
        }
        vbox.setPadding(new Insets(10));

        state.setContent(vbox);
    }


    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Player Turn ID");
        turnIDTextField = new TextField();
        turnIDTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label aArboretum = new Label("Player A Arboretum:");
        aArboretumTextField = new TextField();
        aArboretumTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label aDiscard = new Label("Player A Discard:");
        aDiscardTextField = new TextField();
        aDiscardTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label bArboretum = new Label("Player B Arboretum:");
        bArboretumTextField = new TextField();
        bArboretumTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label bDiscard = new Label("Player B Discard:");
        bDiscardTextField = new TextField();
        bDiscardTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label deck = new Label("Deck:");
        deckTextField = new TextField();
        deckTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label aHand = new Label("Player A Hand:");
        aHandTextField = new TextField();
        aHandTextField.setPrefWidth(TEXTBOX_WIDTH);
        Label bHand = new Label("Player B Hand:");
        bHandTextField = new TextField();
        bHandTextField.setPrefWidth(TEXTBOX_WIDTH);

        Button displayState = new Button("Display State");
        displayState.setOnAction(e -> {
            String[] sharedState = {turnIDTextField.getText(), aArboretumTextField.getText(),
                    aDiscardTextField.getText(), bArboretumTextField.getText(), bDiscardTextField.getText()};
            String[] hiddenState = {deckTextField.getText(), aHandTextField.getText(), bHandTextField.getText()};
            displayState(new String[][]{sharedState, hiddenState});
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(boardLabel, turnIDTextField, aArboretum, aArboretumTextField, aDiscard,
                aDiscardTextField, bArboretum, bArboretumTextField, bDiscard, bDiscardTextField, deck, deckTextField,
                aHand, aHandTextField, bHand, bHandTextField, displayState);
        vbox.setSpacing(10);
        vbox.setLayoutX(10.4 * (GRID_SIZE) + (2 * WINDOW_XOFFSET) + (GRID_DIMENSION * GRID_SIZE) + (0.5 * GRID_SIZE));
        vbox.setLayoutY(WINDOW_YOFFSET);

        controls.getChildren().add(vbox);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Arboretum Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);
        root.getChildren().add(state);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //display the current state
    public void displayState() {
    }

    //the position of the desk
    public void deskPosition() {
        deck.setLayoutX(600);
        deck.setLayoutY(350);
        deck.setRotate(90);
    }

    //the position of the player
    public void playerPosition() {
        playerAHand.setLayoutX(10);
        playerAHand.setLayoutY(690);
        playerBHand.setLayoutX(1190);
        playerBHand.setLayoutY(10);
    }

    //the position of the discard pile
    public void discardPilePosition() {
        PlayerADiscard.setLayoutX(10);
        PlayerADiscard.setLayoutY(10);
        PlayerBDiscard.setLayoutX(1190);
        PlayerBDiscard.setLayoutY(690);
    }

    //zoom based on length of the grid
    public void zoom() {
    }

}

