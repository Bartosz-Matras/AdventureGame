package pl.matrasbartosz.gamerpg.unit.monster.factory;

import pl.matrasbartosz.gamerpg.unit.monster.Monster;
import pl.matrasbartosz.gamerpg.unit.monster.zombie.HardZombie;
import pl.matrasbartosz.gamerpg.unit.monster.zombie.MediumZombie;
import pl.matrasbartosz.gamerpg.unit.monster.zombie.WeakZombie;

public class MonsterFactory extends Factory {

    private static MonsterFactory instance;

    private MonsterFactory() {}

    public static MonsterFactory getInstance() {
        if (instance == null) {
            instance = new MonsterFactory();
        }
        return instance;
    }

    @Override
    public Monster createMonster(MonsterType monsterType) {
        switch (monsterType) {
            case WEAK_ZOMBIE -> {
                return new WeakZombie();
            }
            case MEDIUM_ZOMBIE -> {
                return new MediumZombie();
            }
            case HARD_ZOMBIE -> {
                return new HardZombie();
            }
            default -> throw new UnsupportedOperationException("Unsupported type");
        }
    }
}
