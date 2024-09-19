package hello.exception.exhandler;

import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    // 상태코드가 BAD_REQUEST(400)으로 응답되게!
    // @ResponseStatus(HttpStatus.BAD_REQUEST)

    // 이 @ExceptionHandler가 있으면 {id}파라미터가 bad 일때 우리가 원하는 모양(ErrorResult)으로 응답된다!
    // @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandle(IllegalArgumentException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    // 이 @ExceptionHandler가 있으면 {id}파라미터가 user-ex 일때 우리가 원하는 모양(ErrorResult)으로 응답된다!
    // @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandle(UserException e) {
        log.error("[exceptionHandle] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    // 처리되지 않은 모든 예외를 처리하여 내부 서버 오류로 응답
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // HTTP 상태 코드를 500으로 설정
    // @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

    // HTML 오류화면
    // 다음과같이 ModelAndView를 사용해서 오류 화면(HTML)을 응답하는데 사용할 수도 있다
    // @ExceptionHandler(ViewException.class)

    // public ModelAndView ex(ViewException e) {
    //     log.info("exception e", e);
    //     return new ModelAndView("error");
    // }

    // http://localhost:8080/api2/members/{id}?message
    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }
        return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}