package com.hanex.fms.starter.member.service;

import com.hanex.fms.global.exception.Exception400;
import com.hanex.fms.global.exception.Exception404;
import com.hanex.fms.global.security.CustomUser;
import com.hanex.fms.starter.member.domain.Member;
import com.hanex.fms.starter.member.dto.MemberDto;
import com.hanex.fms.starter.member.dto.MemberSearchCondition;
import com.hanex.fms.starter.member.repository.MemberRepository;
import com.hanex.fms.starter.user.domain.BaseUser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberDto.MemberResponse findById(String id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new Exception404("존재하지 않는 고객입니다."));
        return member.toDto();
    }

    @Transactional(readOnly = true)
    public Page<MemberDto.MemberResponse> findByCustomerIdAndSearchCondition(Pageable pageable, MemberSearchCondition memberSearchCondition){
        Page<Member> member = memberRepository.findByCustomerIdAndSearchCondition(pageable,memberSearchCondition);
        return member.map(Member::listToDto);
    }

    @Transactional
    public String save(MemberDto.SaveRequest save, CustomUser securityUser){

        // baseUser > loginId 중복 검사
        Optional<BaseUser> baseUser = memberRepository.selectByLoginId(save.getLoginId());
        if (baseUser.isPresent()){
            throw new Exception400("loginId","중복되는 로그인 아이디 입니다.");
        }

        Member member = memberRepository.insert(save.toEntity(securityUser));
        return member.getId();
    }


    @Transactional
    public void update(String id, MemberDto.UpdateRequest update,CustomUser securityUser){
        Member member = memberRepository.findById(id).orElseThrow(()-> new Exception404("존재하지 않는 고객입니다."));
        memberRepository.updateMemberInfo(id,update.toEntity(securityUser));
    }

    @Transactional
    public void delete(String id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new Exception404("존재하지 않는 고객입니다."));
        // 실제 ROW 삭제 (HARD DELETE)
        memberRepository.delete(member);
    }
}
