package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * 트랜잭션 템플릿 사용
 */
@RequiredArgsConstructor
public class MemberServiceV3_2 {
    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 memberRepositoryV3;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepositoryV3) {
        this.memberRepositoryV3 = memberRepositoryV3;
        this.txTemplate = new TransactionTemplate(transactionManager);
    }

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        txTemplate.executeWithoutResult(transactionStatus -> {
            try {
                bizLogic(fromId, toId, money);
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        });

    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepositoryV3.findById(fromId);
        Member toMember = memberRepositoryV3.findById(toId);

        memberRepositoryV3.update(fromId, fromMember.getMoney() - money);
        validation(toMember);

        memberRepositoryV3.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
