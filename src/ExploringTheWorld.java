import javax.swing.*;

public class ExploringTheWorld extends SwingWorker<Void, String> {
    EnvironmentSettingUp environmentSettingUp = new EnvironmentSettingUp();
    int[][][] board = new int[10][10][7];

    public void setGameBoard(){

        environmentSettingUp.setupEnvironment();
        board=environmentSettingUp.board;

        /*for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){

                if(board[i][j][6]==1){
                    System.out.println("Wumpus is here.\nPosition = "+i+","+j+"\n");
                }
                if(board[i][j][2]==1){
                    System.out.println("Gold is here.\nPosition = "+i+","+j+"\n");
                }

                if(board[i][j][4]==1){
                    System.out.println("Pit Position = "+i+","+j+"\n");
                }
            }
        }*/
    }

    @Override
    protected Void doInBackground() throws Exception {

        return null;
    }


}
