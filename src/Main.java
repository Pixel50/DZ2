import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        // Создаем три объекта GameProgress
        File savegamesDir = new File("/Users/user/Games/savegames");
        if (!savegamesDir.exists()) {
            savegamesDir.mkdirs();
        }
        GameProgress gp1 = new GameProgress(100, 2, 1, 25.5);
        GameProgress gp2 = new GameProgress(80, 5, 2, 50.5);
        GameProgress gp3 = new GameProgress(50, 10, 3, 100.0);

        // Сохраняем их в файлы
        saveGame("/Users/user/Documents/Games/savegames/save1.dat", gp1);
        saveGame("/Users/user/Documents/Games/savegames/save2.dat", gp2);
        saveGame("/Users/user/Documents/Games/savegames/save3.dat", gp3);
        // Пути к файлам сохранений
        List<String> saveFiles = List.of(
                "/Users/user/Documents/Games/savegames/save1.dat",
                "/Users/user/Documents/Games/savegames/save2.dat",
                "/Users/user/Documents/Games/savegames/save3.dat"
        );

        // Упаковываем файлы в zip-архив
        zipFiles("/Users/user/Documents/Games/savegames/zip.zip", saveFiles);

        // Удаление исходных файлов после упаковки (необязательно)
        for (String filePath : saveFiles) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("File deleted: " + file.getPath());
            } else {
                System.out.println("Failed to delete file: " + file.getPath());
            }
        }
        System.out.println("Hello world!");
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            System.out.println("Game progress saved to " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to save game progress to " + filePath);
            e.printStackTrace();
        }
    }

    public static void zipFiles(String zipFilePath, List<String> filePaths) {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (String filePath : filePaths) {
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    ZipEntry zipEntry = new ZipEntry(new File(filePath).getName());
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) >= 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                    System.out.println("File added to zip: " + filePath);
                } catch (IOException e) {
                    System.out.println("Failed to add file to zip: " + filePath);
                    e.printStackTrace();
                }
            }
            System.out.println("Zip file created successfully: " + zipFilePath);
        } catch (IOException e) {
            System.out.println("Failed to create zip file: " + zipFilePath);
            e.printStackTrace();
        }
    }

}

