package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberRepositoryV1Test {
    MemberRepositoryV1 memberRepository;

    @BeforeEach
    void beforeEach() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void curd() throws SQLException {

        String memberId = "memberV2";
        Member member = new Member(memberId, 10000);
        memberRepository.save(member);

        Member findMember = memberRepository.findById(memberId);
        assertThat(findMember).isEqualTo(member);

        memberRepository.update(memberId, 40000);
        Member updateMember = memberRepository.findById(memberId);
        assertThat(updateMember.getMoney()).isEqualTo(40000);

        memberRepository.delete(memberId);
        assertThrows(NoSuchElementException.class, () -> {
            memberRepository.findById(memberId);
        });
    }

}