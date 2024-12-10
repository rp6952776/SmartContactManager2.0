package com.smartcontact.manager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "User-table")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "contacts")
public class User implements UserDetails {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(name = "user_name",nullable = false)
    @NotBlank(message = "Name Must be Required!!")
    @Size(min = 3,max = 20,message = "Minimum 3 character required!!")
    private String name;
    @Column(unique = true,nullable = false)
    @Email(message = "Invalid Email Address!!")
    private String email;
    @NotBlank(message = "Password Must Be Required!!")
    @Size(min = 6,message = "Minimum 6 character Required!!")
    @Getter(AccessLevel.NONE)
    private String password;
    @Column(length = 10000)
    @NotBlank(message = "About is Required!!")
    private String about;
    @Column(length = 10000)
    private String profile;
    private long phoneNumber;
    @Builder.Default
    private Providers provider = Providers.SELF;
    private String providerId;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
