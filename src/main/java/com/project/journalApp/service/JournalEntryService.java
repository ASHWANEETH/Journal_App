package com.project.journalApp.service;

import com.project.journalApp.Repository.JournalEntryRepository;
import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void save(JournalEntry journalEntry, String userName){
        User user = userService.getByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }

    public void save(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(String userName){
        User user = userService.getByUserName(userName);
        return user.getJournalEntries();
    }

    public Optional<JournalEntry> getById(String userName,ObjectId id){
        User user = userService.getByUserName(userName);
        List<JournalEntry> je = user.getJournalEntries().stream().filter(x->x.getId().equals(id)).toList();
        if(!je.isEmpty()) return journalEntryRepository.findById(id);
        else return Optional.empty();
    }

    @Transactional
    public boolean delete(ObjectId id, String userName){
        boolean removed = false;
        User user = userService.getByUserName(userName);
        Optional<JournalEntry> je = user.getJournalEntries().stream().filter(x->x.getId().equals(id)).findFirst();
        if(je.isPresent()){
            journalEntryRepository.deleteById(id);
            removed = user.getJournalEntries().remove(je.get());
            userService.saveUser(user);
        }
        return removed;
    }

}
