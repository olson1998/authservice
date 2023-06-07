package com.olson1998.authdata.domain.service.processing;

import com.olson1998.authdata.domain.port.data.repository.AuthorityDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.Authority;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import com.olson1998.authdata.domain.service.processing.request.AuthorityRequestProcessingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static com.olson1998.authdata.application.datasource.entity.AuthorityTestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.AuthorityRequestDataSet.TEST_AUTHORITY_SAVING_REQUEST;
import static com.olson1998.authdata.application.requesting.model.AuthorityRequestDataSet.TEST_AUTHORITY_SAVING_REQUEST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AuthorityRequestProcessingServiceTest {

    private static final Set<AuthorityDetails> REQUEST_AUTHORITIES_DETAILS =
            TEST_AUTHORITY_SAVING_REQUEST.getAuthoritiesDetails();

    private static final List<Authority> MOCKED_PERSISTED_AUTHORITIES= List.of(
            TEST_AUTHORITY_DATA_1,
            TEST_AUTHORITY_DATA_2,
            TEST_AUTHORITY_DATA_3
    );

    private static final Iterable<String> MOCKED_PERSISTED_AUTHORITIES_IDS = MOCKED_PERSISTED_AUTHORITIES.stream()
            .map(Authority::getId)
            .toList();

    @Mock
    private AuthorityDataSourceRepository authorityDataSourceRepository;

    @InjectMocks
    private AuthorityRequestProcessingService authorityRequestProcessingService;

    @Test
    void shouldSaveAuthorityFromGivenDetailsSet(){
        authorityRequestProcessingService.saveAuthorities(TEST_AUTHORITY_SAVING_REQUEST);

        then(authorityDataSourceRepository).should()
                .saveAuthorities(REQUEST_AUTHORITIES_DETAILS);
    }

    @Test
    void shouldCreateReportBasedOnPersistedData(){
        given(authorityDataSourceRepository.saveAuthorities(REQUEST_AUTHORITIES_DETAILS))
                .willReturn(MOCKED_PERSISTED_AUTHORITIES);

        var report  =
                authorityRequestProcessingService.saveAuthorities(TEST_AUTHORITY_SAVING_REQUEST);

        assertThat(report.getRequestId()).isEqualTo(TEST_AUTHORITY_SAVING_REQUEST_ID);
        assertThat(report.getPersistedAuthoritiesDetailsMap()).containsOnlyKeys(MOCKED_PERSISTED_AUTHORITIES_IDS);
    }

}
