/******************** Importing Essential Libraries ************************/
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/******************** Creating TicTacToe class ************************/
class TicTacToe{

    char[] board = new char[10];
    char winner;
    
    // constructor

    public TicTacToe(){
        // Initializing the board
        for(int i = 0; i < 10; i++){
            char ch = Integer.toString(i).charAt(0); // converting the interger number into character
            this.board[i] = ch; 
        }
    }
    
    // function for printing the current board

    public void printBoard(){
        
        for(int i = 1; i < 10; i++){
            if(i == 1 || i == 4 || i == 7) System.out.print("\t\t\t| ");

            System.out.print(this.board[i] + " | ");

            if(i % 3 == 0 && i != 9)  {
                System.out.print("\n\t\t\t --- --- ---\n");
            }
        }

        System.out.println("\n");      
    }

    // function for checking current available moves

    public List<Integer> available_moves(){

        List<Integer> moves = new ArrayList<>();

        for(int i = 1; i < 10; i++){
            char ch = (char) this.board[i];
            if(ch != 'X' && ch != 'O') {
                moves.add(i);
            }
        }

        return moves;
    }

    // function for checking num of empty squares

    public int num_empty_squares(){

        return (available_moves().size());
    }

    public boolean isWinner(char letter){

        // checking all rows
        if(this.board[1] == letter && this.board[2] == letter && this.board[3] == letter) 
            return true;
        else if(this.board[4] == letter && this.board[5] == letter && this.board[6] == letter) 
            return true;
        else if(this.board[7] == letter && this.board[8] == letter && this.board[9] == letter) 
            return true;

        
        // checking all columns
        if(this.board[1] == letter && this.board[4] == letter && this.board[7] == letter) 
            return true;
        else if(this.board[2] == letter && this.board[5] == letter && this.board[8] == letter) 
            return true;
        else if(this.board[3] == letter && this.board[6] == letter && this.board[9] == letter) 
            return true;

        // checking the diagonals
        if(this.board[1] == letter && this.board[5] == letter && this.board[9] == letter) 
           return true;
        else if(this.board[3] == letter && this.board[5] == letter && this.board[7] == letter) 
           return true;

        // if no match return false
        return false;
    }

}

/******************** Creating Player class ************************/
abstract class Player{
    char letter;

    public Player(char letter){
        this.letter = letter;
    }
    public abstract int getMove(TicTacToe game);
}

/******************** Creating Human Player class which is child of Player class ************************/
class HumanPlayer extends Player{

    // constructor
    public HumanPlayer(char letter){
        super(letter);
    }

    // function for making a move
    public int getMove(TicTacToe game){
 
        Scanner scanner = new Scanner(System.in);
        boolean validSquare = false;
        int position = 0;

        while(!validSquare){

            System.out.print("Your turn. Input move (1-9): ");

            try{
                position = scanner.nextInt();

                if(game.board[position] != 'X' && game.board[position] != 'O' /*&& game.num_empty_squares() > 0/* */){

                    game.board[position] = this.letter; 
                    validSquare = true;
                    System.out.println();  // printing new line

                } else{
                    System.out.println("\nInvalid Square. Try again!\n");
                }
                
            }catch(ArrayIndexOutOfBoundsException e1){
                // handling array of index out of bound case
                System.out.println("\nInvalid Input. Please enter a number between 1 and 9\n");

            }catch(InputMismatchException e2){
                // handling input mismatch exception
                System.out.println("\nInvalid Input. Please enter a number between 1 and 9\n");
            }
        }
        return position;
    }
}

/******************** Creating Computer Player class which is child of Player class ************************/
class ComputerPlayer extends Player{

    // constructor
    public ComputerPlayer(char letter){
        super(letter);
    }

    // function for making a move
    public int getMove(TicTacToe game){
        
        List<Integer> moves = game.available_moves();
        Random rand = new Random();
        
        int index = rand.nextInt(moves.size());
        int position = moves.get(index);
        game.board[position] = this.letter;

        return position;
    }
}

/******************** Main class ************************/
public class Main{
    public static void main(String[] args) {

        TicTacToe t = new TicTacToe();
        
        System.out.println("\nLet's play the famous tic-tac-toe game with me!\n");
               
        Scanner scanner = new Scanner(System.in);
        boolean playerInput = false;
        char letter = 'Y';

        // choosing the correct character from the user
        while(!playerInput){

            System.out.print("Which character you want to choose? ('X' or 'O') : ");
            letter = scanner.next().charAt(0);

            if(letter != 'X' && letter != 'O'){
                System.out.println("\nInvalid choice. Please enter 'X' or 'O'.\n");    
            }
            else{
                playerInput = true;
            }
        }
        System.out.println("");
        
        HumanPlayer human = new HumanPlayer(letter);

        if(letter == 'X'){
            letter = 'O';
        }
        else letter = 'X';

        ComputerPlayer computer = new ComputerPlayer(letter);

        System.out.println("Okay! then I am choosing the other character : " + computer.letter+ "\n");
        

        System.out.println("Now, let's start the game!");
        System.out.println();

        boolean gameOver = false;
        boolean flag = true;

        while(!gameOver && t.num_empty_squares() > 0){

            t.printBoard();

            int position;

            if(flag){

                position = human.getMove(t);

                System.out.println("Human player makes a move to position " + position + "\n");

                if(t.isWinner(human.letter)){
                    t.printBoard();
                    gameOver = true;
                    System.out.println("Congratulations! You have won the game!! \n");           
                }
                flag = false;

            }
            else{

                position = computer.getMove(t);
                System.out.println("I am choosing position " + position + "\n");

                if(t.isWinner(computer.letter)){
                    t.printBoard();
                    gameOver = true;
                    System.out.println("I have won the game!! ha ha ha!! \n");
                }
                flag = true;
            }
            
        }

        if(gameOver == false){
            System.out.println("It's a Tie \n");
        }
    }

}