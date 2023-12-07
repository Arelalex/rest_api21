package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class UserSpec {
    public static RequestSpecification createRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification createResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(201)
            .build();

    public static RequestSpecification updateRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification updateResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .build();

    public static RequestSpecification registerRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification registerResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(400)
            .build();

    public static RequestSpecification singleUserRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body();

    public static ResponseSpecification singleUserResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();

    public static RequestSpecification deleteUserRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body();

    public static ResponseSpecification deleteUserResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();

    public static RequestSpecification listUsersRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().headers()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification listUsersResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();
}
