package henesys.henesysbackend.member.repository;

import henesys.henesysbackend.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByName(String name);

    Member findByNickname(String nickname);
}
