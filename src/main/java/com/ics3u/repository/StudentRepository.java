package com.ics3u.repository;

import com.ics3u.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // 通过用户名和密码查找（用作登录）
    Optional<Student> findByUsernameAndPassword(String username, String password);

    // 通过用户名查找（用于注册查重）
    Optional<Student> findByUsername(String username);

    // 通过邮箱查找
    Optional<Student> findByEmail(String email);
}

