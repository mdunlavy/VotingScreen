create screen that : 
- snapshot of screen and explanation
- ballot format needs to be well-defined
- need to work closely with team 8 to get ballot format
- needs to look beautiful
- methods that screen controller will provide ~~ ex. accepts proposition, displays on screen, captures the vote, returns proposition back, need to make sure you can go back, provide API methods that 
- election official mode vs voter mode (how each of these connect to API)

TODO: 
- create GUI based off ballot structure (both voter and official mode) 
- we need to have a start screen, end screen, maybe once they're done we have a button that says "new voter" 
- write methods that screen will provide to API (how we get input to screen back to API)


do we need to write methods to take input from ballot or can we just create an example that we make up for our demo? 
do we need to create the screens for starting an election? not just turning on but actually activating it? or can we assume that an election is "started" through the card that is put in? 

# CLI Commands

The following commands are available in this CLI application. Each command interacts with the `ScreenController` class to manage the status, user access, and ballot details of an election system.

- **turn on**: Turns on the screen, initializing the voting process and displaying the welcome screen.

- **turn off**: Turns off the screen and deactivates any current display.

- **status**: Prints the list of submitted votes for review, showing the status of the current voting session.

- **unlock session**: Unlocks the voting session, allowing users to submit votes.

- **lock session**: Locks the voting session, preventing any further vote submissions.

- **unlock user**: Unlocks user access, enabling the user to interact with the voting screen.

- **lock user**: Locks user access, restricting the user from interacting with the voting screen.

- **clear ballot**: Clears all propositions from the ballot, resetting it to an empty state.

- **set ballot**: Sets the ballot with a predefined list of propositions, initializing it with the necessary options.

- **exit**: Exits the CLI application, closing the scanner and ending the program.


