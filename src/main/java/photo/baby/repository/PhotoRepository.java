package photo.baby.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import photo.baby.bean.Photo;

import java.util.List;

public interface PhotoRepository extends PagingAndSortingRepository<Photo, Integer> {

    @Query("select a from Photo a")
    public Page<Photo> pageablePhotos(Pageable page);

    public List<Photo> findAllByOrderByIdDesc();

}
