package kr.tastyhouse.www.repository;

import kr.tastyhouse.www.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 JpaRepository가 들고 있음.
// @Repository라는 어노테이션이 없어도 Ioc되요. 이유는 JpaRepository를 상속했기 때문에...
public interface UserRepository extends JpaRepository<User, Integer> {
  // findBy 규칙 -> Username 문법
  // SELECT * FROM user WHERE username = String username
  User findByUsername(String username); // Jpa Query method
}
