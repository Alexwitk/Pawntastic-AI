public class Pawntastic_Location {
    private int row; //Row of location
    private int col; //Column of location
    private final int boardSize; //Size of board used inorder to convert int indices back to chess board positioning
    public Pawntastic_Location(int row, int col, int boardSize) {
        this.row = row;
        this.col = col;
        this.boardSize = boardSize;
    }
    //Get method for location row
    public int getRow() {
        return row;
    }
    //Get method for location column
    public int getCol() {
        return col;
    }
    //Set method for location row
    public void setRow(int row) {
        this.row = row;
    }
    //Set method for location col
    public void setCol(int col) {
        this.col = col;
    }
    //Method to check whether two locations are equal
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pawntastic_Location other = (Pawntastic_Location) obj;
        return this.row == other.row && this.col == other.col;
    }
    //Method to correctly print location
    @Override
    public String toString() {
        //Converts row col value back into chess positioning like a2
        return "" + (char) ('a' + col) + (boardSize - row);
    }
}