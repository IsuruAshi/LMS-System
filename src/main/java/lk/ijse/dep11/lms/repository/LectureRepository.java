package lk.ijse.dep11.lms.repository;

import lk.ijse.dep11.lms.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecturer,Integer> {
}
