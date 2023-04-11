package pl.matrasbartosz.gamerpg.unit.character;


import lombok.ToString;

import static pl.matrasbartosz.gamerpg.unit.character.CharacterConstants.*;


@ToString
public class Elf extends Character {

    public Elf(String name) {
        super(name, ELF_HEALTH, ELF_DAMAGE, ELF_EXPERIENCE, ELF_MONEY, ELF_DEFAULT_INVENTORY_SIZE);
    }

    @Override
    void increaseDamage() {
        this.setDamage((int) Math.round(this.getDamage() * ELF_INCREASE_DAMAGE));
    }

    @Override
    void increaseHealth() {
        this.setHealth((int) Math.round(this.getHealth() * ELF_INCREASE_HEALTH));
    }

}
