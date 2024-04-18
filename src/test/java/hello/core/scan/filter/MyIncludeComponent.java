package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent { //MyIncludeComponent가 붙으면 ComponentScan에 추가한다는 뜻
}
