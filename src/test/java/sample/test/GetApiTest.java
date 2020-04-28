package sample.test;

import static io.restassured.RestAssured.given;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetApiTest extends TestBase {

	private static final String BASE_URL = "https://apiproxy.paytm.com/";
	private static final String ENDPOINT = "v2/movies/upcoming";
	private static final String RESPONSE = "Response";
	private static final String MOVIE_NAME_WITH_IS_CONTENT_AVAILABLE_0 = "Movie name with isContentAvailable = 0 ";
	private static final String UPCOMING_MOVIE_DATA_S_MOVIE_NAME = "upcomingMovieData[%s].movie_name";
	private static final String UPCOMING_MOVIE_DATA_IS_CONTENT_AVAILABLE = "upcomingMovieData.isContentAvailable";
	private static final String CODE_IS_NOT_UNIQUE = "code is not unique";
	private static final String UPCOMING_MOVIE_DATA_PAYTM_MOVIE_CODE = "upcomingMovieData.paytmMovieCode";
	private static final String UPCOMING_MOVIE_DATA_MOVIE_POSTER_URL = "upcomingMovieData.moviePosterUrl";

	@Test
	public void testGetCall() throws ParseException {
		RestAssured.baseURI = BASE_URL;
		Response getResponse = given().get(ENDPOINT);

		// Status code validation
		getResponse.then().statusCode(200);

		// Movie Poster URL : should only have .jpg format validation
		boolean flag = true;
		ArrayList<String> movieUrlList = getResponse.body().jsonPath().get(UPCOMING_MOVIE_DATA_MOVIE_POSTER_URL);
		for (String element : movieUrlList) {
			if (!element.contains(".jpg")) {
				flag = false;
				break;
			}
		}
		Assert.assertEquals(flag, true, "url doesnt contain .jpg");

		// Paytm movie code: is unique validation
		ArrayList<String> paytmMovieCode = getResponse.body().jsonPath().get(UPCOMING_MOVIE_DATA_PAYTM_MOVIE_CODE);
		Set<String> set = new HashSet<String>(paytmMovieCode);
		boolean unique = true;
		if (set.size() < paytmMovieCode.size()) {
			unique = false;
		}
		Assert.assertEquals(unique, true, CODE_IS_NOT_UNIQUE);
		logResponse(getResponse);

		// Analyze and print isContentAvailable = 0 movies
		ArrayList<Integer> isContentAvailable = getResponse.body().jsonPath()
				.get(UPCOMING_MOVIE_DATA_IS_CONTENT_AVAILABLE);
		for (Integer content : isContentAvailable) {
			if (content == 0) {
				String formattedJsonPath = String.format(UPCOMING_MOVIE_DATA_S_MOVIE_NAME,
						isContentAvailable.indexOf(content));
				Allure.addAttachment(MOVIE_NAME_WITH_IS_CONTENT_AVAILABLE_0,
						getResponse.body().jsonPath().get(formattedJsonPath).toString());

			}

		}

	}

	private static void logResponse(Response logResponse) {
		Allure.addAttachment(RESPONSE, logResponse.body().prettyPrint());
	}

}
