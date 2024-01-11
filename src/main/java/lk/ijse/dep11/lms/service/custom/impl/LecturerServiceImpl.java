package lk.ijse.dep11.lms.service.custom.impl;

import lk.ijse.dep11.lms.repository.LectureRepository;
import lk.ijse.dep11.lms.repository.LinkedInRepository;
import lk.ijse.dep11.lms.repository.PictureRepository;
import lk.ijse.dep11.lms.service.custom.LecturerService;
import lk.ijse.dep11.lms.service.util.Transformer;
import lk.ijse.dep11.lms.to.LecturerTO;
import lk.ijse.dep11.lms.to.request.LecturerReqTO;
import lk.ijse.dep11.lms.util.LecturerType;

import java.util.List;

public class LecturerServiceImpl implements LecturerService {
//    private final LectureRepository lectureRepository;
//    private final LinkedInRepository linkedInRepository;
//    private final PictureRepository pictureRepository;
//    private final Transformer transformer;

    @Override
    public LecturerTO saveLecturer(LecturerReqTO lecturerReqTO) {
        return null;
    }

    @Override
    public void updateLecturerDetails(LecturerReqTO lecturerReqTO) {

    }

    @Override
    public void updateLecturerDetails(LecturerTO lecturerTO) {

    }

    @Override
    public void deleteLecturer(Integer lecturerId) {

    }

    @Override
    public LecturerTO getLecturerDetails(Integer lecturerId) {
        return null;
    }

    @Override
    public List<LecturerTO> getLecturers(LecturerType type) {
        return null;
    }
}
