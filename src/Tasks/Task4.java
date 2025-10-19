package src.Tasks;

import java.util.*;

public class Task4 {
    public static void execute() {
        System.out.println("\n   ЗАДАНИЕ 4   ");

        String text = "Travelling to far countries is always a thrilling and interesting adventure. Heading for the " +
                "other end of the world, it’s impossible to go without such an aircraft as “a plane”. But before " +
                "boarding a plane, one must book a seat in advance and go though other different formalities.";

        System.out.println("Исходный текст:\n" + text);

        // Разбиваем текст на слова, игнорируя регистр
        String[] words = text.toLowerCase().split("\\W+");

        // Подсчитываем частоту слов
        Map<String, Integer> wordFrequency = new HashMap<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }
        }

        System.out.println("\nЧастота слов:");
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            System.out.printf("'%s': %d раз(а)\n", entry.getKey(), entry.getValue());
        }

        System.out.println("\nРазличные слова:");
        System.out.println(wordFrequency.keySet());
    }
}