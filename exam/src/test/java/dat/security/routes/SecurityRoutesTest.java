package dat.security.routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
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

public class SecurityRoutesTest {

    private static final String BASE_URI = "http://localhost:7007/api";

    private static Javalin app;
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void setup() {
        // Indstil til testmiljø og start serveren
        HibernateConfig.setTest(true);
        app = ApplicationConfig.startServer(7007);
        RestAssured.baseURI = BASE_URI;

        // Initialiser EntityManagerFactory til test
        entityManagerFactory = HibernateConfig.getEntityManagerFactoryForTest();
    }

    @BeforeEach
    public void setupEach() {
        // Initialiser EntityManager og begynd transaktionen
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        // Opret roller til testbrugere
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");

        // Gem rollerne i databasen
        entityManager.persist(adminRole);
        entityManager.persist(userRole);

        // Opret brugere og tilknyt roller
        User adminUser = new User("admin", "1234");
        adminUser.addRole(adminRole);

        User regularUser = new User("user", "1234");
        regularUser.addRole(userRole);

        // Gem brugerne i databasen
        entityManager.persist(adminUser);
        entityManager.persist(regularUser);

        // Commit transaktionen
        entityManager.getTransaction().commit();
    }

    @AfterEach
    public void tearDownEach() {
        // Ryd op efter hver test ved at slette brugere og roller fra databasen
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.getTransaction().begin();
            entityManager.createQuery("DELETE FROM User").executeUpdate();
            entityManager.createQuery("DELETE FROM Role").executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    @AfterAll
    public static void tearDown() {
        // Stop serveren og luk EntityManagerFactory efter alle tests er kørt
        ApplicationConfig.stopServer(app);
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testLogin() {
        // Test login med korrekt brugernavn og adgangskode
        Response response = given()
                .contentType("application/json")
                .body("{ \"username\": \"admin\", \"password\": \"1234\" }")
                .when()
                .post("/auth/login/")
                .then()
                .statusCode(200) // Tjek at statuskode er 200
                .body("token", notNullValue()) // Bekræft at token er returneret
                .body("username", equalTo("admin")) // Bekræft at brugernavnet er korrekt
                .extract()
                .response();
    }

    @Test
    public void testRegister() {
        // Test registrering af en ny bruger
        Response response = given()
                .contentType("application/json")
                .body("{ \"username\": \"adminTest\", \"password\": \"1234\" }")
                .when()
                .post("/auth/register/")
                .then()
                .statusCode(201) // Tjek at statuskode er 201
                .body("token", notNullValue()) // Bekræft at token er returneret
                .body("username", equalTo("adminTest")) // Bekræft at brugernavnet er korrekt
                .extract()
                .response();
    }
}
