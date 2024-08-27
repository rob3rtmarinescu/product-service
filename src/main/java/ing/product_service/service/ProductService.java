package ing.product_service.service;

import ing.product_service.dto.PriceChangeDto;
import ing.product_service.dto.ProductAddDto;
import ing.product_service.dto.ProductAddRsDto;
import ing.product_service.dto.ProductDto;
import ing.product_service.exception.ResourceNotFoundException;
import ing.product_service.mapper.ProductMapper;
import ing.product_service.repository.ProductDao;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final ProductMapper productMapper;

    public ProductAddRsDto addProduct(@Valid @RequestBody ProductAddDto productAddDto) {
        var savedProduct = productDao.save(productMapper.map(productAddDto));

        return productMapper.mapToProductAddRs(savedProduct);
    }

    public ProductDto searchProduct(@PathVariable Long productId) {
        var product = productDao.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find product with id " + productId));

        return productMapper.map(product);
    }

    @Transactional
    public void updatePrice(PriceChangeDto priceChangeDto) {
        productDao.updatePrice(priceChangeDto.getProductId(), priceChangeDto.getPrice());
    }
}
