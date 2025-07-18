package BookingPOJO;

import BookingPOJO.PojoClasses.BookingDates;
import BookingPOJO.PojoClasses.CreateBooking;
import BookingPOJO.PojoClasses.CreateToken;
import BookingPOJO.PojoClasses.Variables;
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
    CreateToken createToken = new CreateToken();
Variables variables = new Variables();




    @BeforeClass
    public void setUp(){


        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
    pingParameter = "ping";
    tokenParameter = "auth";
    bookingParameter = "booking";
    createToken();
    }

    public  void createToken(){
        createToken.setUsername("admin");
        createToken.setPassword("password1234");

        String tokenResponse =
        given().log().all()
                .header("Content-Type","application/json")
                .body(createToken)
                .when().post(tokenParameter)
                .then().log().all()
                .extract().response().asString();

        JsonPath js = new JsonPath(tokenResponse);
        variables.setToken(js.getString("token"));
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



String response =
        given().log().all()
                .header("Content-Type", "application/json")
                .body(createBooking)
                .when().post(bookingParameter)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

JsonPath js = new JsonPath(response);
Assert.assertEquals(js.getString("booking.firstname"), createBooking.getFirstname());
        Assert.assertEquals(js.getString("booking.lastname"), createBooking.getLastname());
        Assert.assertEquals(js.getInt("booking.totalprice"),createBooking.getTotalprice());
        Assert.assertEquals(js.getBoolean("booking.depositpaid"),createBooking.isDepositpaid());
    }

    @Test
    public  void  createAnyBooking (){
        String response =
                given().log().all()
                        .header("Content-Type","application/json")
                        .body(CreateBooking.setRandomPayload())
                        .when().post(bookingParameter)
                        .then().log().all()
                        .assertThat().statusCode(200)
                        .extract().response().asString();

    }

}
