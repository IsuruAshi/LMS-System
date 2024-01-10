package lk.ijse.dep11.lms.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LecturerImageConstraintValidation implements ConstraintValidator<LecturerImage, MultipartFile> {
    private long maximumFileSize;

    @Override
    public void initialize(LecturerImage constraintAnnotation) {
        maximumFileSize = constraintAnnotation.maximumFileSize();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile == null || multipartFile.isEmpty()) return true;
        if (multipartFile.getContentType() == null || !multipartFile.getContentType().startsWith("image/"))
            return false;

        return multipartFile.getSize() <= maximumFileSize;
    }
}
