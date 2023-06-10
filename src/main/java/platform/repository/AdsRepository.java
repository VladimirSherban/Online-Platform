package platform.repository;

import platform.model.Ads;
import platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AdsRepository extends JpaRepository<Ads, Integer>, JpaSpecificationExecutor<Ads> {

}
