import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    @Test

    public void nullTestException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    public void nullNameMessage() {
        try {
            new Horse(null, 1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t", "\n\n\n"})
    public void blankNameException(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    public void getName () {
        Horse horse = new Horse("qwerty", 1,1);

        Field name = null;
        try {
            name = horse.getClass().getDeclaredField("name");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        name.setAccessible(true);
        String nameValue = null;
        try {
            nameValue = (String) name.get(horse);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        assertEquals("qwerty", nameValue);
    }

    @Test
    public void getSpeed () {
        Horse horse = new Horse("qwerty", 13, 1);
        assertEquals(13, horse.getSpeed());
    }

    @Test
    public void getDistance () {
        Horse horse = new Horse("qwerty", 1, 12);
        assertEquals(12, horse.getDistance());
    }

    @Test
    void moveUsesGetRandom () {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("qwerty", 38, 234).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(anyDouble(), anyDouble()));
        }
    }
}
