package com.example.onlineplatform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Builder
@Data
@Valid
public class UserDto implements Serializable {


    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("phone")
    private String phone;

    @Email
    @JsonProperty("email")
    private String email;

    @JsonProperty("image")
    private String image;
}
