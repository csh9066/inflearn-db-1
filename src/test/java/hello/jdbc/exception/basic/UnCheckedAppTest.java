package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

@Slf4j
public class UnCheckedAppTest {

    /**
     * 체크 예외를 언체크 예외로 변환해서 테스트하기`
     */
    @Test
    void unChecked() {
        Controller controller = new Controller();
        Assertions.assertThatThrownBy(() -> controller.request())
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void printEx() {
        Controller controller = new Controller();
        try {
            controller.request();
        } catch (Exception e) {
            log.info("ex", e);
        }
    }

    static class Controller {
        private Service service = new Service();

        public void request() {
            service.logic();
        }
    }

    static class Service {
        private Repository repository = new Repository();
        private NetWorkClient netWorkClient = new NetWorkClient();

        public void logic() {
            repository.call();
            netWorkClient.call();
        }

    }

    static class NetWorkClient {
        public void call() {
            throw new RunTimeConnectException("연결 실패");
        }
    }

    static class Repository {
        public void call() {
            try {
                runSql();
            } catch (SQLException e) {
                throw new RunTimeSqlException(e);
            }
        }

        public void runSql() throws SQLException {
            throw new SQLException("ex");
        }
    }

    static class RunTimeConnectException extends RuntimeException {
        public RunTimeConnectException(String message) {
            super(message);
        }
    }

    static class RunTimeSqlException extends RuntimeException {
        public RunTimeSqlException(Throwable cause) {
            super(cause);
        }

        public RunTimeSqlException(String message) {
            super(message);
        }

        public RunTimeSqlException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
