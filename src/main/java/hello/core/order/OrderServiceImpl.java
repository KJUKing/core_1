package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
// final을 붙은 필드를 파라미터로 받는 생성자를 만들어줌
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository; //필드 주입
    private final DiscountPolicy discountPolicy;

    @Autowired private DiscountPolicy rateDiscountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    //    @Autowired(required = false) // required = false (주입할 대상이 없어도 동작하게 하려면) 지정 수정자는 Bean등록 후 의존관계 주입 단계에서 일어난다. 변경가능하거나 선택적으로 주입 할 수 있다.
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("discountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }

    // 생성자 주입은 생성자를 Bean에 등록 할 때 의존성 주입을 같이 한다. / 수정자로 주입하면 주입이 되기 때문에 굳이 생성자를 만들지 않아도 된다.

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
