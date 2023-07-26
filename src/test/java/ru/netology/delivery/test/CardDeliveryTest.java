package ru.netology.delivery.test;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id=success-notification] button").click();
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(secondMeetingDate);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification]").shouldHave((text("Необходимо подтверждение")));
        $("[data-test-id=replan-notification]").shouldHave((text("У вас уже запланирована встреча на другую дату. Перепланировать?")));
        $$("[data-test-id=replan-notification] button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Успешно!"));
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}