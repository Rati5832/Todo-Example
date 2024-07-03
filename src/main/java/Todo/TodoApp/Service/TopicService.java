package Todo.TodoApp.Service;

import Todo.TodoApp.Domain.Topic;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;


@Service
public interface TopicService {


    Topic createNewTopic(Topic topic);

    Topic updateTopicById(Long id, Topic topics);

    String remainingTime(Topic topic);
    void TopicDeadline(Topic topic, LocalDateTime deadline);

    void deleteTopicById(Long id);


}
