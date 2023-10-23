package steps;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;

public class RickAndMortySteps {
    private final RequestSpecification rickSpecification = new RequestSpecBuilder().setBaseUri("https://rickandmortyapi.com/api").build();

    @Step("Получаем тело запроса для заданного персонажа")
    public Response getCharacterResponse(int characterId) {
        return given()
                .spec(rickSpecification)
                .when()
                .log().all()
                .get("/character/" + characterId)
                .then()
                .log().all()
                .extract()
                .response();
    }

    @Step("Получаем id последнего персонажа из последнего эпизода")
    public int getLastCharacterId(int characterId) {
        int lastEpisodeId = getCharacterResponse(characterId).jsonPath().getList("episode").size() - 1;
        Response lastEpisodeResponse = given()
                .spec(rickSpecification)
                .when()
                .log().all()
                .get("/episode/" + lastEpisodeId)
                .then()
                .log().all()
                .extract()
                .response();

        return lastEpisodeResponse.jsonPath().getList("characters").size() - 1;
    }
}