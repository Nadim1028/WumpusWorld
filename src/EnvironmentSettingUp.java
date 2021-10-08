import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class EnvironmentSettingUp
{
    int[][][] board = new int[10][10][7];
    int [][] agent_placement;
    int BOARD_SIZE=10;
    Scanner scanner=new Scanner(System.in);
    int num,numOfPit=0,numOfGold=1,numOfWumpus=1,numOfArrow=1;
    int Visited=0,Breeze=1,Gold=2,SafeSquare=3,Pit=4,Stench=5,Wumpus=6;


    public void setupEnvironment(){

        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){
                for(int k=0;k<7;k++){
                    board[i][j][k]=0;
                }
            }
        }


        while(true){
            System.out.println("Enter your choice.");
            System.out.println("1.Fixed Environment  2.Random Environment  3.Print  4.Exit");
            num=scanner.nextInt();

            if(num==1){
                setFixedEnvironment();
            }

            else if(num==2){
                setRandomEnvironment();
            }

            else if(num==3){
                print();
            }

            else if(num==4){
                break;
            }

            else {
                System.out.println("Choose option correctly.");
            }
        }

    }

    private void setRandomEnvironment()
    {
        System.out.println("Enter the number of Gold.");
        numOfGold=scanner.nextInt();
        System.out.println("Enter the number of Pit.");
        numOfPit=scanner.nextInt();
        System.out.println("Enter the number of Wumpus.");
        numOfWumpus=scanner.nextInt();

        numOfArrow=numOfWumpus;
        generateRandomBoard(numOfGold,numOfPit,numOfWumpus);

    }

    private void generateRandomBoard(int numOfGold, int numOfPit, int numOfWumpus) {
        //System.out.println(numOfGold+" "+numOfPit+" "+numOfWumpus);
        agent_placement = new int [10][10];
        agent_placement[9][0] = 1;

        do {
            Random rand = new Random();
            for (int i = 0; i < numOfPit; i++) {
                int x = rand.nextInt(100);
                int row = x / 10;
                int col = x % 10;
                if ((row == BOARD_SIZE - 1 && (col == 0 || col == 1)) || (row == BOARD_SIZE - 2 && col == 0)) {
                    i--;
                    continue;
                }
                board[row][col][4] = 1;
            }

            for(int i = 0; i< numOfWumpus; i++) {
                int x = rand.nextInt( 100 );
                int row = x / 10;
                int col = x % 10;
                if (  (row == BOARD_SIZE-1 && (col == 0 || col == 1)) || (row == BOARD_SIZE-2  && col == 0 )  ){
                    i --;
                    continue;
                }
                if ( board[row][col][Pit] == 1 ){
                    i --;
                    continue;
                }
                board[row][col][Wumpus] = 1;
            }

            for(int i = 0; i< numOfGold; i++) {
                int x = rand.nextInt( 100 );
                int row = x / 10;
                int col = x % 10;
                if (row > 5 || ( row == ( BOARD_SIZE - 1 ) && col == 0) ){
                    i --;
                    continue;
                }
                if ( board[row][col][Pit] == 1  || board[row][col][Wumpus] == 1) {
                    i --;
                    continue;
                }

                board[row][col][Gold] = 1;

            }

        } while(!bfs());
    }

    private void setFixedEnvironment()
    {
        numOfGold=1;
        numOfPit=13;
        numOfWumpus=1;

        board[4][4][6]=1;//Wumpus SetUp
        board[6][6][2]=1;//Gold SetUp

        setPitValueInFixedEnv(numOfPit);

    }

    private void setPitValueInFixedEnv(int numOfPit) {
        board[0][6][4]=1;
        board[1][3][4]=1;
        board[2][7][4]=1;
        board[3][1][4]=1;
        board[3][4][4]=1;
        board[4][8][4]=1;
        board[5][2][4]=1;
        board[5][5][4]=1;
        board[7][0][4]=1;
        board[7][6][4]=1;
        board[8][3][4]=1;
        board[8][7][4]=1;
        board[9][9][4]=1;
    }

    public void print(){
        for(int i=0;i<10;i++){
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
        }
    }

    public boolean bfs( )
    {
        ArrayList<Integer> queue = new ArrayList<Integer>();
        boolean solution_exists = false;

        int[][] marked = new int[BOARD_SIZE][BOARD_SIZE];
        int[][] nodesID = new int[BOARD_SIZE][BOARD_SIZE];
        int[][] relationships = new int[BOARD_SIZE*BOARD_SIZE][BOARD_SIZE*BOARD_SIZE];

        int node_count = 0;

        for ( int row = 0; row < BOARD_SIZE; row++ )
        {
            for ( int col = 0; col < BOARD_SIZE; col++ )
            {
                marked[row][col] = 0;
            }
        }

        for ( int row = 0; row < BOARD_SIZE; row++ )
        {
            for ( int col = 0; col < BOARD_SIZE; col++ )
            {
                nodesID[row][col] = node_count;
                node_count += 1;
            }
        }

        for ( int row = 0; row < BOARD_SIZE*BOARD_SIZE; row++ )
        {
            for ( int col = 0; col < BOARD_SIZE*BOARD_SIZE; col++ )
            {
                relationships[row][col] = 0;
            }
        }

        for ( int row = 0; row < BOARD_SIZE; row++ )
        {
            for ( int col = 0; col < BOARD_SIZE; col++ )
            {
                try
                {
                    relationships[ nodesID[row][col] ][ nodesID[row][col-1]  ] = 1;
                }
                catch( ArrayIndexOutOfBoundsException e ){  }

                try
                {
                    relationships[ nodesID[row][col] ][ nodesID[row-1][col]  ] = 1;
                }
                catch( ArrayIndexOutOfBoundsException e ){  }

                try
                {
                    relationships[ nodesID[row][col] ][ nodesID[row][col+1]  ] = 1;
                }
                catch( ArrayIndexOutOfBoundsException e ){  }

                try
                {
                    relationships[ nodesID[row][col] ][ nodesID[row+1][col]  ] = 1;
                }
                catch( ArrayIndexOutOfBoundsException e ){  }
            }
        }

        queue.add( nodesID[BOARD_SIZE-1][0 ] );
        marked[BOARD_SIZE-1][0 ] = 1;

        while( !queue.isEmpty() )
        {
            int node = queue.remove( 0 );

            if( board[ ( int ) node / 10 ][ (int) node % 10 ] [Gold] == 1 )
            {
                solution_exists = true;
                break;
            }
            else
            {
                for ( int i = 0 ; i < BOARD_SIZE*BOARD_SIZE ; i++ )
                {
                    if ( relationships[node][i] == 1 && board[ ( int ) i / 10 ][ (int) i % 10 ] [Pit] != 1 && marked[ ( int ) i / 10 ][ (int) i % 10 ] != 1 )
                    {
                        queue.add( nodesID[ ( int ) i / 10 ][ (int) i % 10 ] );
                        marked[ ( int ) i / 10 ][ (int) i % 10 ] = 1;
                    }
                }
            }
        }

        return solution_exists;
    }

}

