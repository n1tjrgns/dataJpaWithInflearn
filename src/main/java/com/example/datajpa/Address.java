package com.example.datajpa;

import javax.persistence.Embeddable;

//Composite value 타입 다루기
@Embeddable
public class Address {

    private String street;

    private String city;

    private String state;

    private String zipCode;


}
