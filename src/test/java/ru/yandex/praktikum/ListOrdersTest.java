package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.praktikum.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOrdersTest {
    private final OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("Запрос на получение списка заказов")
    @Description("Проверка успешного получения списка заказов с валидными данными")
    public void getListOrdersTest() {
        orderSteps.getOrdersList()
                .statusCode(HttpStatus.SC_OK)
                .body("orders", notNullValue());
    }
}