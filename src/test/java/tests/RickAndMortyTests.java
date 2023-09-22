package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.RickAndMortySteps;

import java.util.List;

public class RickAndMortyTests {
    private RickAndMortySteps rickAndMortySteps;

    @BeforeClass(alwaysRun = true)
    void beforeClass() {
        rickAndMortySteps = new RickAndMortySteps();
    }

    @Test(description = "Успешное получение эпизодов для Морти")
    void successGetMortyEpisodesTest() {
        String location = rickAndMortySteps.getCharacterLocation(2);
        String species = rickAndMortySteps.getCharacterSpecies(2);
        Assert.assertEquals(location, "Citadel of Ricks","Локация не совпадает");
        Assert.assertEquals(species, "Human", "Раса не совпадает");
    }
}