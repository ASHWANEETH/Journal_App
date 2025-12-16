package com.project.journalApp.service;

import com.project.journalApp.Repository.JournalEntryRepository;
import com.project.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository jer;

    public void save(JournalEntry je){
        jer.save(je);
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
