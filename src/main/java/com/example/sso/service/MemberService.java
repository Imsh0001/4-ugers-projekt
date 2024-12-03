package com.example.sso.service;

import com.example.sso.model.Member;
import com.example.sso.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));
    }


    public Member createMember(Member member) {
        return memberRepository.save(member);
    }


    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }


    public Member updateMember(Long memberId, Member updatedMember) {
        return memberRepository.findById(memberId)
                .map(member -> {
                    member.setName(updatedMember.getName());
                    member.setLastName(updatedMember.getLastName());
                    member.setEmail(updatedMember.getEmail());
                    member.setPassword(updatedMember.getPassword());
                    member.setDepartment(updatedMember.getDepartment());
                    member.setMembershipType(updatedMember.getMembershipType());
                    return memberRepository.save(member);
                })
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));
    }


    public void deleteMember(Long memberId) {
        if (memberRepository.existsById(memberId)) {
            memberRepository.deleteById(memberId);
        } else {
            throw new IllegalArgumentException("Member not found with ID: " + memberId);
        }
    }
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }
}
