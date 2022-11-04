package com.afridevteam.service.impl;

import com.afridevteam.domain.Author;
import com.afridevteam.repository.AuthorRepository;
import com.afridevteam.repository.specification.AuthorSpecification;
import com.afridevteam.service.AuthorService;
import com.afridevteam.service.criteria.AuthorCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }


    @Override
    public boolean isExist(Author author) {
        return findById(author.getId()) != null;
    }

    @Override
    public Author save(Author author) {
        LOGGER.debug("Save {}", author);
        Author existing = repository.findById(author.getId()).orElse(null);
        if (existing != null) {
            LOGGER.debug("There already exists a author with id = {}", author.getId());
        }
        return repository.save(author);
    }

    @Override
    public Author findById(int id) {
        LOGGER.debug("Search author by id: {}",id);
        return repository.findById(id).orElse(null);
    }

    @Override
    public Author findByBookId(int id) {
        LOGGER.debug("Search author by Book id: {}",id);
        return repository.findByBookId(id).orElse(null);
    }

    @Override
    public List<Author> findAll() {
        LOGGER.debug("Retrieve the list of all authors!");
        return repository.findAll();
    }

    @Override
    public List<Author> findAllByFirstName(String firstName) {
        LOGGER.debug("Retrieve the list of all authors by firstName : {}", firstName);
        return repository.findAllByFirstNameLike("%"+firstName+"%");
    }

    @Override
    public List<Author> findAllWithCriteria(AuthorCriteria criteria) {
        LOGGER.debug("Retrieve the list of all authors by Criteria : {}", criteria);
        return repository.findAll(AuthorSpecification.getSpecification(criteria));
    }

    @Override
    public Author update(Author author) {
        LOGGER.debug("Author with id: {} updated!", author.getId());
        if (!entityManager.contains(author))
            author = entityManager.merge(author);
        return author;
    }

    @Override
    public void delete(int id) {
        LOGGER.debug("Author by id: {} Deleted!", id);
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("The list all authors deleted!");
        repository.deleteAll();
    }
}
