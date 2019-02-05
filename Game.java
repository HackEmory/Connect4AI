import java.io.Console;

class Game{
    public static void main(String[] args){

        System.out.println("Welcome to Connect4 against a minimax AI!");
        System.out.println("\t-To play your piece simply enter the column then click enter");
        System.out.println("\t-If you ever want to leave the game type 'exit'");

        Board board = new Board();
        System.out.println(board);

        // Keep playing until somebody wins
        while(!board.isDraw() && !board.checkWin('X') && !board.checkWin('O')){

            // Try to play a piece
            Console console = System.console();
            String input = console.readLine("Enter column to play: ");
            int result=-1;

            try{
                result = Integer.parseInt(input);
            }
            catch(Exception e){
                if(input.equals("exit")){
                    System.exit(0);
                }
                System.out.println("\nPlease enter a number!\n");
                continue;
            }

            if(board.isColPlayable(result)){
                board.playPiece('X', result);
            }else{
                System.out.println("\nPlease enter a playable column!\n");
                continue;
            }

            // Print the board after a piece is played
            System.out.println(board);

            // If the game hasn't been won have the AI play a piece
            if(!board.isDraw() && !board.checkWin('X') && !board.checkWin('O')){
                int move = AIHelper.minimax(4, 'O', Integer.MIN_VALUE, Integer.MAX_VALUE, board)[1]; // Generate best move
                board.playPiece('O', move);
            }

            // Print the board after the AI plays
            System.out.println(board);
        }

        if(board.checkWin('X')){
            System.out.println("You win!");
        }else if(board.checkWin('O')){
            System.out.println("The AI wins!");
        }

    }
}