package com.longketdan.longket.v1.controller;

import com.longketdan.longket.config.exception.CommonResponse;
import com.longketdan.longket.v1.controller.dto.UserProgressDTO;
import com.longketdan.longket.v1.service.AdminService;
import com.longketdan.longket.v1.service.ConfigService;
import com.longketdan.longket.v1.service.user.UserProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final UserProgressService userProgressService;

    private final ConfigService configService;

    @GetMapping("/userProgress/list")
    @Operation(summary = "유저들이 인증올린 영상들 리스트", description = "유저들이 인증올린 영상들 리스트")
    public ResponseEntity<Object> getUserProgressList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return CommonResponse.listResponseEntity(CommonResponse.convert(userProgressService.getAllWaitedUserProgressList(page, pageSize).map(UserProgressDTO.UserProgressAdminResponse::of)));
    }

    @PatchMapping("/userProgress/{id}")
    @Operation(summary = "등록된 스킬 상태 변경", description = "등록된 스킬 상태 변경")
    public ResponseEntity<Object> updateSkillStatus(@PathVariable(value = "id") Long id,
                                                    @RequestParam(value = "approved") @Parameter(description = "승인 여부 true | false") Boolean status) {
        adminService.updateSkillStatus(id, status);
        return CommonResponse.successResponseEntity();
    }

    @GetMapping("/config")
    @Operation(summary = "앱 버젼 확인 ", description = "앱 버젼 확인")
    public ResponseEntity<Object> getAppVersion() {
        return CommonResponse.dataResponseEntity(configService.getAppVersion());
    }

    @GetMapping("/skill-filter")
    @Operation(summary = "스킬 필터 조회", description = "스킬 필터 조회")
    public ResponseEntity<Object> getFilterList() {
        return CommonResponse.dataResponseEntity(configService.getFilterList());
    }

//    @PostMapping("/notice")
//    @Operation(summary = "공지사항 등록", description = "공지사항 등록")
//    public ResponseEntity<Object> createNotice(@RequestParam(value = "title") String title,
//                                              @RequestParam(value = "description") String description) {
//        adminService.createNotice(title, description);
//        return ResponseHandler.successResponseEntity();
//    }
}