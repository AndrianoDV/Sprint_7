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

public class CourierTest {
    private String loginCourier;
    private String passwordCourier;
    private String firstNameCourier;
    private CourierModel courierModel;
    private final CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUp() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);
        firstNameCourier = randomAlphabetic(8);
        courierModel = new CourierModel(loginCourier, passwordCourier, firstNameCourier);
    }

    @Test
    @DisplayName("Создание курьера с заполнением всех полей")
    @Description("Проверка успешного создания курьера при заполнении всех полей")
    public void createCourierWithAllFieldsTest() {
        courierSteps.createCourier(courierModel)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Создание курьера только с заполнением обязательных полей")
    @Description("Проверка успешного создания курьера при заполнении только обязательных полей (логин и пароль)")
    public void createCourierWithRequiredFieldsOnlyTest() {
        CourierModel requiredFieldsOnly = new CourierModel(loginCourier, passwordCourier, "");
        courierSteps.createCourier(requiredFieldsOnly)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Невозможность создания курьера без логина")
    @Description("Проверка ошибки при попытке создания курьера без указания логина")
    public void createCourierWithoutLoginTest() {
        CourierModel noLoginCourier = new CourierModel("", passwordCourier, firstNameCourier);
        courierSteps.createCourier(noLoginCourier)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Невозможность создания курьера без пароля")
    @Description("Проверка ошибки при попытке создания курьера без указания пароля")
    public void createCourierWithoutPasswordTest() {
        CourierModel noPasswordCourier = new CourierModel(loginCourier, "", firstNameCourier);
        courierSteps.createCourier(noPasswordCourier)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Невозможность создания двух курьеров с одинаковым логином")
    @Description("Проверка ошибки при попытке создания дубликата курьера с существующим логином")
    public void createDuplicateCourierTest() {
        // First creation - should succeed
        courierSteps.createCourier(courierModel)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));

        // Attempt to create duplicate - should fail
        courierSteps.createCourier(courierModel)
                .then()
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", is("Этот логин уже используется"));
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