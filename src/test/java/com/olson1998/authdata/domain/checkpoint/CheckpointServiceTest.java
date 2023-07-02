package com.olson1998.authdata.domain.checkpoint;

import com.olson1998.authdata.domain.port.caching.stereotype.CheckpointTimestamp;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.Checkpoint;
import com.olson1998.authdata.domain.port.processing.request.repository.RequestContextHolder;
import com.olson1998.authdata.domain.port.security.repository.TenantSecretProvider;
import com.olson1998.authdata.domain.service.checkpoint.CheckpointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.olson1998.authdata.application.datasource.entity.UserTestDataSet.TEST_USER_ID;
import static com.olson1998.authdata.application.security.TenantSecretTestDataSet.TEST_TENANT_SECRET;
import static com.olson1998.authdata.domain.model.GlobalTestDataSet.TEST_CHECKPOINT_ID;
import static com.olson1998.authdata.domain.model.GlobalTestDataSet.TEST_TENANT_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CheckpointServiceTest {

    @Mock
    private TenantSecretProvider tenantSecretProvider;

    @Mock
    private RequestContextHolder requestContextHolder;

    @Mock
    private CheckpointCacheRepository checkpointCacheRepository;

    @InjectMocks
    private CheckpointService checkpointService;

    @Captor
    private ArgumentCaptor<CheckpointTimestamp> checkpointTimestampCaptor;

    @Captor
    private ArgumentCaptor<Checkpoint> checkpointCaptor;

    @Test
    void shouldExecuteInOrderObtainingTenantSecretAndCacheCheckpointTimestampThenCheckpoint(){
        var checkpointCreationOrder = Mockito.inOrder(
                tenantSecretProvider,
                checkpointCacheRepository
        );

        given(requestContextHolder.getId())
                .willReturn(TEST_CHECKPOINT_ID);
        given(requestContextHolder.getTenantId())
                .willReturn(TEST_TENANT_ID);
        given(requestContextHolder.getUserId())
                .willReturn(TEST_USER_ID);
        given(tenantSecretProvider.getTenantSecret(TEST_TENANT_ID))
                .willReturn(Optional.ofNullable(TEST_TENANT_SECRET));

        checkpointService.create(null, null);

        checkpointCreationOrder.verify(tenantSecretProvider).getTenantSecret(TEST_TENANT_ID);
        checkpointCreationOrder.verify(checkpointCacheRepository).cacheHashValue(anyString(), any(CheckpointTimestamp.class));
        checkpointCreationOrder.verify(checkpointCacheRepository).cacheValue(anyString(), any(Checkpoint.class));
    }

    @Test
    void shouldCreateCheckpointForGivenTenantUserAndForRequestContextUUID(){
        given(requestContextHolder.getId())
                .willReturn(TEST_CHECKPOINT_ID);
        given(requestContextHolder.getTenantId())
                .willReturn(TEST_TENANT_ID);
        given(requestContextHolder.getUserId())
                .willReturn(TEST_USER_ID);
        given(tenantSecretProvider.getTenantSecret(TEST_TENANT_ID))
                .willReturn(Optional.ofNullable(TEST_TENANT_SECRET));

        checkpointService.create(null, null);

        then(checkpointCacheRepository).should().cacheHashValue(anyString(), checkpointTimestampCaptor.capture());
        then(checkpointCacheRepository).should().cacheValue(anyString(), checkpointCaptor.capture());

        var timestamp = checkpointTimestampCaptor.getValue();
        var checkpoint = checkpointCaptor.getValue();

        assertThat(timestamp.getTenantId()).isEqualTo(TEST_TENANT_ID);
        assertThat(timestamp.getUserId()).isEqualTo(TEST_USER_ID);
        assertThat(checkpoint.getId()).isEqualTo(TEST_CHECKPOINT_ID);
        assertThat(checkpoint.isExpiring()).isFalse();
        assertThat(checkpoint.isUsageCount()).isFalse();
    }

    @Test
    void shouldCreateCheckpointWithExpTimeAndUsageCountIfValuesNotNull(){
        given(requestContextHolder.getId())
                .willReturn(TEST_CHECKPOINT_ID);
        given(requestContextHolder.getTenantId())
                .willReturn(TEST_TENANT_ID);
        given(requestContextHolder.getUserId())
                .willReturn(TEST_USER_ID);
        given(tenantSecretProvider.getTenantSecret(TEST_TENANT_ID))
                .willReturn(Optional.ofNullable(TEST_TENANT_SECRET));

        checkpointService.create(1L, 1);

        then(checkpointCacheRepository).should().cacheValue(anyString(), checkpointCaptor.capture());

        var checkpoint = checkpointCaptor.getValue();

        assertThat(checkpoint.isUsageCount()).isTrue();
        assertThat(checkpoint.isExpiring()).isTrue();
    }
}
