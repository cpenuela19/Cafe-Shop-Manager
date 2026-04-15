package com.charlytech.cafemanager.repositories;

import com.charlytech.cafemanager.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.charlytech.cafemanager.entities.UserEntity.Role;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    //first approach when I was starting into springboot
    // better and more accurate implementation will be found in any other repository class...
    // basically change List to Optional... or even just UserEntity but if the object is null
    // it could lead to problems, so Optional would be the beast approach...
    // this was done approx. a month ago...e

    List<UserEntity> findByUsername(String username);
    List<UserEntity> findByEmail(String email);
    List<UserEntity> findByRole(Role role);
}
