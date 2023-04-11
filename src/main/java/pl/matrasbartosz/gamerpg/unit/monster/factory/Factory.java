package pl.matrasbartosz.gamerpg.unit.monster.factory;

import pl.matrasbartosz.gamerpg.unit.monster.Monster;

abstract class Factory {
    protected abstract Monster createMonster(MonsterType monsterType);
}
