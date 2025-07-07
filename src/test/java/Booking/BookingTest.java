package Booking;

import Booking.files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BookingTest {

    @Test
    public void healthCheck (){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        given().log().all().when().get("/ping")
                .then().log().all().assertThat().statusCode(201);



    }

    @Test
    public void getBookingIds (){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        given().log().all().when().get("/booking")
                .then().log().all().assertThat().statusCode(200);

    }

    @Test
    public void createBooking (){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    String response =
        given().log().all()
                .header("Content-Type","application/json")
                .body(Payload.bookingBody1())
                .when().post("/booking")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        System.out.println("RESPONSE: " + response);
        JsonPath js = new JsonPath(response);

        String bookingId = js.getString("bookingid");
        System.out.println("ID: " + bookingId);
    }


    @Test
    public void GetBooking (){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String response =
                given().log().all()
                        .header("Content-Type","application/json")
                        .body(Payload.bookingBody1())
                        .when().post("/booking")
                        .then().log().all().assertThat().statusCode(200)
                        .extract().response().asString();
        System.out.println("RESPONSE: " + response);
        JsonPath js = new JsonPath(response);

        String bookingId = js.getString("bookingid");
        System.out.println("ID: " + bookingId);
        Assert.assertTrue(response.contains(bookingId));
String responseAfterGetMethod = given().log().all().when().get("/booking/"+ bookingId)
        .then().log().all().assertThat().statusCode(200).extract().response().asString();

JsonPath js2 = new JsonPath(responseAfterGetMethod);
Assert.assertFalse(js2.getString("firstname").isBlank());
        Assert.assertFalse(js2.getString("lastname").isBlank());
        Assert.assertFalse(js2.getString("totalprice").isBlank());
        Assert.assertFalse(js2.getString("depositpaid").isBlank());
}

    @Test
    public void updateBooking () {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // 1.  booking
        String responseCreate = given().log().all()
                .header("Content-Type", "application/json")
                .body(Payload.bookingBody1())
                .when().post("/booking")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = new JsonPath(responseCreate);
        String bookingId = js.getString("bookingid");

        // 2. Update booking (koristi PUT )
        String responseUpdate = given().log().all()
                .auth().preemptive().basic("admin","password123")
                .header("Content-Type", "application/json")
                .body(Payload.bookingBody2())
                .when().put("/booking/" + bookingId)   // <--- UPDATE PUT sa ID
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js2 = new JsonPath(responseUpdate);

        // 3. Provera da li je update uspeo assert
        Assert.assertEquals(js2.getString("lastname"), "Izmenjic");
    }

@Test
    public void deleteBooking () {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String responseCreate = given().log().all()
                .header("Content-Type", "application/json")
                .body(Payload.bookingBody1())
                .when().post("/booking")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = new JsonPath(responseCreate);
        String bookingId = js.getString("bookingid");


//brisanje
        given().log().all()
                .auth().preemptive().basic("admin","password123")
                .header("Content-Type", "application/json")
                .when().delete("/booking/"+ bookingId)
                .then().log().all().assertThat().statusCode(201)
                .extract().response().asString();

        //assert
    given().log().all().when().get("/booking/"+bookingId).then().log().all().assertThat().statusCode(404);


    }

}


