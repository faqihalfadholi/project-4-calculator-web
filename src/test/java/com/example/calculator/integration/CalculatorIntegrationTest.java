package com.example.calculator.integration;

import com.example.calculator.model.CalculationHistory;
import com.example.calculator.repository.CalculationHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CalculationHistoryRepository repository;

    @Test
    void testCalculatorIntegration() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("expression", "2 + 2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("4.0")));

        CalculationHistory savedCalculation = repository.findAll().getFirst();
        assertEquals("2 + 2", savedCalculation.getExpression());
        assertEquals(4.0, savedCalculation.getResult());
    }
}