import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MoveButton extends JButton implements ActionListener {
    private JPanel drawingPanel;
    private View view;
    private MouseHandler mouseHandler;
    private UndoManager undoManager;

    public MoveButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Move");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;
        addActionListener(this);
        mouseHandler = new MouseHandler();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        drawingPanel.addMouseListener(mouseHandler);
        drawingPanel.addMouseMotionListener(mouseHandler);
    }

    private class MouseHandler extends MouseAdapter {
        private Point startPoint;

        @Override
        public void mousePressed(MouseEvent e) {
            startPoint = View.mapPoint(e.getPoint()); 
            System.out.println("Mouse pressed at: " + startPoint);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Point endPoint = View.mapPoint(e.getPoint());
            System.out.println("Mouse released at: " + endPoint);
        
            // Calculate dx and dy only once
            int dx = endPoint.x - startPoint.x;
            int dy = endPoint.y - startPoint.y;
        
            System.out.println("Translation: dx=" + dx + ", dy=" + dy);
        
            if (dx != 0 || dy != 0) {
                System.out.println("Creating and executing MoveCommand...");
                MoveCommand moveCommand = new MoveCommand(dx, dy);
                undoManager.beginCommand(moveCommand); // Start undo command
                moveCommand.execute(); // Execute translation
                undoManager.endCommand(moveCommand); // End undo command
            }
        
            // Reset cursor and listeners
            view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            drawingPanel.removeMouseListener(this);
            drawingPanel.removeMouseMotionListener(this);
            view.refresh();
        }
        
        
        
    }
}
