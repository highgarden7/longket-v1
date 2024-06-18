package com.longketdan.longket.v1.service.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadedInfo {
    String absoluteUri;
    Long length;
}
