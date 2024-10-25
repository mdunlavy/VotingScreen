import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class WelcomeScreen extends Screen {

    public WelcomeScreen(String ballotTitle, String ballotDescription, int ballotId) {
        super(ballotTitle, ballotDescription, ballotId);
    }

    @Override
    public void generate_display() {
        // Create a vertical box to structure welcome message
        VBox welcomeBox = new VBox();
        welcomeBox.setSpacing(10);

        // Add labels with ballot information
        Label titleLabel = new Label("Welcome to the Voting System");
        Label ballotTitleLabel = new Label("Ballot Title: " + ballotTitle);
        Label ballotDescriptionLabel = new Label("Description: " + ballotDescription);

        // Add labels to VBox
        welcomeBox.getChildren().addAll(titleLabel, ballotTitleLabel, ballotDescriptionLabel);

        // Add VBox to displayPane
        displayPane.getChildren().add(welcomeBox);
    }
}

