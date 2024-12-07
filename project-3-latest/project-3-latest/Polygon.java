
import java.awt.*;
import java.util.List;

public class Polygon extends Item {

    private List<Point> points;

    public Polygon(List<Point> points) {
        this.points = points;
    }

    public boolean includes(Point point) {
        for (Point p : points) {
            if (distance(point, p) < 10.0) {
                return true;
            }
        }
        return false;
    }

    public void render(UIContext uiContext) {
        for (int i = 0; i < points.size() - 1; i++) {
            uiContext.drawLine(points.get(i), points.get(i + 1));
        }
    }

    public String toString() {
        return "Polygon with " + points.size() + " points";
    }
    @Override
    public void translate(int dx, int dy) {
        System.out.println("Translating polygon by dx=" + dx + ", dy=" + dy);
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i); // Access each point by index
            point.translate(dx, dy); // Translate the point
            System.out.println("Point after translation: " + point);
        }
    }
    
    public List<Point> getPoints() {
        return points;
    }
}
