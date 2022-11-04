package com.afridevteam.repository;

import com.afridevteam.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer>, JpaSpecificationExecutor<Author> {
    Optional<Author> findByBookId(Integer bookId);

    List<Author> findAllByFirstNameLike(String firstName);
}
