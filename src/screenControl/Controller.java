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
    private List<VoteScreen> voteScreens = new ArrayList<>();
    private List<Proposition> submittedVotes = new ArrayList<>();
    private int currentScreenIndex = 0;

    private static Controller instance;

    public Controller() {
        instance = this; // Set the static instance when constructed
    }

    public static Controller getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setupVotingProcess();
        showScreen(currentScreenIndex);
    }

    private void setupVotingProcess() {
        propositions.clear();
        voteScreens.clear();

        Proposition welcomeProposition = new Proposition("Welcome", null, 0, new String[0]);
        propositions.add(welcomeProposition);

        propositions.add(new Proposition("Proposition 1", "Description of Proposition 1", 1, new String[]{"Option A", "Option B", "Option C"}));
        propositions.add(new Proposition("Proposition 2", "Description of Proposition 2", 1, new String[]{"Option D", "Option E"}));
        propositions.add(new Proposition("Proposition 3", "Description of Proposition 3", 1, new String[]{"Option F", "Option G", "Option H", "Option I"}));

        for (Proposition proposition : propositions) {
            voteScreens.add(new VoteScreen(proposition, this));
        }
    }

    public void showScreen(int index) {
        if (index >= 0 && index < voteScreens.size()) {
            currentScreenIndex = index;
            VoteScreen currentVoteScreen = voteScreens.get(index);
            Scene scene = currentVoteScreen.draw();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Voting System - Screen " + (index + 1));
            primaryStage.show();
        }
    }

    public void navigateNext() {
        if (currentScreenIndex < voteScreens.size() - 1) {
            showScreen(currentScreenIndex + 1);
        } else {
            confirmSubmission();
        }
    }

    public void navigateBack() {
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
    }

    // Getter to return the propositions array
    public List<Proposition> getPropositions() {
        return propositions;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
