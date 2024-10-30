package screenControl;
public class Display {
    //the display will create all the display objects (welcome screen, vote screen, confirm votes popup etc) and will be 
    //mainly just a holder for all GUI objects with methods that the controller will call based on current state information that 
    //the display will pass to the controller

    //this will be the only class that the controller will interact with to display the GUI

    //popups - there will be one when the voter is on the last voting screen and hit the next button, 
    //it will ask if they are sure they want to submit their votes
    //the second popup will popup when the admin button is hit, it will ask for a password, and if the password is correct,
    //it will generate the admin screen (that is generated using the VoteScreen Template)
}
