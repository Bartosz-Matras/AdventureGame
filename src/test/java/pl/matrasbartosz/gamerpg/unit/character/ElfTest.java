package pl.matrasbartosz.gamerpg.unit.character;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ElfTest {

    @Test
    void newCreatedElfShouldHaveInitializeFields() {
        //given
        //when
        Elf elf = new Elf("Elf");

        //then
        assertThat(elf.getHealth(), equalTo(100));
        assertThat(elf.getDamage(), equalTo(20));
        assertThat(elf.getExperience(), equalTo(0.0));
        assertThat(elf.getMoney(), equalTo(new BigDecimal(0)));
        assertThat(elf.getName(), equalTo("Elf"));
        assertThat(elf.getLevel(), equalTo(1));
        assertThat(elf.getNextLevelExperience(), equalTo(100.0));
        assertThat(elf.isYourTurn(), is(false));
        assertThat(elf.getFoundedItems(), empty());
        assertThat(elf.getInventoryItems(), empty());
    }


}
