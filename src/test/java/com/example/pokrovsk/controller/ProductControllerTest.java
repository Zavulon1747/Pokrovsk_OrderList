package com.example.pokrovsk.controller;

import com.example.pokrovsk.entity.Product;
import com.example.pokrovsk.repo.ProductRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    WebApplicationContext webApplicationContext;
    MockMvc mvc;

    @Autowired
    private ProductController productController;

    @MockBean
    ProductRepo productRepo;

    @Autowired
    TestRestTemplate restTemplate;

    public List<Product> list;

    @Test
    void contextLoads() {
        assertNotNull(productController);
    }

    @BeforeEach
    void init() {
        list = Stream.of(
                new Product("???????????? ????????", 5, "??????????????", 99),
                new Product("?????????? ??????????????????????????", 5, "??????????????", 99),
                new Product("????????????", 5, "??????????????", 99),
                new Product("????????????", 5, "????????????????????????", 99),
                new Product("???????????????? ?????? ?????????? ??????????", 5, "??????????", 99)
        ).collect(Collectors.toList());

        when(productRepo.findAll()).thenReturn(list);
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAllProducts() {
        assertEquals(5, productController.getAllProducts().size());
    }

    @Test
    void getAllProductsByDepartment() {
        assertEquals(3, productController.getAllProductsByDepartment("??????????").size());
        assertEquals(1, productController.getAllProductsByDepartment("????????????").size());
        assertEquals(1, productController.getAllProductsByDepartment("??????").size());
    }

    @Test
    void getProductByName() {
        assertEquals("???????????? ????????", productController.getProductByName("??????").get(0).getName());
        assertEquals("?????????? ??????????????????????????", productController.getProductByName("????????").get(0).getName());
        assertEquals("???????????????? ?????? ?????????? ??????????", productController.getProductByName("????????").get(0).getName());
        assertEquals("?????????? ??????????????????????????", productController.getProductByName("????????").get(1).getName());
    }

    @Test
    void addProduct() throws Exception {
        Product product = new Product("????????????????", 5, "??????????", 99);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/products/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                        .andExpect(status().isOk());
    }

    @Test
    void updateProductForCancel() {
        Product product = productRepo.findAll().get(0);
        product.setNeedCancel(true);
        assertTrue(product.isNeedCancel());
    }

    @Test
    void deleteProduct() {
        Product product = productRepo.findAll().get(0);
        product.setRemoved(true);
        assertTrue(product.isRemoved());
    }
}