package com.project.journalApp.controllers;

import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.service.JournalEntryService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService jes;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<JournalEntry> all = jes.getAll();
        if(!all.isEmpty())return new ResponseEntity<>(all, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody JournalEntry je){
        jes.save(je);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id){
        Optional<JournalEntry> je = jes.getById(id);
        if(je.isPresent()) return new ResponseEntity<>(je,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId id, @RequestBody JournalEntry je){
        JournalEntry old = jes.getById(id).orElse(null);
        if(old!=null){
            old.setTitle(je.getTitle()!=null && !je.getTitle().isEmpty() ? je.getTitle() : old.getTitle());
            old.setContent(je.getContent()!=null && !je.getContent().isEmpty() ? je.getContent() : old.getContent());
            jes.save(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id){
        jes.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
