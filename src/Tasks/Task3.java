package src.Tasks;

import java.util.*;

public class Task3 {
    public static void execute() {
        System.out.println("\n    ЗАДАНИЕ 3    ");

        // Создаем список объектов Human
        List<Human> humans = Arrays.asList(
                new Human("Даниил", "Улискин", 19),
                new Human("Евгений", "Лякин", 10),
                new Human("Антон", "Чистюхин", 20),
                new Human("Иван", "Толкачев", 25),
                new Human("Александра", "Девушка", 30)
        );

        System.out.println("Исходный список:");
        humans.forEach(System.out::println);

        // HashSet
        Set<Human> hashSet = new HashSet<>(humans);
        System.out.println("\nHashSet (порядок не гарантирован):");
        hashSet.forEach(System.out::println);

        // LinkedHashSet
        Set<Human> linkedHashSet = new LinkedHashSet<>(humans);
        System.out.println("\nLinkedHashSet (порядок вставки):");
        linkedHashSet.forEach(System.out::println);

        // TreeSet (естественный порядок)
        Set<Human> treeSet = new TreeSet<>(humans);
        System.out.println("\nTreeSet (естественная сортировка по имени):");
        treeSet.forEach(System.out::println);

        // TreeSet с компаратором по фамилии
        Set<Human> treeSetByLastName = new TreeSet<>(new HumanComparatorByLastName());
        treeSetByLastName.addAll(humans);
        System.out.println("\nTreeSet с компаратором по фамилии:");
        treeSetByLastName.forEach(System.out::println);

        // TreeSet с анонимным компаратором по возрасту
        Set<Human> treeSetByAge = new TreeSet<>(new Comparator<Human>() {
            @Override
            public int compare(Human h1, Human h2) {
                return Integer.compare(h1.getAge(), h2.getAge());
            }
        });
        treeSetByAge.addAll(humans);
        System.out.println("\nTreeSet с компаратором по возрасту:");
        treeSetByAge.forEach(System.out::println);

        System.out.println("\n    ОБЪЯСНЕНИЕ РАЗЛИЧИЙ    ");
        System.out.println("1. HashSet - порядок не гарантирован, основан на хэш-кодах");
        System.out.println("2. LinkedHashSet - сохраняет порядок вставки элементов");
        System.out.println("3. TreeSet - сортирует элементы по естественному порядку (Comparable)");
        System.out.println("4. TreeSet с компаратором - сортирует по заданному критерию");
        System.out.println("5. TreeSet с анонимным компаратором - сортирует по возрасту");
    }
}

class Human implements Comparable<Human> {
    private String firstName;
    private String lastName;
    private int age;

    public Human(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    // Геттеры
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }

    @Override
    public int compareTo(Human other) {
        return this.firstName.compareTo(other.firstName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return age == human.age &&
                Objects.equals(firstName, human.firstName) &&
                Objects.equals(lastName, human.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }

    @Override
    public String toString() {
        return String.format("%s %s, %d лет", firstName, lastName, age);
    }
}

class HumanComparatorByLastName implements Comparator<Human> {
    @Override
    public int compare(Human h1, Human h2) {
        return h1.getLastName().compareTo(h2.getLastName());
    }
}