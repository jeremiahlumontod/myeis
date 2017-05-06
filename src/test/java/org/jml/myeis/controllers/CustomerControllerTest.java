package org.jml.myeis.controllers;

import org.hamcrest.Matchers;
import org.jml.myeis.controllers.CustomerController;
import org.jml.myeis.domain.Customer;
import org.jml.myeis.domain.Product;
import org.jml.myeis.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(secure=false)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    @Qualifier(value = "customerServiceImpl")
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    Customer customer1;
    @Before
    public void setUpCustomers() throws Exception{

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        customer1 = new Customer();
        customer1.setId(1);
        customer1.setFirstname("customer1 firstname");
        customer1.setLastname("customer1 lastname");
        customer1.setAddressLine1("address line 1");
        customer1.setAddressLine2("address line 2");
        customer1.setCity("city");
        customer1.setState("state");
        customer1.setZipCode("zip");
        customer1.setEmail("email");
        customer1.setPhoneNumber("phone");

    }

    @Test
    public void testList() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        when(customerService.listAll()).thenReturn((List)customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("/customer/list"))
                .andExpect(model().attribute("customers",hasSize(2)))
                .andDo(print());
    }

    @Test
    public void testShowCustomer() throws Exception {

        when(customerService.getById(1)).thenReturn(customer1);

        MvcResult result= mockMvc.perform(get("/customer/show/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/customer/show"))
                .andExpect(model().attribute("customer",instanceOf(Customer.class)))
                .andReturn();

    }

    @Test
    public void testEdit() throws Exception {

        when(customerService.getById(1)).thenReturn(customer1);

        MvcResult result = mockMvc.perform(get("/customer/edit/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/customer/customerform"))
                .andExpect(model().attribute("customer",instanceOf(Customer.class)))
                .andReturn();

    }

    @Test
    public void testNew() throws Exception {

        verifyZeroInteractions(customerService);

        MvcResult result = mockMvc.perform(get("/customer/new", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/customer/customerform"))
                //.andExpect(model().attribute("customer",instanceOf(Customer.class)))
                .andReturn();

    }

    @Test
    public void testSaveOrUpdate() throws Exception {

        //when(customerService.saveOrUpdate(Matchers.<Customer>any<>())).thenReturn(customer1);
        when(customerService.getById(1)).thenReturn(customer1);

        MvcResult result = mockMvc.perform(post("/customer")
                .param("id",customer1.getId().toString())
                .param("firstname",customer1.getFirstname())
                .param("lastname",customer1.getLastname()))
                .andReturn();

        //MockHttpServletResponse response = result.getResponse();

        //assertEquals(HttpStatus.CREATED.value(), response.getStatus());


    }

}
