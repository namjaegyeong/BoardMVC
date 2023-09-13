package com.example.demo12.repository;

import com.example.demo12.entity.Board;
import com.example.demo12.entity.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Board board = Board.builder()
                    .title("Title...." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(boardRepository.save(board));
        });
    }


    @Test
    public void updateTest() {
        Optional<Board> result = boardRepository.findById(300L);
        if (result.isPresent()) {
            Board board = result.get();
            board.changeTitle("Changed Title....");
            board.changeContent("Changed Content...");
            boardRepository.save(board);
        }
    }


    @Test
    public void testQuery1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QBoard qBoard = QBoard.board;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qBoard.title.contains(keyword);
        builder.and(expression);

        Page<Board> result = boardRepository.findAll(builder, pageable);

        result.stream().forEach(board -> {
            System.out.println(board);
        });
    }


    @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QBoard qBoard = QBoard.board;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle =  qBoard.title.contains(keyword);
        BooleanExpression exContent =  qBoard.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        builder.and(qBoard.gno.gt(0L));
        Page<Board> result = boardRepository.findAll(builder, pageable);

        result.stream().forEach(board -> {
            System.out.println(board);
        });
    }

}