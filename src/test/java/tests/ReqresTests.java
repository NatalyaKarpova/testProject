package tests;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import steps.ReqresSteps;

@Epic("Api тесты")
@Feature("Reqres тесты")
@Story("POST client")
public class ReqresTests {
    private ReqresSteps reqresSteps;

    @BeforeClass(description = "Добавляем фильтр Allure для RestAssured", alwaysRun = true)
    void addFilters() {
        RestAssured.filters(new AllureRestAssured());
    }

    @BeforeClass(alwaysRun = true)
    void beforeClass() {
        reqresSteps = new ReqresSteps();
    }

    @Test(description = "Создание и обновление клиента")
    void successCreateClientTest() {
        String clientRequestBody = reqresSteps.getReqresRequestBody("client.json");
        String clientResponseBodyCreate = reqresSteps.postClient(clientRequestBody);

        String expectedClientNameCreate = new JSONObject(clientRequestBody).get("name").toString();

        String actualClientName = new JSONObject(clientResponseBodyCreate).get("name").toString();
        String clientId = new JSONObject(clientResponseBodyCreate).get("id").toString();

        String clientRequestUpdate = reqresSteps.getReqresRequestBody("update_client.json");
        String clientResponseUpdate = reqresSteps.putClient(clientRequestUpdate, clientId);

        String expectedClientNameUpdate = new JSONObject(clientRequestUpdate).get("name").toString();
        String expectedClientJobUpdate = new JSONObject(clientRequestUpdate).get("job").toString();

        String actualClientNameUpdate = new JSONObject(clientResponseUpdate).get("name").toString();
        String actualClientJobUpdate = new JSONObject(clientResponseUpdate).get("job").toString();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualClientName, expectedClientNameCreate, "Поле 'name' сохранилось неверно");
        softAssert.assertNotNull(clientId, "id клиента равен null");
        softAssert.assertEquals(actualClientNameUpdate, expectedClientNameUpdate, "Поле 'name' сохранилось неверно");
        softAssert.assertEquals(actualClientJobUpdate, expectedClientJobUpdate, "Поле 'job' сохранилась неверно");

        softAssert.assertAll();
    }
}
