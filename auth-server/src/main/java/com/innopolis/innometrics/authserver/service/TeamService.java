package com.innopolis.innometrics.authserver.service;

import com.innopolis.innometrics.authserver.DTO.TeamListRequest;
import com.innopolis.innometrics.authserver.DTO.TeamRequest;
import com.innopolis.innometrics.authserver.entitiy.Team;
import com.innopolis.innometrics.authserver.repository.TeamRepository;
import com.innopolis.innometrics.authserver.repository.TeammembersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Component
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public boolean existsById(Integer id){
        return teamRepository.existsByTeamid(id);
    }

    public TeamRequest create(TeamRequest detail){
        Team entity = new Team();
        BeanUtils.copyProperties(detail,entity);
        entity = teamRepository.saveAndFlush(entity);
        BeanUtils.copyProperties(entity,detail);

        return detail;
    }

    public TeamRequest update(TeamRequest detail){
        Team entity = teamRepository.findByTeamid(detail.getTeamid());
        assertNotNull(entity,
                "No team found by this id " + detail.getTeamid());
        detail.setTeamid(null);
        BeanUtils.copyProperties(detail,entity,getNullPropertyNames(detail));
        entity = teamRepository.saveAndFlush(entity);
        BeanUtils.copyProperties(entity,detail);

        return detail;
    }

    public void delete(Integer id) throws Exception {

        Team entity = teamRepository.findById(id).orElse(null);
        assertNotNull(entity,
                "No team found by id " + id);


        teamRepository.delete(entity);
    }

    public TeamRequest findByTeamId(Integer id){
        Team entity = teamRepository.findByTeamid(id);

        assertNotNull(entity,
                "No team found by this id " + id );

        TeamRequest detail = new TeamRequest();

        BeanUtils.copyProperties(entity,detail);

        return detail;

    }

    public TeamListRequest findTeamsByCompanyId(Integer companyId){
        List<Team> teamsFromCompany = teamRepository.findAllByCompanyid(companyId);

        assertNotNull(teamsFromCompany,
                "No teams found in this company " + companyId );

        return convertFromList(teamsFromCompany);

    }

    public TeamListRequest findTeamsByProjectId(Integer projectId){
        List<Team> teamsFromProject = teamRepository.findAllByProjectID(projectId);

        assertNotNull(teamsFromProject,
                "No teams found in this project " + projectId );

        return convertFromList(teamsFromProject);

    }

    public TeamListRequest findTeamsByProjectIdAndCompanyId(Integer projectId, Integer companyid){
        List<Team> teamsFromProject = teamRepository.findAllByProjectIDAndCompanyid(projectId, companyid);

        assertNotNull(teamsFromProject,
                "No teams found in this project  " + projectId + " and from that company " + companyid);

        return convertFromList(teamsFromProject);

    }

    public TeamListRequest findByTeamProperties(Integer teamId, Integer companyId, Integer projectId){
        TeamListRequest returnList = new TeamListRequest();
        if(teamId != null){
            TeamRequest team = findByTeamId(teamId);
            assertNotNull(team,
                    "No team found by this id " + teamId);
            if(companyId != null){
                if(projectId != null){
                    // all 3
                    if(team.getCompanyid().equals(companyId) && team.getProjectID().equals(projectId))
                        returnList.addTeamRequest(team);
                } else {
                    // teamId + companyId
                    if(team.getCompanyid().equals(companyId))
                        returnList.addTeamRequest(team);
                }
            } else {
                if(projectId != null){
                    // teamId + projectId
                    if(team.getProjectID().equals(projectId))
                        returnList.addTeamRequest(team);
                } else {
                    // only teamId
                    returnList.addTeamRequest(findByTeamId(teamId));
                }
            }
        } else {
            if(companyId != null){
                if(projectId != null){
                    //companyId + projectId
                    returnList = findTeamsByProjectIdAndCompanyId(projectId,companyId);
                } else {
                    // only companyId
                    returnList = findTeamsByCompanyId(companyId);
                }
            } else {
                // only projectId
                returnList = findTeamsByProjectId(projectId);
            }
        }
        return returnList;
    }

    public TeamListRequest findAllActiveTeams(){
        List<Team> activeTeams = teamRepository.findAllActive();
        assertNotNull(activeTeams,
                "No active teams found " );
        return convertFromList(activeTeams);

    }

    public TeamListRequest findAllTeams(){
        List<Team> allTeams = teamRepository.findAll();
        assertNotNull(allTeams,
                "No teams found " );
        return convertFromList(allTeams);
    }

    private TeamListRequest convertFromList(List<Team> teamList){
        TeamListRequest teamListRequest = new TeamListRequest();
        for (Team activeTeam : teamList) {
            TeamRequest detail = new TeamRequest();

            BeanUtils.copyProperties(activeTeam,detail);
            teamListRequest.addTeamRequest(detail);
        }

        return teamListRequest;
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
