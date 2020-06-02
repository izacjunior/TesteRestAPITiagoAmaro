package test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestApiTheCat extends massaDados {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://api.thecatapi.com/v1/";
	}

	@Test
	public void cadastro() {

		Response response = given().contentType(ContentType.JSON).body(bodyCadastro).when().post(urlCadastro);

		response.then().log().all().contentType(ContentType.JSON).statusCode(200).body("message",
				containsString("SUCCESS"));

		validacao(response);
		;

	}

	@Test
	public void realizarVotacao() {

		Response response = given().contentType(ContentType.JSON).body(corpoVotacao)

				.when().post("votes/");

		response.then().contentType(ContentType.JSON).statusCode(200).body("message", containsString("SUCCESS"))
				.statusCode(200);

		validacao(response);

		;
	}

	@Test
	public void deletarVotacao() {

		realizarVotacao();
		deletaVoto();

		;
	}

	private void deletaVoto() {
		String url = "votes/{vote_id}";

		Response response = given().contentType(ContentType.JSON)
				.header("x-api-key", "1cfff90e-3607-4cd9-bbfe-598c08bc9c80")
				.pathParam("vote_id", voto_id)

				.when().delete(url);

		response.then().contentType(ContentType.JSON).statusCode(200).body("messagem", containsString("Sucesso"))
				.statusCode(200);

	}

	@Test
	public void favoritosImagem() {
		favoritar();
		desFavoritar();

	}

	private void desFavoritar() {

		Response response = given().contentType(ContentType.JSON)
				.header("x-api-key", "1cfff90e-3607-4cd9-bbfe-598c08bc9c80").pathParam("favorite_id", voto_id)

				.when().delete("favorites/{favorites_id}");

		validacao(response);

	}

	private void favoritar() {

		Response response = given().contentType(ContentType.JSON)
				.header("x-api-key", "1cfff90e-3607-4cd9-bbfe-598c08bc9c80").body(corpoFavorito)

				.when().post("favorites");
		response.then().log().all();

		voto_id = "2021240";
		validacao(response);

	}

	public void validacao(Response response) {

		response.then().contentType(ContentType.JSON).statusCode(200).body("message", containsString("SUCCESS"));

		System.out.println("RETORNO API" + response.body().asString());
		System.out.println("------------------------------------------");
	}

}
