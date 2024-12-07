import java.awt.*;

public class Triangle extends Item {
    private Point point1;
    private Point point2;
    private Point point3;

    public Triangle(Point point1, Point point2, Point point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public Triangle(Point point1) {
        this.point1 = point1;
        this.point2 = null;
        this.point3 = null;
    }

    public Triangle() {
        point1 = null;
        point2 = null;
        point3 = null;
    }

    public boolean includes(Point point) {
        return (distance(point, point1) < 10.0) ||
                (distance(point, point2) < 10.0) ||
                (distance(point, point3) < 10.0);
    }

    public void render(UIContext uiContext) {
        
            uiContext.drawLine(point1, point2);
            uiContext.drawLine(point2, point3);
            uiContext.drawLine(point3, point1);
        }

    @Override
    public void translate(int dx, int dy) {
        System.out.println("Translating triangle by dx=" + dx + ", dy=" + dy);

        if (point1 != null) {
            point1.translate(dx, dy);
            System.out.println("Point1 after translation: " + point1);
        }
        if (point2 != null) {
            point2.translate(dx, dy);
            System.out.println("Point2 after translation: " + point2);
        }
        if (point3 != null) {
            point3.translate(dx, dy);
            System.out.println("Point3 after translation: " + point3);
        }
    }
    

    public void setPoint1(Point point) {
        point1 = point;
    }

    public void setPoint2(Point point) {
        point2 = point;
    }

    public void setPoint3(Point point) {
        point3 = point;
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public Point getPoint3() {
        return point3;
    }

    public String toString() {
        return "Triangle with points " + point1 + ", " + point2 + ", and " + point3;
    }
}
