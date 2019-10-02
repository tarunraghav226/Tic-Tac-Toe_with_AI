import java.util.Scanner;

public class ExecutionClass {
    public static void main(String[] args) {
        Scanner scan =new Scanner(System.in);
        String pattern=scan.nextLine().trim();
        Main game = new Main(pattern);
        System.out.println(game.displaGame());
        String stateOfGame=game.checkWinState();

        if(stateOfGame.compareTo("None")!=0)
            System.out.println(stateOfGame);
        else if(game.checkDraw().compareTo("Draw")==0)
                System.out.println("Draw");
             else
                 System.out.println("Game not finished");
        scan.close();
    }
}
