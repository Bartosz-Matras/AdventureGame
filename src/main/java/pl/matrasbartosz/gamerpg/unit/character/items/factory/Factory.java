package pl.matrasbartosz.gamerpg.unit.character.items.factory;

import pl.matrasbartosz.gamerpg.unit.character.items.Item;

abstract class Factory {
    protected abstract Item createItem(ItemType itemType);
}
