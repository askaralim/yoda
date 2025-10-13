package com.taklip.yoda.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.taklip.yoda.dto.SystemInfoDTO;
import com.taklip.yoda.vo.SystemStatistics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/system")
@Tag(name = "System Management", description = "System management API endpoints")
public class SystemApiController {
    @GetMapping("/info")
    @Operation(summary = "Get system information", description = "Get system information")
    public ResponseEntity<SystemInfoDTO> getSystemInfo() {
        SystemStatistics statistics = new SystemStatistics();
        SystemInfoDTO systemInfoDTO = SystemInfoDTO.builder()
                .serverStats(statistics.getServerStats()).threadStats(statistics.getThreadStats())
                .jvmStats(statistics.getJvmStats()).build();
        return ResponseEntity.ok(systemInfoDTO);
    }
}