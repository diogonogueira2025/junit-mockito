package com.diogonogueira.junit.service;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {
    @Nested
    class AdditionTests {
        private CalculatorService calculatorService;

        @BeforeEach
        void setUp() {
            calculatorService = new CalculatorService();
        }

        @Test
        @DisplayName("Should return correct sum when adding two numbers")
        void shouldAddPositiveTwoNumbers() {
            int result = calculatorService.add(5, 3);
            assertEquals(8, result);
        }

        @Test
        void shouldAddNegativeTwoNumbers() {
            int result = calculatorService.add(-2, -2);
            assertEquals(-4, result);
        }
    }

    @Nested
    class DivisionTests {
        private CalculatorService calculatorService;

        @BeforeEach
        void setUp() {
            calculatorService = new CalculatorService();
        }

        @Test
        @DisplayName("Should return correct result when dividing two numbers")
        void shouldDividePositiveTwoNumbers() {
            int result = calculatorService.divide(10, 5);
            assertEquals(2, result);
        }

        @Test
        @DisplayName("Should throw ArithmeticException when dividing by zero")
        void shouldThrowExceptionWhenDividingByZero() {
            assertThrows(ArithmeticException.class, () -> calculatorService.divide(1, 0));
        }
    }

    @BeforeAll
    static void initAll() {
        System.out.println("Executando testes...");
    }

    @AfterAll
    static void tearDown() {
        System.out.println("Testes finalizados...");
    }

}