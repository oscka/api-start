package com.hanex.fms.starter.customer.repository;

import com.hanex.fms.global.enums.Group;
import com.hanex.fms.starter.customer.domain.Customer;
import com.hanex.fms.starter.user.domain.BaseUser;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> , CustomerRepositoryCustom{

    // baseUser > loginId 중복 검사
    @Query(CustomerSql.SELECT_BY_LOGIN_ID)
    Optional<BaseUser> selectByLoginId(@Param("loginId") String loginId);

    @Modifying
    @Query(
        """
            UPDATE customer
            SET email = :email
                , group = :group
                , name = :name
                , phone = :phone
                , memo = :memo
                , version = version + 1
           WHERE id = :id
        """
    )
    boolean changeCustomer(@Param("email") String email
                            , @Param("group") Group group
                            , @Param("name") String name
                            , @Param("phone") String phone
                           , @Param("memo") String memo
                           , @Param("id") String id
                        );
}
