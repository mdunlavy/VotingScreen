package screenControl;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import testSuite.Proposition;

public class ScreenController extends Application {

    private Stage primaryStage;
    private List<Proposition> propositions = new ArrayList<>();
    private final List<VoteScreen> voteScreens = new ArrayList<>();
    private final List<Proposition> submittedVotes = new ArrayList<>();
    private int currentScreenIndex = 0;
    private boolean unlockedForTheUser = false;
    private boolean unlockedForTheDay = false;

    private static ScreenController instance;

    public ScreenController() {
        instance = this; // Set the static instance when constructed
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setupVotingProcess();
        showScreen(currentScreenIndex);
    }

    private void setupVotingProcess() {
        voteScreens.clear();

        Proposition welcomeProposition = new Proposition("Welcome", null, 0, new String[0]);
        Proposition adminProposition = new Proposition("Admin", null, 0, new String[0]);

        voteScreens.add(new VoteScreen(welcomeProposition, this));
        voteScreens.add(new VoteScreen(adminProposition, this));
        for (Proposition proposition : propositions) {
            voteScreens.add(new VoteScreen(proposition, this));
        }
    }

    private void showScreen(int index) {
        if (index >= 0 && index <= voteScreens.size()) {
            currentScreenIndex = index;
            VoteScreen currentVoteScreen = voteScreens.get(index);
            Scene scene = currentVoteScreen.draw();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Voting System - Screen " + (index + 1));
            primaryStage.show();
        }
    }

    protected void navigateBegin() {
        if (unlockedForTheDay && unlockedForTheUser && propositions.size() >= 2) {
            currentScreenIndex = 2;
            showScreen(currentScreenIndex);
        }
    }

    protected void navigateNext() {
        if (unlockedForTheDay && unlockedForTheUser && currentScreenIndex < voteScreens.size() - 1) {
            showScreen(currentScreenIndex + 1);
        } else if (unlockedForTheDay && unlockedForTheUser && currentScreenIndex > 0){
            confirmSubmission();
        }
    }

    protected void navigateBack() {
        if (currentScreenIndex > 2) {
            showScreen(currentScreenIndex - 1);
        }
        else if (currentScreenIndex == 2) {
            showScreen(0);
        }
    }

    protected void navigateAdmin() {
        adminAuthentication();
    }

    protected void navigateStart() {
        adminInstructionConfirmation(1);
    }

    protected void navigatePause() {
        adminInstructionConfirmation(2);
    }

    protected void navigateStop() {
        adminInstructionConfirmation(3);
    }

    private void adminAuthentication() {

        String validUsername = "adminTest";
        String validPassword = "123456";
        // create a new Stage (window) for the login pop-up
        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL); // Blocks other windows until closed
        loginStage.setTitle("Admin Login");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        submitButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            System.out.println("Username: " + username + ", Password: " + password);

            if (username.equals(validUsername) && password.equals(validPassword)) {
                loginStage.close();
                if (currentScreenIndex >= 0) {
                    currentScreenIndex = 1;
                    showScreen(currentScreenIndex);
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        });

        cancelButton.setOnAction(e -> loginStage.close());

        HBox buttonBox = new HBox(10, submitButton, cancelButton);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);

        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(buttonBox, 1, 2);

        Scene loginScene = new Scene(gridPane, 300, 150);
        loginStage.setScene(loginScene);

        loginStage.showAndWait();
    }

    // method to show admin authentication fail messages
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setWidth(300);
        alert.showAndWait();
    }

    private void adminInstructionConfirmation(int instruction) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Admin Instruction");
        alert.setHeaderText("Are you sure you want to execute the selected instruction?");
        alert.getDialogPane().setStyle("-fx-font-size: 10px;");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (instruction == 1) { //start
                this.unlockedForTheDay = true;
                currentScreenIndex = 0;
                showScreen(currentScreenIndex);
            } else if (instruction == 2) { //pause
                this.unlockedForTheDay = false;
                //clear selections?
                currentScreenIndex = 0;
                showScreen(currentScreenIndex);
            } else if (instruction == 3) { //stop
                this.unlockedForTheDay = false;
                //clear selections?
                currentScreenIndex = 0;
                showScreen(currentScreenIndex);
            }
        }
    }

    private void confirmSubmission() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Submit Votes");
        alert.setHeaderText("Are you sure you want to submit your votes?");
        alert.setContentText("This will finalize your selections.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            saveSubmittedVotes();
            setupVotingProcess();
            showScreen(0);
        }
    }

    private void saveSubmittedVotes() {
        submittedVotes.clear();
        for (Proposition prop : propositions) {
            Proposition copy = new Proposition(
                    prop.getName(),
                    prop.getDescription(),
                    prop.getMaxNumSelections(),
                    prop.getOptions().clone()
            );

            boolean[] selectionsCopy = new boolean[prop.getSelections().length];
            System.arraycopy(prop.getSelections(), 0, selectionsCopy, 0, prop.getSelections().length);
            copy.setSelections(selectionsCopy);

            submittedVotes.add(copy);
        }

        // Lock the voting process after saving the votes
        this.unlockedForTheUser = false;
    }

    public static ScreenController getInstance() {
        return instance;
    }

    // Screen Controller API methods below

    // Getter to return the propositions array
    public List<Proposition> getSubmittedVotes() {
        return this.submittedVotes;
    }

    // Method to unlock the voting process
    public void unlockForUser() {
        this.unlockedForTheUser = true;
    }

    // Method to unlock the voting process
    public void lockForUser() {
        this.unlockedForTheUser = false;
    }

    // Method to unlock the voting process
    public void unlockVotingSession() {
        this.unlockedForTheDay = true;
    }

    // Method to unlock the voting process
    public void lockVotingSession() {
        this.unlockedForTheDay = false;
    }

    public void setPropositions(List<Proposition> propositions) {
        if (currentScreenIndex != 0) {
            return;
        }
        this.propositions = propositions;
        setupVotingProcess();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
