package com.github.javarushcommunity.jrtb.service;


import com.github.javarushcommunity.jrtb.javarushclient.dto.StatisticDTO;

/**
 * Service for getting bot statistics.
 */
public interface StatisticsService {
    StatisticDTO countBotStatistic();
}