package backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.model.Dog;
import backend.repository.DogRepository;

@Service
public class DogService {
	@Autowired
	private DogRepository dogRepository;
	
	
	public Dog save(Dog d) {
		return dogRepository.save(d);
	}

}
