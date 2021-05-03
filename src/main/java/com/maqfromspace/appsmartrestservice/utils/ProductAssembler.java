package com.maqfromspace.appsmartrestservice.utils;

import com.maqfromspace.appsmartrestservice.controllers.CustomersController;
import com.maqfromspace.appsmartrestservice.controllers.ProductController;
import com.maqfromspace.appsmartrestservice.entities.Product;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//Assembler for creating EntityModel from Product entity
@Component
public class ProductAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {
    @NonNull
    public EntityModel<Product> toModel(@NonNull Product product) {

        return EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel(),
                linkTo(methodOn(CustomersController.class).getCustomer(product.getCustomerId())).withRel("customer"),
                linkTo(methodOn(CustomersController.class).getProducts(product.getCustomerId())).withRel("allCustomerProducts"),
                linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers"));
    }
}
