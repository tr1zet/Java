public class MyClass {
    private String name;
    private int value;

    public MyClass() {
        this.name = "Default";
        this.value = 0;
    }

    public MyClass(String name, int value) {
        this.name = name;
        this.value = value;
    }

    // Публичные методы
    public void publicMethod1() {
        System.out.println("Публичный метод 1: " + name);
    }

    public String publicMethod2(String prefix) {
        String result = prefix + " " + name;
        System.out.println("Публичный метод 2: " + result);
        return result;
    }

    // Защищённые методы с аннотациями
    @Repeat(times = 2)
    protected void protectedMethod1() {
        System.out.println("Защищённый метод 1: value = " + value);
    }

    @Repeat(times = 3)
    protected String protectedMethod2(String suffix) {
        String result = name + suffix;
        System.out.println("Защищённый метод 2: " + result);
        return result;
    }

    // Приватные методы с аннотациями
    @Repeat(times = 2)
    private void privateMethod1(int multiplier) {
        int result = value * multiplier;
        System.out.println("Приватный метод 1: " + value + " * " + multiplier + " = " + result);
    }

    @Repeat(times = 4)
    private String privateMethod2(String prefix, int number) {
        String result = prefix + " " + name + " #" + number;
        System.out.println("Приватный метод 2: " + result);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}