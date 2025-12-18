package com.project.journalApp.controllers;

import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllUserJournals(@PathVariable String userName){
        List<JournalEntry> all = journalEntryService.getAll(userName);
        if(!all.isEmpty())return new ResponseEntity<>(all, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        //return !all.isEmpty() ? ResponseEntity.ok(all) : ResponseEntity.noContent().build(); ->this also works.
    }

    @PostMapping("{userName}")
    public ResponseEntity<?> createUserJournal(@RequestBody JournalEntry journalEntry,@PathVariable String userName){
        try {
            journalEntryService.save(journalEntry,userName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{userName}/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id,@PathVariable String userName){
        Optional<JournalEntry> je = journalEntryService.getById(id);
        if(je.isPresent()) return new ResponseEntity<>(je,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{userName}/{id}")
    public ResponseEntity<?> updateById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry journalEntry,
            @PathVariable String userName){
        JournalEntry old = journalEntryService.getById(id).orElse(null);
        if(old!=null){
            old.setTitle(journalEntry.getTitle()!=null && !journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : old.getTitle());
            old.setContent(journalEntry.getContent()!=null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : old.getContent());
            journalEntryService.save(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{userName}/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id,@PathVariable String userName){
        try {
            journalEntryService.delete(id,userName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
