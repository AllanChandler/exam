package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final TripRoutes tripRoutes = new TripRoutes(); // Create an instance of TripRoutes

    public EndpointGroup getRoutes() {
        return () -> {
            path("/trips", tripRoutes.getRoutes()); // Group trip-related routes under /trips
        };
    }
}
