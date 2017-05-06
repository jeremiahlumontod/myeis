package guru.springframework.controllers;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;


/**
 * Created by jeremiahlumontod on 4/22/17.
 */
@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    @Qualifier(value = "productServiceJPADaoImpl")
    public void setProductService(HttpServletRequest request, ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/products")
    public String listProducts(HttpServletRequest request, Model model) throws MalformedURLException {
        model.addAttribute("products",productService.listAll());
        return "/product/products";
    }

    @RequestMapping("/product/{id}")
    public String getProduct(HttpServletRequest request, @PathVariable Integer id, Model model) throws MalformedURLException {
        model.addAttribute("product", productService.getById(id));
        return "/product/product";
    }

    @RequestMapping("/product/edit/{id}")
    public String edit(HttpServletRequest request, @PathVariable Integer id, Model model) throws MalformedURLException {
        model.addAttribute("product", productService.getById(id));
        return "/product/productform";
    }

    @RequestMapping("/product/delete/{id}")
    public String delete(HttpServletRequest request, @PathVariable Integer id) throws MalformedURLException {
        productService.delete(id);
        return "redirect:/products";
    }


    @RequestMapping("/product/new")
    public String newProduct(HttpServletRequest request, Model model) throws MalformedURLException {
        Product product = new Product();
        product = productService.saveOrUpdate(product);
        //model.addAttribute("product", new Product());
        model.addAttribute("product", product);
        return "/product/productform";
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public String saveOrUpdateProduct(HttpServletRequest request, Product product) throws MalformedURLException {
        Product savedProduct = productService.saveOrUpdate(product);
        //return "redirect:/product/" + savedProduct.getId();
        return "redirect:/product/" + product.getId();
    }


}
