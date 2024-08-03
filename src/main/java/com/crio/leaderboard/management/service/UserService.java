package com.crio.leaderboard.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.crio.leaderboard.management.constants.Badge;
import com.crio.leaderboard.management.entity.UserEntity;
import com.crio.leaderboard.management.exchange.RequestDto;
import com.crio.leaderboard.management.repository.UserRepository;
import com.mongodb.client.result.UpdateResult;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private MongoTemplate mongoTemplate;

    public UserEntity saveUser(RequestDto userDto) {
        return userRepository.save(new UserEntity(userDto.getUserId(), userDto.getUsername()));
    }

    public boolean userIdExists(RequestDto userDto) {
        return userRepository.findById(userDto.getUserId()).isPresent();
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    public int updateUsersScore(Integer userId, Integer score) {

        List<Badge> badges = new ArrayList<>();
        if (score >= 1)
            badges.add(Badge.CODE_NINJA);
        if (score >= 30)
            badges.add(Badge.CODE_CHAMP);
        if (score >= 60)
            badges.add(Badge.CODE_MASTER);

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userId));
        Update update = new Update();
        update.set("score", score);
        update.set("earnedBadges", badges);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserEntity.class);
        return ((int) updateResult.getModifiedCount());
    }

    public void deleteByUserId(Integer userId) {
        userRepository.deleteById(userId);
    }

}
