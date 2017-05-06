package org.jml.myeis.controllers;

import org.jml.myeis.domain.Customer;
import org.jml.myeis.domain.Product;
import org.jml.myeis.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jeremiahlumontod on 4/23/17.
 */
@Controller
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    @Qualifier(value = "customerServiceJPADaoImpl")
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping("/customers")
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.listAll());
        return "/customer/list";
    }

    @RequestMapping("/customer/show/{id}")
    public String showCustomer(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.getById(id));
        return "/customer/show";
    }

    @RequestMapping("/customer/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.getById(id));
        return "/customer/customerform";
    }

    @RequestMapping("/customer/delete/{id}")
    public String delete(@PathVariable Integer id) {
        customerService.delete(id);
        return "redirect:/customers";
    }

    @RequestMapping("/customer/new")
    public String newCustomer(Model model) {
        Customer customer = new Customer();
        customer = customerService.saveOrUpdate(customer);
        //model.addAttribute("customer", new Customer());
        model.addAttribute("customer", customer);
        return "/customer/customerform";
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public String saveOrUpdateProduct(Customer customer) {
        Customer savedCustomer = customerService.saveOrUpdate(customer);
        //return "redirect:/customer/show/" + savedCustomer.getId();
        return "redirect:/customer/show/" + customer.getId();
    }


}
