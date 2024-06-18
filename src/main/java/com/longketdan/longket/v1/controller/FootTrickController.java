package com.longketdan.longket.v1.controller;

import com.longketdan.longket.config.exception.CommonResponse;
import com.longketdan.longket.v1.application.FootTrickApplicationService;
import com.longketdan.longket.v1.controller.dto.FootTrickDTO;
import com.longketdan.longket.v1.service.skill.FavoriteTrickService;
import com.longketdan.longket.v1.service.skill.FootTrickService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/foot-tricks")
@Slf4j
@RequiredArgsConstructor
public class FootTrickController {
    private final FootTrickApplicationService footTrickApplicationService;

    private final FootTrickService footTrickService;

    private final FavoriteTrickService favoriteTrickService;

    @GetMapping("")
    @Operation(summary = "단일 풋트릭 묶음", description = "단일 풋트릭 조회")
    public ResponseEntity<Object> getTrick(@RequestParam(value = "originalName") String originalName) {
        return CommonResponse.dataListResponseEntity(footTrickService.getTrickByOriginalName(originalName).stream()
                .map(FootTrickDTO.trickResponse::of)
                .collect(Collectors.toList()));
    }

    @GetMapping("/list")
    @Operation(summary = "풋트릭 리스트 조회", description = "풋트릭 리스트 요청 paging")
    public ResponseEntity<Object> getFootTrickList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                   @RequestParam(value = "keyword", required = false) @Parameter(description = "트릭명 ex) 피봇, pivot ...") String keyword,
                                                   @RequestParam(value = "aliasName", required = false) @Parameter(description = "aliasName ex) pivot ...") String aliasName,
                                                   @RequestParam(value = "footPlant", required = false) @Parameter(description = "스텝 수 ex) 0, 1, 2 ...") Long footPlant,
                                                   @RequestParam(value = "flip", required = false) @Parameter(description = "플립종류 ex) none | kick | heel") String flip,
                                                   @RequestParam(value = "difficulty", required = false) String difficulty,
                                                   @RequestParam(value = "bodyDegree", required = false) Long bodyDegree,
                                                   @RequestParam(value = "boardDegree", required = false) Long boardDegree) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(footTrickApplicationService.getTrickList(
                page,
                pageSize,
                keyword,
                aliasName,
                footPlant,
                flip,
                difficulty,
                bodyDegree,
                boardDegree)));
    }

    @PostMapping("/favorite")
    @Operation(summary = "풋트릭 즐겨찾기 추가", description = "풋트릭 즐겨찾기 추가")
    public ResponseEntity<Object> addFavorite(@RequestParam(value = "footTrickId") Long footTrickId) {
        favoriteTrickService.createFavoriteTrick(footTrickId, "foot_trick");
        return CommonResponse.successResponseEntity();
    }

    @GetMapping("/favorite/list")
    @Operation(summary = "풋트릭 즐겨찾기 리스트 조회", description = "풋트릭 즐겨찾기 리스트 조회")
    public ResponseEntity<Object> getFavoriteList() {
        return CommonResponse.dataListResponseEntity(favoriteTrickService.getFavoriteTrickList("foot_trick"));
    }
}
