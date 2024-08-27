package ing.product_service.controller;

import ing.product_service.dto.PriceChangeDto;
import ing.product_service.dto.ProductAddDto;
import ing.product_service.dto.ProductAddRsDto;
import ing.product_service.dto.ProductDto;
import ing.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/admin/insert")
    public ProductAddRsDto addProduct(@Valid @RequestBody ProductAddDto productAddDto) {
        return productService.addProduct(productAddDto);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/find/{productId}")
    public ProductDto searchProduct(@PathVariable Long productId) {
        return productService.searchProduct(productId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/admin/price/change")
    public void updatePrice(@Valid @RequestBody PriceChangeDto priceChangeDto) {
        productService.updatePrice(priceChangeDto);
    }
}