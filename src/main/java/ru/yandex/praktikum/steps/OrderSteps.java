package ru.yandex.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.model.OrderModel;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    private final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private final String CREATE_ORDER = "/api/v1/orders";
    private final String CANCEL_ORDER = "/api/v1/orders/cancel";
    private final String GET_ORDERS = "/api/v1/orders";

    @Step("Получение списка заказов")
    public ValidatableResponse getOrdersList() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(GET_ORDERS)
                .then();
    }

    @Step("Создание заказа с цветами: {colors}")
    public ValidatableResponse createOrder(String[] colors) {
        OrderModel order = new OrderModel(
                "Андрей",
                "Дементьев",
                "Пенза, ул. Пушкина, д. 12",
                4,
                "+7 900 123 45 67",
                5,
                "2025-11-30",  // Fixed invalid date (November has 30 days)
                "Тестовый заказ",
                colors
        );

        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)  // Automatic serialization here
                .when()
                .post(CREATE_ORDER)
                .then();
    }

    @Step("Отмена заказа с трек-номером: {track}")
    public void cancelOrder(Integer track) {
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .queryParam("track", track)
                .when()
                .put(CANCEL_ORDER)
                .then()
                .statusCode(200);
    }
}