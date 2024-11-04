package dat.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class PackedItemDTO {
    private String name;
    private int weightInGrams;
    private int quantity;
    private String description;
    private String category;

    // Updated to use ZonedDateTime with the correct format
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") // Adjusting for ZonedDateTime
    private ZonedDateTime createdAt; // Changed to ZonedDateTime

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") // Adjusting for ZonedDateTime
    private ZonedDateTime updatedAt; // Changed to ZonedDateTime

    private List<BuyingOptionDTO> buyingOptions;

    @Data
    public static class BuyingOptionDTO {
        private String shopName;
        private String shopUrl;
        private double price;
    }
}
