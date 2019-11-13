package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.UserModel;

public interface UserService{
    UserModel addUser(UserModel user);
    
    String encrypt(String password);

    UserModel findByUsername(String username);

    Boolean isEquals(String inputPassword, String password);

    UserModel updatePassword(UserModel user);
}