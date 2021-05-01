package com.maqfromspace.appsmartrestservice.exceptions;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(UUID id){
        super("Could not found product with id " + id);
    }
}
