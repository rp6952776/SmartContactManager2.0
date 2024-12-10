package com.smartcontact.manager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "Contact-table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;



    private String phoneNumber;
    private String address;
    @Column(length = 10000)
    private String description;
    private String picture;
    private boolean favorite = false;
    private String websiteLink;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    private User user;


}

