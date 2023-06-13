package platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import platform.model.Ads;

import java.util.Collection;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer>, JpaSpecificationExecutor<Ads> {
    Collection<Ads> findAllByUserId(int id);
}
