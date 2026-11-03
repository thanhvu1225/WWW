/*
 * @ (#) PublisherRestController.java   11/1/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.cmm.controllers;

import iuh.fit.cmm.models.Book;
import iuh.fit.cmm.models.Publisher;
import iuh.fit.cmm.repositories.BookRepo;
import iuh.fit.cmm.repositories.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
 * @description: this class represents
 * @author: Nguyen Vu Thanh
 * @created: 11/1/2026
 * @version 1.0
 */
@RestController
@RequestMapping("/api/publishers")
public class PublisherRestController {

    @Autowired
    private PublisherRepo publisherRepo;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublisher(){
        return ResponseEntity.ok(publisherRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getByPublisherId(@PathVariable int id){
        return  publisherRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{publisherId}/books/search")
    public ResponseEntity<Publisher> getByIdAndAuthor(@PathVariable("publisherId") int id, @RequestParam(required = false, defaultValue = "") String author){
        Optional<Publisher> pulisherOptional = publisherRepo.findById(id);
        if(pulisherOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Publisher pulisher = pulisherOptional.get();
        List<Book> books = bookRepo.findByPublisherIdAndAuthorContaining(id, author);
        pulisher.setBooks(books);
        return ResponseEntity.ok(pulisher);
    }



    @Autowired
    private BookRepo bookRepo;

}
