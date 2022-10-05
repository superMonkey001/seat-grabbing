package cn.hncj.grabbing.validate;

import cn.hncj.grabbing.util.ValidMobileUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidate implements ConstraintValidator<IsMobile,String> {
    private boolean required = false;
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        // 获取@IsMobile注解的属性required
        required = constraintAnnotation.required();
    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required)
        {
            return ValidMobileUtils.isMobile(s);
        }
        else {
            if(StringUtils.isEmpty(s))
            {
                return true;
            }
            else{
                return ValidMobileUtils.isMobile(s);
            }
        }
    }
}