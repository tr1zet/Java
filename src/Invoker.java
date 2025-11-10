import java.lang.reflect.Method;

public class Invoker {
    public void invokeAnnotatedMethods() {
        try {
            MyClass myClass = new MyClass("TestObject", 5);
            Method[] methods = myClass.getClass().getDeclaredMethods();

            System.out.println("ВЫЗОВ АННОТИРОВАННЫХ МЕТОДОВ ");

            for (Method method : methods) {
                if (shouldInvokeMethod(method)) {
                    invokeMethodWithRepeat(method, myClass);
                }
            }

        } catch (Exception e) {
        }
    }

    private boolean shouldInvokeMethod(Method method) {
        boolean isProtected = java.lang.reflect.Modifier.isProtected(method.getModifiers());
        boolean isPrivate = java.lang.reflect.Modifier.isPrivate(method.getModifiers());
        return (isProtected || isPrivate) && method.isAnnotationPresent(Repeat.class);
    }

    private void invokeMethodWithRepeat(Method method, MyClass target) throws Exception {
        Repeat repeatAnnotation = method.getAnnotation(Repeat.class);
        int times = repeatAnnotation.times();

        printMethodInfo(method, times);
        method.setAccessible(true);

        for (int i = 0; i < times; i++) {
            invokeSingleMethodCall(method, target, i + 1);
        }
    }

    private void printMethodInfo(Method method, int times) {
        boolean isProtected = java.lang.reflect.Modifier.isProtected(method.getModifiers());

        System.out.println("\nМетод: " + method.getName());
        System.out.println("Модификатор: " + (isProtected ? "protected" : "private"));
        System.out.println("Количество вызовов: " + times);
    }

    private void invokeSingleMethodCall(Method method, MyClass target, int callNumber) throws Exception {
        System.out.print("Вызов " + callNumber + ": ");

        Object[] params = prepareMethodParameters(method);
        Object result = method.invoke(target, params);

        printResultIfExists(result);
    }

    private Object[] prepareMethodParameters(Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] params = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            params[i] = getDefaultParameterValue(paramTypes[i], i);
        }

        return params;
    }

    private Object getDefaultParameterValue(Class<?> paramType, int index) {
        if (paramType == String.class) {
            return "param" + (index + 1);
        } else if (paramType == int.class) {
            return (index + 1) * 10;
        }
        return null;
    }

    private void printResultIfExists(Object result) {
        if (result != null) {
            System.out.println("Результат: " + result);
        }
    }
}