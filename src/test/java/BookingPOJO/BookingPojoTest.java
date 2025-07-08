package BookingPOJO;

import BookingPOJO.PojoClasses.BookingDates;
import BookingPOJO.PojoClasses.CreateBooking;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BookingPojoTest {
    String pingParameter;
    String tokenParameter;
    String bookingParameter;
    CreateBooking createBooking = new CreateBooking();
    BookingDates bookingDates = new BookingDates();




    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
    pingParameter = "ping";
    tokenParameter = "auth";
    bookingParameter = "booking";
    }

    @Test
    public void healthCheck (){
        given().log().all()
                .when().get( pingParameter)
                .then().log().all().assertThat().statusCode(201);
    }

    @Test
    public void  getAllBookings(){
        String response =
        given().log().all()
                .when().get(pingParameter)
                .then().log().all().assertThat().statusCode(201)
                .extract().response().asString();

        Assert.assertFalse(response.isEmpty());
    }

    @Test
    public void createBooking(){
        createBooking.setFirstname("Dragoljub");
        createBooking.setLastname("Branisavljevic");
        createBooking.setTotalprice(500);
        createBooking.setDepositpaid(true);
        bookingDates.setCheckin("2020-04-11");
        bookingDates.setCheckout("2020-05-20");
        createBooking.setBookingdates(bookingDates);
        createBooking.setAdditionalneeds("Parking");




        given().log().all()
                .header("Content-Type", "application/json")
                .body(createBooking)
                .when().post(bookingParameter)
                .then().log().all()
                .assertThat().statusCode(200);
    }

}
