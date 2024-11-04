package dat.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private int yearsOfExperience;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Trip> trips = new HashSet<>();

    public Guide(String firstname, String lastname, String email, String phone, int yearsOfExperience) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

    // Method to add a trip and establish the bidirectional relationship
    public void addTrip(Trip trip) {
        if (trip != null) {
            trips.add(trip);
            trip.setGuide(this); // Ensures bidirectional relationship
        }
    }

    // New method to calculate total price of trips
    public double calculateTotalPrice() {
        return trips.stream()
                .mapToDouble(Trip::getPrice) // Map each Trip to its price
                .sum(); // Sum up all prices
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email); // Use ID and email for hash
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Guide)) return false;
        Guide other = (Guide) obj;
        return id == other.id;
    }
}
