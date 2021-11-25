import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.info.UserInfo;

import java.util.Locale;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AuthTest {
    private DataGenerator data;
    private Faker faker = new Faker(new Locale("en"));

    @BeforeEach
    public void shouldOpenForm() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldShowActiveUser() {
        UserInfo activeUser = data.userActive();
        $("[data-test-id='login'] input").setValue(activeUser.getLogin());
        $("[data-test-id='password'] input").setValue(activeUser.getPassword());
        $$("button").find(Condition.text("Продолжить")).click();
        $(byText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldShowBlockedUser() {
        UserInfo blockedUser = data.userBlocked();
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $$("button").find(Condition.text("Продолжить")).click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldShowWrongLoginUser() {
        UserInfo activeUser = data.userActive();
        $("[data-test-id='login'] input").setValue(faker.name().username());
        $("[data-test-id='password'] input").setValue(activeUser.getPassword());
        $$("button").find(Condition.text("Продолжить")).click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    public void shouldShowWrongPasswordUser() {
        UserInfo activeUser = data.userActive();
        $("[data-test-id='login'] input").setValue(activeUser.getLogin());
        $("[data-test-id='password'] input").setValue(faker.internet().password());
        $$("button").find(Condition.text("Продолжить")).click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
}

