package com.project.community.common;

import com.project.community.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public String handleRuntimeException(@CurrentUser User user, HttpServletRequest req, RuntimeException e){
        if(user != null){
           log.info("'{}' request '{}'", user.getNickname(), req.getRequestURI());
        }else{
            log.info("requested '{}'", req.getRequestURI());
        }
        log.error("bad request",e);
        return "error";
    }
}
