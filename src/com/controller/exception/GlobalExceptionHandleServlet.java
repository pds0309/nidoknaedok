package com.controller.exception;


import com.controller.common.SendJSONResponse;
import com.errors.ErrorCode;
import com.errors.ErrorResponse;
import com.errors.exception.InvalidValueException;
import com.errors.exception.UpperCustomException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.MessageFormat;

/**
 * 예외에 대한 처리를 받아 응답하는 서블릿
 */
@WebServlet("/error")
public class GlobalExceptionHandleServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
        ErrorCode errorCode;

        if (exception == null) {
            errorCode = ErrorCode.NOT_FOUND;
            logger.error(MessageFormat.format("{0}", errorCode));
            request.setAttribute("errorResponse", ErrorResponse.of(errorCode));
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        if (exception instanceof UpperCustomException) {
            errorCode = ((UpperCustomException) (exception)).getErrorCode();
            logger.error(MessageFormat.format("{0}:{1}", errorCode, exception.getMessage()));
            SendJSONResponse.sendAsJson(response, errorCode, exception.getMessage());
            return;
        }

        if (exception instanceof PersistenceException) {
            errorCode = ErrorCode.CONFLICT_INPUT;
            logger.error(MessageFormat.format("{0}:{1}", errorCode, exception.getMessage()));
            SendJSONResponse.sendAsJson(response, errorCode, "데이터 요청 처리에 실패했습니다");
            return;
        }

        if (exception instanceof Exception) {
            errorCode = ErrorCode.INTERNA_SERVER_ERROR;
            logger.error(MessageFormat.format("{0}:{1}", errorCode, exception.getMessage()));
            SendJSONResponse.sendAsJson(response, errorCode, "서버 내부 에러입니다");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
