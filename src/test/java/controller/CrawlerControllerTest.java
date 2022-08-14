package controller;

import javax.ws.rs.core.Response.Status;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 *
 * @author SRamos
 */
@QuarkusTest
public class CrawlerControllerTest {

    private RequestSpecification given() {
        var config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 90000)
                        .setParam("http.socket.timeout", 90000));

        return RestAssured.given().config(config).contentType(ContentType.JSON);
    }

    @Test
    public void whenGetCrawler() {
        var result = given()
                .headers("review_star", "1")
                .when().get("/crawler")
                .then()
                .statusCode(Status.OK.getStatusCode())
                .extract().asString();

        Approvals.verify(result);
    }

}
