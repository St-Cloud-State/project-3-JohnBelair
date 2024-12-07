//Code modified from LineButton.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PolygonButton extends JButton implements ActionListener {

    protected JPanel drawingPanel;
    protected View view;
    private MouseHandler mouseHandler;
    private UndoManager undoManager;

    public PolygonButton(UndoManager undoManager, View jFrame, JPanel jPanel) {
        super("Polygon");
        this.undoManager = undoManager;
        addActionListener(this);
        view = jFrame;
        drawingPanel = jPanel;
        mouseHandler = new MouseHandler();
    }

    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        // Change cursor when button is clicked
        drawingPanel.addMouseListener(mouseHandler);
        // Start listening for mouseclicks on the drawing panel
    }

    private class MouseHandler extends MouseAdapter {

        private PolygonCommand polygonCommand;

        @Override
        public void mouseClicked(MouseEvent event) {

            if (SwingUtilities.isLeftMouseButton(event)) {
                
                Point clickedPoint = View.mapPoint(event.getPoint());

                if (polygonCommand == null) {
                    polygonCommand = new PolygonCommand();
                    undoManager.beginCommand(polygonCommand);
                }
                
                polygonCommand.addPoint(clickedPoint);
                drawingPanel.repaint();

            } else if (SwingUtilities.isRightMouseButton(event)) {
                if (polygonCommand != null) {
                    polygonCommand.closePolygon();
                    undoManager.endCommand(polygonCommand);
                    drawingPanel.removeMouseListener(this);
                    view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    drawingPanel.repaint();
                    polygonCommand = null;
                }
            }
        }
    }
}
