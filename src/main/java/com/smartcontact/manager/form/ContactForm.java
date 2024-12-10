package com.smartcontact.manager.form;

import com.smartcontact.manager.validators.ValidFile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import org.springframework.web.multipart.MultipartFile;


public class ContactForm {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Address [ example@gmail.com ]")
    private String email;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phoneNumber;

    @Override
    public String toString() {
        return "ContactForm{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", favorite=" + favorite +
                ", websiteLink='" + websiteLink + '\'' +
                ", contactImage=" + contactImage +
                ", picture='" + picture + '\'' +
                '}';
    }

    @NotBlank(message = "Address is required")
    private String address;

    private String description;

    private boolean favorite;

    private String websiteLink;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
// annotation create karenge jo file validate
    // size
    // resolution

    @ValidFile(message = "Invalid File")
    private MultipartFile contactImage;

    private String picture;

    public @NotBlank(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Invalid Email Address [ example@gmail.com ]") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid Email Address [ example@gmail.com ]") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Phone Number is required") @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotBlank(message = "Phone Number is required") @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @NotBlank(message = "Address is required") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "Address is required") String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public MultipartFile getContactImage() {
        return contactImage;
    }

    public void setContactImage(MultipartFile contactImage) {
        this.contactImage = contactImage;
    }

    public ContactForm(String name, String email, String phoneNumber, String address, String description, boolean favorite, String websiteLink, MultipartFile contactImage) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = description;
        this.favorite = favorite;
        this.websiteLink = websiteLink;
        this.contactImage = contactImage;
    }

    public ContactForm() {
    }

}
