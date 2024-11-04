package dat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideTotalPriceDTO {
    private int guideId;   // Guide ID
    private double totalPrice; // Total price of trips
}
