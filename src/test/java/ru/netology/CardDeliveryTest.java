package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

class CardDeliveryTest {


    String meetingDay(int day) {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    String secondMeetingDay(int day) {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void testHappyPath() {
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").setValue("Нижний Новгород");
        form.$("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        form.$("[data-test-id='date'] input").setValue(meetingDay(3));
        form.$("[data-test-id='name'] input").setValue("Денис Кюстина");
        form.$("[data-test-id='phone'] input").setValue("+72743275118");
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        $("[data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на "
                + meetingDay(3)));
        $(".button").click();
        $("[data-test-id='replan-notification']").shouldHave(text("У вас уже запланирована встреча на другую дату." +
                " Перепланировать?"));
        $(byText("Перепланировать")).click();
        $("[data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на "
                + secondMeetingDay(3))); // здесь должен быть другой день?
    }
}