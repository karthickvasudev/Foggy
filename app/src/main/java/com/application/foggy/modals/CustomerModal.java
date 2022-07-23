package com.application.foggy.modals;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerModal {
    private String id;
    private String name;
    private String phoneNumber;
    private String address;
    private String city;
    private String pinCode;
    private String createOn;
    private String createUserName;
    private String createUserEmail;
    private String lastUpdatedOn;
    private String lastUpdatedUserName;
    private String lastUpdatedEmail;
}
