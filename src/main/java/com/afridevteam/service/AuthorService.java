package com.afridevteam.service;

import com.afridevteam.domain.Author;
import com.afridevteam.service.criteria.AuthorCriteria;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    boolean isExist(Author author);

    Author save(Author author);

    Author findById(int id);

    Author findByBookId(int id);

    List<Author> findAll();

    List<Author> findAllByFirstName(String firstName);

    List<Author> findAllWithCriteria(AuthorCriteria criteria);

    Author update(Author author);

    void delete(int id);

    void deleteAll();
}
