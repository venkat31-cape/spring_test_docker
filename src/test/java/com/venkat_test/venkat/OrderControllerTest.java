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
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Testcontainers
class OrderControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private OrderEntityRepository orderEntityRepository;

    @Container
    static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:latest"))
            .withExposedPorts(6379);

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
        registry.add("spring.redis.url", () -> String.format("redis://%s:%d", redis.getHost(), redis.getMappedPort(6379)));
    }

    @BeforeEach
    public void beforeEach() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderType(1L);
        orderEntity.setOrderName("order-1");
        orderEntity.setOrderTypeDes("FullFilled");
        orderEntityRepository.save(orderEntity);
    }

    @AfterEach
    public void afterEach() {
        orderEntityRepository.deleteAll();
    }

    @Test
    void testContainer() {
        try (GenericContainer<?> container = new GenericContainer<>(DockerImageName.parse("alpine:latest"))
                .withCommand("echo", "Hello World")) {
            container.start();
            System.out.println("Container started: " + container.getLogs());
        }
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
                .consumeWith(orderEntityResponse -> Assertions.assertNotNull(orderEntityResponse.getResponseBody().getId()));
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
                .consumeWith(response -> {
                    List<OrderEntity> list = response.getResponseBody();
                    Assertions.assertFalse(CollectionUtils.isEmpty(list));
                    Assertions.assertNotNull(list.get(0).getOrderTypeDes());
                    Assertions.assertEquals("FullFilled", list.get(0).getOrderTypeDes());
                });
    }
}
