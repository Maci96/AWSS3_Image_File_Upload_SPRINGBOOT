package com.MassinissaSaoudi.AWS_Image_Upload.bucket;

public enum BucketImageUploader {
    PROFILE_IMAGE("springimageuploader");
    private final String bucketName;

    BucketImageUploader(String bucketName){
         this.bucketName = bucketName;
     }

    public String getBucketName() {
        return bucketName;
    }
}
