import java.awt.Point;

public class TriangleCommand extends Command {
    private Triangle triangle;
    private Point p1;
    private Point p2;
    private Point p3;
    private Line line1;  // To track the line between p1 and p2

    public TriangleCommand(Point p1) {
        this.p1 = p1;
    }

    public void setSecondPoint(Point p2) {
        this.p2 = p2;
        // Create a line between the first and second points
        this.line1 = new Line(this.p1, this.p2);
        model.addItem(this.line1);
    }

    public void setThirdPoint(Point p3) {
        this.p3 = p3;
    }

    public void execute() {
        if (this.p1 != null && this.p2 != null && this.p3 != null) {
            // Remove the line item from the model so it doesn't still exist when the triangle is created
            model.removeItem(this.line1);
            // Create and add the triangle only if all three points are available
            this.triangle = new Triangle(this.p1, this.p2, this.p3);
            model.addItem(this.triangle);
        }
    }

    public boolean undo() {
        // Remove the triangle and line from the model
        if (this.triangle != null) {
            model.removeItem(this.triangle);
        }
        if (this.line1 != null) {
            model.removeItem(this.line1); // Remove the line between the first and second points
        }
        return true;
    }

    public boolean redo() {
        this.execute();
        return true;
    }
}
