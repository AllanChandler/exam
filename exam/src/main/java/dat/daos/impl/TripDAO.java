package dat.daos.impl;

import dat.daos.IDAO;
import dat.daos.ITripGuideDAO;
import dat.dtos.GuideDTO;
import dat.dtos.GuideTotalPriceDTO;
import dat.dtos.TripDTO;
import dat.entities.Trip;
import dat.entities.Guide; // Import Guide entity
import dat.enums.TripCategory;
import io.javalin.http.BadRequestResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TripDAO implements IDAO<TripDTO>, ITripGuideDAO {

    private static TripDAO instance;
    private static EntityManagerFactory emf;

    // Singleton instance
    public static TripDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripDAO();
        }
        return instance;
    }

    @Override
    public List<TripDTO> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t", Trip.class);
            List<Trip> trips = query.getResultList();
            List<TripDTO> tripDTOs = new ArrayList<>();
            for (Trip trip : trips) {
                tripDTOs.add(new TripDTO(trip)); // Assuming TripDTO has a constructor that takes Trip
            }
            return tripDTOs;
        }
    }

    @Override
    public TripDTO getById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Trip ID must be greater than zero.");
        }

        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                throw new BadRequestResponse("Trip not found with ID: " + id);
            }
            return new TripDTO(trip); // Assuming TripDTO has a constructor that takes Trip
        } catch (Exception e) {
            throw new BadRequestResponse("Error while retrieving trip: " + e.getMessage());
        }
    }

    @Override
    public TripDTO create(TripDTO tripDTO) {
        if (tripDTO == null) {
            throw new IllegalArgumentException("Trip DTO cannot be null.");
        }

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = new Trip(
                    tripDTO.getName(),
                    tripDTO.getStarttime(),
                    tripDTO.getEndtime(),
                    tripDTO.getStartposition(),
                    tripDTO.getPrice(),
                    tripDTO.getCategory()
            );
            em.persist(trip);
            em.getTransaction().commit();
            tripDTO.setId(trip.getId()); // Set the generated ID
            return tripDTO;
        } catch (Exception e) {
            throw new BadRequestResponse("Error while creating trip: " + e.getMessage());
        }
    }

    @Override
    public TripDTO update(int id, TripDTO tripDTO) {
        if (tripDTO == null) {
            throw new IllegalArgumentException("Trip DTO cannot be null.");
        }

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                throw new BadRequestResponse("Trip not found with ID: " + id);
            }

            // Update the trip details
            trip.setName(tripDTO.getName());
            trip.setStarttime(tripDTO.getStarttime());
            trip.setEndtime(tripDTO.getEndtime());
            trip.setStartposition(tripDTO.getStartposition());
            trip.setPrice(tripDTO.getPrice());
            trip.setCategory(tripDTO.getCategory());

            em.merge(trip); // Merge the changes
            transaction.commit();
            return new TripDTO(trip);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new BadRequestResponse("Error while updating trip: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Trip ID must be greater than zero.");
        }

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                throw new BadRequestResponse("Trip not found with ID: " + id);
            }

            em.remove(trip); // Remove the trip
            transaction.commit();
            return true; // Indicate successful deletion
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new BadRequestResponse("Error while deleting trip: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    // Implementation of ITripGuideDAO methods

    @Override
    public void addGuideToTrip(int tripId, int guideId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Trip trip = em.find(Trip.class, tripId);
            Guide guide = em.find(Guide.class, guideId);

            // Check if the trip or guide exists
            if (trip == null || guide == null) {
                transaction.rollback();
                throw new BadRequestResponse("Trip or Guide not found with provided IDs.");
            }

            // Use the methods to establish the relationship
            trip.setGuide(guide);  // Establish the relationship in the Trip
            guide.addTrip(trip);   // Establish the relationship in the Guide

            // Persist the changes
            em.merge(trip);  // Merging the trip updates the guide reference

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while adding guide to trip: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Set<TripDTO> getTripsByGuide(int guideId) {
        EntityManager em = emf.createEntityManager();

        try {
            Guide guide = em.find(Guide.class, guideId);

            if (guide == null) {
                throw new BadRequestResponse("Guide not found with ID: " + guideId);
            }

            // Return the set of trips associated with the guide
            Set<TripDTO> tripDTOs = new HashSet<>();
            for (Trip trip : guide.getTrips()) { // Assuming getTrips() returns a collection of Trip entities
                tripDTOs.add(new TripDTO(trip)); // Assuming TripDTO has a constructor that takes Trip
            }
            return tripDTOs;
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving trips for guide: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public List<TripDTO> getTripsByCategory(TripCategory category) {
        try (EntityManager em = emf.createEntityManager()) {
            // Query to fetch trips by category
            List<Trip> trips = em.createQuery("SELECT t FROM Trip t WHERE t.category = :category", Trip.class)
                    .setParameter("category", category)
                    .getResultList();

            // Convert to TripDTO
            return trips.stream()
                    .map(TripDTO::new)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<GuideTotalPriceDTO> getTotalPriceByGuide() {
        EntityManager em = emf.createEntityManager();

        try {
            // Fetch all guides and calculate total prices
            return em.createQuery("SELECT g FROM Guide g", Guide.class)
                    .getResultList()
                    .stream()
                    .map(guide -> new GuideTotalPriceDTO(
                            guide.getId(),
                            guide.calculateTotalPrice())) // Calculate total price using the entity method
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving total prices by guide: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }


}
