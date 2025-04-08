package br.com.olua.rest;


import br.com.olua.E2ETest;
import br.com.olua.command.CreateProductCommand;
import br.com.olua.command.UpdateProductCommand;
import br.com.olua.output.GetAllProductOutput;
import br.com.olua.output.GetProductOutput;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Container
    public static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("olua_product")
            .withUsername("postgres")
            .withPassword("postgres");


    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresDB::getJdbcUrl);
        registry.add("spring.datasource.username", postgresDB::getUsername);
        registry.add("spring.datasource.password", postgresDB::getPassword);
    }

    @AfterAll
    public static void tearDown() {
        postgresDB.stop();
    }

    @Test
    void givenAValidCreateProductCommand_whenCallsCreateProduct_thenShouldReturnIdCreated() throws Exception {

        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        ObjectMapper objectMapper = new ObjectMapper();
        CreateProductCommand command = new CreateProductCommand(expectedName, expectedDescription, expectedCategory, expectedPrice);

        String json = objectMapper.writeValueAsString(command);

        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void givenAValidCreateProductCommandWithDuplicateName_whenCallsCreateProduct_thenShouldReturnIdCreated() throws Exception {

        final var expectedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        ObjectMapper objectMapper = new ObjectMapper();
        CreateProductCommand command = new CreateProductCommand(expectedName, expectedDescription, expectedCategory, expectedPrice);

        String json = objectMapper.writeValueAsString(command);

        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[\"product.name.already.exists\"]"));

    }

    @Test
    void givenAValidUpdateProductCommand_whenCallsUpdateProduct_thenShouldReturnIdUpdated() throws Exception {

        final var expectedName = UUID.randomUUID().toString();
        final var updatedName = UUID.randomUUID().toString();
        final var expectedDescription = UUID.randomUUID().toString();
        final var expectedCategory = UUID.randomUUID().toString();
        final var expectedPrice = 10.0;

        ObjectMapper objectMapper = new ObjectMapper();
        CreateProductCommand command = new CreateProductCommand(expectedName, expectedDescription, expectedCategory, expectedPrice);

        String json = objectMapper.writeValueAsString(command);

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        String id = result.getResponse().getContentAsString();

        // Usa o ID para a requisição de atualização
        UpdateProductCommand updateCommand = new UpdateProductCommand(id, updatedName, expectedDescription, expectedCategory, expectedPrice);
        String updateJson = objectMapper.writeValueAsString(updateCommand);

        mockMvc.perform(MockMvcRequestBuilders.put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk());

        var productJson = mockMvc.perform(MockMvcRequestBuilders.get("/product/" + id)
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();


        GetProductOutput productOutput = objectMapper.readValue(productJson, GetProductOutput.class);

        assertEquals(productOutput.getId(), id);
        assertEquals(productOutput.getName(), updatedName);
        assertEquals(productOutput.getDescription(), expectedDescription);
        assertEquals(productOutput.getCategory(), expectedCategory);
        assertEquals(productOutput.getPrice(), expectedPrice);
    }

    @Test
    void givenAValidRequest_whenCallsFindAllPerPage_thenShouldTenFirstResults() throws Exception {

        for(int i = 0; i < 50; i++) {
            final var expectedName = UUID.randomUUID().toString();
            final var expectedDescription = UUID.randomUUID().toString();
            final var expectedCategory = UUID.randomUUID().toString();
            final var expectedPrice = 10.0;

            ObjectMapper objectMapper = new ObjectMapper();
            CreateProductCommand command = new CreateProductCommand(expectedName, expectedDescription, expectedCategory, expectedPrice);

            String json = objectMapper.writeValueAsString(command);

            mockMvc.perform(MockMvcRequestBuilders.post("/product")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated())
                    .andReturn();
        }

        final var response = mockMvc.perform(MockMvcRequestBuilders.get("/product")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "name")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        GetAllProductOutput output = objectMapper.readValue(response, GetAllProductOutput.class);

        assertEquals(10, output.getProducts().size());
        assertEquals(50, output.getTotal());

    }

    @Test
    void givenAValidFiltredRequest_whenCallsFindAllPerPage_thenShouldTenFirstResults() throws Exception {

        for(int i = 0; i < 50; i++) {
            final var expectedName = "Product " + i;
            final var expectedDescription = UUID.randomUUID().toString();
            final var expectedCategory = UUID.randomUUID().toString();
            final var expectedPrice = 10.0;

            ObjectMapper objectMapper = new ObjectMapper();
            CreateProductCommand command = new CreateProductCommand(expectedName, expectedDescription, expectedCategory, expectedPrice);

            String json = objectMapper.writeValueAsString(command);

            mockMvc.perform(MockMvcRequestBuilders.post("/product")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated())
                    .andReturn();
        }

        final var response = mockMvc.perform(MockMvcRequestBuilders.get("/product")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "name")
                        .param("filter", "15")
                        .param("order", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        GetAllProductOutput output = objectMapper.readValue(response, GetAllProductOutput.class);

        assertEquals(1, output.getProducts().size());
        assertEquals(1, output.getTotal());
        assertEquals("Product 15", output.getProducts().get(0).getName());

    }
}
