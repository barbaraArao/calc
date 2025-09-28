package org.altice;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class CalcIntegrationTests {

    @Test
    void testValidSmallValue() {
        given()
                .when().get("/calc/5")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("value", notNullValue())
                .body("digits", greaterThan(0));
    }

    @Test
    void testZeroReturnsBadRequest() {
        given()
                .when().get("/calc/0")
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("message", containsString("can not be 0 or lower"));
    }

    @Test
    void testNegativeReturnsBadRequest() {
        given()
                .when().get("/calc/-10")
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("message", containsString("can not be 0 or lower"));
    }

    @Test
    void testAboveLimitReturnsBadRequest() {
        given()
                .when().get("/calc/2000001")
                .then()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("message", containsString("maximum allowed"));
    }

    @Test
    void testLargeValidValue() {
        given()
                .when().get("/calc/100000")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("digits", greaterThan(1000));
    }
}
