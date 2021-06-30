package com.tenniscourts.schedules;

import com.tenniscourts.exceptions.EntityNotFoundException;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import com.tenniscourts.tenniscourts.TennisCourtService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    @Autowired
    private  TennisCourtService tennisCourtService;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
        TennisCourtDTO tennisCourtDTO = tennisCourtService.findTennisCourtById(tennisCourtId);
        if (tennisCourtDTO != null) {
            ScheduleDTO sDTO = new ScheduleDTO();
            sDTO.setTennisCourt(tennisCourtDTO);
            sDTO.setStartDateTime(createScheduleRequestDTO.getStartDateTime());
            sDTO.setEndDateTime(createScheduleRequestDTO.getEndDateTime());
            sDTO.setTennisCourtId(tennisCourtId);

            return scheduleMapper.map(scheduleRepository.saveAndFlush(scheduleMapper.map(sDTO)));
        } else {
            return null;
        }
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository.findAllByEndDateTimeLessThanEqualAndStartDateTimeGreaterThanEqual(startDate, endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).map(scheduleMapper::map).orElseThrow(() ->  new EntityNotFoundException("Schedule not found."));
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
