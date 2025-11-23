package isp;

public class Scanner implements ScanDevice {
    private String model;

    public Scanner(String model) {
        this.model = model;
    }

    @Override
    public String getDeviceInfo() {
        return "Сканер модели: " + model;
    }

    @Override
    public void scan(String document) {
        System.out.println("Сканирую с высоким разрешением: " + document);
    }
}