package com.MassinissaSaoudi.AWS_Image_Upload.profile;

import com.MassinissaSaoudi.AWS_Image_Upload.bucket.BucketImageUploader;
import com.MassinissaSaoudi.AWS_Image_Upload.fileStore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserProfileService {
    public byte[] downloadUserProfileImage(UUID userProfileId){
     UserProfile user = getUser(userProfileId);
     String path= String.format("%s/%s", BucketImageUploader.PROFILE_IMAGE.getBucketName(), user.getUserProfileID());
     return user.getUserProfileLink().map(key->fileStore.download(path,key.replace(path,""))).orElse(new byte[0]);

    }
    private final UserProfileDataAccessService userProfileDataAcessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAcessService, FileStore fileStore){
        this.userProfileDataAcessService = userProfileDataAcessService;
        this.fileStore = fileStore;
    }
    List<UserProfile> getUserProfiles(){
        return userProfileDataAcessService.getUserProfiles();
    }
    void uploadUserProfileImage(UUID userProfileId, MultipartFile file){
        //1) Image must not be empty
        isFileEmpty(file);
        //2) File must be an image
        isFileAnImage(file);
        //3) User Must Exist in DB
       UserProfile user = getUser(userProfileId);
        //4) Grab Metadata from file
        Map<String, String> metadata = grabMetadata(file);
        //5) Store Image in S3 and update DB with S3 image link
        String path = String.format("%s/%s", BucketImageUploader.PROFILE_IMAGE.getBucketName(), userProfileId);
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        try {
            fileStore.Save(path,filename,Optional.of(metadata), file.getInputStream());
            user.setUserProfileLink(path+""+filename);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }



    private static Map<String, String> grabMetadata(MultipartFile file) {
        Map<String,String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private UserProfile getUser(UUID userProfileId) {
        return userProfileDataAcessService
                .getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileID().equals(userProfileId))
                .findAny()
                .orElseThrow(()->new IllegalStateException(String.format("userProfile not found "+ userProfileId)));
    }

    private static void isFileAnImage(MultipartFile file) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType()).contains(file.getContentType())){
            throw new IllegalStateException("File must be an image");
        }
    }

    private static void isFileEmpty(MultipartFile file) {
        if(file.isEmpty()){
            throw new IllegalStateException("Cannot upload an empty file ["+ file.getSize()+"]");
        }
    }
}
