package hello.exception.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;

@Slf4j
@RestController
public class ApiExceptionController {

    // http://localhost:8080/api/members/spring
    // http://localhost:8080/api/members/ex => 예외처리
    // postman으로 검증! => header에 accept 타입을 application/json으로 설정해야 한다.
    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        // id의 값으로 bad가 들어갈 수 없게 설정
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        // http://localhost:8080/api/members/user-ex
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }
    
    
    // http://localhost:8080/api/response-status-ex1?message=
    // Postman으로 검증! => header에 accept 타입을 application/json으로 설정해야 한다
    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1() {
        throw new BadRequestException(); // 여기 설정 때문에 "message": "잘못된 요청 오류"라고 떠야한다
    }

    // http://localhost:8080/api/response-status-ex2?message=
    // Postman으로 검증! => header에 accept 타입을 application/json으로 설정해야 한다
    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
    }

    // http://localhost:8080/api/default-handler-ex?data=hello   =>ERROR
    // http://localhost:8080/api/default-handler-ex?data=10  => OK
    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam("data") Integer data) {
        return "ok";
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}