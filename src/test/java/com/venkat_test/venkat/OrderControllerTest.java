package com.venkat_test.venkat;

import com.venkat_test.venkat.entity.OrderEntity;
import com.venkat_test.venkat.repository.OrderEntityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.CollectionUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
class OrderControllerTest {

    static final PostgreSQLContainer MY_SQL_CONTAINER;

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private OrderEntityRepository orderEntityRepository;

    static {
        MY_SQL_CONTAINER = new PostgreSQLContainer(DockerImageName.parse("postgres"));
        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto",() -> "create");

    }

    @BeforeEach
    public void beforeEach(){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderType(1L);
        orderEntity.setOrderName("order-1");
        orderEntity.setOrderTypeDes("FullFilled");
        orderEntityRepository.save(orderEntity);
    }
    @AfterEach
    public void afterEach(){
        orderEntityRepository.deleteAll();
    }

    @Test
    void saveOrderEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderType(1L);
        orderEntity.setOrderName("order-2");
        webTestClient.post()
                .uri("/save")
                .bodyValue(orderEntity)
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(OrderEntity.class)
                .consumeWith(orderentity -> Assertions.assertNotNull(orderentity.getResponseBody().getId()));
    }

    @Test
    void getOrderEntity() {
        webTestClient.get()
                .uri("/allorders")
                .exchange()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(OrderEntity.class)
                .consumeWith(listOfObject ->{
                    List<OrderEntity> list  = listOfObject.getResponseBody();
                    Assertions.assertFalse(CollectionUtils.isEmpty(list));
                    Assertions.assertNotNull(list.getFirst().getOrderTypeDes());
                    Assertions.assertEquals("FullFilled", list.getFirst().getOrderTypeDes());
                });
    }
}