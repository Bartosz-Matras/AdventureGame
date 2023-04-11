package pl.matrasbartosz.gamerpg.unit.character.items.armour;

import pl.matrasbartosz.gamerpg.unit.character.items.Item;

public abstract class Armour extends Item {

    protected Armour(int gainMoreHealth, int gainMoreDamage, int gainMoreExp, double gainMoreMoney, int chanceForDodge) {
        super(gainMoreHealth, gainMoreDamage, gainMoreExp, gainMoreMoney, chanceForDodge);
    }
}
