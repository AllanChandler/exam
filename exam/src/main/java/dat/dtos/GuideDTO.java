package dat.dtos;

import dat.entities.Guide;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideDTO {
    private int id;  // auto-generated
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private int yearsOfExperience;
    private Set<TripDTO> trips = new HashSet<>();

    // Constructor to initialize GuideDTO from a Guide entity
    public GuideDTO(Guide guide) {
        if (guide != null) {
            this.id = guide.getId();
            this.firstname = guide.getFirstname();
            this.lastname = guide.getLastname();
            this.email = guide.getEmail();
            this.phone = guide.getPhone();
            this.yearsOfExperience = guide.getYearsOfExperience();
            this.trips = guide.getTrips().stream().map(TripDTO::new).collect(Collectors.toSet());
        }
    }

    // Method to convert GuideDTO back to a Guide entity (trips handled elsewhere)
    public Guide toEntity() {
        Guide guide = new Guide();
        guide.setId(this.id);
        guide.setFirstname(this.firstname);
        guide.setLastname(this.lastname);
        guide.setEmail(this.email);
        guide.setPhone(this.phone);
        guide.setYearsOfExperience(this.yearsOfExperience);
        return guide;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof GuideDTO)) return false;
        GuideDTO other = (GuideDTO) obj;
        return id == other.id;
    }
}
