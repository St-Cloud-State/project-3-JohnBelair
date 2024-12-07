import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class MoveCommand extends Command {
    private List<Item> itemsToMove;
    private int dx, dy;
    private boolean hasExecuted = false; 

    public MoveCommand(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
        itemsToMove = new ArrayList<>();

        // Collect all selected items
        Enumeration enumeration = model.getSelectedItems();
        while (enumeration.hasMoreElements()) {
            Item item = (Item) enumeration.nextElement();
            itemsToMove.add(item);
        }
    }

    @Override
    public void execute() {
        if (hasExecuted) {
            System.out.println("MoveCommand already executed. Skipping...");
            return;
        }
        hasExecuted = true;
    
        System.out.println("Executing MoveCommand with dx=" + dx + ", dy=" + dy);
        for (Item item : itemsToMove) {
            item.translate(dx, dy);
    
            if (item instanceof Polygon) {
                Polygon polygon = (Polygon) item;
                System.out.println("Translating Polygon:");
                for (Point p : polygon.getPoints()) {
                    System.out.println("Point after translation: " + p);
                }
            } else {
                System.out.println("Translated item: " + item);
            }
        }
        model.setChanged();
    }
    

    @Override
    public boolean undo() {
        for (Item item : itemsToMove) {
            item.translate(-dx, -dy); 
        }
        model.setChanged();
        return true;
    }

    @Override
    public boolean redo() {
        for (Item item : itemsToMove) {
            item.translate(dx, dy); // Reapply the translation
        }
        model.setChanged();
        return true;
    }
}

