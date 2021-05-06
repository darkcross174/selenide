package ru.netology;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @Test
    void shouldSubmitForm() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Челябинск");
        LocalDate today = LocalDate.now();
        String dayVisit = dayVisit (LocalDate.now(plusDays(3)));
        $("[data-test-id=date] input").setValue(dayVisit);
        $("[data-test-id=name] input").setValue("Андреев Алексей");
        $("[data-test-id=phone] input").setValue("+79525140000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }
}