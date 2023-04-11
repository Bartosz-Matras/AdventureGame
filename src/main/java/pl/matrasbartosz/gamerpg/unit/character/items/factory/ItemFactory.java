package pl.matrasbartosz.gamerpg.unit.character.items.factory;

import pl.matrasbartosz.gamerpg.unit.character.items.armour.Armour;
import pl.matrasbartosz.gamerpg.unit.character.items.armour.CommonArmour;
import pl.matrasbartosz.gamerpg.unit.character.items.armour.GreatArmour;
import pl.matrasbartosz.gamerpg.unit.character.items.armour.RareArmour;

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
    public Armour createArmour(ItemType itemType) {
        switch (itemType) {
            case COMMON_ARMOR -> {
                return new CommonArmour();
            }
            case GREAT_ARMOR -> {
                return new GreatArmour();
            }
            case RARE_ARMOR -> {
                return new RareArmour();
            }
            default -> throw new UnsupportedOperationException("Unsupported type");
        }

    }
}
