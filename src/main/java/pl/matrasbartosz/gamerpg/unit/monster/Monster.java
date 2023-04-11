package pl.matrasbartosz.gamerpg.unit.monster;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public abstract class Monster {

    private int health;
    private int damage;
    private double experience;
    private BigDecimal money;


    protected Monster(int health, int damage, double experience, BigDecimal money) {
        this.health = health;
        this.damage = damage;
        this.experience = experience;
        this.money = money;
    }
}
