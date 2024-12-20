package screenControl;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import testSuite.Proposition;
import javafx.scene.layout.Region;

public class VoteScreen {

    private Proposition proposition;
    private ScreenController controller;

    public VoteScreen(Proposition proposition, ScreenController controller) {
        this.proposition = proposition;
        this.controller = controller;
    }

    public Scene draw() {
        if ("Welcome".equals(proposition.getName())) {
            return createWelcomeScreen();
        } else if ("Admin".equals((proposition.getName()))) {
            return createAdminScreen();
        } else {
            return createVotingScreen();
        }
    }

    public Scene drawOffScreen() {
        VBox layout = new VBox();
        layout.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        return new Scene(layout, 400, 600);
    }

    private Scene createWelcomeScreen() {
        Label welcomeToLabel = new Label("Welcome to");
        welcomeToLabel.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 36));
        welcomeToLabel.setStyle("-fx-text-fill: #2c3e50;");
        welcomeToLabel.setTextAlignment(TextAlignment.CENTER);

        Label yearLabel = new Label("2024");
        yearLabel.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 48));
        yearLabel.setStyle("-fx-text-fill: #2c3e50;");
        yearLabel.setTextAlignment(TextAlignment.CENTER);

        Label electionLabel = new Label("Election!");
        electionLabel.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 36));
        electionLabel.setStyle("-fx-text-fill: #2c3e50;");
        electionLabel.setTextAlignment(TextAlignment.CENTER);

        Button adminButton = new Button("Admin Mode");
        styleAdminButton(adminButton);
        adminButton.setOnAction(e -> controller.navigateAdmin());

        Button startButton = new Button("BEGIN ➔");
        startButton.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        startButton.setMinWidth(200);
        startButton.setStyle(
                "-fx-background-color: #4a90e2; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-padding: 10px;"
        );
        startButton.setOnMouseEntered(e -> startButton.setStyle(
                "-fx-background-color: #357ABD; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-padding: 10px;"
        ));
        startButton.setOnMouseExited(e -> startButton.setStyle(
                "-fx-background-color: #4a90e2; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15; " +
                        "-fx-padding: 10px;"
        ));
        startButton.setOnAction(e -> controller.navigateBegin());

        Stop[] stops = new Stop[] { new Stop(0, Color.web("#DCE2F2")), new Stop(1, Color.web("#F4D3D3")) };
        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, null, stops);

        Region spacer = new Region();
        spacer.setPrefHeight(80);
        VBox layout = new VBox(20, adminButton, spacer, welcomeToLabel, yearLabel, electionLabel, startButton);
        layout.setPadding(new Insets(0, 0, 140, 0));
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

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

            // Pre-select any previously saved selection
            if (i < proposition.getSelections().length && proposition.getSelections()[i]) {
                optionButton.setSelected(true);
                optionButton.setStyle(
                        "-fx-background-color: darkblue; -fx-text-fill: white; -fx-border-radius: 10; " +
                                "-fx-background-radius: 10; -fx-padding: 10px; -fx-font-size: 18px; -fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-border-color: darkgray;"
                );
            }

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
                                            ? "-fx-background-color: darkblue; -fx-text-fill: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10px; -fx-font-size: 18px; -fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-border-color: darkgray;"
                                            : "-fx-background-color: lightgray; -fx-text-fill: black; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10px; -fx-font-size: 18px; -fx-font-family: 'Times New Roman'; -fx-font-weight: bold; -fx-border-color: darkgray;"
                            );
                        }
                    });
                });
            }

            optionsBox.getChildren().add(optionButton);
        }

        Button adminButton = new Button("Admin Mode");
        styleAdminButton(adminButton);
        adminButton.setOnAction(e -> controller.navigateAdmin());

        Button backButton = new Button("Back");
        styleNavigationButton(backButton);
        backButton.setOnAction(e -> controller.navigateBack());

        Button nextButton = new Button("Next");
        styleNavigationButton(nextButton);
        nextButton.setOnAction(e -> controller.navigateNext());

        HBox navigationBox = new HBox(30, backButton, nextButton);
        navigationBox.setAlignment(Pos.CENTER);

        Region spacer = new Region();
        spacer.setPrefHeight(30);

        VBox mainLayout = new VBox(20, adminButton, spacer, titleLabel, descriptionLabel, optionsBox, navigationBox);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #e6ecf2, #cfd8e4); -fx-background-radius: 20;");

        return new Scene(mainLayout, 400, 600);
    }

    private Scene createAdminScreen() {
        Label adminLabel = new Label("Admin");
        adminLabel.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 28));
        adminLabel.setStyle("-fx-text-fill: black;");

        Label descriptionLabel = new Label("Select 1 Option");
        descriptionLabel.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 20));
        descriptionLabel.setStyle("-fx-text-fill: black;");

        Button startButton = new Button("Start");
        styleAdminNavigationButtons(startButton);
        startButton.setOnAction(e -> controller.navigateStart());

        Button pauseButton = new Button("Pause");
        styleAdminNavigationButtons(pauseButton);
        pauseButton.setOnAction(e -> controller.navigatePause());

        Button stopButton = new Button("Stop");
        styleAdminNavigationButtons(stopButton);
        stopButton.setOnAction(e -> controller.navigateStop());

        VBox mainLayout = new VBox(20, adminLabel, descriptionLabel, startButton, pauseButton, stopButton);
        mainLayout.setPadding(new Insets(0,0,100,0));
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

    private void styleAdminButton(Button button) {
        button.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
        button.setMinWidth(120);
        button.setStyle(
                "-fx-background-color: lightgray; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        );
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: gray; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: lightgray; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        ));
    }

    private void styleAdminNavigationButtons(Button button) {
        button.setMinWidth(250);
        button.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        button.setStyle(
                "-fx-background-color: lightgray; " +
                        "-fx-text-fill: black; " +
                        "-fx-border-color: darkgray; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        );
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: darkblue; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-color: darkgray; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: lightgray; " +
                        "-fx-text-fill: black; " +
                        "-fx-border-color: darkgray; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        ));
    }
}