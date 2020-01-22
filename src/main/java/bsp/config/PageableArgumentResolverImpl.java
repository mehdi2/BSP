package bsp.config;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by mbadiuzzaman on 26/5/2017.
 */
public class PageableArgumentResolverImpl implements PageableArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        System.out.println("kkkkkkkkkkkk" + methodParameter);
        return false;
    }

    @Override
    public Pageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        System.out.println("ppppppppp" + methodParameter);
        return null;
    }
}
