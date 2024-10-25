import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.List;

public class PropositionScreen extends Screen {
    private String propositionPrompt;
    private List<String> propositions; // List of propositions to vote on

    public PropositionScreen(String ballotTitle, String ballotDescription, int ballotId, String propositionPrompt, List<String> propositions) {
        super(ballotTitle, ballotDescription, ballotId);
        this.propositionPrompt = propositionPrompt;
        this.propositions = propositions; // Initialize the list of propositions
    }

    @Override
    public void generate_display() {
        // Create a vertical box to structure propositions
        VBox propositionBox = new VBox();
        propositionBox.setSpacing(10);

        // Add a title for the proposition screen
        Label propositionPromptLabel = new Label(propositionPrompt);
        propositionBox.getChildren().add(propositionPromptLabel);

        // Add each proposition as a label
        for (String proposition : propositions) {
            Label propositionLabel = new Label("- " + proposition);
            propositionBox.getChildren().add(propositionLabel);
        }

        // Add VBox to displayPane
        displayPane.getChildren().add(propositionBox);
    }
}

