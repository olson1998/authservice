package com.olson1998.authdata.application.datasource.repository.tenant.spring;

import com.olson1998.authdata.application.datasource.entity.tenant.UserSecretByteData;
import com.olson1998.authdata.application.datasource.entity.tenant.id.UserSecretByteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSecretByteJpaRepository extends JpaRepository<UserSecretByteData, UserSecretByteId> {

    @Query("SELECT usb.byteValue " +
            "FROM UserSecretByteData usb " +
            "LEFT OUTER JOIN UserData u ON u.id=usb.byteId.userId " +
            "WHERE u.id=:userId " +
            "ORDER BY usb.byteId.byteOrdinal ASC")
    List<Byte> selectUserSecretBytesByUserId(long userId);

    @Query("DELETE FROM UserSecretByteData usb WHERE usb.byteId.userId=:userId")
    int deleteUserSecretByUserId(long userId);
}
