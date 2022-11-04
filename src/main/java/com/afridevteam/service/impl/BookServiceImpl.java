package com.afridevteam.service.impl;

import com.afridevteam.domain.Book;
import com.afridevteam.repository.BookRepository;
import com.afridevteam.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isExist(Book book) {
        return findById(book.getId()) != null;
    }

    @Override
    public Book save(Book book) {
        LOGGER.debug("Save {}", book);
        Book existing = repository.findById(book.getId()).orElse(null);
        if (existing != null) {
            LOGGER.debug("There already exists a book with id = {}", book.getId());
        }
        return repository.save(book);
    }

    @Override
    public Book findById(int id) {
        LOGGER.debug("Search book by id: {}", id);
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Book> findAll() {
        LOGGER.debug("Retrieve the list of all books!");
        return repository.findAll();
    }

    @Override
    public Book update(Book book) {
        LOGGER.debug("Book with id: " + book.getId() + " updated! ");
        if (!entityManager.contains(book))
            book = entityManager.merge(book);
        return book;
    }

    @Override
    public void delete(int id) {
        LOGGER.debug("Book by id: {} Deleted!", id);
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("The list all books deleted!");
        repository.deleteAll();
    }
}
