package com.LinkTable.LinkTable.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.LinkTable.LinkTable.model.Plan;
import com.LinkTable.LinkTable.repository.PlanRepository;

@SpringBootTest
public class PlanServiceTest {

    @Autowired
    private PlanService planService;

    @MockBean
    private PlanRepository planRepository;

    private Plan createPlan(){
        return new Plan(
            1,
            "hola",
            1000,
            "1 semana"
        );
    }

    @Test
    public void testFindAll(){
        when(planRepository.findAll()).thenReturn(List.of(createPlan()));
        List<Plan> plan = planService.findAll();
        assertNotNull(plan);
        assertEquals(1, plan.size());
    }

    @Test
    public void testFindById(){
        when(planRepository.findById(1L)).thenReturn(java.util.Optional.of(createPlan()));
        Plan plan = planService.findById(1L);
        assertNotNull(plan);
        assertEquals("hola", plan.getTipoPlan());
    }

    @Test
    public void testSave(){
        Plan plan = createPlan();
        when(planRepository.save(plan)).thenReturn(plan);
        Plan savedPlan = planService.save(plan);
        assertNotNull(savedPlan);
        assertEquals("hola", savedPlan.getTipoPlan());
    }

    @Test void testPatchPlan(){
        Plan existingPlan = createPlan();
        Plan patchData = new Plan();
        patchData.setTipoPlan("hola actualizado");

        when(planRepository.findById(1L)).thenReturn(java.util.Optional.of(existingPlan));
        when(planRepository.save(any(Plan.class))).thenReturn(patchData);

        Plan patchedPlan = planService.patchPlan(1L, patchData);
        assertNotNull(patchedPlan);
        assertEquals("hola actualizado", patchedPlan.getTipoPlan());
    }

    @Test
    public void testDeleteById(){
        doNothing().when(planRepository).deleteById(1L);
        planRepository.deleteById(1L);
        verify(planRepository, times(1)).deleteById(1L);
    }
}
