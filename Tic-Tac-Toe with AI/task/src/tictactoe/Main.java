package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

//Main Class for Game. All Methods are present in this class.
public class Main {

    //this is the pattern we will get from game state
    private String pattern;

    //Constructor of this class
    public Main(String pattern){this.pattern=pattern;}

    //setter for this class pattern attribute
    public void setPattern(String pattern){this.pattern=pattern;}

    //this function displays state of game
    public String displaGame() {
        return "---------"+"\n"
                +"| "+pattern.charAt(1)+" "+pattern.charAt(2)+" "+pattern.charAt(3)+" |"+"\n"
                +"| "+pattern.charAt(4)+" "+pattern.charAt(5)+" "+pattern.charAt(6)+" |"+"\n"
                +"| "+pattern.charAt(7)+" "+pattern.charAt(8)+" "+pattern.charAt(9)+" |"+"\n"
                +"---------";
    }

    //this function checks whether game is draw or not
    public String checkDraw(){
        if(pattern.indexOf(' ')==-1)
            return "Draw";
        return "None";
    }

    //this function checks for the winner of the game
    public String checkWinState() {
        int[][] a = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {1, 5, 9},
                {3, 5, 7},
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };
        for (int i = 0; i < 8; i++) {
            if (pattern.charAt(a[i][0]) == pattern.charAt(a[i][1]) && pattern.charAt(a[i][1]) == pattern.charAt(a[i][2]))
                return pattern.charAt(a[i][0]) + " wins";
        }
        return "None";
    }

    //this function is overloaded and it checks that a particular player won or not
    public boolean checkWinState(char c) {
        int[][] a = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {1, 5, 9},
                {3, 5, 7},
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };
        for (int i = 0; i < 8; i++) {
            if (pattern.charAt(a[i][0]) == pattern.charAt(a[i][1]) && pattern.charAt(a[i][1]) == pattern.charAt(a[i][2]) && pattern.charAt(a[i][0]) == c)
                return true;
        }
        return false;
    }

    //this function returns the number of occurence of a character in a string
    public int numberOfOccurenceOf(char c) {
        int occurence = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == c)
                occurence++;
        }
        return occurence;
    }

    //this function adds new user move to the game pattern
    public int updateState(char choice,int move1,int move2){
        int indexOfChoiceInPattern=(move1-1)+3*(3-move2)+1;

        //condition for checking if the particular place is free or not for the move
        if(pattern.charAt(indexOfChoiceInPattern)=='X' || pattern.charAt(indexOfChoiceInPattern)=='O')
            return 0;

        //updating the game state
        pattern=pattern.substring(1,indexOfChoiceInPattern)+choice+pattern.substring(indexOfChoiceInPattern+1);
        pattern="\""+pattern;
        return 1;

    }

    //function to print win state of game
    public String printWinState(){
        String stateOfGame = this.checkWinState();
        if(Math.abs(this.numberOfOccurenceOf('X')-this.numberOfOccurenceOf('O'))>1)
            return "Impossible";
        else if(this.checkWinState('X')==true && this.checkWinState('O')==true)
            return  "Impossible";
        else if (stateOfGame.compareTo("None") != 0)
            return stateOfGame;
        else if (this.checkDraw().compareTo("Draw") == 0)
            return "Draw";
        else
            return "Game not finished";
    }

    //main method to execute code
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String pattern = scan.nextLine().trim();
        Main game = new Main(pattern);
        System.out.println(game.displaGame());

        int c1,c2;
        boolean flag=true;
        while(flag) {
            try {
                System.out.print("Enter the coordinates: ");
                c1 = scan.nextInt();
                c2 = scan.nextInt();

                if(c1>3 || c1<1 || c2>3||c2<1) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    flag = true;
                }
                else if( game.updateState('X',c1,c2)==1){
                    System.out.println(game.displaGame());
                    flag=false;
                }
                else
                    System.out.println("This cell is occupied! Choose another one!");

            } catch (InputMismatchException e) {
                System.out.println("You should enter numbers!");
                scan.nextLine().trim();
            }
        }
        //System.out.println(game.printWinState());
        scan.close();
    }
}