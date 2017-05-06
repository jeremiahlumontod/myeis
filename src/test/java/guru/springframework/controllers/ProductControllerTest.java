package guru.springframework.controllers;

import guru.springframework.domain.Customer;
import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;
import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    @Qualifier(value = "productServiceImpl")
    private ProductService productService;

    private Product product1;

    @InjectMocks
    private ProductController productController;

    @Before
    public void setUpProduct() throws Exception{

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        product1 = new Product();
        product1.setId(99);
        product1.setDescription("product99");
        product1.setPrice(new BigDecimal("18.95"));
        product1.setImgUrl("jpg1");
    }

    @Test
    public void testList() throws Exception {
        assertThat(this.productService).isNotNull();
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                //.andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("/product/products"))
                //.andExpect(content().string(Matchers.containsString("Spring Core Online Tutorial - List Products")))
                .andDo(print());
    }

    @Test
    public void testShowProduct() throws Exception {

        assertThat(this.productService).isNotNull();

        when(productService.getById(1)).thenReturn(product1);

        MvcResult result = mockMvc.perform(get("/product/{id}/", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/product/product"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", hasProperty("id", is(99))))
                .andExpect(model().attribute("product", hasProperty("description", is("product99"))))
                .andExpect(model().attribute("product", hasProperty("price", is(new BigDecimal("18.95")))))
                .andExpect(model().attribute("product", hasProperty("imgUrl", is("jpg1"))))
                .andReturn();
    }


    @Test
    public void testEdit() throws Exception {

        Product product = new Product();
        product.setId(99);
        product.setDescription("product99");
        product.setPrice(new BigDecimal("18.95"));
        product.setImgUrl("jpg1");

        when(productService.getById(1)).thenReturn(product);

        MvcResult result = mockMvc.perform(get("/product/edit/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/product/productform"))
                .andExpect(model().attribute("product",instanceOf(Product.class)))
                .andReturn();

    }


    @Test
    public void testNewProduct() throws Exception {

        MvcResult result = mockMvc.perform(get("/product/new", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/product/productform"))
                //.andExpect(model().attributeExists("product"))
                //.andExpect(model().attribute("product", instanceOf(Product.class)))
                .andReturn();

    }


    @Test
    public void testSaveOrUpdate() throws Exception {

        //when(productService.getById(1)).thenReturn(product1);

        MvcResult result = mockMvc.perform(post("/product")
                .param("id",product1.getId().toString())
                .param("description",product1.getDescription())
                .param("imgUrl",product1.getImgUrl())
                .param("price",product1.getPrice().toString()))
                .andReturn();

    }


    @Test
    public void test_create_product_success() throws Exception {


        Product product = new Product();
        product.setId(99);
        product.setDescription("product99");
        product.setPrice(new BigDecimal("18.95"));
        product.setImgUrl("jpg1");

        when(productService.getById(product.getId())).thenReturn(null);

        //doNothing().when(productService).saveOrUpdate(product);

        mockMvc.perform(
                post("/product")
                .param("id",product.getId().toString())
                .param("description",product.getDescription())
                .param("imgUrl",product.getImgUrl())
                .param("price",product.getPrice().toString()))
                //.andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/product/99")));

        //verify(productService, times(1)).saveOrUpdate(product);

    }
}
