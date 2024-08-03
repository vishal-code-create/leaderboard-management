package com.crio.leaderboard.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crio.leaderboard.management.constants.Badge;
import com.crio.leaderboard.management.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, Integer> {

//    public Optional<UserEntity> updateScoreAndBadgeByUserId(Integer score, List<Badge> badges, Integer userId);
    
}
