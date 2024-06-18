package com.longketdan.longket.v1.service;

import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import com.longketdan.longket.v1.controller.dto.ConfigDTO;
import com.longketdan.longket.v1.model.entity.Config;
import com.longketdan.longket.v1.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfigService {
    private final ConfigRepository configRepository;

    public String getAppVersion() {
        return configRepository.findById(1L).get().getAppVersion();
    }

    public Object getFilterList() {
        Config config = configRepository.findById(1L).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        List<String> footTrickFilter = null;
        List<String> handTrickFilter = null;
        List<String> dancingFilter = null;

        if(config.getFootTrickFilter() != null) footTrickFilter = Arrays.asList(config.getFootTrickFilter().split(","));
        if (config.getHandTrickFilter() != null) handTrickFilter = Arrays.asList(config.getHandTrickFilter().split(","));
        if (config.getDancingFilter() != null) dancingFilter = Arrays.asList(config.getDancingFilter().split(","));

        return ConfigDTO.FilterListDTO.of(footTrickFilter, handTrickFilter, dancingFilter);
    }
}
