package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitForm() {
        $("[data-test-id=city] input").setValue("Челябинск");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A" + Keys.DELETE);
        LocalDate reservation = LocalDate.now().plusDays(3);
        String dayVisit = reservation.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(dayVisit);
        $("[data-test-id=name] input").setValue("Андреев Алексей");
        $("[data-test-id=phone] input").setValue("+79525140000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestFormCityNotCorrect() {
        $("[data-test-id=city] input").setValue("Moscow");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A" + Keys.DELETE);
        LocalDate reservation = LocalDate.now().plusDays(3);
        String dayVisit = reservation.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(dayVisit);
        $("[data-test-id=name] input").setValue("Андреев Алексей");
        $("[data-test-id=phone] input").setValue("+79525140000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestFormNameHyphen() {
        $("[data-test-id=city] input").setValue("Челябинск");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A" + Keys.DELETE);
        LocalDate reservation = LocalDate.now().plusDays(3);
        String dayVisit = reservation.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(dayVisit);
        $("[data-test-id=name] input").setValue("Андреев-Алексей");
        $("[data-test-id=phone] input").setValue("+79525140000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(12000));
        $("[data-test-id=notification] .notification__content")
                .shouldHave(exactText("Встреча успешно забронирована на " + dayVisit));

    }

    @Test
    void shouldTestFormNameNotCorrect() {
        $("[data-test-id=city] input").setValue("Челябинск");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A" + Keys.DELETE);
        LocalDate reservation = LocalDate.now().plusDays(3);
        String dayVisit = reservation.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(dayVisit);
        $("[data-test-id=name] input").setValue("Andreev Alexey");
        $("[data-test-id=phone] input").setValue("+79525140000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestFormNameEmpty() {
        $("[data-test-id=city] input").setValue("Челябинск");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A" + Keys.DELETE);
        LocalDate reservation = LocalDate.now().plusDays(3);
        String dayVisit = reservation.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(dayVisit);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79525140000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestFormPhoneIncorrectly() {
        $("[data-test-id=city] input").setValue("Челябинск");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A" + Keys.DELETE);
        LocalDate reservation = LocalDate.now().plusDays(3);
        String dayVisit = reservation.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(dayVisit);
        $("[data-test-id=name] input").setValue("Андреев Алексей");
        $("[data-test-id=phone] input").setValue("+78910");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestFormAgreementEmpty() {
        $("[data-test-id=city] input").setValue("Челябинск");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A" + Keys.DELETE);
        LocalDate reservation = LocalDate.now().plusDays(3);
        String dayVisit = reservation.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(dayVisit);
        $("[data-test-id=name] input").setValue("Андреев Алексей");
        $("[data-test-id=phone] input").setValue("+79525140000");
        $("button.button").click();
        $("[data-test-id=agreement].input_invalid .checkbox__text")
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldTestFormCityAndNameAndPhoneEmpty() {
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }
}





