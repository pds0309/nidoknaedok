package com.controller.exception;


import com.controller.common.JSONResponse;
import com.errors.ErrorCode;
import com.errors.ErrorResponse;
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
            request.setAttribute("errorinfo", ErrorResponse.of(errorCode));
            request.getRequestDispatcher("pages/error.jsp").forward(request, response);
            return;
        }

        ErrorResponse errorResponse = null;

        if (exception instanceof UpperCustomException) {
            errorCode = ((UpperCustomException) (exception)).getErrorCode();
            errorResponse = ErrorResponse.of(errorCode, exception.getMessage());

        } else if (exception instanceof PersistenceException) {
            errorCode = ErrorCode.DATA_SERVICE_UNAVAILABLE;
            errorResponse = ErrorResponse.of(errorCode);

        } else {
            errorCode = ErrorCode.INTERNA_SERVER_ERROR;
            errorResponse = ErrorResponse.of(errorCode);
        }

        logger.error(MessageFormat.format("{0}:{1}", errorCode, exception.getMessage()));
        showError(request, response, errorResponse);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * 요청 형태에 따라 예외를 다른 방식으로 응답한다.
     *
     * @param errorResponse
     */
    private void showError(HttpServletRequest request, HttpServletResponse response, ErrorResponse errorResponse) throws IOException, ServletException {
        if (request.getAttribute("url") == null) {
            JSONResponse.send(response, errorResponse);
            return;
        }
        request.setAttribute("errorinfo", errorResponse);
        request.getRequestDispatcher("pages/error.jsp").forward(request, response);
    }

}
