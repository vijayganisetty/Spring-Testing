package com.springBoot.practice.springTesting;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

@Slf4j
class SpringTestingApplicationTests {

    @BeforeAll
    static void beforeAll() {
        log.info("Setup once");
    }

    @AfterAll
    static void afterAll() {
        log.info("tearing down all at once");
    }

    @BeforeEach
    void beforeEach() {
        log.info("Before every test");
    }

    @Test
    @DisplayName("this is first test")
    void testDivideTwoIntegers_whenBothNumbersArePositive() {
        log.info("test 1");
        int a = 5;
        int b = 6;

        int result = addTwoNumbers(5, 6);
        //  Assertions.assertEquals(11,result);
        Assertions.assertThat(result).isEqualTo(11);
    }

    @Test
    @DisplayName("secondTest")
    void testDivideTwoIntegers_whenDenominatorIsZero() {
        Assertions.assertThatThrownBy(() -> divideTwoIntegers(25, 0))
                .isInstanceOf(ArithmeticException.class);
    }

    int addTwoNumbers(int a, int b) {
        return a + b;
    }

    double divideTwoIntegers(int a, int b) {
        try {
            return a / b;
        } catch (ArithmeticException e) {
            throw new ArithmeticException(e.getLocalizedMessage());
        }
    }


}
