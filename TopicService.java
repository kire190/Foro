package com.forohub.service;

import com.forohub.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    public Optional<Topic> findById(Long id) {
        return topicRepository.findById(id);
    }

    @Transactional
    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }

    @Transactional
    public void deleteById(Long id) {
        topicRepository.deleteById(id);
    }

    public Optional<Topic> findByTitleAndMessage(String title, String message) {
        return topicRepository.findByTitleAndMessage(title, message);
    }

    public List<Topic> findAllSortedByCreationDate() {
        return topicRepository.findAllByOrderByCreationDateAsc();
    }
}
