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
import javafx.scene.control.Hyperlink;

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
        // Main container with modern gradient background
        VBox layout = new VBox(25);
        Stop[] stops = new Stop[] { 
            new Stop(0, Color.web("#FFFFFF")),
            new Stop(1, Color.web("#F5F7FA"))
        };
        LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, null, stops);
        layout.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));
        
        // Main title section
        VBox titleBox = new VBox(8);
        titleBox.setAlignment(Pos.CENTER);
        
        Label secureVotingLabel = new Label("Secure Voting");
        secureVotingLabel.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 42));
        secureVotingLabel.setStyle("-fx-text-fill: #1A365D;"); // Deep blue for trust
        
        Label systemLabel = new Label("SYSTEM");
        systemLabel.setFont(Font.font("SF Pro Display", FontWeight.LIGHT, 38));
        systemLabel.setStyle("-fx-text-fill: #2D3748;"); // Slightly lighter
        
        // Subtitle with election year
        Label yearLabel = new Label("2024 Election");
        yearLabel.setFont(Font.font("SF Pro Display", FontWeight.MEDIUM, 24));
        yearLabel.setStyle("-fx-text-fill: #4A5568; -fx-padding: 0 0 20 0;");
        
        // Trust indicators
        HBox trustIndicators = new HBox(40);
        trustIndicators.setAlignment(Pos.CENTER);
        trustIndicators.setPadding(new Insets(30, 0, 30, 0));
        
        VBox[] indicators = new VBox[3];
        String[][] indicatorContent = {
            {MaterialIcons.LOCK, "Encrypted", "End-to-end encryption"},
            {MaterialIcons.VERIFY, "Verified", "Multiple verification steps"},
            {MaterialIcons.LIGHTNING, "Real-time", "Instant vote confirmation"}
        };
        
        for (int i = 0; i < 3; i++) {
            indicators[i] = createTrustIndicator(
                indicatorContent[i][0],
                indicatorContent[i][1],
                indicatorContent[i][2]
            );
            trustIndicators.getChildren().add(indicators[i]);
        }
        
        // Begin buttons
        Button beginButton = createBeginButton();
        
        // Admin access subtle link
        Hyperlink adminLink = new Hyperlink("Admin Access");
        adminLink.setStyle("-fx-text-fill: #718096; -fx-border-color: transparent;");
        adminLink.setOnAction(e -> controller.navigateAdmin());
        adminLink.setFont(Font.font("SF Pro Display", FontWeight.MEDIUM, 14));
        
        // Footer text
        Label footerLabel = new Label("Certified Electronic Voting System");
        footerLabel.setStyle("-fx-text-fill: #718096; -fx-font-size: 12px;");
        
        // Layout assembly
        titleBox.getChildren().addAll(secureVotingLabel, systemLabel);
        layout.getChildren().addAll(
            titleBox,
            yearLabel,
            trustIndicators,
            beginButton,
            new Region() {{ setPrefHeight(20); }},
            adminLink,
            footerLabel
        );
        
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40, 20, 40, 20));
        
        return new Scene(layout, 400, 600);
    }

    private VBox createTrustIndicator(String iconName, String title, String description) {
        VBox indicator = new VBox(5);
        indicator.setAlignment(Pos.CENTER);
        
        // Update the icon label to use material-icons class
        Label iconLabel = new Label(iconName);
        iconLabel.getStyleClass().add("material-icons");
        iconLabel.setStyle("-fx-font-size: 24px;");
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 14));
        titleLabel.setStyle("-fx-text-fill: #2D3748;");
        
        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("SF Pro Display", FontWeight.NORMAL, 12));
        descLabel.setStyle("-fx-text-fill: #718096; -fx-wrap-text: true;");
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setMaxWidth(120);
        
        indicator.getChildren().addAll(iconLabel, titleLabel, descLabel);
        return indicator;
    }

    private Button createBeginButton() {
        Button beginButton = new Button("Begin Voting");
        beginButton.setFont(Font.font("SF Pro Display", FontWeight.BOLD, 16));
        
        // button styling
        String buttonStyle = """
            -fx-background-color: #2B6CB0;
            -fx-text-fill: white;
            -fx-padding: 15 50;
            -fx-background-radius: 8;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 4);
            -fx-cursor: hand;
        """;
        
        String hoverStyle = """
            -fx-background-color: #2C5282;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 12, 0, 0, 4);
        """;
        
        beginButton.setStyle(buttonStyle);
        beginButton.setOnMouseEntered(e -> beginButton.setStyle(buttonStyle + hoverStyle));
        beginButton.setOnMouseExited(e -> beginButton.setStyle(buttonStyle));
        beginButton.setOnAction(e -> controller.navigateBegin());
        
        return beginButton;
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