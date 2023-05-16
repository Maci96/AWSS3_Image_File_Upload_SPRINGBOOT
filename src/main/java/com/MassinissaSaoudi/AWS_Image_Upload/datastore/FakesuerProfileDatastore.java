package com.MassinissaSaoudi.AWS_Image_Upload.datastore;

import com.MassinissaSaoudi.AWS_Image_Upload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Repository
public class FakesuerProfileDatastore {
    private static final List<UserProfile> USER_PROFILES= new ArrayList<>();
    static{
        USER_PROFILES.add(new UserProfile(UUID.fromString("c5307e40-d5b7-40fd-8255-c1b26ef970fd"), "massinissasaoudi",null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("7ab4629a-ce32-45e3-a5c9-c03d8f5bedd2"), "rexsaoudi",null));
    }
    public List<UserProfile> getUserProfiles(){
        return USER_PROFILES;
    }
}
