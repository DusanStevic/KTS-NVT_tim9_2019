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
            Map uploadResult = cloudinaryConfig.uploadFile(file.getBytes(),ObjectUtils.asMap("resource_type", "image"));
            cloudinaryUploadedImageUrl = uploadResult.get("url").toString();
            
        } catch (IOException e){
            e.printStackTrace();
            
        }
		return cloudinaryUploadedImageUrl;
	}
	
	public void imageDelete(String url) throws IOException{
		//IMAGE DELETE
		//funkcija za brisanje slika
		
		/*splitovanje url kako bih izvukao publicId iz url-a
		https://res.cloudinary.com/djxkexzcr/image/upload/v1574108286/lf4ddnka9rqe62creizz.jpg
		*/
		String[] splitedUrl = url.split("/");
		/* uzimam poslednji element iz url-a tj. uploadovani file lf4ddnka9rqe62creizz.jpg
		 */
	    String file  = splitedUrl[splitedUrl.length-1];
	    /*
	     razbijam file na njegovo ime na cloud-u(publicId) i ekstenziju lf4ddnka9rqe62creizz i jpg
	     */
	    String[] fileAndExtension = file.split("\\.");
	    /*
	     kupim publicId lf4ddnka9rqe62creizz
	     */
	    String publicId = fileAndExtension[0];
		cloudinaryConfig.deleteFile(publicId,ObjectUtils.asMap("invalidate", true));
		
	}
	
	public String videoUpload(MultipartFile file){
		String cloudinaryUploadedVideoUrl = new String();
		try {
			
			//VIDEO UPLOAD
			//umesto parametra auto ako tacno znam koji tip fajla cu upload-ovati onda mogu da navedem taj tip fajla
			//Map uploadResult = cloudinaryConfig.upload(file.getBytes(),ObjectUtils.asMap("resource_type","auto")); auto → video
			Map uploadResult = cloudinaryConfig.uploadFile(file.getBytes(),ObjectUtils.asMap("resource_type", "video"));
            cloudinaryUploadedVideoUrl = uploadResult.get("url").toString();
          	
    		
        } catch (IOException e){
            e.printStackTrace();
            
        }
		return cloudinaryUploadedVideoUrl;
	}
	
	public void videoDelete(String url) throws IOException{
		//VIDEO DELETE
		//funkcija za brisanje videa
		
		/*splitovanje url kako bih izvukao publicId iz url-a
		https://res.cloudinary.com/djxkexzcr/image/upload/v1574108286/lf4ddnka9rqe62creizz.mp4
		*/
		String[] splitedUrl = url.split("/");
		/* uzimam poslednji element iz url-a tj. uploadovani file lf4ddnka9rqe62creizz.mp4
		 */
	    String file  = splitedUrl[splitedUrl.length-1];
	    /*
	     razbijam file na njegovo ime na cloud-u(publicId) i ekstenziju lf4ddnka9rqe62creizz i mp4
	     */
	    String[] fileAndExtension = file.split("\\.");
	    /*
	     kupim publicId lf4ddnka9rqe62creizz
	     */
	    String publicId = fileAndExtension[0];
		cloudinaryConfig.deleteFile(publicId,ObjectUtils.asMap("resource_type", "video"));
		
	}

}
