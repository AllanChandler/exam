package dat.dtos;

import dat.entities.Trip;
import dat.enums.TripCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List; // Import List to store packed items
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private int id;  // auto-generated
    private String name;
    private LocalDate starttime;
    private LocalDate endtime;
    private String startposition;
    private double price;  // Change from BigDecimal to double
    private TripCategory category;
    private String guideFirstname;  // Guide's first name
    private String guideLastname;   // Guide's last name
    private List<PackedItemDTO> packedItems; // Add this field for packing items

    // Constructor to initialize TripDTO from a Trip entity
    public TripDTO(Trip trip) {
        if (trip != null) {
            this.id = trip.getId();
            this.name = trip.getName();
            this.starttime = trip.getStarttime();
            this.endtime = trip.getEndtime();
            this.startposition = trip.getStartposition();
            this.price = trip.getPrice(); // Assuming Trip has price as double
            this.category = trip.getCategory();
            if (trip.getGuide() != null) {
                this.guideFirstname = trip.getGuide().getFirstname();
                this.guideLastname = trip.getGuide().getLastname();
            }
        }
    }

    // Method to convert TripDTO back to a Trip entity (guide setup handled elsewhere)
    public Trip toEntity() {
        Trip trip = new Trip();
        trip.setName(this.name);
        trip.setStarttime(this.starttime);
        trip.setEndtime(this.endtime);
        trip.setStartposition(this.startposition);
        trip.setPrice(this.price); // Using double
        trip.setCategory(this.category);
        return trip;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TripDTO)) return false;
        TripDTO other = (TripDTO) obj;
        return id == other.id;
    }
}
