package com.pro.wardrobe.Extra;

import com.pro.wardrobe.ApiHelper.APIClient;

public class Config {
    // File upload url (replace the ip with your server address)
    public static final String FILE_UPLOAD_URL = APIClient.BASE_URL1 + "/update_user_profile";

    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
}