package com.forohub.controller;

import com.forohub.model.Topic;
import com.forohub.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
@Validated
public class TopicController {
    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody @Valid Topic topic) {
        if (topicService.findByTitleAndMessage(topic.getTitle(), topic.getMessage()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        topic.setCreationDate(LocalDateTime.now());
        topic.setStatus("ACTIVE");
        return new ResponseEntity<>(topicService.save(topic), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.findAllSortedByCreationDate();
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable @NotNull Long id) {
        Optional<Topic> topic = topicService.findById(id);
        return topic.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable @NotNull Long id, @RequestBody @Valid Topic topic) {
        if (!topicService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        topic.setId(id);
        topic.setCreationDate(LocalDateTime.now());
        return new ResponseEntity<>(topicService.save(topic), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable @NotNull Long id) {
        if (!topicService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        topicService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
