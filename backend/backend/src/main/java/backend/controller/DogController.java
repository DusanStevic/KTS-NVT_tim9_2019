package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import backend.config.CloudinaryConfig;
import backend.model.Dog;
import backend.service.DogService;
import backend.service.FileUploadService;

@RestController
@RequestMapping("/api")
public class DogController {
	@Autowired
	DogService dogService;
	/*@Autowired
    CloudinaryConfig cloudc;*/

	@Autowired
	FileUploadService fileUploadService;
	
	
	/* saving address */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SYS_ADMIN')")
	@RequestMapping(value = "/dogs", method = RequestMethod.POST)
	public ResponseEntity<Dog> createDog(@RequestParam("file")MultipartFile file) {
		System.out.println("ULETEO SAM U DODAVANJE KEROVA");
		Dog dog = new Dog();
		/*try {
			
			//ovo radi
            Map uploadResult = cloudc.upload(file.getBytes(),ObjectUtils.asMap("resource_type", "image"));
            dog.setImage(uploadResult.get("url").toString());
			
			//ZA VIDEO
			//Map uploadResult = cloudc.upload(file.getBytes(),ObjectUtils.asMap("resource_type", "video"));
            //dog.setImage(uploadResult.get("url").toString());
            
    		//dog.setName(dogDTO.getName());
    		//dog.setSpecies(dogDTO.getSpecies());
    		
    		dogService.save(dog);
        } catch (IOException e){
            e.printStackTrace();
            
        }*/
		//dog.setName(dogDTO.getName());
		//dog.setSpecies(dogDTO.getSpecies());
		dog.setImage(fileUploadService.imageUpload(file));
		dogService.save(dog);
		return new ResponseEntity<>(dog, HttpStatus.CREATED);	
	}

}
