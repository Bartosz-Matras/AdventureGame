package pl.matrasbartosz.gamerpg.unit.character.items.armour;

import static pl.matrasbartosz.gamerpg.unit.character.items.ItemConstants.*;

public class CommonArmour extends Armour {

    public CommonArmour() {
        super(COMMON_ARMOUR_HEALTH, COMMON_ARMOUR_DAMAGE, COMMON_ARMOUR_GAIN_MORE_EXP,
                COMMON_ARMOUR_GAIN_MORE_MONEY, COMMON_ARMOUR_CHANCE_FOR_DODGE);
    }
}
