package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import ru.netology.info.UserInfo;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@UtilityClass
public class DataGenerator {
    private Faker faker = new Faker(new Locale("en"));
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

    static void setUpAll(UserInfo info) {
        given()
                .spec(requestSpec)
                .body(info)
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public UserInfo userActive() {
        UserInfo activeUser = new UserInfo(
                faker.name().username(),
                faker.internet().password(),
                "active");
                setUpAll(activeUser);
                return activeUser;
    }

    public UserInfo userBlocked() {
        UserInfo blockedUser = new UserInfo(
                faker.name().username(),
                faker.internet().password(),
                "blocked");
                setUpAll(blockedUser);
                return blockedUser;
    }

}
