package com.ipactconsult.tawasalnabackendapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyUsageStats {
    private String equipmentId;
    private String equipmentName;
    private long totalUsageDuration;
    private long dailyUsageDuration;
    private long weeklyUsageDuration;
    private long monthlyUsageDuration;
}
