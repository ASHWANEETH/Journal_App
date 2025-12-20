package com.project.journalApp.service;

import com.project.journalApp.Repository.JournalEntryRepository;
import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void save(JournalEntry journalEntry, String userName){
        try {
            User user = userService.getByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.save(user);
        } catch (Exception e) {
            log.error("Exception",e);
        }
    }

    public void save(JournalEntry journalEntry){
        try {
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
        } catch (Exception e) {
            log.error("Exception",e);
        }
    }

    public List<JournalEntry> getAll(String userName){
        User user = userService.getByUserName(userName);
        return user.getJournalEntries();
    }

    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void delete(ObjectId id, String userName){
        User user = userService.getByUserName(userName);
        journalEntryRepository.deleteById(id);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.save(user);
    }

}
