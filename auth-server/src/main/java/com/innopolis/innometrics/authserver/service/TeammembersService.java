package com.innopolis.innometrics.authserver.service;

import com.innopolis.innometrics.authserver.DTO.TeammembersListRequest;
import com.innopolis.innometrics.authserver.DTO.TeammembersRequest;
import com.innopolis.innometrics.authserver.entitiy.Teammembers;
import com.innopolis.innometrics.authserver.exceptions.ValidationException;
import com.innopolis.innometrics.authserver.repository.TeammembersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Component
public class TeammembersService {
    @Autowired
    private TeammembersRepository teammembersRepository;

    public boolean existsById(Integer id){
        return teammembersRepository.existsById(id);
    }

    public boolean existInTheTeam(Integer id,String email){
        return teammembersRepository.existsByTeamidAndEmail(id,email);
    }

    public void delete(Integer id) {

        Teammembers entity = teammembersRepository.findById(id).orElse(null);
        assertNotNull(entity,
                "No team found by id " + id);


        teammembersRepository.delete(entity);
    }

    public TeammembersRequest create(TeammembersRequest detail){

        Teammembers entity = new Teammembers();
        BeanUtils.copyProperties(detail,entity);
        entity = teammembersRepository.saveAndFlush(entity);
        BeanUtils.copyProperties(entity,detail);

        return detail;
    }

    public TeammembersRequest update(TeammembersRequest detail){
        Teammembers entity = teammembersRepository.findByMemberid(detail.getMemberid());
        assertNotNull(entity,
                "No team member found by this id " + detail.getMemberid());
        if(teammembersRepository.existsByTeamidAndEmail( detail.getTeamid(), detail.getEmail())
                && !teammembersRepository.findByEmailAndTeamid(detail.getEmail(), detail.getTeamid()).getMemberid().equals(detail.getMemberid())){
            throw new ValidationException("there is already email "+ detail.getEmail() + " in the team with id "+  detail.getTeamid());
        }

        detail.setMemberid(null);
        BeanUtils.copyProperties(detail,entity,getNullPropertyNames(detail));
        entity = teammembersRepository.saveAndFlush(entity);
        BeanUtils.copyProperties(entity,detail);

        return detail;
    }

    public TeammembersListRequest findAllActiveTeammembers(){
        List<Teammembers> activeTeams = teammembersRepository.findAllActive();
        assertNotNull(activeTeams,
                "No active team members found " );
        return convertFromList(activeTeams);

    }

    public TeammembersListRequest findAllTeammembers(){
        List<Teammembers> allTeams = teammembersRepository.findAll();
        assertNotNull(allTeams,
                "No team members found " );
        return convertFromList(allTeams);

    }

    public TeammembersListRequest findByTeammemberProperties(Integer memberId, Integer teamId, String email){
        TeammembersListRequest teammembersListRequest = new TeammembersListRequest();
        if (memberId != null){
            Teammembers member = teammembersRepository.findByMemberid(memberId);
            assertNotNull(member,
                    "No team member found by this id " + memberId);
//            if(teamId == null){
//                if(email == null || member.getEmail().equals(email)){
//                    TeammembersRequest memberReq = new TeammembersRequest();
//                    BeanUtils.copyProperties(member,memberReq);
//                    teammembersListRequest.addTeammembersRequest(memberReq);
//                }
//            } else if (member.getTeamid().equals(teamId)){
//                if(email == null || member.getEmail().equals(email)){
//                    TeammembersRequest memberReq = new TeammembersRequest();
//                    BeanUtils.copyProperties(member,memberReq);
//                    teammembersListRequest.addTeammembersRequest(memberReq);
//                }
//            }
            if( (teamId == null || member.getTeamid().equals(teamId)) && (email == null || email.equals("") || member.getEmail().equals(email)) ) {
                TeammembersRequest memberReq = new TeammembersRequest();
                BeanUtils.copyProperties(member,memberReq);
                teammembersListRequest.addTeammembersRequest(memberReq);
            }
        } else {
            if (email != null && !email.equals("") && teamId != null){
                Teammembers member = teammembersRepository.findByEmailAndTeamid(email,teamId);
                assertNotNull(member,
                        "No team member found by this email " + email + " and team id " + teamId);
                TeammembersRequest memberReq = new TeammembersRequest();
                BeanUtils.copyProperties(member,memberReq);
                teammembersListRequest.addTeammembersRequest(memberReq);
            } else if ((email == null || email.equals("")) && teamId != null){
                List<Teammembers> teammembers = teammembersRepository.findByTeamid(teamId);
                assertNotNull(teammembers,
                        "No team members found by this team id " + teamId );
                teammembersListRequest = convertFromList(teammembers);
            } else if (email != null && !email.equals("")) {
                List<Teammembers> teammembers = teammembersRepository.findByEmail(email);
                assertNotNull(teammembers,
                        "No team members found by this email " + email );
                teammembersListRequest = convertFromList(teammembers);
            }
        }

        return teammembersListRequest;
    }

    private TeammembersListRequest convertFromList(List<Teammembers> teammembers){
        TeammembersListRequest teammembersListRequest = new TeammembersListRequest();
        for (Teammembers activeTeammember : teammembers) {
            TeammembersRequest detail = new TeammembersRequest();

            BeanUtils.copyProperties(activeTeammember,detail);
            teammembersListRequest.addTeammembersRequest(detail);
        }

        return teammembersListRequest;
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set emptyNames = new HashSet();

        for(java.beans.PropertyDescriptor descriptor : src.getPropertyDescriptors()) {

            if (src.getPropertyValue(descriptor.getName()) == null) {
                emptyNames.add(descriptor.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return (String[]) emptyNames.toArray(result);
    }
}
