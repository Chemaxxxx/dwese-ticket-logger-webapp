
package org.alixar.daw2.JoseManuel.dwese_ticket_logger_webapp.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class) // Probamos solo el controlador HelloController
public class HelloControllerTest {

    // Inyecta MockMvc para realizar peticiones simuladas.
    @Autowired
    private MockMvc mockMvc;

    /**
     * Test para el endpoint /hello sin pasar el parámetro 'name'.
     * Debe devolver "Hello World!".
     */
    @Test
    public void testHelloDefault() throws Exception {

        // Realiza una petición GET a /hello sin pasar el parámetro 'name'.
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk()) // Espera el estado 200 (OK).
                .andExpect(content().string("Hello World!"));
    }

    /**
     * Test para el endpoint /hello pasando un parámetro 'name'.
     * Debe devolver "Hello prof.albertocanca!".
     */
    @Test
    public void testHelloWithName() throws Exception {

        // Realiza una petición GET a /hello con el parámetro 'name'.
        mockMvc.perform(get("/hello").param("name", "prof.albertocanca"))
                .andExpect(status().isOk()) // Espera el estado 200 (OK).
                .andExpect(content().string("Hello prof.albertocanca!"));
    }
}
