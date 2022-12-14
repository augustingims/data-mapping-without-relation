package com.afridevteam.service;

import com.afridevteam.domain.Book;

import java.util.List;

public interface BookService {
    boolean isExist(Book book);

    Book save(Book book);

    Book findById(int id);

    List<Book> findAll();

    Book update(Book book);

    void delete(int id);

    void deleteAll();
}
