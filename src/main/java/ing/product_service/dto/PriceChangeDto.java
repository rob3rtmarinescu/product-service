package ing.product_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceChangeDto {

    @NotNull(message = "Product Id is required")
    private Long productId;

    @NotNull(message = "New price is required")
    private Double price;
}
