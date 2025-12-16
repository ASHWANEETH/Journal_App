package com.project.journalApp.controllers;

import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService jes;

    @GetMapping
    public List<JournalEntry> getAll(){
        return jes.getAll();
    }

    @PostMapping
    public boolean create(@RequestBody JournalEntry je){
        jes.save(je);
        return true;
    }

    @GetMapping("/{id}")
    public JournalEntry getById(@PathVariable ObjectId id){
        return jes.getById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public JournalEntry updateById(@PathVariable ObjectId id, @RequestBody JournalEntry je){
        JournalEntry old = jes.getById(id).orElse(null);
        if(old!=null){
            old.setTitle(je.getTitle()!=null && !je.getTitle().isEmpty() ? je.getTitle() : old.getTitle());
            old.setContent(je.getContent()!=null && !je.getContent().isEmpty() ? je.getContent() : old.getContent());
        }
        jes.save(old);
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable ObjectId id){
        jes.delete(id);
        return "Success";
    }
}
