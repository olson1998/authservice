package com.olson1998.authdata.domain.port.data.utils;

import javax.sql.DataSource;

public interface SqlDataSourceType {

    boolean isMsSql();

    boolean isPostgres();

    boolean isMariaDb();
}
