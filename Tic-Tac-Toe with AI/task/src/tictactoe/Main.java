package tictactoe;
import java.util.Scanner;

public class Main {
    public static String displaGame(String pattern) {
       return "---------"+"\n"
               +"| "+pattern.charAt(1)+" "+pattern.charAt(2)+" "+pattern.charAt(3)+" |"+"\n"
               +"| "+pattern.charAt(4)+" "+pattern.charAt(5)+" "+pattern.charAt(6)+" |"+"\n"
               +"| "+pattern.charAt(7)+" "+pattern.charAt(8)+" "+pattern.charAt(9)+" |"+"\n"
               +"---------";
    }

    public static void main(String[] args) {
            Scanner scan =new Scanner(System.in);
            String pattern=scan.nextLine();
            System.out.println(displaGame(pattern));
            scan.close();
        }

}