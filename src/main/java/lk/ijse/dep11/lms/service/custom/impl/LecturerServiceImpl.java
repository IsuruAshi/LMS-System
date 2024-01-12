package lk.ijse.dep11.lms.service.custom.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import lk.ijse.dep11.lms.entity.Lecturer;
import lk.ijse.dep11.lms.entity.LinkedIn;
import lk.ijse.dep11.lms.entity.Picture;
import lk.ijse.dep11.lms.exception.AppException;
import lk.ijse.dep11.lms.repository.LectureRepository;
import lk.ijse.dep11.lms.repository.LinkedInRepository;
import lk.ijse.dep11.lms.repository.PictureRepository;
import lk.ijse.dep11.lms.service.custom.LecturerService;
import lk.ijse.dep11.lms.service.util.Transformer;
import lk.ijse.dep11.lms.to.LecturerTO;
import lk.ijse.dep11.lms.to.request.LecturerReqTO;
import lk.ijse.dep11.lms.util.LecturerType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
public class LecturerServiceImpl implements LecturerService {
    private final LectureRepository lectureRepository;
    private final LinkedInRepository linkedInRepository;
    private final PictureRepository pictureRepository;
    private final Transformer transformer;
    private final Bucket bucket;

    public LecturerServiceImpl(LectureRepository lectureRepository, LinkedInRepository linkedInRepository, PictureRepository pictureRepository, Transformer transformer, Bucket bucket) {
        this.lectureRepository = lectureRepository;
        this.linkedInRepository = linkedInRepository;
        this.pictureRepository = pictureRepository;
        this.transformer = transformer;
        this.bucket = bucket;
    }

    @Override
    public LecturerTO saveLecturer(LecturerReqTO lecturerReqTO) {
        Lecturer lecturer = transformer.fromLecturerReqTO(lecturerReqTO);
        lectureRepository.save(lecturer);
        if (lecturerReqTO.getLinkedIn() != null) {
            linkedInRepository.save(lecturer.getLinkedIn());
        }
        String signUrl = null;
        if (lecturerReqTO.getPicture() != null) {
            Picture picture = new Picture(lecturer, "lecturers/" + lecturer.getId());
            pictureRepository.save(picture);
            Blob blobRef = null;
            try {
                blobRef = bucket.create(picture.getPicturePath(), lecturerReqTO.getPicture().getInputStream(),
                        lecturerReqTO.getPicture().getContentType());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            signUrl = (blobRef.signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
        }
        LecturerTO lecturerTO = transformer.toLecturerTO(lecturer);
        lecturerTO.setPicture(signUrl);

        return lecturerTO;
    }

    @Override
    public void updateLecturerDetails(LecturerReqTO lecturerReqTO) {
        Lecturer currentLecturer = lectureRepository.findById(lecturerReqTO.getId())
                .orElseThrow(() -> new AppException(404, "No lecturer associated with the id"));
        Blob blobRef = null;
        if (currentLecturer.getPicture() != null) {
            blobRef = bucket.get(currentLecturer.getPicture().getPicturePath());
            pictureRepository.delete(currentLecturer.getPicture());
        }
        if (currentLecturer.getLinkedIn() != null) {
            linkedInRepository.delete(currentLecturer.getLinkedIn());
        }
        Lecturer newLecturer = transformer.fromLecturerReqTO(lecturerReqTO);
        newLecturer.setLinkedIn(null);
        newLecturer = lectureRepository.save(newLecturer);
        if (lecturerReqTO.getPicture()!=null){
            Picture picture = new Picture(newLecturer, "lecturers/" + newLecturer.getId());
            newLecturer.setPicture(pictureRepository.save(picture));
        }
        if(lecturerReqTO.getPicture()!=null){
            LinkedIn linkedIn = new LinkedIn(newLecturer, lecturerReqTO.getLinkedIn());
            newLecturer.setLinkedIn(linkedInRepository.save(linkedIn));
        }
        try {
            if (lecturerReqTO.getPicture() != null) {
                bucket.create(newLecturer.getPicture().getPicturePath(), lecturerReqTO.getPicture().getInputStream(), lecturerReqTO.getPicture().getContentType());
            }
        }catch (IOException e){
            throw new AppException(500, "Failed to update the image",e);
        }

    }

    @Override
    public void updateLecturerDetails(LecturerTO lecturerTO) {
        Lecturer currentLecturer = lectureRepository.findById(lecturerTO.getId()).orElseThrow(() -> new AppException(404, "No lecturer associated with the id"));
        if(currentLecturer.getLinkedIn()!=null){
            linkedInRepository.delete(currentLecturer.getLinkedIn());
        }
        Lecturer newLecturer = transformer.fromLecturerTO(lecturerTO);
        newLecturer.setLinkedIn(null);
        newLecturer=lectureRepository.save(newLecturer);
        if(lecturerTO.getLinkedIn()!=null){
            LinkedIn linkedIn = new LinkedIn(newLecturer, lecturerTO.getLinkedIn());
            newLecturer.setLinkedIn(linkedInRepository.save(linkedIn));
        }
    }

    @Override
    public void deleteLecturer(Integer lecturerId) {
        if(!lectureRepository.existsById(lecturerId)) throw new AppException(404, "No lecturer found");
        lectureRepository.deleteById(lecturerId);
    }

    @Override
    public LecturerTO getLecturerDetails(Integer lecturerId) {
        Optional<Lecturer> optionalLecturer=lectureRepository.findById(lecturerId);
        if(optionalLecturer.isEmpty()) throw new AppException(404,"No lecturer found");
        LecturerTO lecturerTO = transformer.toLecturerTO(optionalLecturer.get());
        if (optionalLecturer.get().getPicture()!=null){
            lecturerTO.setPicture(bucket.get(optionalLecturer.get().getPicture().getPicturePath()).signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
        }
        return lecturerTO;
    }

    @Override
    public List<LecturerTO> getLecturers(LecturerType type) {
        List<Lecturer> lecturerList;
        if(type==LecturerType.FULL_TIME){
            lecturerList=lectureRepository.findFullTimeLecturers();
        } else if (type==LecturerType.VISITING) {
            lecturerList=lectureRepository.findVisitingLecturers();

        }else {
            lecturerList=lectureRepository.findAll();
        }
        return lecturerList.stream().map(l->{
            LecturerTO lecturerTO=transformer.toLecturerTO(l);
            if(l.getPicture()!=null){
                lecturerTO
                        .setPicture(bucket.get(l.getPicture().getPicturePath()).signUrl(1, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature()).toString());
            }
            return lecturerTO;
        }).collect(Collectors.toList());
    }
}
