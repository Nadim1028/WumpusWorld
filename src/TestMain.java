public class TestMain {
    public static void main(String[] args) throws Exception {
        /*EnvironmentSettingUp environmentSettingUp = new EnvironmentSettingUp();
        environmentSettingUp.setupEnvironment();*/

        ExploringTheWorld exploringTheWorld = new ExploringTheWorld();
        exploringTheWorld.setGameBoard();
        exploringTheWorld.doInBackground();
       // exploringTheWorld.printRoute();

    }
}
