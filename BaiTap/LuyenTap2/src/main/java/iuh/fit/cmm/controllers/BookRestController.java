/*
 * @ (#) BookRestController.java   11/1/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.cmm.controllers;

import iuh.fit.cmm.models.Book;
import iuh.fit.cmm.repositories.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * @description: this class represents
 * @author: Nguyen Vu Thanh
 * @created: 11/1/2026
 * @version 1.0
 */
@RestController
@RequestMapping("/api/books")
public class BookRestController {
    @Autowired
    private BookRepo bookRepo;
    @GetMapping
    public ResponseEntity<List<Book>> getAll(){
        return ResponseEntity.ok(bookRepo.findAll());
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getByAuthor(@PathVariable String author){
        return  ResponseEntity.ok(bookRepo.findByAuthorContaining(author));
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookRepo.save(book));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable int id, @RequestBody Book book){
        if (!bookRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        book.setId(id);
        return ResponseEntity.ok(bookRepo.save(book));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        if (!bookRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        bookRepo.deleteById(id);
        return ResponseEntity.ok("Xóa sách thành công");
    }
}
