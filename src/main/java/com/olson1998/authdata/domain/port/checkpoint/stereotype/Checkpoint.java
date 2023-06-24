package com.olson1998.authdata.domain.port.checkpoint.stereotype;

import java.util.LinkedList;
import java.util.UUID;

public interface Checkpoint extends CheckpointValues{

    UUID getId();

    boolean isExpiring();

    boolean isUsageCount();

    LinkedList<String> getLogs();

    String writeCheckpointToken(String sign);

    boolean verifyCheckpointToken(String checkpointToken, String sign);

}
