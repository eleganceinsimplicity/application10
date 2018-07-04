package com.test10.rest.enpoints;

import com.test10.models.ProductDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rest")
public class ProductsController {


    @RequestMapping(value="/products")
    public ProductDTO getProducts(HttpServletRequest httpServletRequest) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Puffs Tissue");
        productDTO.setPrice(10.543);
        productDTO.setQuantity(5);
        productDTO.setRelation("self");
        productDTO.setUrl(httpServletRequest.getRequestURL().toString());
        return productDTO;
    }
}
