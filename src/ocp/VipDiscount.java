package ocp;

public class VipDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double price) {
        return price * 0.1;
    }
}