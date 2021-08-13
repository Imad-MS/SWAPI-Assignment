package com.swapi.api.Test;

import io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.openqa.selenium.json.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

public class GET_Test {
    //Verify that the people endpoint is returning a successful response
    @Test
    public void testResponse(){
        Response response = get("https://swapi.dev/api/people/");
        int responseCode = response.getStatusCode();
        assertEquals(responseCode, 200);
    }

    //Verify that the total number of people where height is greater than 200 matches the expected count
    // (10 at the time this was assigned)
    @Test
    public void testHeight(){
        Response response = get("https://swapi.dev/api/people/?page=1");
        String rsp = response.asString();
        JsonPath jsonPath = new JsonPath(rsp);

        List<String> allTallChars = null;

        for(int i = 1; i < 10; i++){
            response = get("https://swapi.dev/api/people/?page=" + String.valueOf(i));
            rsp = response.asString();
            jsonPath = new JsonPath(rsp);

            if(allTallChars == null){
                try{
                    allTallChars = jsonPath.getList("results.findAll { Integer.parseInt(it.height) > 200 }.name");
                } catch(IllegalArgumentException e){}
            } else {
                try{
                    allTallChars.addAll(jsonPath.getList("results.findAll { Integer.parseInt(it.height) > 200 }.name"));
                } catch(IllegalArgumentException e){}
            }

        }
        List<String> expectedList = new ArrayList<String>();
        String[] expectedArray = {"Darth Vader", "Chewbacca", "Roos Tarpals", "Rugor Nass", "Yarael Poof", "Lama Su", "Taun We", "Grievous", "Tarfful", "Tion Medon"};
        for(String entry:expectedArray){
            expectedList.add(entry);
        }

        assertEquals(expectedList, allTallChars);

    }

    //Verify that the 10 individuals returned are:
    // Darth Vader, Chewbacca, Roos Tarpals, Rugor Nass, Yarael Poof, Lama Su, Tuan Wu, Grevious, Tarfful, Tion Medon
    @Test
    public void testHeightPeople(){
        Response response = get("https://swapi.dev/api/people/?page=1");
        String rsp = response.asString();
        JsonPath jsonPath = new JsonPath(rsp);

        List<String> allTallChars = null;

        for(int i = 1; i < 10; i++){
            response = get("https://swapi.dev/api/people/?page=" + String.valueOf(i));
            rsp = response.asString();
            jsonPath = new JsonPath(rsp);

            if(allTallChars == null){
                try{
                    allTallChars = jsonPath.getList("results.findAll { Integer.parseInt(it.height) > 200 }.name");
                } catch(IllegalArgumentException e){}
            } else {
                try{
                    allTallChars.addAll(jsonPath.getList("results.findAll { Integer.parseInt(it.height) > 200 }.name"));
                } catch(IllegalArgumentException e){}
            }

        }

        assertEquals(10, allTallChars.size());
    }

    //Verify that the total number of people checked equals the expected count (82 at the time)
    @Test
    public void testTotalPeople(){
        given().
                get("https://swapi.dev/api/people/").
        then().
                body("count", equalTo(82));
    }
}
