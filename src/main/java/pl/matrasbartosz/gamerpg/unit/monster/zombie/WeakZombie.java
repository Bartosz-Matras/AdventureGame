package pl.matrasbartosz.gamerpg.unit.monster.zombie;


import static pl.matrasbartosz.gamerpg.unit.monster.zombie.ZombieConstants.*;

public class WeakZombie extends Zombie {

    public WeakZombie() {
        super(WEAK_ZOMBIE_HEALTH, WEAK_ZOMBIE_DAMAGE, WEAK_ZOMBIE_EXPERIENCE, WEAK_ZOMBIE_MONEY);
    }
}
