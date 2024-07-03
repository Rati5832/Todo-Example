package Todo.TodoApp.Controller;


import Todo.TodoApp.Domain.Topic;
import Todo.TodoApp.Repository.TopicRepository;
import Todo.TodoApp.Service.TopicService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;


    @PostMapping("/createTopic")
    @ResponseStatus(HttpStatus.CREATED)
    public Topic createNewTopic(@RequestBody Topic topic){


       // When sending a JSON we need to specifically set an adress that way "2024-07-01T15:26:00" YEAR-MONTH-DAY
       // and then HOURS MINUTES AND SECONDS
       topicService.TopicDeadline(topic,topic.getDeadline());
       return topicService.createNewTopic(topic);

    }

    @GetMapping("/{id}/remaining")
    @ResponseStatus(HttpStatus.OK)
    public String getRemainingTime(@PathVariable Long id){

        Optional<Topic> topicOptional = topicRepository.findById(id);
        if(topicOptional.isPresent()){

            Topic topic = topicOptional.get();
            return topicService.remainingTime(topic);

        } else {

            throw new EntityNotFoundException("Entity With Given ID " + id + " Not Found!");

        }
    }

    @GetMapping("/topic/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Topic findByTopicId(@PathVariable String id){

        return topicRepository.findByTopic(id);

    }

    @GetMapping("/{deadline}")
    @ResponseStatus(HttpStatus.OK)
    public List<Topic> findTopicByDateAfter(@PathVariable LocalDateTime deadline){

        return topicRepository.findByDateAfter(deadline);

    }

    @GetMapping("/allTopic")
    @ResponseStatus(HttpStatus.OK)
    public List<Topic> findAllTopic(){

        return topicRepository.findAllTopic();

    }

    @PutMapping("/updateTopic/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Topic updateTopicById(@PathVariable Long id, @RequestBody Topic topic){

        topicService.TopicDeadline(topic,topic.getDeadline());
        return topicService.updateTopicById(id,topic);

    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTopicById(@PathVariable Long id){

        topicService.deleteTopicById(id);

    }


}
