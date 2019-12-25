package tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

//Main Class for Game. All Methods are present in this class.
public class Main {

    //this is the pattern we will get from game state
    private String pattern;

    //created a global scan
    public static Scanner scan = new Scanner(System.in);

    //Constructor of this class
    public Main(String pattern) {
        this.pattern = pattern;
    }

    //main method to execute code
    public static void main(String[] args) {
        String pattern = "\"         \"";
        Main game = new Main(pattern);
        boolean flag = true;                    //for taking correct input from user
        String[] userChoice = {"", "", ""};
        int gameLoopVariable = 9;

        //loop for taking user input
        while (flag) {
            System.out.print("Input command: ");
            userChoice = scan.nextLine().trim().split(" ", 3);
            if (userChoice.length == 3 || (userChoice.length == 1 && userChoice[0].equals("exit")))
                flag = false;
            else
                System.out.println("Bad parameters!");
        }

        if (userChoice[0].equals("exit"))
            return;

        System.out.println(game.displaGame());

        //condition for deciding if the game is AI vs AI
        if ((userChoice[1].equals("easy") || userChoice[1].equals("medium") || userChoice[1].equals("hard")) && (userChoice[2].equals("easy") || userChoice[2].equals("medium") || userChoice[2].equals("hard"))) {
            while (gameLoopVariable > 0) {

                //condition for making first computer move
                if (userChoice[1].equals("easy")) {
                    System.out.println("Making move level \"easy\"");
                    game.computerMove('X', 0);
                } else if (userChoice[1].equals("medium")) {
                    System.out.println("Making move level \"medium\"");
                    game.computerMove('X', 1);
                }

                //checking if anyone has won
                if (game.checkWinState('X')) {
                    System.out.println("X wins");
                    break;
                }

                gameLoopVariable--;

                if (gameLoopVariable <= 0)
                    break;

                //condition for making second computer move
                if (userChoice[2].equals("easy")) {
                    System.out.println("Making move level \"easy\"");
                    game.computerMove('O', 0);
                } else if (userChoice[2].equals("medium")) {
                    System.out.println("Making move level \"medium\"");
                    game.computerMove('O', 1);
                }

                //checking if anyone has won
                if (game.checkWinState('O')) {
                    System.out.println("O wins");
                    break;
                }
                gameLoopVariable--;
            }
        }
        //condtion for checking if the game is human vs AI
        else if (userChoice[1].equals("user") && (userChoice[2].equals("easy") || userChoice[2].equals("medium") || userChoice[2].equals("hard"))) {
            while (gameLoopVariable > 0) {
                //waiting till user makes a correct input
                while (game.userInput('X')) ;

                //checking if anyone has won
                if (game.checkWinState('X')) {
                    System.out.println("X wins");
                    break;
                }

                gameLoopVariable--;

                if (gameLoopVariable <= 0)
                    break;

                //making a computer move
                if (userChoice[2].equals("easy")) {
                    System.out.println("Making move level \"easy\"");
                    game.computerMove('O', 0);
                } else if (userChoice[2].equals("medium")) {
                    System.out.println("Making move level \"medium\"");
                    game.computerMove('O', 1);
                }

                //checking if anyone has won
                if (game.checkWinState('O')) {
                    System.out.println("O wins");
                    break;
                }
                gameLoopVariable--;
            }
        }
        //checking if the game is human vs human
        else if (userChoice[1].equals("user") && userChoice[2].equals("user")) {
            while (gameLoopVariable > 0) {
                //waiting till user makes a correct move
                while (game.userInput('X')) ;

                //checking if someone has won
                if (game.checkWinState('X')) {
                    System.out.println("X wins");
                    break;
                }

                gameLoopVariable--;

                if (gameLoopVariable <= 0)
                    break;

                //waiting till user makes a correct move
                while (game.userInput('O')) ;

                //checking if someone has won
                if (game.checkWinState('O')) {
                    System.out.println("O wins");
                    break;
                }
                gameLoopVariable--;
            }
        }
        //checking if the game is AI vs human
        else if ((userChoice[1].equals("easy") || userChoice[1].equals("medium")) && userChoice[2].equals("user")) {
            while (gameLoopVariable > 0) {

                //making a computer move
                if (userChoice[1].equals("easy")) {
                    System.out.println("Making move level \"easy\"");
                    game.computerMove('X', 0);
                } else if (userChoice[1].equals("medium")) {
                    System.out.println("Making move level \"medium\"");
                    game.computerMove('X', 1);
                }

                //checking if someone has won
                if (game.checkWinState('X')) {
                    System.out.println("X wins");
                    break;
                }
                gameLoopVariable--;

                if (gameLoopVariable <= 0)
                    break;

                //waiting till user makes valid move
                while (game.userInput('O')) ;

                //checkiing if someone has won
                if (game.checkWinState('O')) {
                    System.out.println("O wins");
                    break;
                }
                gameLoopVariable--;
            }
        }

        //condition for printing the state of game
        if (gameLoopVariable <= 0)
            System.out.println(game.printWinState());
    }

    //setter for this class pattern attribute
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    //this function checks for the winner of the game
    public String checkWinState() {

        //declaring a 2D array of all possible position of winning the game
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

        //loop for checking if anyone has won
        for (int i = 0; i < 8; i++) {
            if (pattern.charAt(a[i][0]) == pattern.charAt(a[i][1]) && pattern.charAt(a[i][1]) == pattern.charAt(a[i][2]))
                return pattern.charAt(a[i][0]) + " wins";
        }

        //returns none if no one has won
        return "None";
    }

    //this function is overloaded and it checks that a particular player won or not
    public boolean checkWinState(char c) {

        //declaring a 2D array of all possible position of winning the game
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

        //loop for checking if particular player has won
        for (int i = 0; i < 8; i++) {
            if (pattern.charAt(a[i][0]) == pattern.charAt(a[i][1]) && pattern.charAt(a[i][1]) == pattern.charAt(a[i][2]) && pattern.charAt(a[i][0]) == c)
                return true;
        }

        //returns false if that player has not won the game
        return false;
    }

    //this function is to check whether the bot can win in a single move or not
    public boolean canIWin(char move) {

        //declaring the 2D array of all possible postition of winning the game
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

        //loop which checks if AI can win the game in single move
        //if yes it will make that move and update the changes in game
        for (int i = 0; i < 8; i++) {
            if (pattern.charAt(a[i][0]) == move && pattern.charAt(a[i][0]) == pattern.charAt(a[i][1]) && pattern.charAt(a[i][2]) == ' ') {
                return this.updateState(move, a[i][2]);
            } else if (pattern.charAt(a[i][1]) == move && pattern.charAt(a[i][1]) == pattern.charAt(a[i][2]) && pattern.charAt(a[i][0]) == ' ') {
                return this.updateState(move, a[i][0]);
            } else if (pattern.charAt(a[i][0]) == move && pattern.charAt(a[i][0]) == pattern.charAt(a[i][2]) && pattern.charAt(a[i][1]) == ' ') {
                return this.updateState(move, a[i][0]);
            }
        }

        //returns false if not
        return false;
    }

    //this function is to check whether the user can win in a single move or not if yes then stop the user
    public boolean stopUserFromWin(char move) {

        //declaring the 2D array of all possible postition of winning the game
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
        //deciding the user move
        char userMove = (move == 'X') ? 'O' : 'X';

        //loop checks if user can win in single move
        //if yes then block the user
        for (int i = 0; i < 8; i++) {
            if (pattern.charAt(a[i][0]) == userMove && pattern.charAt(a[i][0]) == pattern.charAt(a[i][1]) && pattern.charAt(a[i][2]) == ' ') {
                return updateState(move, a[i][2]);
            } else if (pattern.charAt(a[i][1]) == userMove && pattern.charAt(a[i][1]) == pattern.charAt(a[i][2]) && pattern.charAt(a[i][0]) == ' ') {
                return updateState(move, a[i][0]);
            } else if (pattern.charAt(a[i][0]) == userMove && pattern.charAt(a[i][0]) == pattern.charAt(a[i][2]) && pattern.charAt(a[i][1]) == ' ') {
                return updateState(move, a[i][1]);
            }
        }

        //returns false if not
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

    //this function displays state of game
    public String displaGame() {
        return "---------" + "\n"
                + "| " + pattern.charAt(1) + " " + pattern.charAt(2) + " " + pattern.charAt(3) + " |" + "\n"
                + "| " + pattern.charAt(4) + " " + pattern.charAt(5) + " " + pattern.charAt(6) + " |" + "\n"
                + "| " + pattern.charAt(7) + " " + pattern.charAt(8) + " " + pattern.charAt(9) + " |" + "\n"
                + "---------";
    }

    //this function checks whether game is draw or not
    public String checkDraw() {
        if (pattern.indexOf(' ') == -1)
            return "Draw";
        return "None";
    }

    //this function adds new user move to the game pattern
    public int updateState(char choice, int move1, int move2) {
        int indexOfChoiceInPattern = (move1 - 1) + 3 * (3 - move2) + 1;

        //condition for checking if the particular place is free or not for the move
        if (pattern.charAt(indexOfChoiceInPattern) == 'X' || pattern.charAt(indexOfChoiceInPattern) == 'O')
            return 0;

        //updating the game state
        pattern = pattern.substring(1, indexOfChoiceInPattern) + choice + pattern.substring(indexOfChoiceInPattern + 1);
        pattern = "\"" + pattern;
        return 1;

    }

    //this function is overloaded and it adds new computer move to the game pattern
    public boolean updateState(char choice, int moveValue) {
        //condition for checking if the particular place is free or not for the move
        if (pattern.charAt(moveValue) == 'X' || pattern.charAt(moveValue) == 'O')
            return false;

        //updating the game state
        pattern = pattern.substring(1, moveValue) + choice + pattern.substring(moveValue + 1);
        pattern = "\"" + pattern;
        return true;

    }

    //function to print win state of game
    public String printWinState() {
        String stateOfGame = this.checkWinState();
        if (Math.abs(this.numberOfOccurenceOf('X') - this.numberOfOccurenceOf('O')) > 1)
            return "Impossible";
        else if (this.checkWinState('X') == true && this.checkWinState('O') == true)
            return "Impossible";
        else if (stateOfGame.compareTo("None") != 0)
            return stateOfGame;
        else if (this.checkDraw().compareTo("Draw") == 0)
            return "Draw";
        else
            return "Game not finished";
    }

    //method for making computer move
    public void computerMove(char move, int difficulty) {
        int computerMove1, computerMove2;
        boolean flag = true;
        while (flag) {
            //condition for defining difficulty of comuter move
            if (difficulty == 0) {
                computerMove1 = (int) (Math.random() * 3) + 1;
                computerMove2 = (int) (Math.random() * 3) + 1;
                if (computerMove1 > 3 || computerMove1 < 1 || computerMove2 > 3 || computerMove2 < 1) {
                    flag = true;
                } else if (this.updateState(move, computerMove1, computerMove2) == 1) {
                    System.out.println(this.displaGame());
                    flag = false;
                } else if (this.numberOfOccurenceOf(' ') == 0)
                    flag = false;
            } else if (difficulty == 1) {
                //making move to win in a single move
                if (this.canIWin(move)) {
                    System.out.println(this.displaGame());
                    flag = false;
                } else if (this.stopUserFromWin(move)) {
                    System.out.println(this.displaGame());
                    flag = false;
                } else {
                    //making a random move after both cases fail
                    computerMove1 = (int) (Math.random() * 3) + 1;
                    computerMove2 = (int) (Math.random() * 3) + 1;
                    if (computerMove1 > 3 || computerMove1 < 1 || computerMove2 > 3 || computerMove2 < 1) {
                        flag = true;
                    } else if (this.updateState(move, computerMove1, computerMove2) == 1) {
                        System.out.println(this.displaGame());
                        flag = false;
                    } else if (this.numberOfOccurenceOf(' ') == 0)
                        flag = false;
                }
            }
        }
    }

    //method for user input
    public boolean userInput(char move) {
        int userMove1, userMove2;
        try {
            System.out.print("Enter the coordinates: ");
            userMove1 = scan.nextInt();
            userMove2 = scan.nextInt();
            if (userMove1 > 3 || userMove1 < 1 || userMove2 > 3 || userMove2 < 1) {
                System.out.println("Coordinates should be from 1 to 3!");
                return true;
            } else if (this.updateState(move, userMove1, userMove2) == 1) {
                System.out.println(this.displaGame());
                return false;
            } else if (this.numberOfOccurenceOf(' ') == 0) {
                System.out.println(this);
                return false;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
                return true;
            }
        } catch (InputMismatchException e) {
            System.out.println("You should enter numbers!");
            scan.next();
            return true;
        }
    }
}