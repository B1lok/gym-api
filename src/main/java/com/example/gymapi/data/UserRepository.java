package com.example.gymapi.data;

import com.example.gymapi.domain.Role;
import com.example.gymapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE SIZE(u.roles) = 1 AND :role MEMBER OF u.roles")
    List<User> findCustomers(@Param("role") Role role);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("select u from User u where u.email = :email and u.id != :id")
    Optional<User> isEmailInUse(@Param("email") String email, @Param("id") Long id);

    @Query("select u from User u where u.phoneNumber = :phoneNumber and u.id != :id")
    Optional<User> isPhoneNumberInUse(@Param("phoneNumber") String phoneNumber, @Param("id") Long id);
}