package ocp;

public class RegularDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double price) {
        return price * 0.05;
    }
}