package com.olson1998.authdata.application.datasource.entity.tenant;

import com.olson1998.authdata.application.datasource.entity.tenant.id.UserSecretByteId;
import com.olson1998.authdata.domain.port.data.stereotype.UserSecretByte;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "AUTHUSERSEC")

@NoArgsConstructor
@AllArgsConstructor
public class UserSecretByteData implements Persistable<UserSecretByteId>, UserSecretByte {

    @EmbeddedId
    private UserSecretByteId byteId;

    @Column(name = "USERSECBYTE", nullable = false, updatable = false)
    private Byte byteValue;

    @Override
    public UserSecretByteId getId() {
        return byteId;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public UserSecretByteData(long userId, int byteOrdinal, byte byteVal) {
        this.byteId = new UserSecretByteId(userId, byteOrdinal);
        this.byteValue = byteVal;
    }

    @Override
    public Long getUserId() {
        return byteId.getUserId();
    }

    @Override
    public Integer getByteOrdinal() {
        return byteId.getByteOrdinal();
    }

    @Override
    public Byte getByteValue() {
        return byteValue;
    }

    public void setByteId(UserSecretByteId byteId) {
        this.byteId = byteId;
    }

    public void setByteValue(Byte byteValue) {
        this.byteValue = byteValue;
    }
}
