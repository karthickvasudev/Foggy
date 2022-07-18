package com.application.foggy.modals;

import com.application.foggy.constants.Roles;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Builder
@Getter
@Setter
@ToString
public class LoginSignupModal {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String photoUrl;
    private Roles role;
}
