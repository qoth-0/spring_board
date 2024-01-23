package com.encore.board.author.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.Role;
import com.encore.board.author.dto.AuthorDetailResDto;
import com.encore.board.author.dto.AuthorUpdateReqDto;
import com.encore.board.author.dto.AuthorListResDto;
import com.encore.board.author.dto.AuthorSaveReqDto;
import com.encore.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void authorCreate(AuthorSaveReqDto authorSaveReqDto) throws DataIntegrityViolationException {
        Role role = null;
        if(authorSaveReqDto.getRole() == null || authorSaveReqDto.getRole().equals("user")) {
            role = Role.USER;
        }else {
            role = Role.ADMIN;
        }
////        일반 생성자 방식 - 매개변수의 순서와 개수를 맞춰줘야 함
//        Author author = new Author(authorSaveReqDto.getName(),
//                                    authorSaveReqDto.getEmail(),
//                                    authorSaveReqDto.getPassword(),
//                                    role);

//        빌더 패턴
        Author author = Author.builder()
                .email(authorSaveReqDto.getEmail())
                .name(authorSaveReqDto.getName())
                .password(authorSaveReqDto.getPassword())
                .build();
        authorRepository.save(author); // save - 내장 메서드
    }

    public List<AuthorListResDto> authorList() {
        List<Author> authors = authorRepository.findAll(); // findAll - 내장
        List<AuthorListResDto> authorListResDtos = new ArrayList<>();
        for(Author author : authors) {
            AuthorListResDto authorListResDto = new AuthorListResDto();
            authorListResDto.setId(author.getId());
            authorListResDto.setName(author.getName());
            authorListResDto.setEmail(author.getEmail());
            authorListResDtos.add(authorListResDto);
        }
        return authorListResDtos;
    }

//    findById 공통화
    public Author findById(Long id) throws EntityNotFoundException{
        Author author = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return author;
    }

    public AuthorDetailResDto authorDetail(Long id) throws EntityNotFoundException{
        Author author = this.findById(id);
        String role = null;
        if(author.getRole() == null || author.getRole().equals(Role.USER)) {
            role = "일반사용자";
        }else {
            role = "관리자";
        }
        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto(
                author.getId(), author.getName(), author.getEmail(), author.getPassword(), author.getCreatedTime(), role
        );
        return authorDetailResDto;
    }

    public void authorUpdate(Long id, AuthorUpdateReqDto authorUpdateReqDto) {
        Author author = this.findById(id);
        author.authorUpdate(authorUpdateReqDto.getName(), authorUpdateReqDto.getPassword());
        authorRepository.save(author);
    }

    public void authorDelete(Long id) {
        Author author = this.findById(id);
        authorRepository.delete(author);
    }
}

