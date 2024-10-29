import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class ScreenVisualization extends Application {

    @Override
    public void start(Stage primaryStage) {
        WelcomeScreen welcomeScreen = new WelcomeScreen("Ballot Title", "Ballot Description", 1);
        welcomeScreen.generate_display();
        primaryStage.setScene(new Scene(welcomeScreen.getDisplayPane(), 300, 275));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

