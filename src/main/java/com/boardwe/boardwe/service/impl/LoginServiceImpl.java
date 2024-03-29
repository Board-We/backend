package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.exception.custom.other.InvalidPasswordException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.service.LoginService;
import com.boardwe.boardwe.type.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final BoardRepository boardRepository;

    @Override
    public Boolean login(String boardCode, String password) {
        Board board = boardRepository.findByCode(boardCode)
                .orElseThrow(BoardNotFoundException::new);
        return board.getPassword().equals(password);
    }
}
