package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.model.CourierModel;
import ru.yandex.praktikum.steps.CourierSteps;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {
    private String loginCourier;
    private String passwordCourier;
    private CourierModel courierModel;
    private final CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUp() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);
        courierModel = new CourierModel(loginCourier, passwordCourier, "");
        courierSteps.createCourier(courierModel)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Успешная авторизация курьером")
    @Description("Проверка успешной авторизации курьера с валидными данными")
    public void courierLoginSuccessTest() {
        courierSteps.loginCourier(courierModel)
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Попытка авторизации с несуществующим логином")
    @Description("Проверка ошибки при авторизации с несуществующим логином")
    public void courierLoginWithWrongLoginTest() {
        CourierModel wrongLoginCourier = new CourierModel(randomAlphabetic(12), passwordCourier, "");
        courierSteps.loginCourier(wrongLoginCourier)
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Попытка авторизации с неверным паролем")
    @Description("Проверка ошибки при авторизации с неверным паролем")
    public void courierLoginWithWrongPasswordTest() {
        CourierModel wrongPasswordCourier = new CourierModel(loginCourier, randomAlphabetic(10), "");
        courierSteps.loginCourier(wrongPasswordCourier)
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Попытка авторизации без логина")
    @Description("Проверка ошибки при авторизации без указания логина")
    public void courierLoginWithoutLoginTest() {
        CourierModel noLoginCourier = new CourierModel("", passwordCourier, "");
        courierSteps.loginCourier(noLoginCourier)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Попытка авторизации без пароля")
    @Description("Проверка ошибки при авторизации без указания пароля")
    public void courierLoginWithoutPasswordTest() {
        CourierModel noPasswordCourier = new CourierModel(loginCourier, "", "");
        courierSteps.loginCourier(noPasswordCourier)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @After
    public void tearDown() {
        Integer idCourier = courierSteps.loginCourier(courierModel)
                .extract()
                .body()
                .path("id");
        if (idCourier != null) {
            courierSteps.deleteCourier(idCourier.toString());
        }
    }
}