package lk.ijse.dep11.lms.to;

import lk.ijse.dep11.lms.util.LecturerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LecturerTO implements Serializable {
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
    @NotNull(message = "Display order can't be empty")
    @PositiveOrZero(message = "Invalid Display Order")
    private Integer displayOrder;
    @Null(message = "Picture should be empty")
    private String picture;
    @Pattern(regexp = "http(s)://.+$",message = "Invalid Linkedin Url")
    private String linkedIn;

}
