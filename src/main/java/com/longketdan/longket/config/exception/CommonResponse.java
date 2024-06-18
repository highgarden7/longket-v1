package com.longketdan.longket.config.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
public class CommonResponse {
    @JsonIgnore
    private static final String MESSAGE_SUCCESS = "SUCCESS";

    @Schema(description = "성공이면 0, 그 이외에는 모두 에러에맞는 코드값 반환")
    private int code;
    @Schema(description = "성공이면 SUCCESS 실패에 맞는 message 반환")
    private String message;
    
    private HttpStatus status;
    private String name;


    /**
     * Exception handler
     * @param e
     * @return
     */
    public static ResponseEntity<CommonResponse> errorResponseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(CommonResponse.builder()
                        .code(e.getCode())
                        .status(e.getHttpStatus())
                        .name(e.name())
                        .message(e.getMessage())
                        .build()
                );
    }

    /**
     * successResponseEntity
     * @return
     */
    public static ResponseEntity<Object> successResponseEntity() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "SUCCESS");
        map.put("code", 0);
        map.put("message", "성공");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * data ResponseEntity
     * @param data
     * @return
     */
    public static ResponseEntity<Object> dataResponseEntity(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "SUCCESS");
        map.put("code", 0);
        map.put("message", "성공");
        map.put("data", data);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * data ResponseEntity
     * @param data
     * @return
     */
    public static ResponseEntity<Object> dataListResponseEntity(Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "SUCCESS");
        map.put("code", 0);
        map.put("message", "성공");
        map.put("list", data);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * cacheDataResponseEntity
     * @param data
     * @param timeData
     * @param timeUnit
     * @return
     */
    public static ResponseEntity<Object> cacheDataResponseEntity(Object data, int timeData, TimeUnit timeUnit){
        Map<String, Object> map = new HashMap<>();
        map.put("status", "SUCCESS");
        map.put("code", 0);
        map.put("message", "성공");
        map.put("data", data);

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(timeData, timeUnit)).body(map);
    }

    /**
     * list ResponseEntity
     * @param list
     * @return
     */
    public static ResponseEntity<Object> listResponseEntity(Page<Object> list) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "SUCCESS");
        map.put("code", 0);
        map.put("message", "성공");
        map.put("list", list.getContent());
        map.put("pageNumber", list.getNumber()+1);
        map.put("pageSize", list.getSize());
        map.put("totalCount", list.getTotalElements());
        map.put("totalPage", list.getTotalPages());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * cacheListResponseEntity
     * @param list
     * @return
     */
    public static ResponseEntity<Object> listResponseEntity(Page<Object> list, int timeData, TimeUnit timeUnit){
        Map<String, Object> map = new HashMap<>();
        map.put("status", "SUCCESS");
        map.put("code", 0);
        map.put("message", "성공");
        map.put("list", list.getContent());
        map.put("pageNumber", list.getNumber()+1);
        map.put("pageSize", list.getSize());
        map.put("totalCount", list.getTotalElements());
        map.put("totalPage", list.getTotalPages());

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(timeData, timeUnit)).body(map);
    }

    public static <T, U> Page<U> convert(Page<T> originalPage) {
        return (Page<U>) originalPage;
    }

    public static <T, U> Page<U> convert(Page<T> originalPage, List<U> convertedContent) {
        return new PageImpl<>(
                convertedContent,
                originalPage.getPageable(),
                originalPage.getTotalElements()
        );
    }

    public static <T, U> Page<U> convert(Page<T> originalPage, Function<T, U> converter) {
        List<U> convertedContent = originalPage.getContent().stream()
                .map(converter)
                .collect(Collectors.toList());

        return convert(originalPage, convertedContent);
    }

}
