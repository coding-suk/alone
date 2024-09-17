package com.web.personalstudy.repository;

import com.web.personalstudy.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Reply findByIdOrElseThrow(Long rid);
}
