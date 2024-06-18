package com.longketdan.longket.v1.service;

import com.longketdan.longket.v1.model.entity.community.Calendar;
import com.longketdan.longket.v1.repository.community.CalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public List<Calendar> getCalendarList(LocalDate startDate, LocalDate endDate) {
        return null;
    }
}
