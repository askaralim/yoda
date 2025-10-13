package com.taklip.yoda.dto;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemInfoDTO {
    private Map<String, String> serverStats;
    private Map<String, String> threadStats;
    private Map<String, String> jvmStats;
}
