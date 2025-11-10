package ocp;

public class StudentDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double price) {
        return price * 0.15;
    }
}
