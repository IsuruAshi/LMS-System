package lk.ijse.dep11.lms.service.util;

import lk.ijse.dep11.lms.entity.Lecturer;
import lk.ijse.dep11.lms.entity.LinkedIn;
import lk.ijse.dep11.lms.entity.Picture;
import lk.ijse.dep11.lms.to.LecturerTO;
import lk.ijse.dep11.lms.to.request.LecturerReqTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class Transformer {
    private final ModelMapper mapper;

    public Transformer(ModelMapper mapper) {
        this.mapper = mapper;
        mapper.typeMap(LinkedIn.class, String.class)
                .setConverter(ctx-> ctx.getSource()!=null?ctx.getSource().getUrl():null);
        mapper.typeMap(MultipartFile.class, Picture.class)
                .setConverter(ctx->null);
        mapper.typeMap(String.class, LinkedIn.class)
                .setConverter(ctx->ctx.getSource()!=null?new LinkedIn(null,ctx.getSource()):null);
    }

    public Lecturer fromLecturerReqTO(LecturerReqTO lecturerReqTO){
        Lecturer lecturer = mapper.map(lecturerReqTO, Lecturer.class);
        if(lecturerReqTO.getLinkedIn()!=null) lecturer.getLinkedIn().setLecturer(lecturer);
        return lecturer;
    }

    public Lecturer fromLecturerTO(LecturerTO lecturerTO){
        Lecturer lecturer = mapper.map(lecturerTO, Lecturer.class);
        if(lecturerTO.getLinkedIn()!=null) lecturer.getLinkedIn().setLecturer(lecturer);
        return lecturer;
    }
    public LecturerTO toLecturerTO(Lecturer lecturer){return mapper.map(lecturer,LecturerTO.class);}
    public List<LecturerTO>toLecturerTOList(List<Lecturer> lecturersList){
        return lecturersList.stream().map(this::toLecturerTO).collect(Collectors.toList());
    }
}
