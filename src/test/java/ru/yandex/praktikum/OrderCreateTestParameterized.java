package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.steps.OrderSteps;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTestParameterized {
    private final OrderSteps orderSteps = new OrderSteps();
    private Integer trackNumber;
    private final String[] color;

    @Parameterized.Parameters(name = "Color options: {0}")
    public static Object[][] colorData() {
        return new Object[][]{
                {new String[]{"BLACK"}, "Black color"},
                {new String[]{"GREY"}, "Grey color"},
                {new String[]{"BLACK", "GREY"}, "Both colors"},
                {new String[]{}, "No color specified"}
        };
    }

    public OrderCreateTestParameterized(String[] color, String testName) {
        this.color = color;
    }

    @Test
    @DisplayName("Параметризованный тест создания заказа")
    @Description("Проверка создания заказа с различными вариантами цветов")
    public void createOrderWithDifferentColorsTest() {
        trackNumber = orderSteps.createOrder(color)
                //.then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", notNullValue())
                .extract()
                .path("track");
    }

    @After
    public void tearDown() {
        if (trackNumber != null) {
            orderSteps.cancelOrder(trackNumber);
        }
    }
}