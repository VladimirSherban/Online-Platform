package com.example.onlineplatform.repository;

import com.example.onlineplatform.model.Ads;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdsRepository extends JpaRepository<Ads, Integer> {
    List<Ads> findByAdsAuthor_Email(String username);
}
