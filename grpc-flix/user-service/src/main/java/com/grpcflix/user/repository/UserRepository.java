package com.grpcflix.user.repository;

import com.grpcflix.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Ezekiel Eromosei
 * @created : 04 Oct, 2021
 */

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
