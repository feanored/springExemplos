package com.binha.demo.controller;

import com.binha.demo.exception.EntityNotFoundException;
import com.binha.demo.model.Author;
import com.binha.demo.model.Book;
import com.binha.demo.model.Member;
import com.binha.demo.model.dto.AuthorCreationDto;
import com.binha.demo.model.dto.BookCreationDto;
import com.binha.demo.model.dto.BookLendDto;
import com.binha.demo.model.dto.MemberCreationDto;
import com.binha.demo.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/books")
    public ResponseEntity<Iterable<Book>> readBooks() {
        return ResponseEntity.ok(libraryService.readBooks());
    }

    @GetMapping("/book")
    public ResponseEntity readBookByIsbn(@RequestParam String isbn) {
        try {
            return ResponseEntity.ok(libraryService.readBook(isbn));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getLocalizedMessage());
        }
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity readBookById(@PathVariable String bookId) {
        try {
            return ResponseEntity.ok(libraryService.readBookById(bookId));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getLocalizedMessage());
        }
    }

    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@RequestBody BookCreationDto request) {
        return ResponseEntity.ok(libraryService.createBook(request));
    }

    @PutMapping("/book/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable("bookId") String bookId, @RequestBody BookCreationDto request) {
        return ResponseEntity.ok(libraryService.updateBook(bookId, request));
    }

    @GetMapping("/authors")
    public ResponseEntity<Iterable<Author>> getAuthors() {
        return ResponseEntity.ok(libraryService.readAuthors());
    }

    @PostMapping("/author")
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorCreationDto request) {
        return ResponseEntity.ok(libraryService.createAuthor(request));
    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        libraryService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/member")
    public ResponseEntity<Member> createMember(@RequestBody MemberCreationDto request) {
        return ResponseEntity.ok(libraryService.createMember(request));
    }

    @GetMapping("/member")
    public ResponseEntity<List<Member>> readMembers() {
        return ResponseEntity.ok(libraryService.readMembers());
    }

    @PutMapping("/member/{memberId}")
    public ResponseEntity<Member> updateMember(@RequestBody MemberCreationDto request, @PathVariable String memberId) {
        return ResponseEntity.ok(libraryService.updateMember(memberId, request));
    }

    @PostMapping("/book/lend")
    public ResponseEntity<List<String>> lendABook(@RequestBody BookLendDto bookLendRequests) {
        return ResponseEntity.ok(libraryService.lendABook(bookLendRequests));
    }
}
