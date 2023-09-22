package steps;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;

public class RickAndMortySteps {
    private final RequestSpecification rickSpecification = new RequestSpecBuilder().setBaseUri("https://rickandmortyapi.com/api").build();

    public String getCharacterLocation(int characterId) {
        Response response = given()
                .spec(rickSpecification)
                .when()
                .log().all()
                .get("/character/" + characterId)
                .then()
                .log().all()
                .extract()
                .response();
        int lastEpisodeId = response.jsonPath().getList("episode").size()-1;
        String lastEpisodeUrl = response.jsonPath().getString("episode[" + lastEpisodeId + "]");

        Response lastEpisodeResponse = given()
                .spec(rickSpecification)
                .when()
                .log().all()
                .get(lastEpisodeUrl)
                .then()
                .log().all()
                .extract()
                .response();

        String lastCharacter = lastEpisodeResponse.jsonPath().getString("characters[-1]");

        Response lastCharacterEpisode = given()
                .spec(rickSpecification)
                .when()
                .log().all()
                .get(lastCharacter)
                .then()
                .log().all()
                .extract()
                .response();

        return lastCharacterEpisode.jsonPath().getString("location.name") ;
    }
    public String getCharacterSpecies(int characterId) {
        Response episodeResponse = given()
                .spec(rickSpecification)
                .when()
                .log().all()
                .get("/character/" + characterId)
                .then()
                .log().all()
                .extract()
                .response();

        int lastEpisodeId = episodeResponse.jsonPath().getList("episode").size() - 1;
        String lastEpisodeUrl = episodeResponse.jsonPath().getString("episode[" + lastEpisodeId + "]");

        Response lastEpisodeResponse = given()
                .spec(rickSpecification)
                .when()
                .log().all()
                .get(lastEpisodeUrl)
                .then()
                .log().all()
                .extract()
                .response();

        String lastCharacter = lastEpisodeResponse.jsonPath().getString("characters[-1]");
        Response lastCharacterEpisode = given()
                .spec(rickSpecification)
                .when()
                .log().all()
                .get(lastCharacter)
                .then()
                .log().all()
                .extract()
                .response();

        return lastCharacterEpisode.jsonPath().getString("species") ;

    }
}