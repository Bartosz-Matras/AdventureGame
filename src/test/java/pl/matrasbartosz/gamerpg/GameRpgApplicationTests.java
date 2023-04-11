package pl.matrasbartosz.gamerpg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class GameRpgApplicationTests {

    @Autowired
    ApplicationContext context;

    @Test
    void userServiceBeanShouldExistInContext() {
        assertThat(context.containsBean("userService"), is(true));
    }

}
