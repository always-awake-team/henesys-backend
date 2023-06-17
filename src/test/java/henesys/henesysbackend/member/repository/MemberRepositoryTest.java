package henesys.henesysbackend.member.repository;

import henesys.henesysbackend.member.domain.entity.Member;
import henesys.henesysbackend.member.domain.enumtype.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
@Slf4j
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    private Member memberA;
    private Member memberB;

    @BeforeEach
    public void before() {
        memberA = new Member("memberA", "password", RoleType.USER, "nicknameA", "emailA");
        memberB = new Member("memberB", "password", RoleType.USER, "nicknameB", "emailB");
    }

    @Test
    public void saveTest() throws Exception {
        //when
        Member savedMember = memberRepository.save(memberA);

        //then
        assertThat(savedMember.getId()).isEqualTo(memberA.getId());
    }

    @Test
    public void findAllTest() throws Exception {
        //given
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        List<Member> findMembers = memberRepository.findAll();

        //then
        assertThat(findMembers.size()).isEqualTo(2);
    }

    @Test
    public void findByIdTest() throws Exception {
        //given
        Member savedMember = memberRepository.save(memberA);
        Long findId = savedMember.getId();
        Long notExistsId = 238946L;

        //when
        Member findMember = memberRepository.findById(findId).get();
        Optional<Member> notExistsMember = memberRepository.findById(notExistsId);

        //then
        assertThat(findMember.getName()).isEqualTo(savedMember.getName());
        assertThat(findMember.getId()).isEqualTo(savedMember.getId());

        assertThat(notExistsMember).isEmpty();
    }

    @Test
    public void findAllByNameTest() throws Exception {
        //given
        Member savedMember = memberRepository.save(memberA);

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
        Member savedMember = memberRepository.save(memberA);
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
