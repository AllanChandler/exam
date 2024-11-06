package dat.daos;

import dat.dtos.TripDTO; // Assuming you want to return TripDTO as well
import dat.entities.Guide;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ITripGuideDAO {
    void addGuideToTrip(int tripId, int guideId);
    Set<TripDTO> getTripsByGuide(int guideId);
    Map<Guide, Double> getTotalPriceByGuide(); // Updated return type

}
