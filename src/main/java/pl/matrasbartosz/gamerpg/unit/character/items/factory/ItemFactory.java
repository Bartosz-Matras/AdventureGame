package pl.matrasbartosz.gamerpg.unit.character.items.factory;

import pl.matrasbartosz.gamerpg.unit.character.items.Item;
import pl.matrasbartosz.gamerpg.unit.character.items.armour.CommonArmour;
import pl.matrasbartosz.gamerpg.unit.character.items.armour.GreatArmour;

public class ItemFactory extends Factory{

    private static ItemFactory instance;

    private ItemFactory() {}

    public static ItemFactory getInstance() {
        if (instance == null) {
            instance = new ItemFactory();
        }
        return instance;
    }

    @Override
    protected Item createItem(ItemType itemType) {
        switch (itemType) {
            case COMMON_ARMOR -> {
                return new CommonArmour();
            }
            case GREAT_ARMOR -> {
                return new GreatArmour();
            }
            default -> throw new UnsupportedOperationException("Unsupported type");
        }

    }
}
