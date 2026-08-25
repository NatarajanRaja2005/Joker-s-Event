package com.example.Joker.s.Event.Event.repository;

import com.example.Joker.s.Event.Event.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Long>{
}
