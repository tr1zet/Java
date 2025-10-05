package src.Tasks;

import java.util.*;

public class Task1 {
    public static void execute() {
        System.out.println("\n ЗАДАНИЕ 1 ");

        // Создаем массив из N случайных чисел
        int n = 10;
        Integer[] array = new Integer[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(101); // 0-100
        }
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
        List<Integer> duplicates = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        for (Integer num : list) {
            if (!seen.add(num)) {
                duplicates.add(num);
            }
        }
        System.out.println("8. Только дублирующиеся элементы: " + duplicates);

        // Получаем массив из списка
        Integer[] newArray = list.toArray(new Integer[0]);
        System.out.println("9. Массив из списка: " + Arrays.toString(newArray));

        // Подсчет вхождений каждого числа
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer num : list) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        System.out.println("10. Частота вхождений: " + frequencyMap);
    }
}