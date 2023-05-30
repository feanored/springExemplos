package com.binha.demo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.binha.demo.model.*;
import com.binha.demo.repository.AuthorRepository;
import com.binha.demo.repository.BookRepository;
import com.binha.demo.repository.LendRepository;
import com.binha.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import static com.binha.demo.config.DynamoDBConfig.createTable;

@RestController
@RequestMapping(value = "/config")
@RequiredArgsConstructor
public class MainController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;
    private final LendRepository lendRepository;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @GetMapping("/reset")
    public ResponseEntity<String> resetDados() {
        try {
            apagarTabelas();
            criarTabelas();
            inserirDados();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.toString());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/drop")
    public ResponseEntity<String> dropTables() {
        try {
            apagarTabelas();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.toString());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/create")
    public ResponseEntity<String> createTables() {
        try {
            criarTabelas();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.toString());
        }
        return ResponseEntity.ok().build();
    }

    private void apagarTabelas() {
        amazonDynamoDB.deleteTable("book");
        amazonDynamoDB.deleteTable("author");
        amazonDynamoDB.deleteTable("lend");
        amazonDynamoDB.deleteTable("member");
        amazonDynamoDB.deleteTable("appUser");
    }

    private void criarTabelas() throws InterruptedException {
        createTable(amazonDynamoDB, "book");
        createTable(amazonDynamoDB, "author");
        createTable(amazonDynamoDB, "lend");
        createTable(amazonDynamoDB, "member");
        createTable(amazonDynamoDB, "appUser");
    }

    private void inserirDados() {
        Author author = new Author();
        author.setFirstName("Random");
        author.setLastName("Upu");
        Author save = authorRepository.save(author);
        authorRepository.save(new Author("J.R.R.", "Tolkien"));

        Book book = new Book();
        book.setAuthorId(save.getId());
        book.setName("Arthur");
        book.setIsbn("2892828282822");
        Book bookSaved = bookRepository.save(book);

        Member member = new Member();
        member.setStatus(MemberStatus.ACTIVE);
        member.setFirstName("Srimath");
        member.setLastName("Anagarika");
        Member member1 = memberRepository.save(member);

        Lend lend = new Lend();
        lend.setDueOn(Instant.now().toString());
        lend.setStartOn(Instant.now().toString());
        lend.setStatus(LendStatus.BURROWED);
        lend.setBookId(bookSaved.getId());
        lend.setMemberId(member1.getId());
        lendRepository.save(lend);
    }
}
