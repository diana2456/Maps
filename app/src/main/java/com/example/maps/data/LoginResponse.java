package com.example.maps.data;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
        @SerializedName("status")
        private String status;

        @SerializedName("message")
        private String message;

        @SerializedName("username")
        private String username;

        @SerializedName("password")
        private String password;

        @SerializedName("image")
        private String image;

        @SerializedName("dateofbirth")
        private String dateOfBirth;

        @SerializedName("numberby")
        private String numberBy;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getImage() {
        return image;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNumberBy() {
        return numberBy;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    @SerializedName ("dateofissue")
        private String dateOfIssue;

}
