package testSuite;

import java.util.ArrayList;
import java.util.List;

public class Propositions {
    private List<Proposition> listOfPropositions = new ArrayList<>();

    public Propositions() {
        String[] options01 = new String[]{"Kamala", "Trump"};
        Proposition proposition01 = new Proposition("President", "Leader of the country.", 2, options01);

        String[] options02 = new String[]{"Vini Jr.", "Rodri"};
        Proposition proposition02 = new Proposition("Balon D'Or Winner", "Best soccer player of the world.", 2, options02);

        String[] options03 = new String[]{"Chicken", "Egg"};
        Proposition proposition03 = new Proposition("Existentialism", "Which really came first?", 2, options03);

        String[] options04 = new String[]{"Math", "English", "History", "Physical Education", "Culinary Arts"};
        Proposition proposition04 = new Proposition("Favorite Subject", "Which subject was your favorite in school?", 5, options04);

        String[] options05 = new String[]{"Bulbasaur", "Squirtle", "Charmander"};
        Proposition proposition05 = new Proposition("Starter Pokemon", "This will be your first pokemon for your adventure.", 3, options05);

        listOfPropositions.add(proposition01);
        listOfPropositions.add(proposition02);
        listOfPropositions.add(proposition03);
        listOfPropositions.add(proposition04);
        listOfPropositions.add(proposition05);
    }

    public List<Proposition> getListOfPropositions() {
        return this.listOfPropositions;
    }
}
