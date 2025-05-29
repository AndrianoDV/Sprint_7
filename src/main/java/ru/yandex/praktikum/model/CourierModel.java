package ru.yandex.praktikum.model;

import java.util.Objects;

public class CourierModel {
    private String login;
    private String password;
    private String firstName;

    public CourierModel() {

    }

    public CourierModel(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    // Getters and setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourierModel that = (CourierModel) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                Objects.equals(firstName, that.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, firstName);
    }

    @Override
    public String toString() {
        return "CourierModel{" +
                "login='" + login + '\'' +
                ", password='[PROTECTED]'" +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
