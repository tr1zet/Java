package src.Tasks;

import java.util.*;

public class Task1 {
    public static void execute() {
        System.out.println("\n ЗАДАНИЕ 1 ");
        Integer[] array = {10,7, 23, 56, 99, 3, 55, 91, 23, 33};
        // Вывод исходного массива
        System.out.println("1. Исходный массив: " + Arrays.toString(array));
        // Создаем список на основе массива
        List<Integer> list = new ArrayList<>(Arrays.asList(array));
        System.out.println("2. Список: " + list);

        // Сортируем по возрастанию
        Collections.sort(list);
        System.out.println("3. Отсортированный по возрастанию: " + list);

        // Сортируем в обратном порядке
        Collections.sort(list, Collections.reverseOrder());
        System.out.println("4. Отсортированный в обратном порядке: " + list);

        // Перемешиваем список
        Collections.shuffle(list);
        System.out.println("5. Перемешанный список: " + list);

        // Циклический сдвиг на 1 элемент
        Collections.rotate(list, 1);
        System.out.println("6. После циклического сдвига: " + list);

        // Оставляем только уникальные элементы
        Set<Integer> uniqueSet = new LinkedHashSet<>(list);
        List<Integer> uniqueList = new ArrayList<>(uniqueSet);
        System.out.println("7. Только уникальные элементы: " + uniqueList);

        // Оставляем только дублирующиеся элементы
        List<Integer> duplicates = findDuplicates(list);
        System.out.println("8. Только дублирующиеся элементы: " + duplicates);

        // Получаем массив из списка
        Integer[] newArray = list.toArray(new Integer[0]);
        System.out.println("9. Массив из списка: " + Arrays.toString(newArray));

        // Подсчет вхождений каждого числа
        Map<Integer, Integer> frequencyMap = countFrequency(list);
        System.out.println("10. Частота вхождений: " + frequencyMap);
    }

    // Метод для поиска дублирующихся элементов
    private static List<Integer> findDuplicates(List<Integer> list) {
        List<Integer> duplicates = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicatesSet = new HashSet<>();

        for (Integer num : list) {
            if (!seen.add(num)) {
                duplicatesSet.add(num);
            }
        }
        duplicates.addAll(duplicatesSet);
        return duplicates;
    }

    // Метод для подсчета частоты вхождений
    private static Map<Integer, Integer> countFrequency(List<Integer> list) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer num : list) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        return frequencyMap;
    }
}