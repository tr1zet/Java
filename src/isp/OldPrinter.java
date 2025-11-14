package isp;

public class OldPrinter implements PrintDevice {
    private String model;

    public OldPrinter(String model) {
        this.model = model;
    }

    @Override
    public String getDeviceInfo() {
        return "Старый принтер модели: " + model;
    }

    @Override
    public void print(String document) {
        System.out.println("Печатаю: " + document);
    }
}
