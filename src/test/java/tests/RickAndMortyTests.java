package tests;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.RickAndMortySteps;

@Epic("Api тесты")
@Feature("RickAndMorty тесты")
@Story("POST client")
public class RickAndMortyTests {
    private RickAndMortySteps rickAndMortySteps;

    @BeforeClass(description = "Добавляем фильтр Allure для RestAssured", alwaysRun = true)
    void addFilters() {
        RestAssured.filters(new AllureRestAssured());
    }

    @BeforeClass(alwaysRun = true)
    void beforeClass() {
        rickAndMortySteps = new RickAndMortySteps();
    }

    @Test(description = "Успешное получение эпизодов для Морти")
    void successGetMortyEpisodesTest() {
        Response mortyResponse = rickAndMortySteps.getCharacterResponse(2);
        String mortyLocation = mortyResponse.jsonPath().getString("location.name");
        String mortySpecies = mortyResponse.jsonPath().getString("spesies");


        int lastCharacterId = rickAndMortySteps.getLastCharacterId(2);
        Response lastCharacterResponse = rickAndMortySteps.getCharacterResponse(lastCharacterId);
        String lastCharacterLocation = lastCharacterResponse.jsonPath().getString("location.name");
        String lastCharacterSpecies = lastCharacterResponse.jsonPath().getString("species");


        Assert.assertEquals(lastCharacterLocation, mortyLocation, "Локация не совпадает");
        Assert.assertEquals(lastCharacterSpecies, mortySpecies, "Раса не совпадает");
    }
}