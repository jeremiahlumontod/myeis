package guru.springframework.services;

import guru.springframework.domain.Customer;
import guru.springframework.domain.DomainObject;
import guru.springframework.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jeremiahlumontod on 4/23/17.
 */
@Service
public class CustomerServiceImpl extends AbstractMapService implements CustomerService {



    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Customer getById(Integer id) {
        return (Customer) super.getById(id);
    }

    @Override
    public Customer saveOrUpdate(Customer domainObject) {
        return (Customer) super.saveOrUpdate(domainObject);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

    @Override
    protected void loadDomainObjects() {
        domainMap = new HashMap<>();
        Customer customer1 = new Customer();
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
        domainMap.put(1,customer1);

        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setFirstname("customer2 firstname");
        customer2.setLastname("customer2 lastname");
        customer2.setAddressLine1("address line 1");
        customer2.setAddressLine2("address line 2");
        customer2.setCity("city");
        customer2.setState("state");
        customer2.setZipCode("zip");
        customer2.setEmail("email");
        customer2.setPhoneNumber("phone");
        domainMap.put(2,customer2);

        Customer customer3 = new Customer();
        customer3.setId(3);
        customer3.setFirstname("customer1 firstname");
        customer3.setLastname("customer1 lastname");
        customer3.setAddressLine1("address line 1");
        customer3.setAddressLine2("address line 2");
        customer3.setCity("city");
        customer3.setState("state");
        customer3.setZipCode("zip");
        customer3.setEmail("email");
        customer3.setPhoneNumber("phone");
        domainMap.put(3,customer3);

    }

    public CustomerServiceImpl() {
        loadDomainObjects();
    }

    private Integer getNextKey() {
        if(domainMap==null) {
            return 1;
        }
        if(domainMap.size()<1) {
            return 1;
        }
        return Collections.max(domainMap.keySet()) + 1;
    }

}
