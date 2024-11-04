package dat.daos;

import dat.dtos.GuideTotalPriceDTO;
import dat.dtos.TripDTO; // Assuming you want to return TripDTO as well

import java.util.List;
import java.util.Set;

public interface ITripGuideDAO {
    void addGuideToTrip(int tripId, int guideId);
    Set<TripDTO> getTripsByGuide(int guideId);
    List<GuideTotalPriceDTO> getTotalPriceByGuide(); // Updated return type

}
