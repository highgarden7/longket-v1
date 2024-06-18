package com.longketdan.longket.v1.controller;

import com.longketdan.longket.config.exception.CommonResponse;
import com.longketdan.longket.v1.service.skill.BasicSkillService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/basic-skill")
@RequiredArgsConstructor
public class BasicSkillController {
    private final BasicSkillService basicSkillService;

    @GetMapping("/list")
    @Operation(summary = "베이직 스킬 리스트 조회", description = "베이직 스킬 리스트 조회")
    public ResponseEntity<Object> getBasicSkillList() {
        return CommonResponse.dataListResponseEntity(basicSkillService.getAllBasicSkills());
    }
}
