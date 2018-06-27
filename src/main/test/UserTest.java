import com.anton.kursach.util.GeneralTest;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class UserTest {

    @Test
    public void authorization() {
        Response response=given().accept("application/json").contentType("application/json")
                .body("{\"login\":\"accountForTests\",\"password\":\"root\"}")
                .when()
                .post("http://localhost:8080/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response();
        GeneralTest.getInstance().setToken(response.getHeader("Authorization"));
    }

    @Test
    public void registration(){
        given().accept("application/json").contentType("application/json")
                .body("{\"login\":\"accountForTests\",\"password\":\"root\"}")
                .when()
                .post("http://localhost:8080/user")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
