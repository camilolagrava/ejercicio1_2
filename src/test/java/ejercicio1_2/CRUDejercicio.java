package ejercicio1_2;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CRUDejercicio {

    JSONObject body = new JSONObject();
    int idProject = 0;
    String[] auth = {"upb_api@api.com","12345"};

    public CRUDejercicio(){
        body.put("Content","mi item");
    }

    @Test
    public void crud(){
        //create
        Response response=given()
                .auth()
                .preemptive()
                .basic(auth[0],auth[1])
                .body(body.toString())
                .log().all()
                .when()
                .post("https://todo.ly/api/items.json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo("mi item"))
                .log().all();

        idProject =response.then().extract().path("Id");

        //read
        response=given()
                .auth()
                .preemptive()
                .basic(auth[0],auth[1])
                .log().all()
                .when()
                .get("https://todo.ly/api/items/"+idProject+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo("mi item"))
                .log().all();

        //update
        body.put("Content","mi item pero con update");
        response=given()
                .auth()
                .preemptive()
                .basic(auth[0],auth[1])
                .body(body.toString())
                .log().all()
                .when()
                .put("https://todo.ly/api/items/"+idProject+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo("mi item pero con update"))
                .log().all();

        //delete
        response=given()
                .auth()
                .preemptive()
                .basic(auth[0],auth[1])
                .log().all()
                .when()
                .delete("https://todo.ly/api/items/"+idProject+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo("mi item pero con update"))
                .body("Deleted",equalTo(true))
                .log().all();

    }



}
