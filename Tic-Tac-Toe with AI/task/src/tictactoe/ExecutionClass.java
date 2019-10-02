import java.util.Scanner;

public class ExecutionClass {
    public static void main(String[] args) {
        Scanner scan =new Scanner(System.in);
        String pattern=scan.nextLine().trim();
        Main game = new Main(pattern);
        System.out.println(game.displaGame());
        System.out.println(game.checkWinState());
        scan.close();
    }
}
