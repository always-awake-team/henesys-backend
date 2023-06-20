package henesys.henesysbackend.member.repository;

import henesys.henesysbackend.member.domain.entity.Member;
import henesys.henesysbackend.member.domain.enumtype.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    private Member memberA;
    private Member memberB;
    private Member savedMember;

    @BeforeEach
    public void before() {
        memberA = new Member("memberA", "password", RoleType.USER, "nicknameA", "emailA");
        memberB = new Member("memberB", "password", RoleType.USER, "nicknameB", "emailB");

        savedMember = memberRepository.save(memberA);
    }

    @Test
    public void findAllByNameTest() throws Exception {
        //given
        Member duplicateNameMember = new Member("memberA", "password", RoleType.USER, "nicknameA", "emailA");
        memberRepository.save(duplicateNameMember);

        String notExistsName = memberB.getName();

        //when
        List<Member> findMembers = memberRepository.findAllByName(savedMember.getName());
        List<Member> notExistsMembers = memberRepository.findAllByName(notExistsName);

        //then
        assertThat(findMembers.size()).isEqualTo(2);
        assertThat(notExistsMembers).isEmpty();
    }

    @Test
    public void findByNicknameTest() throws Exception {
        //given
        String findNickname = savedMember.getNickname();
        String notExistsNickname = memberB.getNickname();

        //when
        Member findMember = memberRepository.findByNickname(findNickname);
        Member notExistsMember = memberRepository.findByNickname(notExistsNickname);

        //then
        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
        assertThat(findMember.getNickname()).isEqualTo(savedMember.getNickname());

        assertThat(notExistsMember).isNull();
    }

}
