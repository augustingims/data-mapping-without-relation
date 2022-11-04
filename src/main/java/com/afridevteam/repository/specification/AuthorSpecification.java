package com.afridevteam.repository.specification;

import com.afridevteam.domain.Author;
import com.afridevteam.domain.Book;
import com.afridevteam.service.criteria.AuthorCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;

public class AuthorSpecification {

    public static Specification<Author> hasFirstNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.<String>get("firstName"), "%" + name + "%");
    }

    public static Specification<Author> hasLastName(String name) {
        return (root, query, cb) ->
                cb.equal(root.<String>get("lastName"), name);
    }

    public static Specification<Author> hasBookWithId(Integer bookId) {
        return (root, query, criteriaBuilder) -> {
            Join<Book, Author> authorsBook = root.join("book");
            return criteriaBuilder.equal(authorsBook.get("id"), bookId);
        };
    }

    public static Specification<Author> getSpecification(AuthorCriteria criteria){
        Specification<Author> spec = null;

        if(StringUtils.hasLength(criteria.getFirstName())){
            spec = Specification.where(hasFirstNameLike(criteria.getFirstName()));
        }

        if(StringUtils.hasLength(criteria.getLastName())){
            spec = Specification.where(hasLastName(criteria.getLastName()));
        }

        if(criteria.getBookId() != null){
            spec = Specification.where(hasBookWithId(criteria.getBookId()));
        }

        return spec;
    }
}
