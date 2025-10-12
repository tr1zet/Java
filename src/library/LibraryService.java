package library;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
public class LibraryService {
    private List<Visitor> visitors;

    public LibraryService() {
        loadData();
    }

    private void loadData() {
        FileReader reader = null;
        try {
            // Пробуем разные пути к файлу
            String[] possiblePaths = {"books.json"};

            String foundPath = null;

            // Ищем файл по всем возможным путям
            for (String path : possiblePaths) {
                File file = new File(path);
                if (file.exists()) {
                    reader = new FileReader(file);
                    foundPath = path;
                    System.out.println("Файл найден: " + file.getAbsolutePath());
                    break;
                }
            }

            if (reader == null) {
                throw new RuntimeException("Файл books.json не найден.");
            }

            // Загружаем данные через Gson
            Gson gson = new Gson();
            Type visitorListType = new TypeToken<List<Visitor>>(){}.getType();
            visitors = gson.fromJson(reader, visitorListType);

            System.out.println("Данные успешно загружены из: " + foundPath);
            System.out.println("Загружено посетителей: " + visitors.size());

        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки данных: " + e.getMessage());
        } finally {
            // Закрываем reader в блоке finally
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    System.out.println("Ошибка при закрытии файла: " + e.getMessage());
                }
            }
        }
    }

    // Задание 1: Вывести список посетителей и их количество
    public void task1() {
        System.out.println("=== ЗАДАНИЕ 1: Список посетителей ===");
        visitors.forEach(visitor ->
                System.out.println(visitor.getName() + " " + visitor.getSurname() + " - " + visitor.getPhone())
        );
        System.out.println("Общее количество посетителей: " + visitors.size());
        System.out.println();
    }

    // Задание 2: Список всех уникальных книг
    public void task2() {
        System.out.println("=== ЗАДАНИЕ 2: Уникальные книги ===");
        List<Book> uniqueBooks = visitors.stream()
                .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                .distinct()
                .collect(Collectors.toList());

        uniqueBooks.forEach(book ->
                System.out.println(book.getName() + " - " + book.getAuthor() + " (" + book.getPublishingYear() + ")")
        );
        System.out.println("Количество уникальных книг: " + uniqueBooks.size());
        System.out.println();
    }

    // Задание 3: Сортировка книг по году издания
    public void task3() {
        System.out.println("=== ЗАДАНИЕ 3: Книги отсортированные по году ===");
        List<Book> sortedBooks = visitors.stream()
                .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                .distinct()
                .sorted(Comparator.comparingInt(Book::getPublishingYear))
                .collect(Collectors.toList());

        sortedBooks.forEach(book ->
                System.out.println(book.getPublishingYear() + ": " + book.getName() + " - " + book.getAuthor())
        );
        System.out.println();
    }

    // Задание 4: Проверка наличия книг Jane Austen
    public void task4() {
        System.out.println("=== ЗАДАНИЕ 4: Поиск книг Jane Austen ===");
        boolean hasJaneAusten = visitors.stream()
                .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                .anyMatch(book -> "Jane Austen".equalsIgnoreCase(book.getAuthor()));

        if (hasJaneAusten) {
            System.out.println("Книги Jane Austen найдены в избранных книгах посетителей");

            // Выводим посетителей, у которых есть книги Jane Austen
            visitors.stream()
                    .filter(visitor -> visitor.getFavoriteBooks().stream()
                            .anyMatch(book -> "Jane Austen".equalsIgnoreCase(book.getAuthor())))
                    .forEach(visitor ->
                            System.out.println("• " + visitor.getName() + " " + visitor.getSurname())
                    );
        } else {
            System.out.println("Книги Jane Austen не найдены в избранных книгах посетителей");
        }
        System.out.println();
    }

    // Задание 5: Максимальное количество книг у одного посетителя
    public void task5() {
        System.out.println("=== ЗАДАНИЕ 5: Максимальное количество книг ===");
        int maxBooks = visitors.stream()
                .mapToInt(visitor -> visitor.getFavoriteBooks().size())
                .max()
                .orElse(0);

        System.out.println("Максимальное количество книг у одного посетителя: " + maxBooks);

        // Выводим посетителей с максимальным количеством книг
        visitors.stream()
                .filter(visitor -> visitor.getFavoriteBooks().size() == maxBooks)
                .forEach(visitor ->
                        System.out.println("• " + visitor.getName() + " " + visitor.getSurname() +
                                " - " + maxBooks + " книг")
                );
        System.out.println();
    }

    // Задание 6: Генерация SMS сообщений
    public void task6() {
        System.out.println("=== ЗАДАНИЕ 6: SMS рассылка ===");

        // Вычисляем среднее количество книг
        OptionalDouble averageOpt = visitors.stream()
                .mapToInt(visitor -> visitor.getFavoriteBooks().size())
                .average();

        if (averageOpt.isEmpty()) {
            System.out.println("Нет данных для расчета");
            return;
        }

        double averageBooks = averageOpt.getAsDouble();
        System.out.println("Среднее количество книг на посетителя: " + String.format("%.2f", averageBooks));

        // Генерируем SMS сообщения для подписанных пользователей
        List<SmsMessage> smsMessages = visitors.stream()
                .filter(Visitor::isSubscribed)
                .map(visitor -> {
                    int bookCount = visitor.getFavoriteBooks().size();
                    String message;

                    if (bookCount > averageBooks) {
                        message = "you are a bookworm";
                    } else if (bookCount < averageBooks) {
                        message = "read more";
                    } else {
                        message = "fine";
                    }

                    return new SmsMessage(visitor.getPhone(), message);
                })
                .collect(Collectors.toList());

        System.out.println("Сгенерированные SMS сообщения:");
        smsMessages.forEach(sms ->
                System.out.println("Телефон: " + sms.getPhoneNumber() + " | Сообщение: " + sms.getMessage())
        );
        System.out.println("Всего сообщений: " + smsMessages.size());
        System.out.println();
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }
}