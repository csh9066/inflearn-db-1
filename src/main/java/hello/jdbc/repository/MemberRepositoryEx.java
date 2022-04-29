package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;

/**
 * 레포지토리를 서비스 계층에서 사용할 때 특정 데이터베이스에 의존하고 싶지 않아
 * 인터페이스를 만들었음
 * 그런데 인터페이스 구현체가 체크 예외를 던지려면 인터페이스에서도 체크 예외를 던지는 부분이 선언 되어야 함
 * 인터페이스를 만드는 목적은 구현체를 쉽게 변경하기 쉽게 만듬인데 결국 특정 기술에 의존할 수 바께 없음
 */
public interface MemberRepositoryEx {
    int save(Member member) throws SQLException;
    Member findById(String memberId) throws SQLException;
    void update(String memberId, int money) throws SQLException;
    void delete(String memberId) throws SQLException;
}
