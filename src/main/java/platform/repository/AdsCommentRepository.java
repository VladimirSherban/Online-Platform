package platform.repository;

import platform.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdsCommentRepository extends JpaRepository<Comment, Integer> {
}
