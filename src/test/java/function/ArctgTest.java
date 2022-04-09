package function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Arctg(х) Test")
public class ArctgTest {

    private Arctg arctg;

    @BeforeEach
    void init() {
        arctg = new Arctg();
    }

    @ParameterizedTest(name = "Test {index}: arctg({0}) with e = {1}")
    @CsvSource({
            "0.5, 0.1",
            "-0.9, 0.1",
            "0.1, 0.1",
            Double.MAX_VALUE + ", 0.1",
            Double.MIN_VALUE + ", 0.001",
            "122.1, 0.01"
    })
    void getArctgTest(double x, double e) {
        assertEquals(Math.atan(x), arctg.getArctg(x, e), 0.1);
    }
}
