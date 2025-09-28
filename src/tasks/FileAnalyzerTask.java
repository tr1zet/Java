package tasks;

import java.io.BufferedReader;
import java.io.FileReader;

class FileStats {
    int lines;
    int words;
    int chars;

    public FileStats(int lines, int words, int chars) {
        this.lines = lines;
        this.words = words;
        this.chars = chars;
    }
}

class FileAnalyzer {

    public FileStats analyze(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            int lineCount = 0;
            int wordCount = 0;
            int charCount = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;

                boolean inWord = false;
                for (char c : line.toCharArray()) {
                    if (c != ' ') {
                        charCount++;
                        if (!inWord) {
                            wordCount++;
                            inWord = true;
                        }
                    } else {
                        inWord = false;
                    }
                }
            }

            reader.close();
            return new FileStats(lineCount, wordCount, charCount);

        } catch (Exception e) {
            System.out.println("Файл не найден!");
            return null;
        }
    }

    public void printAnalysis(String filename) {
        FileStats stats = analyze(filename);
        if (stats != null) {
            System.out.println("Строк: " + stats.lines);
            System.out.println("Слов: " + stats.words);
            System.out.println("Символов: " + stats.chars);
        }
    }
}

public class FileAnalyzerTask {
    public static void run() {
        System.out.println("=== Задание 6: Анализ файла ===");

        FileAnalyzer analyzer = new FileAnalyzer();
        analyzer.printAnalysis("Java/Files/Content.txt");
    }
}