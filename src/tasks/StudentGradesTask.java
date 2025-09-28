package tasks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class Student {
    String name;
    List<Integer> grades;
    double average;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public void calculateAverage() {
        if (grades.isEmpty()) {
            average = 0;
            return;
        }

        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        average = (double) sum / grades.size();
    }
}

class StudentGrades {
    private List<Student> students;

    public StudentGrades() {
        students = new ArrayList<>();
    }

    public void analyze(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split("\\s+");

                if (parts.length > 1) {
                    Student student = new Student(parts[0]);

                    for (int i = 1; i < parts.length; i++) {
                        try {
                            int grade = Integer.parseInt(parts[i]);
                            if (grade >= 2 && grade <= 5) {
                                student.grades.add(grade);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Некорректная оценка у студента " + parts[0] + ": " + parts[i]);
                        }
                    }

                    if (!student.grades.isEmpty()) {
                        student.calculateAverage();
                        students.add(student);
                    }
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Ошибка: Файл не найден! - " + filename);
        }
    }

    public void printAnalysis() {
        if (students.isEmpty()) {
            System.out.println("Нет данных для анализа");
            return;
        }

        System.out.println("=== Результаты анализа оценок ===");
        for (Student student : students) {
            System.out.printf("%s: %.2f\n", student.name, student.average);
        }

        System.out.println("\nЛучший студент: " + bestStudent());
        System.out.println("Худший студент: " + worstStudent());
    }

    public String bestStudent() {
        if (students.isEmpty()) {
            return "нет данных";
        }

        Student best = students.get(0);
        for (Student student : students) {
            if (student.average > best.average) {
                best = student;
            }
        }
        return best.name + " (" + String.format("%.2f", best.average) + ")";
    }

    public String worstStudent() {
        if (students.isEmpty()) {
            return "нет данных";
        }

        Student worst = students.get(0);
        for (Student student : students) {
            if (student.average < worst.average) {
                worst = student;
            }
        }
        return worst.name + " (" + String.format("%.2f", worst.average) + ")";
    }
}

public class StudentGradesTask {
    public static void run() {
        System.out.println("=== Задание 8: Анализ оценок студентов ===");

        StudentGrades analyzer = new StudentGrades();
        analyzer.analyze("Java/Files/Student.txt");
        analyzer.printAnalysis();
    }
}