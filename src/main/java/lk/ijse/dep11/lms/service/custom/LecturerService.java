package lk.ijse.dep11.lms.service.custom;

import lk.ijse.dep11.lms.service.SuperService;
import lk.ijse.dep11.lms.to.LecturerTO;
import lk.ijse.dep11.lms.to.request.LecturerReqTO;
import lk.ijse.dep11.lms.util.LecturerType;

import java.util.List;

public interface LecturerService extends SuperService {
    LecturerTO saveLecturer(LecturerReqTO lecturerReqTO);
    void updateLecturerDetails(LecturerReqTO lecturerReqTO);
    void updateLecturerDetails(LecturerTO lecturerTO);
    void deleteLecturer(Integer lecturerId);
    LecturerTO getLecturerDetails(Integer lecturerId);
    List<LecturerTO> getLecturers(LecturerType type);

}
