package pl.matrasbartosz.gamerpg.unit.character.items;

import lombok.Getter;

@Getter
public abstract class Item {

    private final int gainMoreHealth;
    private final int gainMoreDamage;
    private final int gainMoreExp;
    private final double gainMoreMoney;
    private final int chanceForDodge;

    protected Item(int gainMoreHealth, int gainMoreDamage, int gainMoreExp, double gainMoreMoney, int chanceForDodge) {
        this.gainMoreHealth = gainMoreHealth;
        this.gainMoreDamage = gainMoreDamage;
        this.gainMoreExp = gainMoreExp;
        this.gainMoreMoney = gainMoreMoney;
        this.chanceForDodge = chanceForDodge;
    }
}
