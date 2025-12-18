package com.project.journalApp.service;

import com.project.journalApp.Repository.JournalEntryRepository;
import com.project.journalApp.entity.JournalEntry;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository jer;

    public void save(JournalEntry je){
        try {
            je.setDate(LocalDateTime.now());
            jer.save(je);
        } catch (Exception e) {
            log.error("Exception",e);
        }
    }

    public List<JournalEntry> getAll(){
        return jer.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id){
        return jer.findById(id);
    }

    public void delete(ObjectId id){
        jer.deleteById(id);
    }

}
