package Todo.TodoApp.Service;

import Todo.TodoApp.Domain.Topic;
import Todo.TodoApp.Repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;


    @Override
    @Transactional
    public Topic createNewTopic(Topic topic) {

        Optional<Topic> topicOptional = topicRepository.findById(topic.getId());

        if(topicOptional.isPresent()){

            throw new IllegalArgumentException("Topic With Given ID " + topic.getId() + " Already Exists!");

        }

        return topicRepository.save(topic);

    }
    @Override
    public void TopicDeadline(Topic topic, LocalDateTime deadline) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        if(deadline.isAfter(currentDateTime)){

            topic.setDeadline(deadline);

        } else {

            throw new IllegalArgumentException("Deadline IS Before Current Time!");

        }
    }
    public String remainingTime(Topic topic){

        LocalDateTime currentTime = LocalDateTime.now();

        Duration duration = Duration.between(currentTime, topic.getDeadline());
        long remainingHours = duration.toHours();

        return "You Have " + remainingHours + " Hours Left To Do That Task";


    }

    @Override
    @Transactional
    public Topic updateTopicById(Long id, Topic topic) {

        Optional<Topic> optionalTopic = topicRepository.findById(id);
        if (optionalTopic.isPresent()) {
            Topic topics = optionalTopic.get();

            topics.setName(topic.getName());
            topics.setDescription(topic.getDescription());
            topics.setDeadline(topic.getDeadline());

            return topicRepository.save(topics);
        } else {

            throw new EntityNotFoundException("Topic Not Found For ID: " + id);

        }

    }

    @Override
    @Transactional
    public void deleteTopicById(Long id) {

        Optional<Topic> topic = topicRepository.findById(id);

        if(topic.isPresent()) {

            topicRepository.deleteById(id);

            System.out.println("Sucessfully Deleted ID: " + id);

        } else {

            System.out.println("Topic With Given ID " + id + " Doesnot Exist!");
        }
    }
}
