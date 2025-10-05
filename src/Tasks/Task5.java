package src.Tasks;

import java.util.*;

public class Task5 {
    public static void execute() {
        System.out.println("\n  ЗАДАНИЕ 5  ");

        // Создаем тестовую Map
        Map<String, Integer> originalMap = new HashMap<>();
        originalMap.put("apple", 1);
        originalMap.put("banana", 2);
        originalMap.put("car", 3);
        originalMap.put("date", 4);
        originalMap.put("var", 2);
        originalMap.put("man", 3);

        System.out.println("Исходная Map:");
        originalMap.forEach((k, v) -> System.out.println(k + " -> " + v));

        // Меняем ключи и значения местами
        Map<Integer, String> swappedMap = swapKeysAndValues(originalMap);

        System.out.println("\nMap после обмена ключей и значений:");
        swappedMap.forEach((k, v) -> System.out.println(k + " -> " + v));

        System.out.println("\nПримечание: при дублирующихся значениях " +
                "сохраняется последний ключ");
    }

    public static <K, V> Map<V, K> swapKeysAndValues(Map<K, V> map) {
        Map<V, K> swappedMap = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            swappedMap.put(entry.getValue(), entry.getKey());
        }
        return swappedMap;
    }
}