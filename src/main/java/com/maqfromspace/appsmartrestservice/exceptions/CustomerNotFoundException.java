package com.maqfromspace.appsmartrestservice.exceptions;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(UUID id){
        super("Could not found customer with id " + id);
    }
}
