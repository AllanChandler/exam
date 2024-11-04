package dat.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.config.HibernateConfig;
import dat.config.Populator;
import dat.controllers.ITripController;
import dat.daos.impl.TripDAO;
import dat.dtos.PackedItemDTO;
import dat.dtos.GuideTotalPriceDTO;
import dat.dtos.PackedItemResponseDTO;
import dat.dtos.TripDTO;
import dat.enums.TripCategory;
import dat.exceptions.Message;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

import jakarta.persistence.EntityManagerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

public class TripController implements ITripController {

    private final TripDAO tripDAO;

    public TripController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.tripDAO = TripDAO.getInstance(emf);
    }

    @Override
    public void readAll(Context ctx) {
        try {
            List<TripDTO> trips = tripDAO.getAll();
            ctx.status(200).json(trips);
        } catch (Exception e) {
            ctx.status(500).json(new Message(500, "Unable to retrieve trips from the database.", getCurrentTimestamp()));
        }
    }

    @Override
    public void readById(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get(); // Get trip ID from URL

        try {
            TripDTO trip = tripDAO.getById(id);
            if (trip == null) {
                ctx.status(404).json(new Message(404, "Trip not found with ID: " + id, getCurrentTimestamp()));
                return;
            }
            ctx.status(200).json(trip);
        } catch (Exception e) {
            ctx.status(500).json(new Message(500, "Error occurred while retrieving the trip.", getCurrentTimestamp()));
        }
    }

    @Override
    public void create(Context ctx) {
        try {
            TripDTO tripRequest = ctx.bodyAsClass(TripDTO.class);
            TripDTO createdTrip = tripDAO.create(tripRequest);
            ctx.status(201).json(createdTrip);
        } catch (Exception e) {
            ctx.status(400).json(new Message(400, "Failed to create trip due to invalid data.", getCurrentTimestamp()));
        }
    }

    @Override
    public void update(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get(); // Get trip ID from URL
        TripDTO tripRequest = ctx.bodyAsClass(TripDTO.class);

        try {
            TripDTO updatedTrip = tripDAO.update(id, tripRequest);
            if (updatedTrip == null) {
                ctx.status(404).json(new Message(404, "Trip not found with ID: " + id, getCurrentTimestamp()));
                return;
            }
            ctx.status(200).json(updatedTrip);
        } catch (Exception e) {
            ctx.status(500).json(new Message(500, "Error occurred while updating the trip.", getCurrentTimestamp()));
        }
    }

    @Override
    public void delete(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get(); // Get trip ID from URL

        try {
            if (tripDAO.delete(id)) {
                ctx.status(204); // No Content response on successful deletion
            } else {
                ctx.status(404).json(new Message(404, "Trip not found with ID: " + id, getCurrentTimestamp()));
            }
        } catch (Exception e) {
            ctx.status(500).json(new Message(500, "Error occurred while deleting the trip.", getCurrentTimestamp()));
        }
    }

    @Override
    public void addGuideToTrip(Context ctx) {
        Integer tripId = ctx.pathParamAsClass("tripId", Integer.class).get();
        Integer guideId = ctx.pathParamAsClass("guideId", Integer.class).get();

        try {
            // Attempt to add the guide to the trip using the DAO method
            tripDAO.addGuideToTrip(tripId, guideId);

            // If successful, respond with a 200 OK status and a success message
            ctx.status(200).json(new Message(200, "Guide successfully added to trip.", getCurrentTimestamp()));
        } catch (BadRequestResponse e) {
            // Handle bad request errors (e.g., trip or guide not found)
            ctx.status(400).json(new Message(400, e.getMessage(), getCurrentTimestamp()));
        } catch (RuntimeException e) {
            // Handle runtime errors and respond with a 500 status
            ctx.status(500).json(new Message(500, "Error occurred while adding guide to trip: " + e.getMessage(), getCurrentTimestamp()));
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            ctx.status(500).json(new Message(500, "Internal Server Error.", getCurrentTimestamp()));
        }
    }


    @Override
    public void getTripsByGuide(Context ctx) {
        Integer guideId = ctx.pathParamAsClass("guideId", Integer.class).get();

        try {
            // Retrieve the set of trips associated with the guide
            Set<TripDTO> trips = tripDAO.getTripsByGuide(guideId);

            // Check if any trips were found
            if (trips.isEmpty()) {
                ctx.status(404).json(new Message(404, "No trips found for guide with ID: " + guideId, getCurrentTimestamp()));
                return;
            }

            // Successful response with the list of trips
            ctx.status(200).json(trips);
        } catch (BadRequestResponse e) {
            ctx.status(400).json(new Message(400, e.getMessage(), getCurrentTimestamp()));
        } catch (RuntimeException e) {
            ctx.status(500).json(new Message(500, "Error occurred while retrieving trips for the guide: " + e.getMessage(), getCurrentTimestamp()));
        } catch (Exception e) {
            ctx.status(500).json(new Message(500, "Internal Server Error.", getCurrentTimestamp()));
        }
    }

    // Populate the database
    public void populate(Context ctx) {
        try {
            Populator.main(new String[]{}); // Call the main method of Populator
            ctx.status(200).json(new Message(200, "Database populated successfully.", getCurrentTimestamp()));
        } catch (Exception e) {
            ctx.status(500).json(new Message(500, "Error occurred while populating the database: " + e.getMessage(), getCurrentTimestamp()));
        }
    }

    @Override
    public void getTripsByCategory(Context ctx) {
        String categoryParam = ctx.pathParam("category"); // Get category from URL
        TripCategory category;

        try {
            category = TripCategory.valueOf(categoryParam.toUpperCase()); // Convert to enum
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(new Message(400, "Invalid trip category: " + categoryParam, getCurrentTimestamp()));
            return;
        }

        try {
            List<TripDTO> trips = tripDAO.getTripsByCategory(category);
            ctx.status(200).json(trips);
        } catch (Exception e) {
            ctx.status(500).json(new Message(500, "Unable to retrieve trips from the database.", getCurrentTimestamp()));
        }
    }

    @Override
    public void getTotalPriceByGuide(Context ctx) {
        try {
            List<GuideTotalPriceDTO> guideTotalPriceList = tripDAO.getTotalPriceByGuide(); // Retrieve total prices
            ctx.status(200).json(guideTotalPriceList); // Return the list as JSON
        } catch (Exception e) {
            ctx.status(500).json("Error occurred while retrieving total prices by guide: " + e.getMessage());
        }
    }

    public void getPackingItemsByCategory(Context ctx) {
        String category = ctx.pathParam("category"); // Get category from URL

        try {
            // Fetch packing items based on the category
            PackedItemResponseDTO packedItems = fetchPackedItemsByCategory(category.toLowerCase());
            ctx.status(200).json(packedItems); // Respond with the fetched packing items
        } catch (Exception e) {
            ctx.status(500).json("Error fetching packing items: " + e.getMessage());
        }
    }

    // Method to fetch packing items for a given category using HttpClient
    private PackedItemResponseDTO fetchPackedItemsByCategory(String category) throws Exception {
        // Create an HttpClient instance
        HttpClient client = HttpClient.newHttpClient();

        // Create the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://packingapi.cphbusinessapps.dk/packinglist/" + category))
                .GET()
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check the response status and handle accordingly
        if (response.statusCode() == 200) {
            // Configure ObjectMapper to handle ZonedDateTime
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            // Parse the response body into PackedItemResponseDTO
            return objectMapper.readValue(response.body(), PackedItemResponseDTO.class);
        } else {
            throw new RuntimeException("Failed to fetch packing items: HTTP error code : " + response.statusCode());
        }
    }

    @Override
    public void getTripById(Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.class).get(); // Get trip ID from URL
        TripDTO trip = tripDAO.getById(id); // Get trip details

        if (trip != null) {
            // Fetch packing items based on the trip's category
            try {
                PackedItemResponseDTO packedItems = fetchPackedItemsByCategory(trip.getCategory().name().toLowerCase());
                trip.setPackedItems(packedItems.getItems()); // Assuming you added a packedItems field in TripDTO
                ctx.status(200).json(trip);
            } catch (Exception e) {
                ctx.status(500).json("Error fetching packing items: " + e.getMessage());
            }
        } else {
            ctx.status(404).json(new Message(404, "Trip not found with ID: " + id, getCurrentTimestamp()));
        }
    }


    // New endpoint to get the sum of weights of all packing items for a trip
    @Override
    public void getTotalPackingWeightByTripId(Context ctx) {
        Integer tripId = ctx.pathParamAsClass("id", Integer.class).get();
        TripDTO trip = tripDAO.getById(tripId); // Get trip details

        if (trip != null) {
            try {
                PackedItemResponseDTO packedItems = fetchPackedItemsByCategory(trip.getCategory().name().toLowerCase());
                int totalWeight = packedItems.getItems().stream()
                        .mapToInt(PackedItemDTO::getWeightInGrams)
                        .sum(); // Calculate the total weight
                ctx.status(200).json(totalWeight);
            } catch (Exception e) {
                ctx.status(500).json("Error fetching packing items: " + e.getMessage());
            }
        } else {
            ctx.status(404).json(new Message(404, "Trip not found with ID: " + tripId, getCurrentTimestamp()));
        }
    }

    // Utility method to get the current timestamp
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
