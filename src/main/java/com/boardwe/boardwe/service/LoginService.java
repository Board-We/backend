package com.boardwe.boardwe.service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface LoginService {
    Boolean login(String boardCode, String password);
}
