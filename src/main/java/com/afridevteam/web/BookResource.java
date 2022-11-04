package com.afridevteam.web;

import com.afridevteam.domain.Book;
import com.afridevteam.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class BookResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookResource.class);

    private final BookService bookService;

    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    /* Create a book */
    @PostMapping("books")
    public ResponseEntity<Book> createBook(@RequestBody Book book, UriComponentsBuilder ucBuilder) {
        LOGGER.debug(">>> Creating book with id: {}", book.getId());
        if (bookService.isExist(book)) {
            LOGGER.debug("A book with name {} exist.", book.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        bookService.save(book);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("books/{id}").buildAndExpand(book.getId()).toUri());
        return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
    }

    /* Reading single book */
    @GetMapping("books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") int id) {
        LOGGER.debug("Fetching book with id: {}", id);
        Book book = bookService.findById(id);
        if (book == null) {
            LOGGER.debug("Book with id: {}, not found!", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    /*Reads all books*/
    @GetMapping(value = "books")
    public ResponseEntity<List<Book>> listAllBooks() {
        LOGGER.debug("Received request to list all books");
        List<Book> books = bookService.findAll();
        if (books.isEmpty()) {
            LOGGER.debug("Books do not have.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
