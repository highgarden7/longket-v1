package com.longketdan.longket.v1.service.skill;

import com.longketdan.longket.v1.model.entity.skill.BasicSkill;
import com.longketdan.longket.v1.repository.skill.BasicSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasicSkillService {
    private final BasicSkillRepository basicSkillRepository;

    public List<BasicSkill> getAllBasicSkills() {
        return basicSkillRepository.findAll();
    }
}
