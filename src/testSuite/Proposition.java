package testSuite;

public class Proposition {
    private final String name;
    private final String description;
    private final int maxNumSelections;// I think for simplicity we will only be choosing one option per proposition
    private final String[] options;
    private boolean[] selections;

    public Proposition(String name, String description, int maxNumSelections, String[] options) {
        this.name = name;
        this.description = description;
        this.maxNumSelections = maxNumSelections;
        this.options = options;
        this.selections = new boolean[options.length];
    }

    public String getName() {
        return this.name;
    }

    public int getMaxNumSelections() {
        return this.maxNumSelections;
    }

    public String getDescription() {
        return this.description;
    }

    public String[] getOptions() {
        return this.options;
    }

    public boolean[] getSelections() {
        return this.selections;
    }

    public void setSelection(int index, boolean newValue) {
        try {
            this.selections[index] = newValue;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error: Index " + index + " is out of bounds for selections array.");
        }
    }
    public void setSelections(boolean[] selections) {
        this.selections = selections;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        sb.append(description).append("\n");
        sb.append(maxNumSelections).append("\n");
        for (int i = 0; i < options.length; i++) {
            sb.append(options[i]).append("-------").append(selections[i]).append("\n");
        }
        return sb.toString();
    }
}