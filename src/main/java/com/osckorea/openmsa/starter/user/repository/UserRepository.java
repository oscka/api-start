package com.osckorea.openmsa.starter.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.osckorea.openmsa.global.enums.UserStatus;
import com.osckorea.openmsa.global.util.jdbc.WithInsert;
import com.osckorea.openmsa.starter.user.domain.User;

import java.util.*;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> , UserRepositoryCustom , WithInsert<User> {

	Optional<User> findByLoginId(String loginId);

	boolean existsByLoginId(String loginId);

	@Override
	void deleteById(UUID uuid);

	@Override
	void delete(User entity);

	@Modifying
	@Query("""
   			UPDATE user_tb
   			SET  user_name = :name
   				, email = :email
   				, phone = :phone
   			WHERE id = :id
			""")
	boolean changeUserInfo(@Param("id") UUID id, @Param("name") String name, @Param("email")String email, @Param("phone")String phone);





	Optional<User> findByIdAndUserStatusIn(UUID uuid, Set<UserStatus> userStatus);

	default Optional<User> findByIdExcludeDeleted(UUID id) {
		return this.findByIdAndUserStatusIn(id, EnumSet.of(UserStatus.ACTIVE, UserStatus.LOCKED,UserStatus.BAN));
	}

	List<User> findByUserStatus(Pageable pageable,UserStatus userStatus);

}
