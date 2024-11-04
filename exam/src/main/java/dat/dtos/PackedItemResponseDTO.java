package dat.dtos;

import lombok.Data;
import java.util.List;

@Data
public class PackedItemResponseDTO {
    private List<PackedItemDTO> items; // Using PackingItemDTO instead
}
