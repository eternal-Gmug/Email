package org.example.master.repository;

import org.example.master.Entity.InboxEntity;
import org.example.master.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InboxRepository extends JpaRepository<InboxEntity,Long> {

}
