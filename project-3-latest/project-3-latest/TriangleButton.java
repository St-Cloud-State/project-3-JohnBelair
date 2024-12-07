import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TriangleButton extends JButton implements ActionListener {
    private JPanel drawingPanel;
    private View view;
    private MouseHandler mouseHandler;
    private TriangleCommand triangleCommand;
    private UndoManager undoManager;

    public TriangleButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Triangle");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;
        this.mouseHandler = new MouseHandler();
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        // Change cursor when button is clicked
        drawingPanel.addMouseListener(mouseHandler);
        // Start listening for mouseclicks on the drawing panel
    }

    private class MouseHandler extends MouseAdapter {
        private int pointCount = 0;

        @Override
        public void mouseClicked(MouseEvent event) {
            Point clickedPoint = View.mapPoint(event.getPoint());

            if (pointCount == 0) {
                // First point: create a new TriangleCommand and render the point
                triangleCommand = new TriangleCommand(clickedPoint);
                undoManager.beginCommand(triangleCommand);
                triangleCommand.execute();
                pointCount++;
            } else if (pointCount == 1) {
                // Second point: set the second point and render the line
                triangleCommand.setSecondPoint(clickedPoint);
                triangleCommand.execute();
                pointCount++;
            } else if (pointCount == 2) {
                // Third point: set the third point, render the triangle, and finalize
                triangleCommand.setThirdPoint(clickedPoint);
                triangleCommand.execute();
                drawingPanel.removeMouseListener(this); 
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
                undoManager.endCommand(triangleCommand);
                pointCount = 0; 
            }
        }
    }
}
