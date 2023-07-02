package com.olson1998.authdata.application.datasource.entity.tenant;

import com.olson1998.authdata.domain.port.data.stereotype.UserBan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

@Getter
@Setter

@Entity
@Table(name = "AUTHUSERBAN")

@NoArgsConstructor
@AllArgsConstructor
public class UserBansData implements UserBan {

    @Id
    @Column(name = "BANID")
    private String id;

    @Column(name = "USERID", nullable = false)
    private Long userId;

    @Column(name = "BANREASON")
    private String reason;

    @Column(name = "BANTMP", nullable = false)
    private Long banTimestamp;

    @Column(name = "BANENDTM")
    private Long endTime;

    @Transient
    private Long banDuration;

    @Transient
    private TemporalUnit banDurationTimeUnit;

    @PrePersist
    public void createValues(){
        this.banTimestamp = System.currentTimeMillis();
        if(banDuration != null){
            this.endTime = banTimestamp + Duration.of(banDuration, banDurationTimeUnit).toMillis();
        }
        this.id = new StringBuilder("BAN&")
                .append(userId)
                .append('&')
                .append(banTimestamp)
                .append('&')
                .append(RandomStringUtils.randomAlphanumeric(4))
                .toString();
    }
}
