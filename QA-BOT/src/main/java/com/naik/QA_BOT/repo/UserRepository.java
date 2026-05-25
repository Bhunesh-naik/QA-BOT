package com.naik.QA_BOT.repo;




import com.naik.QA_BOT.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
