package tictactoe;

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

    //main method to execute code
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String pattern = scan.nextLine().trim();
        Main game = new Main(pattern);
        System.out.println(game.displaGame());
        String stateOfGame = game.checkWinState();

        if(Math.abs(game.numberOfOccurenceOf('X')-game.numberOfOccurenceOf('O'))>1)
                System.out.println("Impossible");
             else if(game.checkWinState('X')==true && game.checkWinState('O')==true)
                    System.out.println("Impossible");
                  else if (stateOfGame.compareTo("None") != 0)
                        System.out.println(stateOfGame);
                       else if (game.checkDraw().compareTo("Draw") == 0)
                            System.out.println("Draw");
                       else
                            System.out.println("Game not finished");

        scan.close();
    }
}