public class Event extends Task {

    private String from;
    private String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), from, to);
    }

    @Override
    public String toSaveString() {
        return "E | " + super.toSaveString() + " | " + from + " | " + to;
    }
}
