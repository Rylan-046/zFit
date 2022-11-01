package edu.cmis.zfit.service;

import edu.cmis.zfit.repository.UserProfileRepository;

import java.io.IOException;
import java.util.Map;

public class UserProfileDefaultService implements UserProfileService{
    private UserProfileRepository userProfileRepository;
    // TODO - Use concurrent map for thread safety
    private Map<String, String> userCredentials;

   public UserProfileDefaultService(UserProfileRepository userProfileRepository) throws IOException {
       this.userProfileRepository = userProfileRepository;
       userCredentials = userProfileRepository.fetchUserCredentialsList();
   }

   @Override
   public void saveCredentials(String userId, String passwd) throws IOException {
       userCredentials.put(userId, passwd);
       userProfileRepository.saveCredentials(userId, passwd);
   }

    @Override
    public boolean isValidCredentials(String userId, String passwd) {
       boolean validCredentials = false;

       if(userCredentials.containsKey(userId)) {
           if(userCredentials.get(userId).equals(passwd)) {
               validCredentials = true;
           } else {
               System.out.println("Password is incorrect for userId: " + userId);
           }
       } else {
           System.out.println("Invalid User! userId: " + userId + " does not exist!");
       }

       return validCredentials;
    }
}