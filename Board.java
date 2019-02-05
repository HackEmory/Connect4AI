class Board{
    char[][] positions = new char[6][7]; // Represents pieces on the board
    int moves = 0; // Counts number of moves played
    int lastMove = 0; // Keeps track of the last move played

    /**
    * Converts the board into a user friendly string
    *
    * @return   The board represented as a string
    */
    public String toString(){
        String divider = "+---+---+---+---+---+---+---+\n|";
        String board = "\n  1   2   3   4   5   6   7   \n" + divider;
        for(int i = 0 ; i < 6 ; i++){ // For every row
            for(int j = 0 ; j < 7 ; j++){ // For every column
                if(positions[i][j]!=0){ //If there is a character put it in between | |
                    board+=" " + positions[i][j] + " |";
                }else{ //If the tile is empty put spaces
                    board+="   |";
                }
            }
            board+="\n"+divider;
        }

        return board.substring(0, board.length() - 1); //Remove last character (extra |)
    }

    /**
    * Checks to see if a piece can be played in a given column
    *
    * @param  col   The column being checked
    * @return   Whether or not the piece can be placed there
    */
    public boolean isColPlayable(int col){
        if(col>0 && col<8){ // Make sure it is a valid column
            return positions[0][col-1]==0; // If there is no existing value
        }else{
            return false;
        }
    }

    /**
    * Plays a piece in the given column
    *
    * @param  c    The person placing the piece ('X' or 'O')
    * @param  col   The column being played
    */
    public void playPiece(char c,int col){
        this.lastMove = col; // Update last move for use in minimax

        for(int i = 0; i < 5 ; i++){ // Search down a column until something is found
            if(positions[i+1][col-1]!=0){
                positions[i][col-1]=c;
                moves++;
                return;
            }
        }

        // Check edge case of the bottom of the column
        if(positions[5][col-1]==0){
            positions[5][col-1]=c;
            moves++;
        }
    }

    /**
    * Checks to see if a player has won the game
    *
    * @param  c     The person who may or may not have won
    * @return   Whether or not they have won
    */
    public boolean checkWin(char c){
        for(int i = 0 ; i < 6 ; i++){ //Check vertically
            for(int j = 0 ; j < 4 ; j++){
                if(positions[i][j]==c && positions[i][j+1]==c && positions[i][j+2]==c && positions[i][j+3]==c){
                    return true;
                }
            }
        }
        for(int i = 0 ; i < 3 ; i++){ //Check horizontally
            for(int j = 0 ; j < 7 ; j++){
                if(positions[i][j]==c && positions[i+1][j]==c && positions[i+2][j]==c && positions[i+3][j]==c){
                    return true;
                }
            }
        }
        for(int i = 0 ; i < 3 ; i++){ //Check diagonally up
            for(int j = 3 ; j < 7 ; j++){
                if(positions[i][j]==c && positions[i+1][j-1]==c && positions[i+2][j-2]==c && positions[i+3][j-3]==c){
                    return true;
                }
            }
        }
        for(int i = 3 ; i < 6 ; i++){ //Check diagonally down
            for(int j = 3 ; j < 7 ; j++){
                if(positions[i][j]==c && positions[i-1][j-1]==c && positions[i-2][j-2]==c && positions[i-3][j-3]==c){
                    return true;
                }
            }
        }

        return false; // If nothing was found the game continues
    }

    /**
    * Assigns a score to the current state of the board for use in minimax
    *
    * @return   The score of the board
    */
    public int scoreBoard(){
        if (this.checkWin('X')){
            return 100 + (42-moves);
        }else if(this.checkWin('O')){
            return -100 - (42-moves);
        }else{
            return 0;
        }
    }

    /**
    * Tells us whether or not the game is a draw 
    *
    * @return   Whether or not the game is a draw 
    */
    public boolean isDraw(){
        return (moves==42 && !this.checkWin('X') && !this.checkWin('O'));
    }
}

/* 

Example Board

  1   2   3   4   5   6   7
+---+---+---+---+---+---+---+
|   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+
|   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+
|   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+
|   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+
|   | X | O |   |   |   |   |
+---+---+---+---+---+---+---+
| X | O | X |   |   |   |   |
+---+---+---+---+---+---+---+

*/
