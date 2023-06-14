package platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import platform.model.Image;


public interface ImageRepository extends JpaRepository<Image, Integer> {
}
