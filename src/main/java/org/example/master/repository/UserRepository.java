package org.example.master.repository;

import org.example.master.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//JpaRepository接口用于管理实体类和数据库，为实体类数据存储进数据库提供接口，第一个参数是实体类类型，第二个参数是实体类主键的类型
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    //自定义查询方法
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String mail);
}
