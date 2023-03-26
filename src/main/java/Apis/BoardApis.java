package Apis;

import io.restassured.response.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static io.restassured.RestAssured.given;

public class BoardApis {

    FileInputStream fis ;
    String baseURI ="https://api.miro.com";
    String token  = "eyJtaXJvLm9yaWdpbiI6ImV1MDEifQ_832zlj5mvGR0AAUyrkKoITmga5c";
    String spec = "/v2/boards/";
    public static String boardId   ="";
    public static String boardName ="" ;


    /**
     * Create board from Json file (CreateBoard.json) then extract and save the board id & board name
     * to use it for the other tests , then assert on status code
     * */
    public void createBoard() throws FileNotFoundException {

        fis = new FileInputStream(new File(System.getProperty("user.dir") + "/src/test/java/TestData/Files/createBoard.json"));


        Response resp =
                given()
                .baseUri(baseURI)
                .contentType("application/json")
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(fis)
                .when()
                .post(spec)
                .then()
                .statusCode(201)
                .and()
                .extract()
                .response();

        boardId = resp.jsonPath().getString("id");
        boardName= resp.jsonPath().getString("name");

        System.out.println(boardId + boardName );
    }


    /****
     * Share board with other teammates from Json file (ShareBoard.json) then assert on the status code
     * @throws FileNotFoundException
     */
    public void shareBoard () throws FileNotFoundException {
        System.out.println(boardId + boardName );

        fis = new FileInputStream(new File(System.getProperty("user.dir") + "/src/test/java/TestData/Files/shareBoard.json"));

              given()
                      .baseUri(baseURI).header("Authorization", "Bearer " + token)
                      .header("Content-Type", "application/json")
                      .body(fis)
                      .when()
                      .log()
                      .all()
                      .post(spec+boardId+"/members")
                      .then()
                      .assertThat()
                      .statusCode(201)
                      .extract()
                      .response();
    }



/***
 * Delete board after the test execution to be able to create and share all over again
 * **/
    public void deleteBoard () {
        System.out.println(boardId + boardName );

              given().baseUri(baseURI).header("Authorization", "Bearer " + token)
                      .header("Content-Type", "application/json")
                      .when()
                      .log()
                      .all()
                      .delete(spec+boardId)
                      .then()
                      .assertThat()
                      .statusCode(204)
                      .log()
                      .all()
                      .extract()
                      .response();

    }
}
