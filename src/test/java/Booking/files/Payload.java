package Booking.files;

public class Payload {

    public static String bookingBody1 (){
        return "{\n" +
                "    \"firstname\" : \"Vlada\",\n" +
                "    \"lastname\" : \"Vladic\",\n" +
                "    \"totalprice\" : 99,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast,Lunch,Dinner\"\n" +
                "}";
    }


    public static String bookingBody2 (){
        return "{\n" +
                "    \"firstname\" : \"Vlada\",\n" +
                "    \"lastname\" : \"Izmenjic\",\n" +
                "    \"totalprice\" : 88,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast,Lunch,Dinner\"\n" +
                "}";


    }



}
