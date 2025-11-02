import java.nio.file.*;
import java.io.IOException;
import java.util.stream.Stream;

public class FileSystemTask {
    private String surname;
    private String name;
    private Path baseDir;

    public FileSystemTask(String surname, String name) {
        this.surname = surname;
        this.name = name;
        this.baseDir = Paths.get(surname);
    }

    public void executeFileSystemTask() {
        try {
            System.out.println("\n ВЫПОЛНЕНИЕ ЗАДАНИЯ С ФАЙЛОВОЙ СИСТЕМОЙ ");

            createBaseDirectory();
            createNameFile();
            createNestedDirectories();
            createAdditionalFiles();
            traverseDirectory();
            deleteDir1();

            System.out.println("Задание с файловой системой завершено успешно!");

        } catch (IOException e) {
            System.err.println("Ошибка при работе с файловой системой: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createBaseDirectory() throws IOException {
        Files.createDirectories(baseDir);
        System.out.println("1. Создана директория: " + baseDir.toAbsolutePath());
    }

    private void createNameFile() throws IOException {
        Path nameFile = baseDir.resolve(name);
        Files.createFile(nameFile);
        Files.writeString(nameFile, "Это файл с именем: " + name);
        System.out.println("2. Создан файл: " + nameFile);
    }

    private void createNestedDirectories() throws IOException {
        Path nestedDir = baseDir.resolve("dir1/dir2/dir3");
        Files.createDirectories(nestedDir);
        System.out.println("3. Созданы вложенные директории: " + nestedDir);

        // Копируем файл в dir3
        Path sourceFile = baseDir.resolve(name);
        Path targetFile = nestedDir.resolve(name);
        Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("   Файл скопирован в: " + targetFile);
    }

    private void createAdditionalFiles() throws IOException {
        // Создаём file1 в dir1
        Path file1 = baseDir.resolve("dir1/file1");
        Files.createFile(file1);
        Files.writeString(file1, "Содержимое file1");
        System.out.println("4. Создан файл: " + file1);

        // Создаём file2 в dir2
        Path file2 = baseDir.resolve("dir1/dir2/file2");
        Files.createFile(file2);
        Files.writeString(file2, "Содержимое file2");
        System.out.println("5. Создан файл: " + file2);
    }

    private void traverseDirectory() throws IOException {
        System.out.println("6. Рекурсивный обход директории " + baseDir + ":");

        try (Stream<Path> paths = Files.walk(baseDir)) {
            paths.forEach(path -> {
                try {
                    if (Files.isDirectory(path)) {
                        System.out.println("D: " + baseDir.relativize(path));
                    } else {
                        System.out.println("F: " + baseDir.relativize(path));
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка при обработке пути: " + path);
                }
            });
        }
    }

    private void deleteDir1() throws IOException {
        Path dir1 = baseDir.resolve("dir1");

        if (Files.exists(dir1)) {
            // Удаляем рекурсивно с обходом в обратном порядке
            try (Stream<Path> paths = Files.walk(dir1)) {
                paths.sorted(java.util.Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                                System.out.println("Удалён: " + path);
                            } catch (IOException e) {
                                System.err.println("Не удалось удалить: " + path);
                            }
                        });
            }

            System.out.println("7. Директория dir1 удалена со всем содержимым");
        } else {
            System.out.println("7. Директория dir1 не существует, удаление не требуется");
        }
    }
}