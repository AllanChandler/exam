package dat.entities;

import dat.enums.TripCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private LocalDate starttime; // Using LocalDate
    private LocalDate endtime; // Using LocalDate
    private String startposition;
    private double price; // Change from int to double

    @Enumerated(EnumType.STRING)
    private TripCategory category;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    private Guide guide;

    public Trip(String name, LocalDate starttime, LocalDate endtime, String startposition, double price, TripCategory category) {
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.startposition = startposition;
        this.price = price; // Update constructor to accept double
        this.category = category;
    }

    // Updated constructor to include guide parameter
    public Trip(String name, LocalDate starttime, LocalDate endtime, String startposition, double price, TripCategory category, Guide guide) {
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.startposition = startposition;
        this.price = price;
        this.category = category;
        this.guide = guide; // Initialize the guide here
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Trip)) return false;
        Trip other = (Trip) obj;
        return id == other.id;
    }
}
