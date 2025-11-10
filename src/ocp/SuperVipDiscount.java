package ocp;

public class SuperVipDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double price) {
        return price * 0.2;
    }
}
