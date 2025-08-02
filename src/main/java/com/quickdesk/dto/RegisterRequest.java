package com.quickdesk.dto;



import com.quickdesk.model.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role; // END_USER, AGENT, ADMIN
}

