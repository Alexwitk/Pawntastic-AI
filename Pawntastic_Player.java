public class Pawntastic_Player {
    private final String name; // Name of player
    private final char pieceColor; // 'X' for white, 'O' for black
    private final boolean isHuman; // Indicates whether the player is human or not
    public Pawntastic_Player(String name, char pieceColor, boolean isHuman) {
        this.name = name;
        this.pieceColor = pieceColor;
        this.isHuman = isHuman;
    }
    // Get method for name
    public String getName() {
        return name;
    }
    // Get method for piece color
    public char getPieceColor() {
        return pieceColor;
    }
    // Get method for whether the player is the human or the computer
    public boolean isHuman() {
        return isHuman;
    }
}