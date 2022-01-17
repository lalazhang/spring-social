package com.example.springsocial.controller;

import com.example.springsocial.exception.ResourceNotFoundException;
import com.example.springsocial.model.AuthProvider;
import com.example.springsocial.model.Fitness;
import com.example.springsocial.model.Interest;
import com.example.springsocial.model.Leisure;
import com.example.springsocial.model.Religion;
import com.example.springsocial.model.User;
import com.example.springsocial.payload.ApiResponse;
import com.example.springsocial.payload.SignUpRequest;
import com.example.springsocial.payload.UpdateInterestRequest;
import com.example.springsocial.payload.UpdateProfileRequest;
import com.example.springsocial.repository.FitnessRepository;
import com.example.springsocial.repository.InterestRepository;
import com.example.springsocial.repository.LeisureRepository;
import com.example.springsocial.repository.ReligionRepository;
import com.example.springsocial.repository.UserRepository;
import com.example.springsocial.security.CurrentUser;
import com.example.springsocial.security.UserPrincipal;


import java.net.URI;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private FitnessRepository fitnessRepository;
    @Autowired
    private ReligionRepository religionRepository;
    
    @Autowired
    private LeisureRepository leisureRepository;
    
    @GetMapping("/user/me")
    //@PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    } 
    
    @PostMapping("/user/me/updateprofile")
    //@PreAuthorize("hasRole('USER')")
    public String registerUser(@CurrentUser @Valid @RequestBody UpdateProfileRequest updateProfileRequest){
    	String key = updateProfileRequest.getEmail();
    	User user = userRepository.getUserDetails(key);
    	user.setPhoneNumber(updateProfileRequest.getPhoneNumber());
    	user.setGenderPreference(updateProfileRequest.getGenderPreference());
    	user.setLocationPref(updateProfileRequest.getLocationPref());
    	user.setInpersonRelationship(updateProfileRequest.isInpersonRelationship());
    	userRepository.save(user);
    	
    	return user.getLocationPref();
    }
    
    @PostMapping("/user/me/createInterest")
    //@PreAuthorize("hasRole('USER')")
    public Interest updateInterest(@CurrentUser @Valid @RequestBody UpdateInterestRequest updateInterestRequest){
    	String key = updateInterestRequest.getEmail();
    	//User user = userRepository.getUserDetails(key);
    	Interest interest = new Interest(updateInterestRequest.getEmail()) ;
    	interest.setMovie(updateInterestRequest.isMovie());
    	interest.setMusic(updateInterestRequest.isMusic());
    	interest.setName(updateInterestRequest.getName());
    	interest.setVideogame(updateInterestRequest.isVideoGame());
    	interest.setPersonalType(updateInterestRequest.getPersonalityType());
    	interest.setSport(updateInterestRequest.isSport());   	
    	interestRepository.save(interest);
    	
    	//fitness
    	Fitness fitness = new Fitness();
    	fitness.setEmail(updateInterestRequest.getEmail());
    	fitness.setGym(updateInterestRequest.isGym());
    	fitness.setCalisthenics(updateInterestRequest.isCalisthenics());
    	fitness.setCycling(updateInterestRequest.isCycling());
    	fitness.setDancing(updateInterestRequest.isDancing());
    	fitness.setHiking(updateInterestRequest.isHiking());
    	fitness.setHorseRiding(updateInterestRequest.isHorseRiding());
    	fitness.setRockClimbing(updateInterestRequest.isRockClimbing());
    	fitness.setWeightLifting(updateInterestRequest.isWeightLifting());
    	fitness.setYoga(updateInterestRequest.isYoga());
    	fitnessRepository.save(fitness);
    	
    	//religion
    	Religion religion = new Religion();
    	religion.setEmail(updateInterestRequest.getEmail());
    	religion.setAnimism(updateInterestRequest.isAnimism());
    	religion.setAtheism(updateInterestRequest.isAtheism());
    	religion.setMonotheism(updateInterestRequest.isMonotheism());
    	religion.setPolytheism(updateInterestRequest.isPolytheism());
    	religion.setTotemism(updateInterestRequest.isTotemism());
    	religionRepository.save(religion);
    	
    	//leisure
    	Leisure leisure = new Leisure();
    	leisure.setEmail(updateInterestRequest.getEmail());
    	leisure.setCognitive(updateInterestRequest.isCognitive());
    	leisure.setPhysical(updateInterestRequest.isPhysical());
    	leisure.setSocial(updateInterestRequest.isSocial());
    	leisureRepository.save(leisure);
    	return interest;
  
    }
     
    @PostMapping("/user/me/changeInterest")
    //@PreAuthorize("hasRole('USER')")
    public Interest changeInterest(@CurrentUser @Valid @RequestBody UpdateInterestRequest updateInterestRequest){
    	//general interest
    	String email = updateInterestRequest.getEmail();
    	Interest interest = interestRepository.getByEmail(email);
    	interest.setMovie(updateInterestRequest.isMovie());
    	interest.setMusic(updateInterestRequest.isMusic());
    	interest.setName(updateInterestRequest.getName());
    	interest.setVideogame(updateInterestRequest.isVideoGame());
    	interest.setPersonalType(updateInterestRequest.getPersonalityType());
    	interest.setSport(updateInterestRequest.isSport());   	
    	interestRepository.save(interest);
  
    	//fitness
    	Fitness fitness = fitnessRepository.findByEmail(email);
    	fitness.setGym(updateInterestRequest.isGym());
       	fitness.setCalisthenics(updateInterestRequest.isCalisthenics());
    	fitness.setCycling(updateInterestRequest.isCycling());
    	fitness.setDancing(updateInterestRequest.isDancing());
    	fitness.setHiking(updateInterestRequest.isHiking());
    	fitness.setHorseRiding(updateInterestRequest.isHorseRiding());
    	fitness.setRockClimbing(updateInterestRequest.isRockClimbing());
    	fitness.setWeightLifting(updateInterestRequest.isWeightLifting());
    	fitness.setYoga(updateInterestRequest.isYoga());
    	fitnessRepository.save(fitness);
    	
    	//religion
    	Religion religion = religionRepository.getByEmail(email);
    	religion.setAnimism(updateInterestRequest.isAnimism());
    	religion.setAtheism(updateInterestRequest.isAtheism());
    	religion.setMonotheism(updateInterestRequest.isMonotheism());
    	religion.setPolytheism(updateInterestRequest.isPolytheism());
    	religion.setTotemism(updateInterestRequest.isTotemism());
    	religionRepository.save(religion);
    	
    	//leisure
    	Leisure leisure = leisureRepository.getByEmail(email);
    	leisure.setCognitive(updateInterestRequest.isCognitive());
    	leisure.setPhysical(updateInterestRequest.isPhysical());
    	leisure.setSocial(updateInterestRequest.isSocial());
    	leisureRepository.save(leisure);
    	return interest;

 
    	
    }
   
    
    @GetMapping("/user/me/getProfile")
    public User getCurrentUserInfo(@CurrentUser @Valid @RequestBody String email) {
    	//To run postman use text and type string: zhanglala0208@gmail.com 
    	User user = userRepository.getUserDetails(email);
    	return user;
//    	return user.getName();
    	
    }
  
}
