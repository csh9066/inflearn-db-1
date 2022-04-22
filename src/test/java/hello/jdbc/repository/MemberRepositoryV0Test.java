package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {
    MemberRepositoryV0 memberRepositoryV0 = new MemberRepositoryV0();

    @Test
    void curd() throws SQLException {
        String memberId = "memberV2";
        Member member = new Member(memberId, 10000);
        memberRepositoryV0.save(member);

        Member findMember = memberRepositoryV0.findById(memberId);
        assertThat(findMember).isEqualTo(member);

        memberRepositoryV0.update(memberId, 40000);
        Member updateMember = memberRepositoryV0.findById(memberId);
        assertThat(updateMember.getMoney()).isEqualTo(40000);

        memberRepositoryV0.delete(memberId);
        assertThrows(NoSuchElementException.class, () -> {
            memberRepositoryV0.findById(memberId);
        });
    }

}