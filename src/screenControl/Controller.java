package screenControl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import testSuite.Proposition;

public class Controller extends Application {

    private Stage primaryStage;
    private List<Proposition> propositions = new ArrayList<>();
    private final List<VoteScreen> voteScreens = new ArrayList<>();
    private final List<Proposition> submittedVotes = new ArrayList<>();
    private int currentScreenIndex = 0;
    private boolean unlocked = false;

    private static Controller instance;

    public Controller() {
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

        voteScreens.add(new VoteScreen(welcomeProposition, this));
        for (Proposition proposition : propositions) {
            voteScreens.add(new VoteScreen(proposition, this));
        }
    }

    private void showScreen(int index) {
        if (index >= 0 && index < voteScreens.size()) {
            currentScreenIndex = index;
            VoteScreen currentVoteScreen = voteScreens.get(index);
            Scene scene = currentVoteScreen.draw();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Voting System - Screen " + (index + 1));
            primaryStage.show();
        }
    }

    protected void navigateNext() {
        if (unlocked && currentScreenIndex < voteScreens.size() - 1) {
            showScreen(currentScreenIndex + 1);
        } else if (unlocked && currentScreenIndex > 0){
            confirmSubmission();
        }
    }

    protected void navigateBack() {
        if (currentScreenIndex > 0) {
            showScreen(currentScreenIndex - 1);
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
        this.unlocked = false;
    }

    // Screen Controller API methods below

    public static Controller getInstance() {
        return instance;
    }

    // Getter to return the propositions array
    public List<Proposition> getSubmittedVotes() {
        return this.submittedVotes;
    }

    // Method to unlock the voting process
    public void unlock() {
        this.unlocked = true;
    }

    // Method to unlock the voting process
    public void lock() {
        this.unlocked = false;
    }

    public void setPropositions(List<Proposition> propositions) {
        // TODO maybe find better way to handle this
        if (propositions.isEmpty() && currentScreenIndex != 0) {
            return;
        }
        this.propositions = propositions;
        setupVotingProcess();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
