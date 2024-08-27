package ing.product_service.mapper;

import ing.product_service.dto.ProductAddDto;
import ing.product_service.dto.ProductAddRsDto;
import ing.product_service.dto.ProductDto;
import ing.product_service.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product map(ProductAddDto productAddDto) {
        return Product.builder()
                .name(productAddDto.getName())
                .description(productAddDto.getDescription())
                .build();
    }

    public ProductDto map(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public ProductAddRsDto mapToProductAddRs(Product product) {
        return ProductAddRsDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .build();
    }
}