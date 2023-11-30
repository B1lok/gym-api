package com.example.gymapi.web.dto.statistic;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
public class StatisticDto implements Serializable {

    private Integer numberOfCustomers;

    private Integer numberOfCoaches;

    private Integer overallSubscriptionsSold;

    private Integer subscriptionSoldThisMonth;

    private Map<String, Integer> eachMonthStatistic;
}