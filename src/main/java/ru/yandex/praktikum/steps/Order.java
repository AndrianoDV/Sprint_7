package ru.yandex.praktikum.steps;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Data
@AllArgsConstructor
public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public Order(String[] color) {
        this.firstName = randomAlphabetic(15);
        this.lastName = randomAlphabetic(20);
        this.address = randomAlphabetic(100);
        this.metroStation = randomAlphabetic(16);
        this.phone = randomAlphabetic(10);
        this.rentTime = (int) (1 + Math.random()) * 365;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.deliveryDate = format.format(new Date()).toString();
        this.comment = randomAlphabetic(150);
        this.color = color;
    }

    public Order() {
    }
}