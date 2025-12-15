package com.project.journalApp.controllers;

import com.project.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

    Map<Long,JournalEntry> JE = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(JE.values());
    }

    @PostMapping
    public boolean create(@RequestBody JournalEntry je){
        JE.put(je.getId(),je);
        return true;
    }

    @GetMapping("/{id}")
    public JournalEntry getById(@PathVariable long id){
        return JE.get(id);
    }

    @PutMapping("/{id}")
    public JournalEntry updateById(@PathVariable long id, @RequestBody JournalEntry je){
        return JE.put(id,je);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable long id){
        JE.remove(id);
        return "Success";
    }
}
