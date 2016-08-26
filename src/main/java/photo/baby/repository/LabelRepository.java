package photo.baby.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import photo.baby.bean.Label;

public interface LabelRepository extends PagingAndSortingRepository<Label, Integer> {


}
