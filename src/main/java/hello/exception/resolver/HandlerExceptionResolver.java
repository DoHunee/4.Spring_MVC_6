package hello.exception.resolver;

import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HandlerExceptionResolver {
    ModelAndView resolveException( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex);
   }