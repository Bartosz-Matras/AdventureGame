package pl.matrasbartosz.gamerpg.unit.character;

import org.junit.jupiter.api.Test;
import pl.matrasbartosz.gamerpg.unit.monster.Monster;
import pl.matrasbartosz.gamerpg.unit.monster.factory.MonsterFactory;
import pl.matrasbartosz.gamerpg.unit.monster.factory.MonsterType;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CharacterTest {

    @Test
    void levelShouldBeIncreasedAfterReachedNeededExperience() {
        //given
        Character character = new Elf("elf");
        int expGained = 300;

        //when
        character.setExperience(expGained);
        character.checkNextLevel();

        //then
        assertThat(character.getLevel(), equalTo(3));
    }

    @Test
    void experienceShouldBeEqualTo0AfterGainedExpRequiredToNextLevel() {
        //given
        Character character = new Elf("elf");
        int expGained = 100;

        //when
        character.setExperience(expGained);
        character.checkNextLevel();

        //then
        assertThat(character.getExperience(), equalTo(0.0));
        assertThat(character.getLevel(), equalTo(2));
    }

    @Test
    void nextLevelExperienceShouldBeIncreasedAfterGainNextLevel() {
        //given
        Character character = new Elf("elf");
        int expGained = 100;

        //when
        character.setExperience(expGained);
        character.checkNextLevel();

        //then
        assertThat(character.getNextLevelExperience(), equalTo(200.0));
        assertThat(character.getExperience(), equalTo(0.0));
        assertThat(character.getLevel(), equalTo(2));
    }

    @Test
    void character1ShouldLostAfterAttackCharacter2AndGainNoExp() {
        //given
        Character character1 = new Elf("Elf1");
        Character character2 = new Elf("Elf2");
        double character1Exp = character1.getExperience();

        //when
        character1.attackCharacter(character2);

        //then
        assertThat(character1.getExperience(), equalTo(character1Exp));
    }

    @Test
    void character1ShouldWonAfterAttackCharacter2AndGainExp() {
        //given
        Elf character1 = new Elf("Elf1");
        character1.setHealth(1000);
        character1.setDamage(1000);
        Elf character2 = new Elf("Elf2");
        double character1Exp = character1.getExperience();
        double character1CalculateExp = character1.increaseExperience();

        //when
        character1.attackCharacter(character2);

        //then
        assertThat(character1.getExperience(), not(equalTo(character1Exp)));
        assertThat(character1.getExperience(), equalTo(character1Exp + character1CalculateExp));
    }

    @Test
    void character1ShouldWonAfterAttackCharacter2AndLevelUp() {
        //given
        Elf character1 = new Elf("Elf1");
        character1.setHealth(1000);
        character1.setDamage(1000);
        character1.setExperience(90);
        Elf character2 = new Elf("Elf2");
        double character1Exp = character1.getExperience();
        double character1Level = character1.getLevel();

        //when
        character1.attackCharacter(character2);

        //then
        assertThat(character1.getExperience(), not(equalTo(character1Exp)));
        assertThat(character1.getExperience(), equalTo(0.0));
        assertThat(character1.getLevel(), not(equalTo(character1Level)));
        assertThat(character1.getLevel(), equalTo(2));
    }

    @Test
    void characterDamageShouldIncreaseAfterLevelUp() {
        //given
        Elf character = new Elf("Elf");
        int experience = 100;
        int previousDamage = character.getDamage();

        //when
        character.setExperience(character.getExperience() + experience);
        character.checkNextLevel();

        //then
        assertThat(character.getLevel(), equalTo(2));
        assertThat(character.getDamage(), not(equalTo(previousDamage)));
        assertThat(character.getDamage(), equalTo(21));
    }

    @Test
    void characterHealthShouldBeIncreasedAfterLevelUp() {
        //given
        Elf character = new Elf("Elf");
        int experience = 100;
        int previousHealth = character.getHealth();

        //when
        character.setExperience(character.getExperience() + experience);
        character.checkNextLevel();

        //then
        assertThat(character.getLevel(), equalTo(2));
        assertThat(character.getHealth(), not(equalTo(previousHealth)));
        assertThat(character.getHealth(), equalTo(105));
    }

    @Test
    void characterShouldGetMoneyFromMonsterZombieAfterWon() {
        //given
        Elf character = new Elf("Elf");
        BigDecimal previousMoney = character.getMoney();
        MonsterFactory factory = MonsterFactory.getInstance();
        Monster zombie = factory.createMonster(MonsterType.WEAK_ZOMBIE);

        //when
        character.attackMonster(zombie);

        //then
        assertThat(character.getMoney(), equalTo(new BigDecimal(20)));
        assertThat(character.getMoney(), not(equalTo(previousMoney)));
    }

    @Test
   void character1ShouldGetMoneyFromCharacter2AfterWon() {
        //given
        Elf character1 = new Elf("Elf");
        BigDecimal previousMoney = character1.getMoney();
        character1.setHealth(1000);
        character1.setDamage(1000);
        Elf character2 = new Elf("Elf");

        //when
        character1.attackCharacter(character2);

        //then
        assertThat(character1.getMoney(), not(equalTo(previousMoney)));
        assertThat(character1.getMoney(), equalTo(new BigDecimal(100)));
   }

}
