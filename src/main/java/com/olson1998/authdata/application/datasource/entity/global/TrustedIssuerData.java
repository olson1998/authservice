package com.olson1998.authdata.application.datasource.entity.global;

import com.olson1998.authdata.application.datasource.entity.global.id.TrustedIssuerId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Persistable;

@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "TNTTRUSTISS")
public class TrustedIssuerData implements Persistable<TrustedIssuerId> {

    @EmbeddedId
    private TrustedIssuerId trustedIssuerId;

    @Override
    public TrustedIssuerId getId() {
        return trustedIssuerId;
    }

    public String getName(){
        return trustedIssuerId.getName();
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public TrustedIssuerData(@NonNull String name, @NonNull String tid) {
        this.trustedIssuerId = new TrustedIssuerId(name, tid);
    }
}
