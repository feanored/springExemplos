package com.binha.demo.service;

import com.binha.demo.model.Author;
import com.binha.demo.model.Book;
import com.binha.demo.exception.EntityNotFoundException;
import com.binha.demo.model.*;
import com.binha.demo.model.dto.AuthorCreationDto;
import com.binha.demo.model.dto.BookCreationDto;
import com.binha.demo.model.dto.BookLendDto;
import com.binha.demo.model.dto.MemberCreationDto;
import com.binha.demo.repository.AuthorRepository;
import com.binha.demo.repository.BookRepository;
import com.binha.demo.repository.LendRepository;
import com.binha.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;
    private final LendRepository lendRepository;
    private final BookRepository bookRepository;

    public Book readBookById(String id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Cant find any book under given ID");
    }

    public Iterable<Book> readBooks() {
        return bookRepository.findAll();
    }

    public Book readBook(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Cant find any book under given ISBN");
    }

    public Book createBook(BookCreationDto book) {
        Optional<Author> author = authorRepository.findById(book.getAuthorId());
        if (author.isEmpty()) {
            throw new EntityNotFoundException("Author Not Found");
        }
        Book bookToCreate = new Book();
        BeanUtils.copyProperties(book, bookToCreate);
        bookToCreate.setAuthorId(author.get().getId());
        return bookRepository.save(bookToCreate);
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    public Member createMember(MemberCreationDto request) {
        Member member = new Member();
        BeanUtils.copyProperties(request, member);
        member.setStatus(MemberStatus.ACTIVE);
        return memberRepository.save(member);
    }

    public Member updateMember(String id, MemberCreationDto request) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) {
            throw new EntityNotFoundException("Member not present in the database");
        }
        Member member = optionalMember.get();
        member.setLastName(request.getLastName());
        member.setFirstName(request.getFirstName());
        return memberRepository.save(member);
    }

    public Iterable<Author> readAuthors() {
        return authorRepository.findAll();
    }

    public Author createAuthor(AuthorCreationDto request) {
        Author author = new Author();
        BeanUtils.copyProperties(request, author);
        return authorRepository.save(author);
    }

    public List<String> lendABook(BookLendDto request) {
        Optional<Member> memberForId = memberRepository.findById(request.getMemberId());
        if (memberForId.isEmpty()) {
            throw new EntityNotFoundException("Member not present in the database");
        }
        Member member = memberForId.get();
        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new RuntimeException("User is not active to proceed a lending.");
        }
        List<String> booksApprovedToBurrow = new ArrayList<>();
        request.getBookIds().forEach(bookId -> {
            Optional<Book> bookForId = bookRepository.findById(bookId);
            if (bookForId.isEmpty()) {
                throw new EntityNotFoundException("Cant find any book under given ID");
            }
            Optional<Lend> burrowedBook = lendRepository.findByBookIdAndStatus(bookForId.get().getId(), LendStatus.BURROWED);
            if (burrowedBook.isEmpty()) {
                booksApprovedToBurrow.add(bookForId.get().getName());
                Lend lend = new Lend();
                lend.setMemberId(memberForId.get().getId());
                lend.setBookId(bookForId.get().getId());
                lend.setStatus(LendStatus.BURROWED);
                lend.setStartOn(Instant.now().toString());
                lend.setDueOn(Instant.now().plus(30, ChronoUnit.DAYS).toString());
                lendRepository.save(lend);
            }
        });
        return booksApprovedToBurrow;
    }

    public Book updateBook(String bookId, BookCreationDto request) {
        Optional<Author> author = authorRepository.findById(request.getAuthorId());
        if (author.isEmpty()) {
            throw new EntityNotFoundException("Author Not Found");
        }
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new EntityNotFoundException("Book Not Found");
        }
        Book book = optionalBook.get();
        book.setIsbn(request.getIsbn());
        book.setName(request.getName());
        book.setAuthorId(author.get().getId());
        return bookRepository.save(book);
    }

    public List<Member> readMembers() {
        Iterable<Member> members = memberRepository.findAll();
        List<Member> memberList = new ArrayList<>();
        members.forEach(memberList::add);
        return memberList;
    }

}
