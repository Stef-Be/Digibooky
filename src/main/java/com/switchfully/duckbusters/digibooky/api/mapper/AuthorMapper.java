package com.switchfully.duckbusters.digibooky.api.mapper;

import com.switchfully.duckbusters.digibooky.api.dto.AuthorDTO;
import com.switchfully.duckbusters.digibooky.domain.book.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public AuthorDTO mapToAuthorDTO(Author author){
        return new AuthorDTO(author.getFirstName(), author.getLastName());
    }
}
