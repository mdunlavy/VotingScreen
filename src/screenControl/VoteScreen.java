package screenControl;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import testSuite.Proposition;

public class VoteScreen {

    private Proposition proposition;
    private Controller controller;

    public VoteScreen(Proposition proposition, Controller controller) {
        this.proposition = proposition;
        this.controller = controller;
    }

    public Scene draw() {
        if ("Welcome".equals(proposition.getName())) {
            return createWelcomeScreen();
        } else {
            return createVotingScreen();
        }
    }

    private Scene createWelcomeScreen() {
        Label welcomeLabel = new Label("Welcome to the Voting System");
        welcomeLabel.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 28));
        welcomeLabel.setStyle("-fx-text-fill: black;");

        Button startButton = new Button("Start Voting");
        startButton.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        startButton.setMinWidth(150);
        startButton.setStyle(
                "-fx-background-color: darkblue; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        );
        startButton.setOnAction(e -> controller.navigateNext());

        VBox layout = new VBox(20, welcomeLabel, startButton);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #e6ecf2, #cfd8e4); -fx-background-radius: 20;");

        return new Scene(layout, 400, 600);
    }

    private Scene createVotingScreen() {
        Label titleLabel = new Label(proposition.getName());
        titleLabel.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: black;");

        Label descriptionLabel = new Label("Select " + proposition.getMaxNumSelections() + " Option");
        descriptionLabel.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
        descriptionLabel.setStyle("-fx-text-fill: black;");

        ToggleGroup toggleGroup = new ToggleGroup();
        VBox optionsBox = new VBox(15);
        optionsBox.setAlignment(Pos.CENTER);

        for (int i = 0; i < 5; i++) {
            String optionText = i < proposition.getOptions().length ? proposition.getOptions()[i] : "";
            ToggleButton optionButton = new ToggleButton(optionText);
            optionButton.setMinWidth(250);
            optionButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
            optionButton.setStyle(
                    "-fx-background-color: lightgray; " +
                            "-fx-text-fill: black; " +
                            "-fx-border-color: darkgray; " +
                            "-fx-border-radius: 10; " +
                            "-fx-background-radius: 10; " +
                            "-fx-padding: 10px;"
            );

            optionButton.setToggleGroup(toggleGroup);
            final int index = i;

            if (optionText.isEmpty()) {
                optionButton.setDisable(true);
                optionButton.setStyle(
                        "-fx-background-color: #e0e0e0; " +
                                "-fx-text-fill: gray; " +
                                "-fx-border-color: darkgray; " +
                                "-fx-border-radius: 10; " +
                                "-fx-background-radius: 10; " +
                                "-fx-padding: 10px;"
                );
            } else {
                optionButton.setOnAction(e -> {
                    for (int j = 0; j < proposition.getSelections().length; j++) {
                        proposition.setSelection(j, j == index);
                    }
                    optionsBox.getChildren().forEach(node -> {
                        if (node instanceof ToggleButton) {
                            ToggleButton button = (ToggleButton) node;
                            button.setStyle(
                                    button.isSelected()
                                            ? "-fx-background-color: darkblue; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10px; -fx-font-size: 18px; -fx-font-family: 'Times New Roman'; -fx-border-color: darkgray;"
                                            : "-fx-background-color: lightgray; -fx-text-fill: black; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10px; -fx-font-size: 18px; -fx-font-family: 'Times New Roman'; -fx-border-color: darkgray;"
                            );
                        }
                    });
                });
            }

            optionsBox.getChildren().add(optionButton);
        }

        Button backButton = new Button("Back");
        styleNavigationButton(backButton);
        backButton.setOnAction(e -> controller.navigateBack());

        Button nextButton = new Button("Next");
        styleNavigationButton(nextButton);
        nextButton.setOnAction(e -> controller.navigateNext());

        HBox navigationBox = new HBox(30, backButton, nextButton);
        navigationBox.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(20, titleLabel, descriptionLabel, optionsBox, navigationBox);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #e6ecf2, #cfd8e4); -fx-background-radius: 20;");

        return new Scene(mainLayout, 400, 600);
    }

    private void styleNavigationButton(Button button) {
        button.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        button.setMinWidth(120);
        button.setStyle(
                "-fx-background-color: darkblue; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        );
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: navy; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: darkblue; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        ));
    }
}
