package testSuite;

import javafx.application.Application;
import screenControl.Controller;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test {

    private static Controller scr;

    public static void main(String[] args) {
        // Start the JavaFX application in a separate thread
        new Thread(() -> Application.launch(Controller.class)).start();

        // Wait for the JavaFX application to initialize the Controller instance
//        Controller scr;
        while ((scr = Controller.getInstance()) == null) {
            // Busy-wait until the Controller instance is available
        }

        // Schedule a task to print the status of propositions every second
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            List<Proposition> propositions = scr.getPropositions();
            System.out.println("Current state of propositions:");
            for (Proposition p : propositions) {
                System.out.println(p.toString());  // Uses the existing toString() method for formatted output
            }
            System.out.println("-----------");
        }, 0, 1, TimeUnit.SECONDS);
    }
}

