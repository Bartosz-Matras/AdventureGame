package pl.matrasbartosz.gamerpg.unit.character;

import org.junit.jupiter.api.Test;
import pl.matrasbartosz.gamerpg.unit.character.items.Item;
import pl.matrasbartosz.gamerpg.unit.character.items.exceptions.EmptyInventoryException;
import pl.matrasbartosz.gamerpg.unit.character.items.exceptions.TooManyItemsException;
import pl.matrasbartosz.gamerpg.unit.character.items.factory.ItemFactory;
import pl.matrasbartosz.gamerpg.unit.character.items.factory.ItemType;
import pl.matrasbartosz.gamerpg.unit.monster.Monster;
import pl.matrasbartosz.gamerpg.unit.monster.factory.MonsterFactory;
import pl.matrasbartosz.gamerpg.unit.monster.factory.MonsterType;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void character1ShouldWonAfterAttackCharacter2AndGainExpWithItems() throws TooManyItemsException, EmptyInventoryException {
        //given
        ItemFactory factory = ItemFactory.getInstance();
        Elf character1 = new Elf("Elf1");
        character1.setHealth(1000);
        character1.setDamage(1000);
        Item item = factory.createArmour(ItemType.RARE_ARMOR);
        character1.addItem(item);
        character1.wearItem(item);
        double character1Exp = character1.getExperience();
        double character1CalculateExp = character1.increaseExperience();

        Elf character2 = new Elf("Elf2");

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
    void character1ShouldGetMoneyFromCharacter2AfterWonWithNoItems() {
        //given
        Elf character1 = new Elf("Elf1");
        BigDecimal previousMoney = character1.getMoney();
        character1.setHealth(1000);
        character1.setDamage(1000);
        Elf character2 = new Elf("Elf2");

        //when
        character1.attackCharacter(character2);

        //then
        assertThat(character1.getMoney(), not(equalTo(previousMoney)));
        assertThat(character1.getMoney(), equalTo(BigDecimal.valueOf(100)));
    }

    @Test
    void character1ShouldGetMoneyFromCharacter2AfterWonWithItems() throws TooManyItemsException, EmptyInventoryException {
        //given
        ItemFactory factory = ItemFactory.getInstance();
        Elf character1 = new Elf("Elf1");
        BigDecimal previousMoney = character1.getMoney();
        character1.setHealth(1000);
        character1.setDamage(1000);
        Item item = factory.createArmour(ItemType.RARE_ARMOR);
        character1.addItem(item);
        character1.wearItem(item);

        Elf character2 = new Elf("Elf2");

        //when
        character1.attackCharacter(character2);

        //then
        assertThat(character1.getMoney(), not(equalTo(previousMoney)));
        assertThat(character1.getMoney(), equalTo(BigDecimal.valueOf(105).setScale(2, RoundingMode.HALF_UP)));
    }

    @Test
    void characterShouldGetMoneyFromMonsterZombieAfterWonWithNoItems() throws TooManyItemsException, EmptyInventoryException {
        //given
        ItemFactory itemFactory = ItemFactory.getInstance();
        Elf character = new Elf("Elf");
        BigDecimal previousMoney = character.getMoney();
        Item item = itemFactory.createArmour(ItemType.RARE_ARMOR);
        character.addItem(item);
        character.wearItem(item);

        MonsterFactory factory = MonsterFactory.getInstance();
        Monster zombie = factory.createMonster(MonsterType.WEAK_ZOMBIE);

        //when
        character.attackMonster(zombie);

        //then
        assertThat(character.getMoney(), equalTo(BigDecimal.valueOf(21).setScale(2, RoundingMode.HALF_UP)));
        assertThat(character.getMoney(), not(equalTo(previousMoney)));
    }

    @Test
    void characterShouldGetMoneyFromMonsterZombieAfterWonWithItems() {
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
    void itemShouldBeAddedToInventory() throws TooManyItemsException {
        //given
        ItemFactory itemFactory = ItemFactory.getInstance();
        Elf character = new Elf("Elf");
        Item item = itemFactory.createArmour(ItemType.COMMON_ARMOR);

        //when
        character.addItem(item);

        //then
        assertThat(character.getInventoryItems().size(), equalTo(1));
        assertThat(character.getInventoryItems(), contains(item));
    }

    @Test
    void errorShouldBeThrownAfterExceededInventoryMaximumSize() throws TooManyItemsException {
        //given
        ItemFactory itemFactory = ItemFactory.getInstance();
        Elf character = new Elf("Elf");
        for (int i = 0; i < 10; i++){
            Item item = itemFactory.createArmour(ItemType.COMMON_ARMOR);
            character.addItem(item);
        }

        //when
        Item item = itemFactory.createArmour(ItemType.COMMON_ARMOR);

        //then
        assertThat(character.getInventoryItems(), hasSize(10));
        assertThrows(TooManyItemsException.class, () -> character.addItem(item));
    }

    @Test
    void itemShouldBeRemovedFromInventory() throws TooManyItemsException, EmptyInventoryException {
        //given
        ItemFactory itemFactory = ItemFactory.getInstance();
        Elf character = new Elf("Elf");
        Item item = itemFactory.createArmour(ItemType.COMMON_ARMOR);
        character.addItem(item);

        //when
        character.removeItem(item);

        //then
        assertThat(character.getInventoryItems().size(), equalTo(0));
        assertThat(character.getInventoryItems(), hasSize(0));
        assertThat(character.getInventoryItems(), not(contains(item)));
    }

    @Test
    void errorShouldBeThrownWhenWantToRemoveItemFromEmptyInventory() {
        //given
        ItemFactory itemFactory = ItemFactory.getInstance();
        Elf character = new Elf("Elf");
        Item item = itemFactory.createArmour(ItemType.COMMON_ARMOR);

        //when
        //then
        assertThat(character.getInventoryItems(), hasSize(0));
        assertThrows(EmptyInventoryException.class, () -> character.removeItem(item));
    }

    @Test
    void afterWearItemFoundedItemListShouldContainsThisItem() throws TooManyItemsException, EmptyInventoryException {
        //given
        ItemFactory itemFactory = ItemFactory.getInstance();
        Elf character = new Elf("Elf");
        Item item = itemFactory.createArmour(ItemType.COMMON_ARMOR);
        character.addItem(item);

        //when
        character.wearItem(item);

        //then
        assertThat(character.getInventoryItems(), hasSize(0));
        assertThat(character.getInventoryItems(), not(contains(item)));
        assertThat(character.getFoundedItems(), hasSize(1));
        assertThat(character.getFoundedItems(), contains(item));
    }

    @Test
    void afterWearOtherItemTheSameTypeCurrentItemShouldBeReplaced() throws TooManyItemsException, EmptyInventoryException {
        //given
        ItemFactory itemFactory = ItemFactory.getInstance();
        Elf character = new Elf("Elf");

        Item item = itemFactory.createArmour(ItemType.COMMON_ARMOR);
        character.addItem(item);
        character.wearItem(item);

        Item item2 = itemFactory.createArmour(ItemType.COMMON_ARMOR);
        character.addItem(item2);

        //when
        character.wearItem(item2);

        //then
        assertThat(character.getInventoryItems(), contains(item));
        assertThat(character.getInventoryItems(), not(contains(item2)));
        assertThat(character.getInventoryItems(), hasSize(1));

        assertThat(character.getFoundedItems(), contains(item2));
        assertThat(character.getFoundedItems(), not(contains(item)));
        assertThat(character.getFoundedItems(), hasSize(1));
    }

    @Test
    void afterWearItemStatsShouldBeIncreased() throws TooManyItemsException, EmptyInventoryException {
        //given
        ItemFactory itemFactory = ItemFactory.getInstance();
        Elf character = new Elf("Elf");
        int characterHealth = character.getHealth();
        int characterDamage = character.getDamage();

        Item item = itemFactory.createArmour(ItemType.COMMON_ARMOR);
        character.addItem(item);

        //when
        character.wearItem(item);

        //then
        assertThat(character.getHealth(), not(equalTo(characterHealth)));
        assertThat(character.getHealth(), equalTo(characterHealth + item.getGainMoreHealth()));
        assertThat(character.getDamage(), not(equalTo(characterDamage)));
        assertThat(character.getDamage(), equalTo(characterDamage + item.getGainMoreDamage()));
    }

    @Test
    void afterTakeOfItemStatsShouldBeDecreased() throws TooManyItemsException, EmptyInventoryException {
        //given
        ItemFactory itemFactory = ItemFactory.getInstance();
        Elf character = new Elf("Elf");

        Item item = itemFactory.createArmour(ItemType.COMMON_ARMOR);
        character.addItem(item);
        character.wearItem(item);

        int characterHealth = character.getHealth();
        int characterDamage = character.getDamage();

        //when
        character.takeOfItem(item);

        //then
        assertThat(character.getHealth(), not(equalTo(characterHealth)));
        assertThat(character.getHealth(), equalTo(characterHealth - item.getGainMoreHealth()));
        assertThat(character.getDamage(), not(equalTo(characterDamage)));
        assertThat(character.getDamage(), equalTo(characterDamage - item.getGainMoreDamage()));
    }

    @Test
    void afterSwitchItemsStatsShouldBeReplaced() throws TooManyItemsException, EmptyInventoryException {
        //given
        ItemFactory itemFactory = ItemFactory.getInstance();
        Elf character = new Elf("Elf");

        Item item = itemFactory.createArmour(ItemType.COMMON_ARMOR);
        character.addItem(item);
        character.wearItem(item);
        int healthWithItem1 = character.getHealth();
        int damageWithItem1 = character.getDamage();

        Item item2 = itemFactory.createArmour(ItemType.RARE_ARMOR);
        character.addItem(item2);

        //when
        character.wearItem(item2);
        int healthWithItem2 = character.getHealth();
        int damageWithItem2 = character.getDamage();

        //then
        assertThat(character.getHealth(), equalTo(healthWithItem2));
        assertThat(character.getHealth(), not(equalTo(healthWithItem1)));
        assertThat(character.getDamage(), equalTo(damageWithItem2));
        assertThat(character.getDamage(), not(equalTo(damageWithItem1)));
    }



}
