package at.spengergasse.spring_thymeleaf.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface reservationRepository extends JpaRepository<reservation,Integer> {
    List<reservation> findByPatientId(Integer patientId);

    List<reservation> findByMachineId(int machineId);

}
