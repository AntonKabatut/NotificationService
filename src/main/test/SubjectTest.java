import com.anton.kursach.util.GeneralTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubjectTest {

    private String token;

    @Before
    public void initialize(){
        token= GeneralTest.getInstance().getToken();
    }

    @Test
    public void aTestAddSubject(){
        Response response=given().accept("application/json").contentType("application/json")
                .header("Authorization",GeneralTest.getInstance().getToken())
                .body("{\"name\":\"databases1\"}")
                .when()
                .post("http://localhost:8080/subject")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response();
        JsonPath jsonPath=response.jsonPath();
        GeneralTest.getInstance().setSubjId(jsonPath.getLong("id"));
    }

    @Test
    public void bTestGetSubjects(){
        given().accept("application/json").contentType("application/json")
                .header("Authorization",token)
                .when()
                .get("http://localhost:8080/subject")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void dTestDeleteSubject(){
        given().accept("application/json").contentType("application/json")
                .header("Authorization",token)
                .when()
                .delete("http://localhost:8080/subject/{id}",GeneralTest.getInstance().getSubjId())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void cTestUpdateSubject(){
        given().accept("application/json").contentType("application/json")
                .header("Authorization",token)
                .body("{\"id\":\""+GeneralTest.getInstance().getSubjId()+"\",\"name\":\"database\"}")
                .when()
                .put("http://localhost:8080/subject")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
