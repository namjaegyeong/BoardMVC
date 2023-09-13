package com.example.demo12.service;

import com.example.demo12.dto.BoardDTO;
import com.example.demo12.dto.PageRequestDTO;
import com.example.demo12.dto.PageResultDTO;
import com.example.demo12.entity.Board;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO);

    BoardDTO read(Long gno);

    void modify(BoardDTO dto);

    void remove(Long gno);

    default Board dtoToEntity(BoardDTO dto) {
        Board entity = Board.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default BoardDTO entityToDto(Board entity) {

        BoardDTO dto = BoardDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }
}
