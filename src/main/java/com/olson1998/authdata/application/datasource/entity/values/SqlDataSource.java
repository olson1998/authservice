package com.olson1998.authdata.application.datasource.entity.values;

import com.olson1998.authdata.domain.port.data.utils.SqlDataSourceType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SqlDataSource implements SqlDataSourceType {
    MS_SQL(true, false, false),
    POSTGRES(false, true, false),
    MARIA_DB(false, false, true);

    private final boolean msSql;

    private final boolean postgres;

    private final boolean mariaDb;
}
