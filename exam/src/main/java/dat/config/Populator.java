package dat.config;

import dat.entities.Guide;
import dat.entities.Trip;
import dat.enums.TripCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;

public class Populator {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            clearExistingData(em);
            populate(em);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Rollback in case of an error
            }
            e.printStackTrace(); // Print the stack trace for debugging
        } finally {
            em.close(); // Ensure that the EntityManager is closed to free resources
        }
    }

    private static void clearExistingData(EntityManager em) {
        // Clear existing trips and guides from the database
        em.createQuery("DELETE FROM Trip").executeUpdate();
        em.createQuery("DELETE FROM Guide").executeUpdate();

        // Reset the ID sequences for both entities
        em.createNativeQuery("ALTER SEQUENCE trip_id_seq RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER SEQUENCE guide_id_seq RESTART WITH 1").executeUpdate();
    }

    private static void populate(EntityManager em) {
        // Creating Guide instances using the appropriate constructor
        Guide guide1 = new Guide("Alice", "Johnson", "alice.johnson@example.com", "123-456-7890", 5);
        Guide guide2 = new Guide("Bob", "Smith", "bob.smith@example.com", "234-567-8901", 8);
        Guide guide3 = new Guide("Clara", "Lee", "clara.lee@example.com", "345-678-9012", 3);

        // Persisting Guide entities to the database
        em.persist(guide1);
        em.persist(guide2);
        em.persist(guide3);

        // Creating Trip instances and associating them with the respective guides
        Trip trip1 = new Trip("Beach Getaway", LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 10), "Miami Beach", 1200.00, TripCategory.BEACH);
        guide1.addTrip(trip1); // Associate trip with guide1
        em.persist(trip1); // Persist the trip

        Trip trip2 = new Trip("Mountain Adventure", LocalDate.of(2024, 7, 15), LocalDate.of(2024, 7, 20), "Rocky Mountains", 800.00, TripCategory.FOREST);
        guide1.addTrip(trip2); // Associate trip with guide1
        em.persist(trip2); // Persist the trip

        Trip trip3 = new Trip("City Tour", LocalDate.of(2024, 8, 1), LocalDate.of(2024, 8, 5), "New York City", 1500.00, TripCategory.CITY);
        guide2.addTrip(trip3); // Associate trip with guide2
        em.persist(trip3); // Persist the trip

        Trip trip4 = new Trip("Cultural Journey", LocalDate.of(2024, 9, 10), LocalDate.of(2024, 9, 15), "Tokyo", 2000.00, TripCategory.CITY);
        guide2.addTrip(trip4); // Associate trip with guide2
        em.persist(trip4); // Persist the trip

        Trip trip5 = new Trip("Desert Safari", LocalDate.of(2024, 10, 5), LocalDate.of(2024, 10, 10), "Dubai", 1800.00, TripCategory.SEA);
        guide3.addTrip(trip5); // Associate trip with guide3
        em.persist(trip5); // Persist the trip

        // Persisting guides to ensure their trips are also persisted
        em.persist(guide1);
        em.persist(guide2);
        em.persist(guide3);
    }
}
