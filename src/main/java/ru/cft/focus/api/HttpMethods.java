package ru.cft.focus.api;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.cft.focus.model.Credentials;

import static io.restassured.RestAssured.given;

class HttpMethods {

    private final String baseUrl;
    private final Credentials credentials;

    HttpMethods(String baseUrl, Credentials credentials) {
        this.baseUrl = baseUrl;
        this.credentials = credentials;
    }

    private RequestSpecification prepareRequest() {
        return given()
                .auth()
                .preemptive()
                .basic(credentials.user, credentials.password)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .baseUri(baseUrl);
    }

    private RequestSpecification prepareRequest(Object bodyData) {
        return prepareRequest()
                .contentType(ContentType.JSON)
                .body(bodyData);
    }

    Response delete(String url) {
        return prepareRequest().
                when().
                delete(url);
    }

    Response get(String url) {
        return prepareRequest().
                when().
                get(url);
    }

    Response post(String url, Object bodyData) {
        return prepareRequest(bodyData).
                when().
                post(url);
    }

    Response put(String url, Object bodyData) {
        return prepareRequest(bodyData).
                when().
                put(url);
    }

    Response patch(String url, Object bodyData) {
        return prepareRequest(bodyData).
                when().
                patch(url);
    }
}
