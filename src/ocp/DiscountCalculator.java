package ocp;

public class DiscountCalculator {
    public double calculateDiscount(DiscountStrategy strategy, double price) {
        return strategy.calculateDiscount(price);
    }
}
