package org.altice;

import org.altice.services.CalcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class CalcServiceTests {

    private CalcService calcService;

    @BeforeEach
    void setUp() {
        calcService = new CalcService();
    }

    @Test
    void testBaseCases() {
        assertEquals(BigInteger.ZERO, calcService.calc(0));
        assertEquals(BigInteger.ONE, calcService.calc(1));
        assertEquals(BigInteger.ZERO, calcService.calc(2));
        assertEquals(BigInteger.ONE, calcService.calc(3));
    }

    @Test
    void testFirstFewValues() {
        assertEquals(BigInteger.ONE, calcService.calc(4)); // 0 + 1
        assertEquals(BigInteger.ONE, calcService.calc(5)); // 1 + 0
        assertEquals(BigInteger.ONE, calcService.calc(6)); // 0 + 1
        assertEquals(BigInteger.valueOf(2), calcService.calc(7)); // 1 + 1
    }

    @Test
    void testLargerValue() {
        BigInteger result = calcService.calc(20);
        assertNotNull(result);
        assertTrue(result.compareTo(BigInteger.ZERO) > 0);
    }

    @Test
    void testCacheEffectiveness() {
        BigInteger firstCall = calcService.calc(1000);
        BigInteger secondCall = calcService.calc(1000);

        // Deve ser exatamente o mesmo objeto da lista em cache
        assertSame(firstCall, secondCall);
    }

    @Test
    void testSequentialCallsDoNotBreak() {
        BigInteger smaller = calcService.calc(50);
        BigInteger larger = calcService.calc(200);

        assertNotNull(smaller);
        assertNotNull(larger);
        assertTrue(larger.compareTo(smaller) > 0);
    }
}