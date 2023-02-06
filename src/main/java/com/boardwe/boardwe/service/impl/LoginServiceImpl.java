package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.entity.Board;
import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.entity.BoardNotFoundException;
import com.boardwe.boardwe.exception.custom.other.InvalidPasswordException;
import com.boardwe.boardwe.repository.BoardRepository;
import com.boardwe.boardwe.service.LoginService;
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
    public void login(String boardCode, String password, HttpServletResponse response, HttpSession session) {
        Board board = boardRepository.findByCode(boardCode).orElseThrow(BoardNotFoundException::new);

        if(!board.getPassword().equals(password)){
            throw new InvalidPasswordException();
        }

        UUID sessionId = UUID.randomUUID();
        Cookie cookie = new Cookie("boardWeSessionId", sessionId.toString());

        cookie.setMaxAge(300);
        response.addCookie(cookie);

        session.setAttribute(sessionId.toString(), board.getCode());
    }
}
