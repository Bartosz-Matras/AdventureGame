package pl.matrasbartosz.gamerpg.unit.character.items.exceptions;

public class EmptyInventoryException extends Exception {
    public EmptyInventoryException(String message) {
        super(message);
    }
}
