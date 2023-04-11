package pl.matrasbartosz.gamerpg.unit.character;

import lombok.Getter;
import lombok.Setter;
import pl.matrasbartosz.gamerpg.unit.character.items.Item;
import pl.matrasbartosz.gamerpg.unit.character.items.ManageItems;
import pl.matrasbartosz.gamerpg.unit.character.items.armour.Armour;
import pl.matrasbartosz.gamerpg.unit.character.items.exceptions.EmptyInventoryException;
import pl.matrasbartosz.gamerpg.unit.character.items.exceptions.TooManyItemsException;
import pl.matrasbartosz.gamerpg.unit.monster.Monster;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static pl.matrasbartosz.gamerpg.unit.character.CharacterConstants.ELF_DEFAULT_INVENTORY_SIZE;

@Getter
@Setter
public abstract class Character implements ManageItems {

    private String name;
    private int health;
    private int damage;
    private double experience;
    private BigDecimal money;
    private int level;
    private double nextLevelExperience;
    private boolean isYourTurn;
    private int inventorySize;
    private List<Item> foundedItems;
    private List<Item> inventoryItems;

    protected Character(String name, int health, int damage, double experience, BigDecimal money, int inventorySize) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.experience = experience;
        this.money = money;
        this.inventorySize = inventorySize;
        this.level = 1;
        this.nextLevelExperience = 100;
        this.isYourTurn = false;
        this.foundedItems = new ArrayList<>();
        this.inventoryItems = new ArrayList<>();
    }

    abstract void increaseDamage();

    abstract void increaseHealth();

    public void attackCharacter(Character character) {
        while (this.getHealth() > 0 && character.getHealth() > 0) {
            if (this.isYourTurn()) {
                character.setHealth(character.getHealth() - this.getDamage());
                this.setYourTurn(false);
            } else {
                this.setHealth(this.getHealth() - character.getDamage());
                this.setYourTurn(true);
            }
        }
        if (this.getHealth() > 0) {
            this.setExperience(this.getExperience() + increaseExperience());
            this.checkNextLevel();
            this.setMoney(this.getMoney().add(increaseMoney()));
        }
        this.setYourTurn(false);
    }

    public void attackMonster(Monster monster) {
        while (this.getHealth() > 0 && monster.getHealth() > 0) {
            if (this.isYourTurn()) {
                monster.setHealth(monster.getHealth() - this.getDamage());
                this.setYourTurn(false);
            } else {
                this.setHealth(this.getHealth() - monster.getDamage());
                this.setYourTurn(true);
            }
        }
        if (this.getHealth() > 0) {
            this.setExperience(this.getExperience() + increaseExperience());
            this.checkNextLevel();
            this.setMoney(this.getMoney().add(increaseMoney(monster.getMoney())));

        }
        this.setYourTurn(false);
    }

    public void checkNextLevel() {
        while (experience >= nextLevelExperience) {
            setExperience(getExperience() - getNextLevelExperience());
            setNextLevelExperience(getNextLevelExperience() + 100);
            setLevel(getLevel() + 1);
            increaseDamage();
            increaseHealth();
        }
    }

    @Override
    public void wearItem(Item item) throws TooManyItemsException, EmptyInventoryException {
        if (item instanceof Armour) {
            Item itemToTakeOf = null;
            for (Item currentItem : this.getFoundedItems()) {
                if (currentItem instanceof Armour) {
                    itemToTakeOf = currentItem;
                }
            }
            if (itemToTakeOf == null) {
                this.getFoundedItems().add(item);
                increaseStats(item);
                removeItem(item);
            } else {
                this.getFoundedItems().remove(itemToTakeOf);
                addItem(itemToTakeOf);
                decreaseStats(itemToTakeOf);
                this.getFoundedItems().add(item);
                removeItem(item);
                increaseStats(item);
            }
        }
    }

    @Override
    public void takeOfItem(Item item) throws TooManyItemsException {
        Item itemToChange = null;
        for (Item currentItem : this.getFoundedItems()) {
            if (currentItem instanceof Armour) {
                itemToChange = currentItem;
            }
        }
        if (itemToChange != null) {
            this.getFoundedItems().remove(item);
            decreaseStats(item);
            addItem(itemToChange);
        }
    }

    @Override
    public void addItem(Item item) throws TooManyItemsException {
        if (this.getInventoryItems().size() != ELF_DEFAULT_INVENTORY_SIZE) {
            this.getInventoryItems().add(item);
        } else {
            throw new TooManyItemsException("Too many items");
        }
    }

    @Override
    public void removeItem(Item item) throws EmptyInventoryException {
        if (!this.getInventoryItems().isEmpty()) {
            this.getInventoryItems().remove(item);
        } else {
            throw new EmptyInventoryException("Inventory is empty");
        }
    }

    double increaseExperience() {

        if (this.getFoundedItems().isEmpty()) {
            return this.getLevel() * 10;
        } else {
            double moreExp = 0;
            for (Item item : this.getFoundedItems()) {
                moreExp += item.getGainMoreExp();
            }
            return (this.getLevel() * 10) * (1.0 + ((moreExp == 0 ? 1 : moreExp)/100));
        }
    }

    BigDecimal increaseMoney() {
        if (this.getFoundedItems().isEmpty()) {
            return new BigDecimal(this.getLevel() * 100);
        } else {
            double moreMoney = 0;
            for (Item item : this.getFoundedItems()) {
                moreMoney += item.getGainMoreMoney();
            }
            double value = (this.getLevel() * 100) * (1.0 + ((moreMoney == 0 ? 1 : moreMoney)/100));
            return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
        }
    }

    BigDecimal increaseMoney(BigDecimal monsterGold) {
        if (this.getFoundedItems().isEmpty()) {
            return monsterGold;
        } else {
            double moreMoney = 0;
            for (Item item : this.getFoundedItems()) {
                moreMoney += item.getGainMoreMoney();
            }
            BigDecimal value = monsterGold.multiply(BigDecimal.valueOf(1.0 + ((moreMoney == 0 ? 1 : moreMoney)/100)));
            return value.setScale(2, RoundingMode.HALF_UP);
        }
    }

    void increaseStats(Item item) {
        this.setHealth(this.getHealth() + item.getGainMoreHealth());
        this.setDamage(this.getDamage() + item.getGainMoreDamage());
    }

    void decreaseStats(Item item) {
        this.setHealth(this.getHealth() - item.getGainMoreHealth());
        this.setDamage(this.getDamage() - item.getGainMoreDamage());
    }
}
