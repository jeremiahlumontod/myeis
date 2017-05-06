package guru.springframework.services;

import guru.springframework.domain.Customer;
import guru.springframework.domain.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

/**
 * Created by jeremiahlumontod on 4/26/17.
 */
@Service
public class CustomerServiceJPADaoImpl implements CustomerService{

    private EntityManagerFactory emf;

    @PersistenceUnit
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public List<Customer> listAll() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("from Customer", Customer.class).getResultList();
    }

    @Override
    public Customer getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Customer.class,id);
    }

    @Override
    public Customer saveOrUpdate(Customer domainObject) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Customer customer = em.merge(domainObject);
        em.getTransaction().commit();
        return customer;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Customer.class,id));
        em.getTransaction().commit();
    }
}
