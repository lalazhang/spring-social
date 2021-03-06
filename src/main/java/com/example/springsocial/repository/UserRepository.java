package com.example.springsocial.repository;


import com.example.springsocial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query(value = "select * from users where email = :email", nativeQuery = true)
    User getUserDetails(String email);

  
    /*
     * 
     * @Query(value = "select * from job where customer_id = :id", nativeQuery =  true)
	List<Job> getJobsByCustomer(int id);
     */
}
