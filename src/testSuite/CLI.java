package testSuite;

import screenControl.Controller;

import java.util.Scanner;

/**
 * Should call functions of the Screen Controller API which controls the Screen Visualization
 */
public class CLI {

    //instantiate ScreenController object
    Controller scr = new Controller();

    public static void main(String[] args) {

        //instantiate CLI object
        CLI cli = new CLI();

        //get user input from CLI
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the commands from the following for testing purpose: ");
        System.out.println("turn screen on, turn screen off, next, back, admin mode, voting mode\n");
        System.out.println("Type \"over\" to finish\n");
        System.out.print("Enter here: ");
        String input = scanner.nextLine();

        while(true)
        {
            if(!"over".equals(input))
            {   cli.commandParser(input);
                System.out.print("Enter here: ");
                input = scanner.nextLine();
            }
            else
            {
                System.out.println("Testing Done!");
                System.exit(1);
            }

        }
    }

    /**
     * method to parse the input command from the user
     * @param input input command from the user
     */
    private void commandParser(String input){
        switch (input) {
//            case "turn screen on" -> scr.turnScreenOn();
//            case "turn screen off" -> scr.turnScreenOff();
//            case "next" -> scr.nextScreen();
//            case "back" -> scr.prevScreen();
//            case "admin mode" -> scr.enterAdminMode();
//            case "voting mode" -> scr.enterVoterMode();
        }
    }

}
