package Bookstore;
import Booking.files.Payload;
import Bookstore.files.PayloadBookstore;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
public class BookstoreTest {

    @Test
    public void createUser() {
        RestAssured.baseURI = "https://demoqa.com";
        String response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(PayloadBookstore.createUser())
                        .when().post("/Account/v1/User")
                        .then().log().all().assertThat().statusCode(201)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + response);
        JsonPath js = new JsonPath(response);

        String userID = js.getString("userID");
        System.out.println("ID: " + userID);
    }

    @Test
    public void createToken() {
        RestAssured.baseURI = "https://demoqa.com";
        String response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(PayloadBookstore.createUser())
                        .when().post("/Account/v1/GenerateToken")
                        .then().log().all().assertThat().statusCode(200)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + response);
        JsonPath js = new JsonPath(response);

        String token = js.getString("token");
        System.out.println("token: " + token);

        String result = js.getString("result");
        System.out.println("result: " + result);

    }

    @Test
    public void authorization() {
        RestAssured.baseURI = "https://demoqa.com";
        String response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(PayloadBookstore.createUser())
                        .when().post("/Account/v1/Authorized")
                        .then().log().all().assertThat().statusCode(200)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + response);
        boolean result = Boolean.parseBoolean(response);

        if (result) {
            System.out.println("You have been authorized");
        } else System.out.println("You have not been authorized!!");
    }

    @Test
    public void getUser() {
        RestAssured.baseURI = "https://demoqa.com";
        String response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(PayloadBookstore.createUser2())
                        .when().post("/Account/v1/User")
                        .then().log().all().assertThat().statusCode(201)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + response);
        JsonPath js = new JsonPath(response);

        String userID = js.getString("userID");
        System.out.println("ID: " + userID);

        String createToken =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(PayloadBookstore.createUser2())
                        .when().post("/Account/v1/GenerateToken")
                        .then().log().all().assertThat().statusCode(200)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + createToken);
        JsonPath js2 = new JsonPath(createToken);

        String token = js2.getString("token");
        System.out.println("token: " + token);

        String authorize =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(PayloadBookstore.createUser2())
                        .when().post("/Account/v1/Authorized")
                        .then().log().all().assertThat().statusCode(200)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + authorize);
        boolean result = Boolean.parseBoolean(authorize);

        if (result) {
            System.out.println("You have been authorized");
        } else System.out.println("You have not been authorized!!");


        String response2 =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .when().get("/Account/v1/User/" + userID)
                        .then().log().all().assertThat().statusCode(200)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + response2);

    }


    @Test
    public void deleteUser() {
        RestAssured.baseURI = "https://demoqa.com";
        String response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(PayloadBookstore.createUser3())
                        .when().post("/Account/v1/User")
                        .then().log().all().assertThat().statusCode(201)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + response);
        JsonPath js = new JsonPath(response);

        String userID = js.getString("userID");
        System.out.println("ID: " + userID);

        String createToken =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(PayloadBookstore.createUser3())
                        .when().post("/Account/v1/GenerateToken")
                        .then().log().all().assertThat().statusCode(200)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + createToken);
        JsonPath js2 = new JsonPath(createToken);

        String token = js2.getString("token");
        System.out.println("token: " + token);

        String authorize =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .body(PayloadBookstore.createUser3())
                        .when().post("/Account/v1/Authorized")
                        .then().log().all().assertThat().statusCode(200)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + authorize);
        boolean result = Boolean.parseBoolean(authorize);

        if (result) {
            System.out.println("You have been authorized");
        } else System.out.println("You have not been authorized!!");


        String response2 =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .when().delete("/Account/v1/User/" + userID)
                        .then().log().all().assertThat().statusCode(204)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + response2);


        String assertGone =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .when().get("/Account/v1/User/" + userID)
                        .then().log().all().assertThat().statusCode(401)
                        .extract().response().asString();

        System.out.println("RESPONSE: " + response2);

    }

}
