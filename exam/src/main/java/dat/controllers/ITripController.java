package dat.controllers;

import io.javalin.http.Context;

public interface ITripController {
    void readAll(Context ctx);
    void readById(Context ctx);
    void create(Context ctx);
    void update(Context ctx);
    void delete(Context ctx);
    void addGuideToTrip(Context ctx);
    void getTripsByGuide(Context ctx);
    void populate(Context ctx);
    void getTripsByCategory(Context ctx);
    void getTotalPriceByGuide(Context ctx);
    void getTripById(Context ctx); // Ensure this is in the interface
    void getTotalPackingWeightByTripId(Context ctx); // Ensure this is in the interface
}
