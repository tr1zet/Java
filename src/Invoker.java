import java.lang.reflect.Method;

public class Invoker {
    public void invokeAnnotatedMethods() {
        try {
            MyClass myClass = new MyClass("TestObject", 5);
            Class<?> clazz = myClass.getClass();
            Method[] methods = clazz.getDeclaredMethods();

            System.out.println("ВЫЗОВ АННОТИРОВАННЫХ МЕТОДОВ ");

            for (Method method : methods) {
                boolean isProtected = java.lang.reflect.Modifier.isProtected(method.getModifiers());
                boolean isPrivate = java.lang.reflect.Modifier.isPrivate(method.getModifiers());

                if ((isProtected || isPrivate) && method.isAnnotationPresent(Repeat.class)) {
                    Repeat repeatAnnotation = method.getAnnotation(Repeat.class);
                    int times = repeatAnnotation.times();

                    System.out.println("\nМетод: " + method.getName());
                    System.out.println("Модификатор: " + (isProtected ? "protected" : "private"));
                    System.out.println("Количество вызовов: " + times);

                    method.setAccessible(true);

                    for (int i = 0; i < times; i++) {
                        System.out.print("Вызов " + (i + 1) + ": ");

                        // Обрабатываем параметры методов
                        Class<?>[] paramTypes = method.getParameterTypes();
                        Object[] params = new Object[paramTypes.length];

                        // Заполняем параметры значениями по умолчанию
                        for (int j = 0; j < paramTypes.length; j++) {
                            if (paramTypes[j] == String.class) {
                                params[j] = "param" + (j + 1);
                            } else if (paramTypes[j] == int.class) {
                                params[j] = (j + 1) * 10;
                            }
                        }

                        Object result = method.invoke(myClass, params);
                        if (result != null) {
                            System.out.println("Результат: " + result);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}