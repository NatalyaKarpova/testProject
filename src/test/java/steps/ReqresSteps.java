package steps;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class ReqresSteps {

    private static RequestSpecification reqresSpecification = new RequestSpecBuilder().setBaseUri("https://reqres.in").setContentType("application/json").build();

    @Step("Формируем тело запроса из файла '{jsonName}'")
    public String getReqresRequestBody(String jsonName) {
        String body;
        try {
            body = new String(Files.readAllBytes(Paths.get("src/test/resources/reqres/" + jsonName)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return body;
    }

    @Step("Выполняем запрос на создание клиента")
    public String postClient(String client) {
        Response response = given()
                .spec(reqresSpecification)
                .body(client)
                .when()
                .log().all()
                .post("/api/users")
                .then()
                .log().all()
                .extract()
                .response();
        Assert.assertEquals("Ошибка статус-кода", 201, response.getStatusCode());
        return response.getBody().asString();
    }

    @Step("Выполняем запрос на обновление клиента")
    public String putClient(String client, String clientId) {
        Response response = given()
                .spec(reqresSpecification)
                .body(client)
                .when()
                .log().all()
                .put("/api/users/" + clientId)
                .then()
                .log().all()
                .extract()
                .response();
        Assert.assertEquals("Ошибка статус-кода", 200, response.getStatusCode());
        return response.getBody().asString();
    }
}
