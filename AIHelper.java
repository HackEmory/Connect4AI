class AIHelper{

    /**
    * Generates an array of all playable moves from a given state
    *
    * @param  b     The current state of the board
    * @return   An array  of possible boards
    */
    public static Board[] genAllMoves(Board b){
        int playableColumns = 0; //Count the playable columns

        for(int i = 1 ; i<=7 ; i++){
            if (b.isColPlayable(i)){
                playableColumns++;
            }
        }

        Board[] possiblities = new Board[playableColumns];
        int index=0;

        for(int i = 0 ; i <  7 ; i++){ //For every column
            char[][] positions = new char[6][7]; //Copy over positions
            for(int j = 0 ; j < 6 ; j++){
                for(int k = 0 ; k < 7 ; k++){
                    positions[j][k] = b.positions[j][k];
                }
            }

            if(b.isColPlayable(i+1)){ //If the column is a possible move
                Board temp = new Board(); //Make a new board
                temp.positions = positions; //Copy positions
                temp.moves = b.moves; //Copy moves
                temp.playPiece('O', i+1); //Play the possible move
                possiblities[index++] = temp; //Adjust the index
            }
        }

        return possiblities;
    }

    /**
    * Calculates next best move for the opponent using minimax
    *
    * @param  level   The number of levels deep to search in the tree
    * @param  c   The person who is playing ('X' or 'O')
    * @param  alpha   Best score of the maximizer
    * @param  beta   Best score of the minimizer
    * @param  b   The board being searched

    * @return         An array  of possible boards
    */
    public static int[] minimax(int level, char c, int alpha, int beta, Board b){
        Board[] nextMoves = AIHelper.genAllMoves(b);
 
        int score; // Keep track of score
        int bestMove = (int)(Math.random()*7)+1; // Random unless a favorable move is found

        if(level==0 || b.isDraw()){ // Once bottom of the tree is found
            score = b.scoreBoard();
            return new int[] {score, bestMove};
        }else{
            for(int i = 0 ; i < nextMoves.length ; i++){ // For every possible move
                if(c=='O'){
                    score = minimax(level - 1, 'X', alpha, beta, nextMoves[i])[0]; // Go to next move
                    if (score > alpha) { // Check if the move is more favorable
                       alpha = score;
                       bestMove = nextMoves[i].lastMove;
                    }
                }else{
                    score = minimax(level - 1, 'O', alpha, beta, nextMoves[i])[0]; // Go to next move
                    if (score < beta) { // Check if the move is more favorable
                        beta = score;
                        bestMove = nextMoves[i].lastMove;
                    }
                }
                if (alpha >= beta) break;
            }

            return new int[] {(c == 'O') ? alpha : beta, bestMove}; // Return the score and the best move
        }
    }
}