package lk.ijse.dep11.lms.to.request;

import lk.ijse.dep11.lms.util.LecturerType;
import lk.ijse.dep11.lms.validation.LecturerImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LecturerReqTO implements Serializable {
    private Integer id;
    @NotBlank(message = "Name can't be empty")
    @Pattern(regexp = "^[A-Za-z ]{2,}$",message = "Invalid Name")
    private String name;
    @NotBlank(message = "Designation can't be empty")
    @Length(min = 3, message = "Invalid Designation")
    private String designation;
    @NotBlank(message = "Qualifications can't be empty")
    @Length(min = 3, message = "Invalid Qualifications")
    private String qualifications;
    @NotNull(message = "Type should be either full-time or visiting")
    private LecturerType type;
    @Null(groups = Create.class,message = "Display order should be empty ")
    @NotNull(groups = Update.class,message = "Display order can't be empty")
    @PositiveOrZero(message = "Invalid Display Order")
    private Integer displayOrder;
    @LecturerImage
    private MultipartFile picture;
    @Pattern(regexp = "http(s)://.+$",message = "Invalid Linkedin Url")
    private String linkedIn;

    public interface Create extends Default{}
    public interface Update extends Default{}

    public LecturerReqTO(String name, String designation, String qualifications, LecturerType type, Integer displayOrder, MultipartFile picture, String linkedIn) {
        this.name = name;
        this.designation = designation;
        this.qualifications = qualifications;
        this.type = type;
        this.displayOrder = displayOrder;
        this.picture = picture;
        this.linkedIn = linkedIn;
    }
}
