package Booking;

import io.restassured.RestAssured;
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
        given().log().all()
                .header("Content-Type","application/json")
                .body("{\n" +
                        "    \"firstname\" : \"Vlada\",\n" +
                        "    \"lastname\" : \"Spasic\",\n" +
                        "    \"totalprice\" : 99,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast,Lunch,Dinner\"\n" +
                        "}")
                .when().post("/booking")
                .then().log().all().assertThat().statusCode(200);
    }

}
