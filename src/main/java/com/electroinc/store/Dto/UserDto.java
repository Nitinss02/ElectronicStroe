package com.electroinc.store.Dto;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String UserId;
    @Size(min = 3, max = 15, message = "name should be more than 3 charater")
    private String name;
    @Email(message = "Email is invalid!!")
    @Pattern(regexp = "^[a-z0-9][a-z0-9._]+@([a-z0-9]+\\.)+[a-z]{2,3}$", message = "email pattern is invalid")
    private String email;

    @NotBlank(message = "Password is requried")
    private String password;
    @Size(min = 4, max = 6, message = "Gender is invalid")
    private String Gender;

    @NotBlank(message = "Image is requried")
    private String imageName;

    private Set<RoleDto> roles = new HashSet<>();
}
