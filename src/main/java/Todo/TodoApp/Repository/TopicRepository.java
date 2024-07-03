package Todo.TodoApp.Repository;

import Todo.TodoApp.Domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {


    @Query("SELECT t FROM Topic t WHERE t.id = :id")
    Topic findByTopic(String id);

    @Query("SELECT t FROM Topic t WHERE t.deadline > :date")
    List<Topic> findByDateAfter(LocalDateTime date);

    @Query("SELECT t FROM Topic t")
    List<Topic> findAllTopic();


}
