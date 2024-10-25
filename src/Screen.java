import javafx.scene.layout.Pane;

/**
 * Abstract class useful for keeping common API among different screens
 */
public abstract class Screen {
    protected String ballotTitle;
    protected String ballotDescription;
    protected int ballotId;

    protected Pane displayPane;

    private final int SCREEN_WIDTH = 600;
    private final int SCREEN_HEIGHT = 800;

    public Screen(String ballotTitle, String ballotDescription, int ballotId) {
        this.ballotTitle = ballotTitle;
        this.ballotDescription = ballotDescription;
        this.ballotId = ballotId;
        this.displayPane = new Pane(); // initialize displayPane
        this.displayPane.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT); // set preferred size as an example
    }

    // Display the screen
    public abstract void generate_display();

    // Getter for displayPane to integrate into the JavaFX scene graph
    public Pane getDisplayPane() {
        return displayPane;
    }
}


