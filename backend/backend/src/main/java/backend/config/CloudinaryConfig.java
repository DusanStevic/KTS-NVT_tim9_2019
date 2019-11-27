package backend.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;

/*source code za cloudinary api
https://cloudinary.com/documentation/java_image_upload
https://cloudinary.com/documentation/java_video_upload
demo example https://github.com/katcampbell18/SpringBoot_503
*/
@Component
public class CloudinaryConfig {
    private Cloudinary cloudinary;
    @Autowired
    public CloudinaryConfig(@Value("${cloud.key}") String key,@Value("${cloud.secret}") String secret,@Value("${cloud.name}") String cloud){
        cloudinary = Singleton.getCloudinary();
        cloudinary.config.cloudName = cloud;
        cloudinary.config.apiSecret = secret;
        cloudinary.config.apiKey = key;
    }

    
    //Chunked file upload.This method must be used to upload files larger than 100 MB in size.
    public Map uploadFile(Object file, Map options){
        try{
            return cloudinary.uploader().uploadLarge(file, options);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //Following code would delete the uploaded file assigned with the public ID
    public Map deleteFile(String publicId, Map options) {
    	try{
            return cloudinary.uploader().destroy(publicId, options);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    
    

    

    
   
}