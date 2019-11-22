package backend.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import backend.config.CloudinaryConfig;

import com.cloudinary.utils.ObjectUtils;
/*source code za cloudinary api
https://cloudinary.com/documentation/java_image_upload
https://cloudinary.com/documentation/java_video_upload
demo example https://github.com/katcampbell18/SpringBoot_503
*/
@Service
public class FileUploadService {
	@Autowired
    CloudinaryConfig cloudinaryConfig;
	
	public String imageUpload(MultipartFile file){
		String cloudinaryUploadedImageUrl = new String();
		try {
			
			//IMAGE UPLOAD
			//umesto parametra auto ako tacno znam koji tip fajla cu upload-ovati onda mogu da navedem taj tip fajla
			//Map uploadResult = cloudinaryConfig.upload(file.getBytes(),ObjectUtils.asMap("resource_type","auto")); auto → image
            Map uploadResult = cloudinaryConfig.upload(file.getBytes(),ObjectUtils.asMap("resource_type", "image"));
            cloudinaryUploadedImageUrl = uploadResult.get("url").toString();
            
        } catch (IOException e){
            e.printStackTrace();
            
        }
		return cloudinaryUploadedImageUrl;
	}
	
	public String videoUpload(MultipartFile file){
		String cloudinaryUploadedVideoUrl = new String();
		try {
			
			//VIDEO UPLOAD
			//umesto parametra auto ako tacno znam koji tip fajla cu upload-ovati onda mogu da navedem taj tip fajla
			//Map uploadResult = cloudinaryConfig.upload(file.getBytes(),ObjectUtils.asMap("resource_type","auto")); auto → video
			Map uploadResult = cloudinaryConfig.upload(file.getBytes(),ObjectUtils.asMap("resource_type", "video"));
            cloudinaryUploadedVideoUrl = uploadResult.get("url").toString();
          	
    		
        } catch (IOException e){
            e.printStackTrace();
            
        }
		return cloudinaryUploadedVideoUrl;
	}

}
