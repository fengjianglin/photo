package photo.baby.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import photo.baby.bean.Comment;

import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {

    public List<Comment> findAllByPhotoId(int photoId);

}
