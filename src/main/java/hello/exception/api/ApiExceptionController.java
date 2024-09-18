package hello.exception.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}