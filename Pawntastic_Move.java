public class Pawntastic_Move {
    private final Pawntastic_Location from; // Location piece is coming from
    private final Pawntastic_Location to; // Location piece is going to
    public Pawntastic_Move(Pawntastic_Location from, Pawntastic_Location to) {
        this.from = from;
        this.to = to;
    }
    // Get method for location move is coming from
    public Pawntastic_Location getFrom() {
        return from;
    }
    // Get method for location move is going to
    public Pawntastic_Location getTo() {
        return to;
    }
    // Override toString method to print move correctly instead of address
    @Override
    public String toString() {
        return "From " + from + " to " + to;
    }
    // Method to check whether two moves are identical
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pawntastic_Move other = (Pawntastic_Move) obj;
        return this.from.equals(other.from) && this.to.equals(other.to);
    }
}