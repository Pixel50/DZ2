import java.io.*;

public class Main {
    public static void main(String[] args) {
        GameProgress game1 = new GameProgress(100, 2, 5, 300.5);
        GameProgress game2 = new GameProgress(80, 5, 10, 450.3);
        GameProgress game3 = new GameProgress(50, 7, 15, 600.8);
        saveGame("/Users/user/Documents/Games/savegames/save1.dat", game1);
        saveGame("/Users/user/Documents/Games/savegames/save2.dat", game2);
        saveGame("/Users/user/Documents/Games/savegames/save3.dat", game3);

        System.out.println("Hello world!");
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(gameProgress);
            System.out.println("Game saved to " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to save game to " + filePath);
            e.printStackTrace();
        }
    }

}


