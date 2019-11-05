package backend.service;

import java.util.List;

import backend.model.User;



public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll ();
}
