package com.example.springjdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class UnCheckedAppTest {
    @Test
    void unChecked() {
        Controller controller = new Controller();
        assertThatThrownBy(() -> controller.request()).isInstanceOf(Exception.class);
    }

    @Test
    void printEx() {
        Controller controller = new Controller();
        try {
            controller.request();
        } catch (Exception e) {
            // e.printStackTrace(); // 사용을 권장하지 않음
            log.info("ex", e);
        }
    }

    static class Controller {
        Service service = new Service();

        // public void request() throws SQLException, ConnectException {
        public void request() {
            service.logic();
        }
    }

    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        // public void logic() throws SQLException, ConnectException {
        public void logic() {
            repository.call();
            networkClient.call();
        }

    }

    static class NetworkClient {
        // public void call() throws ConnectException {
        public void call() {
            // throw new ConnectException("연결 실패");
            throw new RuntimeConnectException("연결 실패");
        }
    }

    static class Repository {
        // public void call() throws SQLException {
        public void call() {
            // throw new SQLException("ex");
            try {
                runSQL();
            } catch (SQLException e) {
                // throw new RuntimeSQLException(e);
                throw new RuntimeSQLException();
            }
        }

        public void runSQL() throws SQLException {
            throw new SQLException("ex");
        }
    }

    static class RuntimeConnectException extends RuntimeException {
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException() {
        }

        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }
}
