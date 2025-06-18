package com.LinkTable.LinkTable.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LinkTable.LinkTable.model.Plan;
import com.LinkTable.LinkTable.repository.PlanRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PlanService {

    @Autowired
    PlanRepository planRepository;

    public List<Plan> findAll(){
        return  planRepository.findAll();
    }

    @SuppressWarnings("deprecation")
    public Plan getById(long id){
        return planRepository.getById(id);
    }

    public Plan save(Plan plan){
        return planRepository.save(plan);
    }

    public void delete(long id){
        planRepository.deleteById(id);
    }

    public Plan patchPlan(Long id, Plan plan){
        Optional<Plan> planOptional = planRepository.findById(id);
        if(planOptional.isPresent()){

            Plan planToUpdate = planOptional.get();
            if(plan.getTipoPlan()!= null){
                planToUpdate.setTipoPlan(plan.getTipoPlan());
            }

            if(plan.getPrecioPlan()!= null){
                planToUpdate.setPrecioPlan(plan.getPrecioPlan());
            }
            if (plan.getDuracionPlan() != null) {
                planToUpdate.setDuracionPlan(plan.getDuracionPlan());
            }

            return planRepository.save(planToUpdate);
        }
        else{
            return null;
        }
    }

}
