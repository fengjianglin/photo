package photo.baby.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import photo.baby.bean.Comment;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {


}
