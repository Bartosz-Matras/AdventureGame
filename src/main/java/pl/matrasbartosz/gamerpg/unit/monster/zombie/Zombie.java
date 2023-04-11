package pl.matrasbartosz.gamerpg.unit.monster.zombie;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.matrasbartosz.gamerpg.unit.monster.Monster;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public abstract class Zombie extends Monster {

    protected Zombie(int health, int damage, double experience, BigDecimal money) {
        super(health, damage, experience, money);
    }
}
