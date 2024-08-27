package ing.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ing.product_service.ProductServiceApplication;
import ing.product_service.dto.PriceChangeDto;
import ing.product_service.dto.ProductAddDto;
import ing.product_service.utils.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql("/init-data.sql")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProductServiceApplication.class})
public class ProductControllerTest {

    private static final String POST_PRODUCT_INSERT = "/api/v1/products/admin/insert";
    private static final String GET_PRODUCT_FIND = "/api/v1/products/find/1";
    private static final String PUT_PRODUCT_CHANGE = "/api/v1/products/admin/price/change";

    private static final String GET_PRODUCT_UPDATED = "/api/v1/products/find/2";
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void addProduct() throws Exception {
        final ProductAddDto productAddDto = ProductAddDto.builder()
                .name("new product added")
                .description("new product description")
                .build();

        mockMvc.perform(post(POST_PRODUCT_INSERT)
                        .content(objectMapper.writeValueAsString(productAddDto))
                        .header(CONTENT_TYPE, APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.productName").value("new product added"));
    }

    @Test
    public void searchProduct() throws Exception {
        mockMvc.perform(get(GET_PRODUCT_FIND))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(FileUtils.readFileAsString("json/get-product-find.json")));
    }

    @Test
    public void updatePrice() throws Exception {
        final PriceChangeDto priceChangeDto = PriceChangeDto.builder()
                .productId(2L)
                .price(50.0)
                .build();

        mockMvc.perform(put(PUT_PRODUCT_CHANGE)
                        .content(objectMapper.writeValueAsString(priceChangeDto))
                        .header(CONTENT_TYPE, APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(GET_PRODUCT_UPDATED))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(content().json(FileUtils.readFileAsString("json/put-update-product.json")));
    }
}