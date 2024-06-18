package com.longketdan.longket.v1.controller;

import com.longketdan.longket.config.exception.CommonResponse;
import com.longketdan.longket.v1.application.DancingApplicationService;
import com.longketdan.longket.v1.controller.dto.DancingDTO;
import com.longketdan.longket.v1.service.skill.DancingService;
import com.longketdan.longket.v1.service.skill.FavoriteTrickService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/dancing")
@Slf4j
@RequiredArgsConstructor
public class DancingController {
    private final DancingApplicationService dancingApplicationService;

    private final DancingService dancingService;

    private final FavoriteTrickService favoriteTrickService;

    @GetMapping("")
    @Operation(summary = "단일 댄싱 묶음", description = "단일 댄싱 조회")
    public ResponseEntity<Object> getTrick(@RequestParam(value = "originalName") String originalName) {
        return CommonResponse.dataListResponseEntity(dancingService.getDancingByOriginalName(originalName).stream()
                .map(DancingDTO.dancingResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping("/list")
    @Operation(summary = "댄싱 리스트 조회", description = "댄싱 리스트 요청 paging")
    public ResponseEntity<Object> getFootTrickList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                   @RequestParam(value = "keyword", required = false) @Parameter(description = "트릭명 ex) 피봇, pivot ...") String keyword,
                                                   @RequestParam(value = "aliasName", required = false) @Parameter(description = "aliasName ex) pivot ...") String aliasName,
                                                   @RequestParam(value = "drivingDirection", required = false) @Parameter(description = "주행방향 ex) FRONT | BACK") String drivingDirection,
                                                   @RequestParam(value = "footPosition", required = false) @Parameter(description = "풋 포지션 ex) FRONT | BACK") String footPosition,
                                                   @RequestParam(value = "carving", required = false) @Parameter(description = "카빙 ex) heel | toe") String carving,
                                                   @RequestParam(value = "steps", required = false) @Parameter(description = "카빙 ex) heel | toe") Long steps,
                                                   @RequestParam(value = "bodyDegree", required = false) @Parameter(description = "카빙 ex) heel | toe") Long bodyDegree,
                                                   @RequestParam(value = "difficulty", required = false) String difficulty) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(dancingApplicationService.getDancingList(
                page,
                pageSize,
                keyword,
                aliasName,
                drivingDirection,
                footPosition,
                carving,
                steps,
                bodyDegree,
                difficulty)));
    }

    @PostMapping("/favorite")
    @Operation(summary = "댄싱 즐겨찾기 추가", description = "댄싱 즐겨찾기 추가")
    public ResponseEntity<Object> addFavorite(@RequestParam(value = "dancingId") Long dancingId) {
        favoriteTrickService.createFavoriteTrick(dancingId, "dancing");
        return CommonResponse.successResponseEntity();
    }

    @GetMapping("/favorite/list")
    @Operation(summary = "댄싱 즐겨찾기 리스트 조회", description = "댄싱 즐겨찾기 리스트 조회")
    public ResponseEntity<Object> getFavoriteList() {
        return CommonResponse.dataListResponseEntity(favoriteTrickService.getFavoriteTrickList("dancing"));
    }
}
