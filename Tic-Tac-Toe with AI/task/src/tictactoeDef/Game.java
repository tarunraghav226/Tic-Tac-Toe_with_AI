package  tictactoe;
public class Game {

    private String pattern;

    public Game(String pattern){this.pattern=pattern;}

    public void setPattern(String pattern){this.pattern=pattern;}

    public String displaGame() {
       return "---------"+"\n"
               +"| "+pattern.charAt(1)+" "+pattern.charAt(2)+" "+pattern.charAt(3)+" |"+"\n"
               +"| "+pattern.charAt(4)+" "+pattern.charAt(5)+" "+pattern.charAt(6)+" |"+"\n"
               +"| "+pattern.charAt(7)+" "+pattern.charAt(8)+" "+pattern.charAt(9)+" |"+"\n"
               +"---------";
    }

    public String checkWinState(){
        int [][]a={
                    {1,2,3},
                    {4,5,6},
                    {7,8,9},
                    {1,5,9},
                    {3,5,7},
                    {1,4,7},
                    {2,5,8},
                    {3,6,9}
                  };
        for(int i=0;i<8;i++){
            if(pattern.charAt(a[i][0])==pattern.charAt(a[i][1])&&pattern.charAt(a[i][1])==pattern.charAt(a[i][2]))
                return pattern.charAt(a[i][0])+" Wins";
        }
        return "None";
    }
    public String checkDraw(){
        if(pattern.indexOf(' ')==-1)
            return "Draw";
        return "None";
    }
}