package com.MassinissaSaoudi.AWS_Image_Upload.profile;

import com.MassinissaSaoudi.AWS_Image_Upload.datastore.FakesuerProfileDatastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProfileDataAccessService {
    private final FakesuerProfileDatastore fakeuserprofileDataStore;
    @Autowired
    public UserProfileDataAccessService(FakesuerProfileDatastore fakeuserprofileDataStore){
        this.fakeuserprofileDataStore = fakeuserprofileDataStore;
    }
    List<UserProfile> getUserProfiles(){
        return fakeuserprofileDataStore.getUserProfiles();
    }
}
