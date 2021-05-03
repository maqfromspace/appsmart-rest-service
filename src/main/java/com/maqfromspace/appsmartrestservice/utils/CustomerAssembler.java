package com.maqfromspace.appsmartrestservice.utils;

import com.maqfromspace.appsmartrestservice.controllers.CustomersController;
import com.maqfromspace.appsmartrestservice.entities.Customer;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//Assembler for creating EntityModel from Customer entity
@Component
public class CustomerAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {
    @NonNull
    public EntityModel<Customer> toModel(@NonNull Customer customer) {

        return EntityModel.of(customer,
                linkTo(methodOn(CustomersController.class).getCustomer(customer.getId())).withSelfRel(),
                linkTo(methodOn(CustomersController.class).getProducts(customer.getId())).withRel("products"),
                linkTo(methodOn(CustomersController.class).getCustomers()).withRel("customers"));
    }
}
