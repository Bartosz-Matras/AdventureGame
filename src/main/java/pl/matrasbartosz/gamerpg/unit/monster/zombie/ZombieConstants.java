package pl.matrasbartosz.gamerpg.unit.monster.zombie;

import java.math.BigDecimal;

public final class ZombieConstants {

    private ZombieConstants() {}

    public static final int WEAK_ZOMBIE_HEALTH = 100;
    public static final int WEAK_ZOMBIE_DAMAGE = 5;
    public static final double WEAK_ZOMBIE_EXPERIENCE = 20;
    public static final BigDecimal WEAK_ZOMBIE_MONEY = new BigDecimal(20);


    public static final int MEDIUM_ZOMBIE_HEALTH = 500;
    public static final int MEDIUM_ZOMBIE_DAMAGE = 25;
    public static final double MEDIUM_ZOMBIE_EXPERIENCE = 200;
    public static final BigDecimal MEDIUM_ZOMBIE_MONEY = new BigDecimal(200);


    public static final int HARD_ZOMBIE_HEALTH = 2000;
    public static final int HARD_ZOMBIE_DAMAGE = 100;
    public static final double HARD_ZOMBIE_EXPERIENCE = 1000;
    public static final BigDecimal HARD_ZOMBIE_MONEY = new BigDecimal(1000);

}
