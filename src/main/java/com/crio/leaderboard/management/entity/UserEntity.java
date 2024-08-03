package com.crio.leaderboard.management.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.crio.leaderboard.management.constants.Badge;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Document(collection = "user")
public class UserEntity{

    @Id
    private final Integer userId;

    private final String username;
    private Integer score = 0;
    private List<Badge> earnedBadges = new ArrayList<>();
}