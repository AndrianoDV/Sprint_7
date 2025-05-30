package ru.yandex.praktikum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Step;
import ru.yandex.praktikum.model.CourierModel;

import static io.restassured.RestAssured.given;

public class CourierSteps {
    public final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    public final String POST_CREATE = "/api/v1/courier";
    public final String POST_LOGIN = "/api/v1/courier/login";
    public final String DELETE_DELETE = "/api/v1/courier/:id";

    @Step("Create courier")
    public Response createCourier(CourierModel courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(courier)
                .when()
                .post(POST_CREATE);
    }

    @Step("Login courier")
    public ValidatableResponse loginCourier(CourierModel courier) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(courier)
                .when()
                .post(POST_LOGIN)
                .then();
    }

    @Step("Delete courier")
    public ValidatableResponse deleteCourier(String id) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .pathParam("id", id)
                .when()
                .delete(DELETE_DELETE + "{id}")
                .then();
    }
}