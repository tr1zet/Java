import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.VisitorData;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class JSONParser {
    private Gson gson;

    public JSONParser() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public List<VisitorData> parseVisitorsFromFile(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            Type listType = new TypeToken<List<VisitorData>>(){}.getType();
            List<VisitorData> visitors = gson.fromJson(reader, listType);
            System.out.println("Успешно загружено " + visitors.size() + " посетителей из JSON файла");
            return visitors;
        } catch (Exception e) {
            System.out.println("Ошибка чтения JSON файла: " + e.getMessage());
            return null;
        }
    }

    public void printVisitorsData(List<VisitorData> visitors) {
        if (visitors == null || visitors.isEmpty()) {
            System.out.println("Нет данных о посетителях");
            return;
        }

        System.out.println("\n=== Анализ структуры данных из JSON ===");
        for (VisitorData visitor : visitors) {
            System.out.println(visitor);
            System.out.println("  Любимые книги (" + visitor.getFavoriteBooks().size() + "):");
            for (model.Book book : visitor.getFavoriteBooks()) {
                System.out.println("    - " + book.getName() + " (" + book.getAuthor() + ", " + book.getPublishingYear() + ")");
            }
        }
    }
}