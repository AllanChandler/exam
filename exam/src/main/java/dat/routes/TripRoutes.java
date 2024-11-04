package dat.routes;

import dat.controllers.impl.TripController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoutes {

    private final TripController tripController = new TripController();

    protected EndpointGroup getRoutes() {
        return () -> {
            post("/populate", tripController::populate); // Route to populate the database
            get("/", tripController::readAll); // Get all trips
            get("/{id}", tripController::readById); // Get a trip by its id
            post("/", tripController::create); // Create a new trip
            put("/{id}", tripController::update); // Update information about a trip
            delete("/{id}", tripController::delete); // Delete a trip
            put("/{tripId}/guides/{guideId}", tripController::addGuideToTrip); // Add an existing guide to an existing trip
            get("/guides/{guideId}", tripController::getTripsByGuide); // Retrieve trips by guide
            get("/categories/{category}", tripController::getTripsByCategory); // Filter trips by category
            get("/{id}/packing", tripController::getTripById); // Get a trip by id including packing items
            get("/{id}/packing/weight", tripController::getTotalPackingWeightByTripId); // Get total packing weight for a trip
            get("/packingitems/{category}", tripController::getPackingItemsByCategory); // Get packing items for a given category
            get("/api/trips/guides/totalprice", tripController::getTotalPriceByGuide); // Retrieve total price by guide
        };
    }
    /*
    protected EndpointGroup getRoutes() {
        return () -> {
            post("/populate", tripController::populate, Role.ADMIN); // Only ADMIN can populate the database
            get("/", tripController::readAll, Role.ANYONE); // Anyone can get all trips
            get("/{id}", tripController::readById, Role.ANYONE); // Anyone can get a trip by its id
            post("/", tripController::create, Role.ADMIN); // Only ADMIN can create a new trip
            put("/{id}", tripController::update, Role.ADMIN); // Only ADMIN can update a trip
            delete("/{id}", tripController::delete, Role.ADMIN); // Only ADMIN can delete a trip
            put("/{tripId}/guides/{guideId}", tripController::addGuideToTrip, Role.ADMIN); // Only ADMIN can add a guide to a trip
            get("/guides/{guideId}", tripController::getTripsByGuide, Role.ANYONE); // Anyone can get trips by guide
            get("/categories/{category}", tripController::getTripsByCategory, Role.ANYONE); // Anyone can filter trips by category
            get("/{id}/packing", tripController::getTripById, Role.ANYONE); // Anyone can get a trip by id including packing items
            get("/{id}/packing/weight", tripController::getTotalPackingWeightByTripId, Role.ANYONE); // Anyone can get total packing weight for a trip
            get("/packingitems/{category}", tripController::getPackingItemsByCategory, Role.ANYONE); // Anyone can get packing items for a category
            get("/api/trips/guides/totalprice", tripController::getTotalPriceByGuide, Role.ANYONE); // Anyone can retrieve total price by guide
        };
    }
     */
}
