import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class EnvironmentSettingUp
{
    int[][][] board = new int[10][10][8];
    int [][] agent_placement;
    int sizeOfBoard =10;
    Scanner scanner=new Scanner(System.in);
    int num,numOfPit=0,numOfGold=1,numOfWumpus=1, numOfArrows =1;
    int Visited=0,Breeze=1,Gold=2,SafeSquare=3,Pit=4,Stench=5,Wumpus=6,Glitter=7;


    public void setupEnvironment(){

        for(int i = 0; i< sizeOfBoard; i++){
            for(int j = 0; j< sizeOfBoard; j++){
                for(int k=0;k<8;k++){
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

        numOfArrows =numOfWumpus;
        generateRandomBoard(numOfGold,numOfPit,numOfWumpus);

    }

    private void generateRandomBoard(int numOfGold, int numOfPit, int numOfWumpus) {
        //System.out.println(numOfGold+" "+numOfPit+" "+numOfWumpus);
        agent_placement = new int [10][10];
        agent_placement[9][0] = 1;

        do {

            for(int i = 0; i< sizeOfBoard; i++){
                for(int j = 0; j< sizeOfBoard; j++){
                    for(int k=0;k<8;k++){
                        board[i][j][k]=0;
                    }
                }
            }

            Random rand = new Random();
            for (int i = 0; i < numOfPit; i++) {
                int x = rand.nextInt(100);
                int row = x / 10;
                int col = x % 10;
                if ((row == sizeOfBoard - 1 && (col == 0 || col == 1)) || (row == sizeOfBoard - 2 && col == 0) || (board[row][col][Pit]==1)) {
                    i--;
                    continue;
                }
                board[row][col][Pit] = 1;
            }

            for(int i = 0; i< numOfWumpus; i++) {
                int x = rand.nextInt( 100 );
                int row = x / 10;
                int col = x % 10;
                if (  (row == sizeOfBoard -1 && (col == 0 || col == 1)) || (row == sizeOfBoard -2  && col == 0 )  ){
                    i --;
                    continue;
                }
                if ( board[row][col][Pit] == 1 || board[row][col][Wumpus] == 1){
                    i --;
                    continue;
                }
                board[row][col][Wumpus] = 1;
            }

            for(int i = 0; i< numOfGold; i++) {
                int x = rand.nextInt( 100 );
                int row = x / 10;
                int col = x % 10;
                if (row > 5 || ( row == ( sizeOfBoard - 1 ) && col == 0) ){
                    i --;
                    continue;
                }
                if ( board[row][col][Pit] == 1 ){// || board[row][col][Wumpus] == 1) {
                    i --;
                    continue;
                }

                board[row][col][Gold] = 1;

            }

        } while(!bfs());

        for (int row = 0; row < sizeOfBoard; row++ )
        {
            for (int col = 0; col < sizeOfBoard; col++ )
            {
                if ( board[row][col][Pit] == 1 )
                {

                    try
                    {
                        if( board[row][col-1][Pit] != 1 )
                            board[row][col-1][Breeze] = 1;
                    }
                    catch( ArrayIndexOutOfBoundsException e ){  }

                    try
                    {
                        if( board[row-1][col][Pit] != 1 )
                            board[row-1][col][Breeze] = 1;
                    }
                    catch( ArrayIndexOutOfBoundsException e ){  }

                    try
                    {
                        if( board[row][col+1][Pit] != 1 )
                            board[row][col+1][Breeze] = 1;
                    }
                    catch( ArrayIndexOutOfBoundsException e ){  }

                    try
                    {
                        if( board[row+1][col][Pit] != 1 )
                            board[row+1][col][Breeze] = 1;
                    }
                    catch( ArrayIndexOutOfBoundsException e ){  }
                }

                if ( board[row][col][Wumpus] == 1 )
                {

                    try
                    {
                        if( board[row][col-1][Pit] != 1 ) board[row][col-1][Stench] = 1;
                    }
                    catch( ArrayIndexOutOfBoundsException e ){  }

                    try
                    {
                        if( board[row-1][col][Pit] != 1 ) board[row-1][col][Stench] = 1;
                    }
                    catch( ArrayIndexOutOfBoundsException e ){  }

                    try
                    {
                        if( board[row][col+1][Pit] != 1 ) board[row][col+1][Stench] = 1;
                    }
                    catch( ArrayIndexOutOfBoundsException e ){  }

                    try
                    {
                        if( board[row+1][col][Pit] != 1 ) board[row+1][col][Stench] = 1;
                    }
                    catch( ArrayIndexOutOfBoundsException e ){  }
                }

                if ( board[row][col][Gold] == 1 )
                {


                    try
                    {
                        if ( board[row][col-1][Pit] != 1 ) board[row][col-1][Glitter] = 1;
                    }
                    catch( ArrayIndexOutOfBoundsException e ){  }

                    try
                    {
                        if ( board[row-1][col][Pit] != 1 ) board[row-1][col][Glitter] = 1;
                    }
                    catch( ArrayIndexOutOfBoundsException e ){  }

                    try
                    {
                        if ( board[row][col+1][Pit] != 1 ) board[row][col+1][Glitter] = 1;
                    }
                    catch( ArrayIndexOutOfBoundsException e ){  }

                    try
                    {
                        if ( board[row+1][col][Pit] != 1 ) board[row+1][col][Glitter] = 1;
                    }
                    catch( ArrayIndexOutOfBoundsException e ){  }
                }
            }
        }
    }

    public boolean bfs( )
    {
        ArrayList<Integer> queue = new ArrayList<Integer>();
        boolean isSolutionExist = false;

        int[][] checked = new int[sizeOfBoard][sizeOfBoard];
        int[][] nodesID = new int[sizeOfBoard][sizeOfBoard];
        int[][] relationships = new int[sizeOfBoard * sizeOfBoard][sizeOfBoard * sizeOfBoard];

        int node_counter = 0;

        for (int row = 0; row < sizeOfBoard; row++ )
        {
            for (int col = 0; col < sizeOfBoard; col++ )
            {
                checked[row][col] = 0;
            }
        }

        for (int row = 0; row < sizeOfBoard; row++ )
        {
            for (int col = 0; col < sizeOfBoard; col++ )
            {
                nodesID[row][col] = node_counter;
                node_counter += 1;
            }
        }

        for (int row = 0; row < sizeOfBoard * sizeOfBoard; row++ )
        {
            for (int col = 0; col < sizeOfBoard * sizeOfBoard; col++ )
            {
                relationships[row][col] = 0;
            }
        }

        for (int row = 0; row < sizeOfBoard; row++ )
        {
            for (int col = 0; col < sizeOfBoard; col++ )
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

        queue.add( nodesID[sizeOfBoard -1][0 ] );
        checked[sizeOfBoard -1][0 ] = 1;

        while( !queue.isEmpty() )
        {
            int node = queue.remove( 0 );

            if( board[ ( int ) node / 10 ][ (int) node % 10 ] [Gold] == 1 )
            {
                isSolutionExist = true;
                break;
            }
            else
            {
                for (int i = 0; i < sizeOfBoard * sizeOfBoard; i++ )
                {
                    if ( relationships[node][i] == 1 && board[ ( int ) i / 10 ][ (int) i % 10 ] [Pit] != 1 && checked[ ( int ) i / 10 ][ (int) i % 10 ] != 1 )
                    {
                        queue.add( nodesID[ ( int ) i / 10 ][ (int) i % 10 ] );
                        checked[ ( int ) i / 10 ][ (int) i % 10 ] = 1;
                    }
                }
            }
        }

        return isSolutionExist;
    }


    private void setFixedEnvironment()
    {
        numOfGold=1;
        numOfPit=13;
        numOfWumpus=1;

        board[4][4][Wumpus]=1;//Wumpus SetUp
        board[6][6][Gold]=1;//Gold SetUp

        setPitsInFixedEnv(numOfPit);
        setBreezesValueInFixedEnv();
        setStenchesValueInFixedEnv();
        setGlittersInFixedEnv();

    }

    private void setGlittersInFixedEnv() {
        board[6][5][Glitter]=1;
        board[6][7][Glitter]=1;
        board[7][5][Glitter]=1;
        board[7][7][Glitter]=1;

    }

    private void setStenchesValueInFixedEnv() {
        board[4][3][Stench]=1;
        board[4][5][Stench]=1;
        board[5][4][Stench]=1;

    }

    private void setBreezesValueInFixedEnv() {
        board[1][4][Breeze]=1;
        board[1][7][Breeze]=1;
        board[2][3][Breeze]=1;
        board[2][6][Breeze]=1;
        board[3][2][Breeze]=1;
        board[3][5][Breeze]=1;
        board[3][7][Breeze]=1;
        board[4][7][Breeze]=1;
        board[4][9][Breeze]=1;
        board[5][2][Breeze]=1;
        board[5][4][Breeze]=1;
        board[5][6][Breeze]=1;
        board[5][8][Breeze]=1;
        board[6][1][Breeze]=1;
        board[6][2][Breeze]=1;
        board[6][5][Breeze]=1;
        board[7][0][Breeze]=1;
        board[7][5][Breeze]=1;
        board[7][7][Breeze]=1;
        board[8][2][Breeze]=1;
        board[8][3][Breeze]=1;
        board[8][6][Breeze]=1;
        board[9][2][Breeze]=1;
        board[9][7][Breeze]=1;
        board[9][8][Breeze]=1;
    }

    private void setPitsInFixedEnv(int numOfPit) {
        board[0][6][Pit]=1;
        board[1][3][Pit]=1;
        board[2][7][Pit]=1;
        board[3][1][Pit]=1;
        board[3][4][Pit]=1;
        board[4][8][Pit]=1;
        board[5][1][Pit]=1;
        board[5][5][Pit]=1;
        board[6][0][Pit]=1;
        board[7][6][Pit]=1;
        board[8][7][Pit]=1;
        board[9][3][Pit]=1;
        board[9][9][Pit]=1;
    }

    public void print(){
        for(int i=0;i<10;i++){
            for(int j=0;j<10;j++){

                if(board[i][j][Wumpus]==1){
                    System.out.println("Wumpus is here.\nPosition = "+i+","+j+"\n");
                }
                if(board[i][j][Gold]==1){
                    System.out.println("Gold is here.\nPosition = "+i+","+j+"\n");
                }

                if(board[i][j][Pit]==1){
                    System.out.println("Pit Position = "+i+","+j+"\n");
                }
            }
        }
    }

}

