package dat.routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dtos.PackedItemDTO;
import dat.dtos.TripDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.TripCategory;
import dat.security.entities.Role;
import dat.security.entities.User;
import io.javalin.Javalin;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.util.List;

public class TripRoutesTest {

    private static final String BASE_URI = "http://localhost:7007/api";
    private static Javalin app;
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private TripDTO testTripDTO;
    private Guide testGuide;

    private String adminToken;
    private String userToken;

    @BeforeAll
    public static void setup() {
        HibernateConfig.setTest(true);
        app = ApplicationConfig.startServer(7007);
        RestAssured.baseURI = BASE_URI;

        entityManagerFactory = HibernateConfig.getEntityManagerFactoryForTest();
    }

    @BeforeEach
    public void setupEach() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        testGuide = new Guide("John", "Doe", "john.doe@example.com", "123-456-7890", 5);
        entityManager.persist(testGuide);

        PackedItemDTO packedItemDTO = new PackedItemDTO();
        packedItemDTO.setName("Tent");
        packedItemDTO.setWeightInGrams(10500);
        packedItemDTO.setQuantity(2);
        packedItemDTO.setDescription("A basic tent");
        packedItemDTO.setCategory(TripCategory.BEACH.name());

        testTripDTO = new TripDTO("Beach Getaway", LocalDate.now(), LocalDate.now().plusDays(5),
                "Beachfront", 1200.0, TripCategory.BEACH, testGuide.getFirstname(), testGuide.getLastname(),
                List.of(packedItemDTO));

        Trip tripEntity = testTripDTO.toEntity(testGuide);
        entityManager.persist(tripEntity);

        entityManager.getTransaction().commit();

        testTripDTO = new TripDTO(tripEntity);

        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");

        entityManager.persist(adminRole);
        entityManager.persist(userRole);

        User adminUser = new User("admin", "1234");
        adminUser.addRole(adminRole);

        User regularUser = new User("user", "1234");
        regularUser.addRole(userRole);

        entityManager.persist(adminUser);
        entityManager.persist(regularUser);

        adminToken = login(adminUser.getUsername(), "admin");
        userToken = login(adminUser.getUsername(), "user");
    }

    @AfterEach
    public void tearDownEach() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.getTransaction().begin();
            entityManager.createQuery("DELETE FROM Trip").executeUpdate();
            entityManager.createQuery("DELETE FROM Guide").executeUpdate();
            entityManager.createQuery("DELETE FROM User").executeUpdate();
            entityManager.createQuery("DELETE FROM Role").executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    @AfterAll
    public static void tearDown() {
        ApplicationConfig.stopServer(app);
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testReadAllTrips() {
        Response response = given()
            //    .header("Authorization", userToken)
                .when()
                .get("/trips")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)))
                .extract()
                .response();
    }

    @Test
    public void testReadTripById() {
        given()
                .when()
                .get("/trips/" + testTripDTO.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo(testTripDTO.getName()))
                .extract()
                .response();
    }

    @Test
    public void testCreateTrip() {
        Response response = given()
             //   .header("Authorization", adminToken)
                .contentType("application/json")
                .body(testTripDTO)
                .when()
                .post("/trips")
                .then()
                .statusCode(201)
                .body("name", equalTo(testTripDTO.getName()))
                .extract()
                .response();
    }

    @Test
    public void testUpdateTrip() {
        testTripDTO.setName("Updated Beach Getaway");
        Response response = given()
              //  .header("Authorization", adminToken)
                .contentType("application/json")
                .body(testTripDTO)
                .when()
                .put("/trips/" + testTripDTO.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo(testTripDTO.getName()))
                .extract()
                .response();
    }

    @Test
    public void testDeleteTrip() {
        Response response = given()
             //   .header("Authorization", adminToken)
                .when()
                .delete("/trips/" + testTripDTO.getId())
                .then()
                .statusCode(204)
                .extract()
                .response();

        given()
            //    .header("Authorization", adminToken)
                .when()
                .get("/trips/" + testTripDTO.getId())
                .then()
                .statusCode(404);
    }

    @Test
    public void testAddGuideToTrip() {
        Response response = given()
             //   .header("Authorization", adminToken)
                .when()
                .put("/trips/" + testTripDTO.getId() + "/guides/" + testGuide.getId())
                .then()
                .statusCode(200)
                .body("message", equalTo("Guide successfully added to trip."))
                .extract()
                .response();
    }

    @Test
    public void testGetTripsByGuide() {
        given()
                .when()
                .put("/trips/" + testTripDTO.getId() + "/guides/" + testGuide.getId())
                .then()
                .statusCode(200);

        Response response = given()
                .when()
                .get("/trips/guides/" + testGuide.getId())
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)))
                .extract()
                .response();
    }

    @Test
    public void testPopulateDatabase() {
        Response response = given()
             //   .header("Authorization", adminToken)
                .when()
                .post("/trips/populate")
                .then()
                .statusCode(200)
                .body("message", equalTo("Database populated successfully."))
                .extract()
                .response();
    }

    @Test
    public void testGetTripsByCategory() {
        Response response = given()
                .when()
                .get("/trips/categories/" + TripCategory.BEACH.name())
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)))
                .extract()
                .response();
    }

    @Test
    public void testGetTotalPriceByGuide() {
        Response response = given()
                .when()
                .get("/trips/guides/totalprice")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)))
                .extract()
                .response();
    }

    @Test
    public void testGetPackingItemsByCategory() {
        Response response = given()
                .when()
                .get("/trips/packingitems/" + TripCategory.BEACH.name())
                .then()
                .statusCode(200)
                .body("items", hasSize(greaterThan(0)))
                .extract()
                .response();
    }

    @Test
    public void testGetTripByIdWithPackingItems() {
        // Send GET request to retrieve the trip by ID along with its packing items
        Response response = given()
                .when()
                .get("/trips/" + testTripDTO.getId() + "/packing")
                .then()
                .statusCode(200)  // Verify that the status code is 200 OK
                .body("name", equalTo(testTripDTO.getName()))  // Verify that the trip's name matches the expected name
                .body("packedItems", hasSize(greaterThan(0)))  // Ensure that there is at least one packed item
                .extract()
                .response();

        given()
                .then()
                .body("packedItems[0].name", equalTo("Tent"))  // Check that the first item is "Tent"
                .body("packedItems[0].weightInGrams", equalTo(10500))  // Check the weight of the "Tent"
                .body("packedItems[0].quantity", equalTo(2))  // Check the quantity of the "Tent"
                .body("packedItems[0].description", equalTo("A basic tent"))  // Check the description of the "Tent"
                .body("packedItems[0].category", equalTo(TripCategory.BEACH.name()));  // Check the category of the "Tent"
    }


    @Test
    public void testGetTotalPackingWeightByTripId() {
        Response response = given()
                .when()
                .get("/trips/" + testTripDTO.getId() + "/packing/weight")
                .then()
                .statusCode(200)
                .body("totalWeight", greaterThan(0))
                .extract()
                .response();
    }

    private static String login(String username, String password) {
        String json = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        var token = given()
                .contentType("application/json")
                .accept("application/json")
                .body(json)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .response()
                .body()
                .path("token");

        return "Bearer " + token;
    }
}
