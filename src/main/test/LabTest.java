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
public class LabTest {

    private Long subjId;
    private String token;

    @Before
    public void initialize(){
        subjId= GeneralTest.getInstance().getSubjId();
        token=GeneralTest.getInstance().getToken();
    }

    @Test
    public void firstTestAddLab() {
        Response response=given().accept("application/json").contentType("application/json")
                .header("Authorization",token)
                .body("{\"name\":\"lab1\", \"deadline\":\"12415664564\", \"days\":\"1526226663279\"," +
                        " \"need\":\"true\",\"subjId\":\""+subjId+"\"}")
                .when()
                .post("http://localhost:8080/lab")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response();
        JsonPath jsonPath=response.jsonPath();
        GeneralTest.getInstance().setLabId(jsonPath.getLong("id"));
    }

    @Test
    public void secondTestGetLabs(){
        given().accept("application/json").contentType("application/json")
                .header("Authorization",token)
                .when()
                .get("http://localhost:8080/lab/{id}",subjId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void fourthTestDeleteLab(){
        given().accept("application/json").contentType("application/json")
                .header("Authorization",token)
                .when()
                .delete("http://localhost:8080/lab/{id}",GeneralTest.getInstance().getLabId())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void thirdTestUpdateLab(){
        given().accept("application/json").contentType("application/json")
                .header("Authorization",token)
                .body("{\"id\":\""+GeneralTest.getInstance().getLabId()+
                        "\",\"name\":\"updatedLab\", \"deadline\":\"12415664563\", \"days\":\"1526226663279\"," +
                        " \"need\":\"false\",\"subjId\":\""+subjId+"\"}")
                .when()
                .put("http://localhost:8080/lab")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
