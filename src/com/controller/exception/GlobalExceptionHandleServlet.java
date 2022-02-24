package com.controller.exception;


import com.controller.common.SendJSONResponse;
import com.errors.ErrorCode;
import com.errors.ErrorResponse;
import com.errors.exception.InvalidValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        if (exception instanceof InvalidValueException) {
            errorCode = ErrorCode.INVALID_INPUT;
            logger.error(MessageFormat.format("{0}:{1}", errorCode, exception.getMessage()));
            SendJSONResponse.sendAsJson(response, errorCode);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
