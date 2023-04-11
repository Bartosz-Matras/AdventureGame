package pl.matrasbartosz.gamerpg.unit.character.items.armour;

import static pl.matrasbartosz.gamerpg.unit.character.items.armour.ArmourConstants.*;

public class RareArmour extends Armour {

    public RareArmour() {
        super(RARE_ARMOUR_HEALTH, RARE_ARMOUR_DAMAGE, RARE_ARMOUR_GAIN_MORE_EXP,
                RARE_ARMOUR_GAIN_MORE_MONEY, RARE_ARMOUR_CHANCE_FOR_DODGE);
    }

}
