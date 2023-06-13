package platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import platform.model.Comment;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface AdsCommentRepository extends JpaRepository<Comment, Integer> {
    void deleteCommentByAdId (int id);

    Optional<Comment> findByIdAndAdId(int id, int adsId);

    Collection<Comment> findAllByAdId(int id);

}
