package pl.matrasbartosz.gamerpg.unit.monster.factory;

import org.junit.jupiter.api.Test;
import pl.matrasbartosz.gamerpg.unit.monster.Monster;
import pl.matrasbartosz.gamerpg.unit.monster.factory.MonsterFactory;
import pl.matrasbartosz.gamerpg.unit.monster.factory.MonsterType;
import pl.matrasbartosz.gamerpg.unit.monster.zombie.HardZombie;
import pl.matrasbartosz.gamerpg.unit.monster.zombie.MediumZombie;
import pl.matrasbartosz.gamerpg.unit.monster.zombie.WeakZombie;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MonsterFactoryTests {

    @Test
    void monsterFactoryShouldBeSingleton() {
        //given
        //when
        MonsterFactory monsterFactory1 = MonsterFactory.getInstance();
        MonsterFactory monsterFactory2 = MonsterFactory.getInstance();

        //then
        assertThat(monsterFactory1, sameInstance(monsterFactory2));
    }

    @Test
    void monsterFactoryShouldReturnWeakZombie() {
        //given
        MonsterFactory monsterFactory = MonsterFactory.getInstance();

        //when
        Monster zombie = monsterFactory.createMonster(MonsterType.WEAK_ZOMBIE);

        //then
        assertThat(zombie, instanceOf(WeakZombie.class));
    }

    @Test
    void monsterFactoryShouldReturnMediumZombie() {
        //given
        MonsterFactory monsterFactory = MonsterFactory.getInstance();

        //when
        Monster zombie = monsterFactory.createMonster(MonsterType.MEDIUM_ZOMBIE);

        //then
        assertThat(zombie, instanceOf(MediumZombie.class));
    }


    @Test
    void monsterFactoryShouldReturnHardZombie() {
        //given
        MonsterFactory monsterFactory = MonsterFactory.getInstance();

        //when
        Monster zombie = monsterFactory.createMonster(MonsterType.HARD_ZOMBIE);

        //then
        assertThat(zombie, instanceOf(HardZombie.class));
    }

    @Test
    void monsterFactoryShouldThrowUnsupportedOperationException() {
        //given
        //when
        MonsterFactory monsterFactory = MonsterFactory.getInstance();

        //then
        assertThrows(UnsupportedOperationException.class, () -> monsterFactory.createMonster(MonsterType.UNSUPPORTED_TYPE));
    }


}
