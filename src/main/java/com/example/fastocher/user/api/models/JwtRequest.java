package com.example.fastocher.user.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;

//    @NotBlank(message = "Login can't be null")
   // @Pattern(regexp = "[a-z0-9A-Z_]+",message = "Not available characters for login! Only a-z, 0-9, '_' available")
    private String name;

//    @NotBlank(message = "Password can't be null")
//    @Size(min = 5,max = 15,message = "Password must be between 5 and 15 characters long")
//    @Pattern(regexp = "[^ ]+",message = "Password can't include space")
    private String password;

}
