package geometry3d;

import geometry2d.Figure;

public class Cylinder {
    private Figure base;
    private double height;

    public Cylinder(Figure base, double height) {
        this.base = base;
        this.height = height;
    }

    public double volume() {
        return base.area() * height;
    }

    @Override
    public String toString() {
        return "Цилиндр [основание=" + base + ", h=" + height + "]";
    }
}