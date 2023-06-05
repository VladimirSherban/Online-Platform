package com.example.onlineplatform.repository;

import com.example.onlineplatform.model.Ads;
import com.example.onlineplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AdsRepository extends JpaRepository<Ads, Integer>, JpaSpecificationExecutor<Ads> {
    List<Ads> findAllByUser(User user);
}
