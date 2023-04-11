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
            this.setMoney(this.getMoney().add(monster.getMoney()));

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
    public void wearItem(Item item) throws TooManyItemsException {
        if (item instanceof Armour) {
            Item itemToTakeOf = null;
            for (Item currentItem : this.getFoundedItems()) {
                if (currentItem instanceof Armour) {
                    itemToTakeOf = currentItem;
                }
            }
            if (itemToTakeOf == null) {
                this.getFoundedItems().add(item);
            } else {
                takeOfItem(itemToTakeOf);
                this.getFoundedItems().add(item);
            }
        }
    }

    @Override
    public void takeOfItem(Item item) throws TooManyItemsException {
        Item itemToChange;
        for (Item currentItem : this.getFoundedItems()) {
            if (currentItem instanceof Armour) {
                itemToChange = currentItem;
                this.getFoundedItems().remove(item);
                addItem(itemToChange);
            }
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

    int increaseExperience() {

        if (this.getFoundedItems().isEmpty()) {
            return this.getLevel() * 10;
        } else {
            int moreExp = 0;
            for (Item item : this.getFoundedItems()) {
                moreExp += item.getGainMoreExp();
            }
            return (this.getLevel() * 10) * (moreExp == 0 ? 1 : moreExp); // something is wrong
        }
    }

    BigDecimal increaseMoney() {
        if (this.getFoundedItems().isEmpty()) {
            return new BigDecimal(this.getLevel() * 100);
        } else {
            int moreMoney = 0;
            for (Item item : this.getFoundedItems()) {
                moreMoney += item.getGainMoreExp();
            }
            return new BigDecimal((this.getLevel() * 100) * (moreMoney == 0 ? 1 : moreMoney)); // something is wrong
        }
    }
}
