package sample.test;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HttpRequestSender {
	private static Map<String, String> defaultHeaders = null;
	final static Logger LOG = Logger.getLogger(HttpRequestSender.class);
	static RequestSpecification requestSpec = new RequestSpecBuilder().build();

	static {
	    BasicConfigurator.configure();
		defaultHeaders = new HashMap();
		defaultHeaders.put("content-type", "application/json");
		requestSpec.baseUri(RestAssured.baseURI);
	}


	public static Response executeGet(String call, Headers headers, Map<String, Object> params, BasicAuthScheme auth) {
		logRequestURL("GET", call);
		Response getResponse = given().get(call);
		logResponse(getResponse);
		LOG.info("---------------------------- end Of GET ----------------");
		return getResponse;
	}


	private static void logResponse(Response logResponse) {
		Allure.addAttachment("Response", logResponse.body().prettyPrint());
	}

	private static void logRequestURL(String type, String call) {
		LOG.info("----------------- Start Of " + type + " ----------------");
		LOG.info(String.format("Making " + type + " request to: %s", call));
	}

}
