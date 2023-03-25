package ru.netology;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import lombok.experimental.var;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;
import ru.netology.data.UserInfo;

import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class CardDeliveryTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void testHappyPathOrderMeeting() {
        int daysToAddFirstMeeting = 3;
        String firstMeetingDate = DataGenerator.generateDate(daysToAddFirstMeeting);
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").val(DataGenerator.generateCity());
        form.$("[data-test-id='date'] input").sendKeys(firstMeetingDate);
        form.$("[data-test-id='name'] input").val(DataGenerator.generateName());
        form.$("[data-test-id='phone'] input").val(DataGenerator.generatePhone());
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        $("[data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на "
                + firstMeetingDate));
    }
    @Test
    void testHappyPathReOrderMeeting() {
        int daysToAddFirstMeeting = 3;
        int daysToAddSecondMeeting = 7;
        String firstMeetingDate = DataGenerator.generateDate(daysToAddFirstMeeting);
        String secondMeetingDate = DataGenerator.generateDate(daysToAddSecondMeeting);
        SelenideElement form = $("[action='/']");
        form.$("[data-test-id='city'] input").val(DataGenerator.generateCity());
        form.$("[data-test-id='date'] input").sendKeys(firstMeetingDate);
        form.$("[data-test-id='name'] input").val(DataGenerator.generateName());
        form.$("[data-test-id='phone'] input").val(DataGenerator.generatePhone());
        form.$("[data-test-id='agreement']").click();
        form.$(".button__content").click();
        $("[data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на "
                + firstMeetingDate));
        $(".button").click();
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(secondMeetingDate);
        $("[data-test-id='replan-notification']").shouldHave(text("У вас уже запланирована встреча на другую дату." +
                " Перепланировать?"));
        $(byText("Перепланировать")).click();
        $("[data-test-id='success-notification']").shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}