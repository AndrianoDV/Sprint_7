package ru.yandex.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

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
        String requestBody = buildOrderRequestBody(colors);
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(requestBody)
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

    private String buildOrderRequestBody(String[] colors) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"firstName\": \"Андрей\",");
        sb.append("\"lastName\": \"Дементьев\",");
        sb.append("\"address\": \"Пенза, ул. Пушкина, д. 12\",");
        sb.append("\"metroStation\": 4,");
        sb.append("\"phone\": \"+7 900 123 45 67\",");
        sb.append("\"rentTime\": 5,");
        sb.append("\"deliveryDate\": \"2025-11-31\",");
        sb.append("\"comment\": \"Тестовый заказ\",");

        if (colors != null && colors.length > 0) {
            sb.append("\"color\": [");
            for (int i = 0; i < colors.length; i++) {
                sb.append("\"").append(colors[i]).append("\"");
                if (i < colors.length - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");
        }
        sb.append("}");

        return sb.toString();
    }
}