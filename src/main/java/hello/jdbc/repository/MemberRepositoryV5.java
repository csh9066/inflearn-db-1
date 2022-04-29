package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

/**
 * 스프링에서 제공하는 ExceptionTranslator을 이용한 예외 추상화
 */

@Slf4j
public class MemberRepositoryV5 implements MemberRepository{
    private final JdbcTemplate template;

    public MemberRepositoryV5(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public int save(Member member) {
        String sql = "insert into member(member_id, money) values(?, ?)";
        return template.update(sql, member.getMemberId(), member.getMoney());
    }

    public Member findById(String id) {
        String sql = "select * from member where member_id = ?";
        return template.queryForObject(sql, memberRowMapper(), id);
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberId(rs.getString(1));
            member.setMoney(rs.getInt(2));
            return member;
        };
    }

    public void update(String memberId, int money) {
        String sql = "update member set money = ? where member_id = ?";
        template.update(sql, money, memberId);
    }

    public void delete(String memberId) {
        String sql = "delete from member where member_id = ?";
        template.update(sql, memberId);
    }
}
