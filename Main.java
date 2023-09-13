import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main{

    public static void printBoard(char[] board){
        // function for printing the current board

        for(int i = 1; i < 10; i++){
            if(i == 1 || i == 4 || i == 7) System.out.print("| ");

            System.out.print(board[i] + " | ");

            if(i % 3 == 0 && i != 9)  {
                System.out.print("\n --- --- ---\n");
            }
        }

        System.out.println("\n");      
    }

    public static boolean winner(char[] board,char letter){

        // checking all rows
        if(board[1] == letter && board[2] == letter && board[3] == letter) 
            return true;
        else if(board[4] == letter && board[5] == letter && board[6] == letter) 
            return true;
        else if(board[7] == letter && board[8] == letter && board[9] == letter) 
            return true;

        
        // checking all columns
        if(board[1] == letter && board[4] == letter && board[7] == letter) 
            return true;
        else if(board[2] == letter && board[5] == letter && board[8] == letter) 
            return true;
        else if(board[3] == letter && board[6] == letter && board[9] == letter) 
            return true;

        // checking the diagonals
        if(board[1] == letter && board[5] == letter && board[9] == letter) 
           return true;
        else if(board[3] == letter && board[5] == letter && board[7] == letter) 
           return true;

        // if no match return false
        return false;
    }

    public static List<Integer> available_moves(char[] board){

        List<Integer> moves = new ArrayList<>();

        for(int i = 1; i < 10; i++){
            char ch = (char) board[i];
            if(ch != 'X' && ch != 'O') {
                moves.add(i);
            }
        }

        return moves;
    }

    public static int num_empty_squares(char[] board){

        return (available_moves(board).size());
    }

    public static void main(String[] args) {

        char[] board = new char[10];

        for(int i = 0; i < board.length; i++){
            board[i] = Integer.toString(i).charAt(0); // converting the interger number into character
        }

        // printBoard(board);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Which character you want to choose? ('X' or 'O') : ");
        char player = scanner.next().charAt(0);
        System.out.println("");

        char otherPlayer;
        if(player == 'X'){
            otherPlayer = 'O';
        }
        else otherPlayer = 'X';

        System.out.println("Okay. Then other player chooses character " + otherPlayer);
        System.out.println();

        System.out.println("Now, let's start the game!");
        System.out.println();

        boolean gameOver = false;

        while(!gameOver && num_empty_squares(board) > 0){
            printBoard(board);
            // gameOver = true;
            System.out.print("Player " + player + " enter: ");
            
            // try and catch block will starts here
            
            try{
                int position = scanner.nextInt();
                if(board[position] != 'X' && board[position] != 'O'){
                    board[position] = player;
                    gameOver = winner(board, player);
                    if(gameOver){
                        System.out.println("Player " + player + " has won!");
                    }
                    else{
                        if(player == 'X'){
                            player = 'O';
                        }else{
                            player = 'X';
                        }
                        
                    }
                }                
                else{
                    System.out.println("Invalid Move. Try again!");
                }
            }catch(ArrayIndexOutOfBoundsException e1){
                // handling array index out of bound case
                System.out.println("Invalid input. Please enter a number between 1 and 9.");

            }catch(InputMismatchException e2){
                // handling input mismatch exception
                System.out.println("Invalid input. Please enter a number between 1 and 9.");

            }
        
        }

        printBoard(board);

       if(!gameOver){
        
        System.out.println("It's tie");
       
        }

    }

}
