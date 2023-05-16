package com.MassinissaSaoudi.AWS_Image_Upload.profile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {

    private UUID userProfileID;
    private String userName;
    private String userProfileLink;
    public UserProfile(UUID userProfileID, String userName, String userProfileLink){
        this.userProfileID =userProfileID;
        this.userName=userName;
        this.userProfileLink = userProfileLink;
    }

    public UUID getUserProfileID() {
        return userProfileID;
    }

    public String getUserName() {
        return userName;
    }

    public Optional<String> getUserProfileLink() {
        return Optional.ofNullable(userProfileLink);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile that)) return false;
        return Objects.equals(getUserProfileID(),that.getUserProfileID()) && Objects.equals(getUserName(),that.getUserName()) && Objects.equals(getUserProfileLink(),that.getUserProfileLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserProfileID(), getUserName(), getUserProfileLink());
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserProfileLink(String userProfileLink) {
        this.userProfileLink = userProfileLink;
    }
}
