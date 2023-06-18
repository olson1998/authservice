package com.olson1998.authdata.domain.service.processing.datasource;

import com.olson1998.authdata.domain.port.caching.repository.impl.TenantDataSourceCacheRepository;
import com.olson1998.authdata.domain.port.data.repository.TenantSqlDbPropertiesDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSource;
import com.olson1998.authdata.domain.port.processing.datasource.SqlDataSourceFactory;
import com.olson1998.authdata.domain.port.processing.datasource.TenantSqlDataSourceRepository;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class TenantSqlDataSourceService implements TenantSqlDataSourceRepository {

    private static final String TENANT_DATA_SOURCE_TIMESTAMP_KEY_FORMAT =
            "com.olson1998.authdata.TenantDataSourceTimestamp(tid=%s)";

    private static final String TENANT_DATA_SOURCE_KEY_FORMAT =
            "com.olson1998.authdata.TenantDataSource(tid=%s,tmp=%s)";

    private final SqlDataSourceFactory sqlDataSourceFactory;

    private final TenantDataSourceCacheRepository tenantDataSourceCacheRepository;

    private final TenantSqlDbPropertiesDataSourceRepository tenantSqlDbPropertiesDataSourceRepository;

    @Override
    public DataSource getForTenant(String tid) {
        var tenantDataSource = getTenantDataSource(tid);
        return sqlDataSourceFactory.fabricate(tenantDataSource);
    }

    private TenantDataSource getTenantDataSource(String tid){
        var timestampKey = getTimestampCacheKey(tid);
        var timestampOpt = tenantDataSourceCacheRepository.getHashValue(timestampKey);
        if(timestampOpt.isPresent()){
            var tmp = timestampOpt.get();
            if(tenantSqlDbPropertiesDataSourceRepository.isTenantDataSourceTimestampValid(tid, tmp)){
                var dsKey = getTenantDataSourceCacheKey(tid, tmp);
                return tenantDataSourceCacheRepository.getValue(dsKey)
                        .orElseThrow();
            } else {
                var tenantDataSource = getFromDataBase(tid);
                timestampKey = getTimestampCacheKey(tid);
                var dsKey = getTenantDataSourceCacheKey(tid, tenantDataSource.getTimestamp());
                tenantDataSourceCacheRepository.cacheHashValue(timestampKey, tenantDataSource.getTimestamp());
                tenantDataSourceCacheRepository.cacheValue(dsKey, tenantDataSource);
                return tenantDataSource;
            }
        } else {
            var tenantDataSource = getFromDataBase(tid);
            timestampKey = getTimestampCacheKey(tid);
            var dsKey = getTenantDataSourceCacheKey(tid, tenantDataSource.getTimestamp());
            tenantDataSourceCacheRepository.cacheHashValue(timestampKey, tenantDataSource.getTimestamp());
            tenantDataSourceCacheRepository.cacheValue(dsKey, tenantDataSource);
            return tenantDataSource;
        }
    }

    private TenantDataSource getFromDataBase(String tid){
        return tenantSqlDbPropertiesDataSourceRepository.getTenantDataSourceByTenantId(tid)
                .orElseThrow();
    }

    private String getTimestampCacheKey(String tid){
        return String.format(TENANT_DATA_SOURCE_TIMESTAMP_KEY_FORMAT, tid);
    }

    private String getTenantDataSourceCacheKey(String tid, Long timestamp){
        return String.format(TENANT_DATA_SOURCE_KEY_FORMAT, tid, timestamp);
    }
}
