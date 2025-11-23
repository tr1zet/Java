package isp;

public class ModernPrinter implements MultiFunctionDevice {
    private String model;

    public ModernPrinter(String model) {
        this.model = model;
    }

    @Override
    public String getDeviceInfo() {
        return "Современный МФУ модели: " + model;
    }

    @Override
    public void print(String document) {
        System.out.println("Печатаю высококачественно: " + document);
    }

    @Override
    public void scan(String document) {
        System.out.println("Сканирую документ: " + document);
    }

    @Override
    public void fax(String document) {
        System.out.println("Отправляю по факсу: " + document);
    }
}
