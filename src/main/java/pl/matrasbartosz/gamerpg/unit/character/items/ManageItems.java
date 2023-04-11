package pl.matrasbartosz.gamerpg.unit.character.items;

import pl.matrasbartosz.gamerpg.unit.character.items.exceptions.EmptyInventoryException;
import pl.matrasbartosz.gamerpg.unit.character.items.exceptions.TooManyItemsException;

public interface ManageItems {
    void wearItem(Item item) throws TooManyItemsException, EmptyInventoryException;
    void takeOfItem(Item item) throws TooManyItemsException;
    void addItem(Item item) throws TooManyItemsException;
    void removeItem(Item item) throws EmptyInventoryException;
}
