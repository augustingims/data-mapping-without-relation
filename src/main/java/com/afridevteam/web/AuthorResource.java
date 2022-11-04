package com.afridevteam.web;

import com.afridevteam.domain.Author;
import com.afridevteam.service.AuthorService;
import com.afridevteam.service.criteria.AuthorCriteria;
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
public class AuthorResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorResource.class);

    private final AuthorService authorService;

    public AuthorResource(AuthorService authorService) {
        this.authorService = authorService;
    }

    /* Create a author */
    @PostMapping("authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author, UriComponentsBuilder ucBuilder) {
        LOGGER.debug(">>> Creating author with id: {}", author.getId());
        if (authorService.isExist(author)) {
            LOGGER.debug("An author with name {} exist.", author.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        authorService.save(author);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("authors/{id}").buildAndExpand(author.getId()).toUri());
        return new ResponseEntity<>(author, headers, HttpStatus.CREATED);
    }

    /* Reading single author */
    @GetMapping("authors/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable("id") int id) {
        LOGGER.debug("Fetching author with id: {}", id);
        Author author = authorService.findById(id);
        if (author == null) {
            LOGGER.debug("Author with id: {}, not found!", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping("authors/{id}/book")
    public ResponseEntity<Author> getAuthorByBookId(@PathVariable("id") int id) {
        LOGGER.debug("Fetching author with id of the book: {}", id);
        Author author = authorService.findByBookId(id);
        if (author == null) {
            LOGGER.debug("Author with id: {}, not found!", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    /*Reads all authors*/
    @GetMapping("authors")
    public ResponseEntity<List<Author>> listAllAuthors() {
        LOGGER.debug("Received request to list all authors");
        List<Author> authors = authorService.findAll();
        if (authors.isEmpty()) {
            LOGGER.debug("authors do not have.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("authors/{firstName}/search")
    public ResponseEntity<List<Author>> listAllAuthorsByFirstName(@PathVariable String firstName) {
        LOGGER.debug("Received request to list all authors by firstName {}", firstName);
        List<Author> authors = authorService.findAllByFirstName(firstName);
        if (authors.isEmpty()) {
            LOGGER.debug("authors do not have.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("authors/criteria")
    public ResponseEntity<List<Author>> listAllAuthorsByFirstName(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) Integer bookId) {
        AuthorCriteria criteria = new AuthorCriteria();
        criteria.setFirstName(firstName);
        criteria.setLastName(lastName);
        criteria.setBookId(bookId);
        LOGGER.debug("Received request to list all authors by criteria {}", criteria);
        List<Author> authors = authorService.findAllWithCriteria(criteria);
        if (authors.isEmpty()) {
            LOGGER.debug("authors do not have.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }
}
