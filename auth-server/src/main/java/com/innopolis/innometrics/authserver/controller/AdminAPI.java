package com.innopolis.innometrics.authserver.controller;

import com.innopolis.innometrics.authserver.DTO.*;
import com.innopolis.innometrics.authserver.config.JwtToken;
import com.innopolis.innometrics.authserver.entitiy.Permission;
import com.innopolis.innometrics.authserver.entitiy.Project;
import com.innopolis.innometrics.authserver.entitiy.Role;
import com.innopolis.innometrics.authserver.entitiy.User;
import com.innopolis.innometrics.authserver.exceptions.ValidationException;
import com.innopolis.innometrics.authserver.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("AdminAPI")
public class AdminAPI {

    @Autowired
    ProjectService projectService;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeammembersService teammembersService;


    @PostMapping("/Project")
    public ResponseEntity<ProjectRequest> updateProject(@RequestBody ProjectRequest project, @RequestHeader(required = false) String Token) {
        String UserName = "API";

        if (project == null)
            throw new ValidationException("Not enough data provided");

        if (project.getName() == null)
            throw new ValidationException("Not enough data provided");

        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        ProjectRequest response;
        if (!projectService.existsByProjectID(project.getProjectID())) {
            response = projectService.create(project);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response = projectService.update(project);
            return ResponseEntity.ok(response);
        }

    }

    @GetMapping("/Project")
    public ResponseEntity<ProjectListRequest> getActiveProjects() {
        ProjectListRequest response = projectService.getProjectList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/Project/{Id}")
    public ResponseEntity<ProjectRequest> getProjectById(@PathVariable int Id) {
        if (projectService.existsByProjectID(Id)) {

            ProjectRequest response = projectService.getById(Id);

            return ResponseEntity.ok(response);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/Project")
    public ResponseEntity<ProjectRequest> deleteProject(@RequestParam Integer id, @RequestHeader(required = false) String Token) {

        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        if (id == null)
            throw new ValidationException("Not enough data provided");

        projectService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/Users")
    public ResponseEntity<UserListResponse> getActiveUsers(@RequestParam(required = false) String projectId) {

        UserListResponse response = userService.getActiveUsers(projectId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/Users/projects/{UserName}")
    public ResponseEntity<ProjectListRequest> getProjectsByUsername(@PathVariable String UserName) {
        if (!UserName.isEmpty()) {
            User userDetails = userService.findByEmail(UserName);

            if (userDetails == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            ProjectListRequest response = new ProjectListRequest();
            for (Project entity : userDetails.getProjects()) {
                ProjectRequest pTemp = new ProjectRequest();
                BeanUtils.copyProperties(entity, pTemp);
                response.getProjectList().add(pTemp);
            }


            return ResponseEntity.ok(response);
        } else
            throw new ValidationException("Not enough data provided");
    }


    @GetMapping("/Role/Permissions/{RoleName}")
    public ResponseEntity<PageListResponse> getPages(@PathVariable String RoleName) {
        PageListResponse pageListResponse = roleService.getPagesWithIconsForRole(RoleName);
        if (pageListResponse == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return ResponseEntity.ok(pageListResponse);
    }


    @GetMapping("/Role/{RoleName}")
    public ResponseEntity<RoleResponse> getRole(@PathVariable String RoleName) {
        RoleResponse roleResponse = roleService.getRole(RoleName);
        if (roleResponse == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return ResponseEntity.ok(roleResponse);
    }


    @GetMapping("/Roles")
    public ResponseEntity<RoleListResponse> getRoles() {
        RoleListResponse roleResponseList = roleService.getRoles();
        if (roleResponseList == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        return ResponseEntity.ok(roleResponseList);
    }


    @PostMapping("/Role")
    public ResponseEntity<RoleResponse> CreateRole(@RequestBody RoleRequest roleRequest, @RequestHeader(required = false) String Token) {
        System.out.println("Create role method started");

        if (roleService.getRole(roleRequest.getName()) != null)
            throw new ValidationException("Role already existed");

        System.out.println("New role creation...");

        RoleResponse roleResponse = roleService.createRole(roleRequest);

        System.out.println("New role saved...");

        return new ResponseEntity<>(roleResponse, HttpStatus.CREATED);
    }

    @PutMapping("/Role")
    public ResponseEntity<RoleResponse> updateRole(@RequestBody RoleRequest roleRequest, @RequestHeader(required = false) String Token) {
        System.out.println("Update role method started");

        if (roleRequest == null) {
            throw new ValidationException("Not enough data provided");
        }

        if (roleRequest.getName() == null || roleRequest.getPages() == null || roleRequest.getDescription() == null) {
            throw new ValidationException("Not enough data provided");
        }


        if (roleService.getRole(roleRequest.getName()) == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }


        RoleResponse roleResponse = roleService.updateRole(roleRequest);
        return new ResponseEntity<>(roleResponse, HttpStatus.CREATED);
    }


    @GetMapping("/User/Permissions/{UserName}")
    public ResponseEntity<PermissionResponse> getPermissions(@PathVariable String UserName) {
        if (!UserName.isEmpty()) {
            User userDetails = userService.findByEmail(UserName);

            if (userDetails == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);


            PermissionResponse response = new PermissionResponse();
            for (Permission p : userDetails.getRole().getPermissions()) {
                response.setRole(p.getRole());
                response.addPage(p.getPage());
            }

            return ResponseEntity.ok(response);
        } else
            throw new ValidationException("Not enough data provided");
    }

    @PostMapping("/Role/Permissions")
    public ResponseEntity<PermissionResponse> createPermissions(@RequestBody PermissionResponse permissionResponse) {
        if (permissionResponse != null) {

            if (roleService.findByName(permissionResponse.getRole()) == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);


            return ResponseEntity.ok(roleService.createPermissions(permissionResponse));
        } else
            throw new ValidationException("Not enough data provided");
    }


    @PostMapping("/User/Role")
    public ResponseEntity<UserResponse> setRoleToUser(@RequestParam String RoleName, @RequestParam String UserName) {
        if (!UserName.isEmpty()) {
            User userDetails = userService.findByEmail(UserName);

            if (userDetails == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            Role role = roleService.findByName(RoleName);
            if (role == null) {
                throw new ValidationException("No such role");
            }

            userDetails.setRole(role);
            userService.save(userDetails);

            UserResponse userResponse = userService.fromUserToUserResponse(userDetails);

            return ResponseEntity.ok(userResponse);
        } else
            throw new ValidationException("Not enough data provided");
    }

    @PostMapping("/User/Profile")
    public ResponseEntity<ProfileRequest> updateProfileOfUser(@RequestBody ProfileRequest profileRequest, @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
//        String email = "";
//        if (Token != null && !Token.equals(""))
//            email = jwtToken.getUsernameFromToken(Token);
        if (profileRequest == null)
            throw new ValidationException("Not enough data provided");

        if (profileRequest.getMacAddress() == null || profileRequest.getUserEmail() == null)
            throw new ValidationException("Not enough data provided");

        ProfileRequest response;
        if (!profileService.existsByEmail(profileRequest.getUserEmail(), profileRequest.getMacAddress())) {
            response = profileService.create(profileRequest);
        } else {
            response = profileService.update(profileRequest);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/User/Profile")
    public ResponseEntity<ProfileRequest> deleteProfile(@RequestParam Integer id, @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String email = "";
        if (Token != null && Token != "")
            email = jwtToken.getUsernameFromToken(Token);

        if (id == null)
            throw new ValidationException("Not enough data provided");

        profileService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/User/Profile")
    public ResponseEntity<ProfileRequest> findByMacaddress(@RequestParam String macaddress, @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String email = "";
        if (Token != null && Token != "")
            email = jwtToken.getUsernameFromToken(Token);

        if (macaddress == null)
            throw new ValidationException("Not enough data provided");

        ProfileRequest profileRequest = profileService.findByMacAddress(macaddress);

        if (profileRequest == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);


        return ResponseEntity.ok(profileRequest);
    }

    @PostMapping("/Company")
    public ResponseEntity<CompanyRequest> createCompany(@RequestBody CompanyRequest companyRequest,
                                                        @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        if (companyRequest == null)
            throw new ValidationException("Not enough data provided");

        companyRequest.setCompanyid(null);
        companyRequest.setUpdateby(UserName);
        companyRequest.setCreatedby(UserName);

        CompanyRequest response = companyService.create(companyRequest);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/Company/{id}")
    public ResponseEntity<CompanyRequest> updateCompany(@PathVariable Integer id,
                                                        @RequestBody CompanyRequest companyRequest,
                                                        @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        if (companyRequest == null)
            throw new ValidationException("Not enough data provided");

        companyRequest.setCompanyid(id);
        companyRequest.setUpdateby(UserName);
        CompanyRequest response;
        if (!companyService.existsById(companyRequest.getCompanyid())) {
            companyRequest.setCreatedby(UserName);
            response = companyService.create(companyRequest);
        } else {
            response = companyService.update(companyRequest);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/Company")
    public ResponseEntity<CompanyRequest> deleteCompany(@RequestParam Integer id, @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        if (id == null)
            throw new ValidationException("Not enough data provided");

        try {
            companyService.delete(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw new ValidationException("Entries from 'agents_x_company' table with such companyid must be deleted firstly");
        }
    }

    @GetMapping("/Company")
    public ResponseEntity<CompanyRequest> findByCompanyId(@RequestParam Integer id, @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        if (id == null)
            throw new ValidationException("Not enough data provided");

        return ResponseEntity.ok(
                companyService.findByCompanyId(id)
        );
    }

    @GetMapping("/Company/all")
    public ResponseEntity<CompanyListRequest> findAllActiveCompanies(@RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        return ResponseEntity.ok(
                companyService.findAllActiveCompanies()
        );
    }

    @PostMapping("/Team")
    public ResponseEntity<TeamRequest> createTeam(@RequestBody TeamRequest teamRequest,
                                                  @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        if (teamRequest == null)
            throw new ValidationException("Not enough data provided");

        teamRequest.setTeamid(null);
        teamRequest.setUpdateby(UserName);
        teamRequest.setCreatedby(UserName);

        TeamRequest response = teamService.create(teamRequest);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/Team/{id}")
    public ResponseEntity<TeamRequest> updateTeam(@PathVariable Integer id,
                                                  @RequestBody TeamRequest teamRequest,
                                                  @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        if (teamRequest == null)
            throw new ValidationException("Not enough data provided");

        teamRequest.setTeamid(id);
        teamRequest.setUpdateby(UserName);
        TeamRequest response;
        if (!teamService.existsById(teamRequest.getTeamid())) {
            teamRequest.setCreatedby(UserName);
            response = teamService.create(teamRequest);
        } else {
            response = teamService.update(teamRequest);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/Team")
    public ResponseEntity<TeamRequest> deleteTeam(@RequestParam Integer id, @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        if (id == null)
            throw new ValidationException("Not enough data provided");

        try {
            teamService.delete(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw new ValidationException("Entries from 'externalproject_x_team' table with such teamid must be deleted firstly");
        }
    }

    @GetMapping("/Team/all")
    public ResponseEntity<TeamListRequest> findAllActiveTeams(@RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        return ResponseEntity.ok(
                teamService.findAllActiveTeams()
        );
    }

    @GetMapping("/Team")
    public ResponseEntity<TeamListRequest> findTeamBy(@RequestParam(required = false) Integer teamId, @RequestParam(required = false) Integer companyId,
                                                      @RequestParam(required = false) Integer projectId, @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        if (teamId == null && companyId == null && projectId == null)
            throw new ValidationException("Not enough data provided");

        return ResponseEntity.ok(
                teamService.findByTeamProperties(teamId, companyId, projectId)
        );
    }

    @PostMapping("/Teammember")
    public ResponseEntity<TeammembersRequest> createTeammember(@RequestBody TeammembersRequest teammembersRequest,
                                                               @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";//teammembersRequest.getEmail();
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        if (teammembersRequest == null)
            throw new ValidationException("Not enough data provided");

        if (!userService.existsByEmail(teammembersRequest.getEmail()))
            throw new ValidationException("No such user");

        teammembersRequest.setMemberid(null);
        teammembersRequest.setUpdateby(UserName);
        teammembersRequest.setCreatedby(UserName);

        TeammembersRequest response = teammembersService.create(teammembersRequest);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/Teammember/{id}")
    public ResponseEntity<TeammembersRequest> updateTeammember(@PathVariable Integer id,
                                                               @RequestBody TeammembersRequest teammembersRequest,
                                                               @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";//teammembersRequest.getEmail();
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        if (teammembersRequest == null)
            throw new ValidationException("Not enough data provided");

        if (!userService.existsByEmail(teammembersRequest.getEmail()))
            throw new ValidationException("No such user");

        teammembersRequest.setMemberid(id);
        teammembersRequest.setUpdateby(UserName);
        TeammembersRequest response;

        if (!(teammembersService.existsById(teammembersRequest.getMemberid()) || teammembersService.existInTheTeam(teammembersRequest.getTeamid(), teammembersRequest.getEmail()))) {
            teammembersRequest.setCreatedby(UserName);
            response = teammembersService.create(teammembersRequest);
        } else {
            response = teammembersService.update(teammembersRequest);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/Teammember")
    public ResponseEntity<TeammembersRequest> deleteTeammember(@RequestParam Integer id, @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        if (id == null)
            throw new ValidationException("Not enough data provided");


        teammembersService.delete(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/Teammember/all")
    public ResponseEntity<TeammembersListRequest> findAllActiveTeammembers(@RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        return ResponseEntity.ok(
                teammembersService.findAllActiveTeammembers()
        );
    }

    @GetMapping("/Teammember")
    public ResponseEntity<TeammembersListRequest> findTeammemberBy(@RequestParam(required = false) Integer memberId, @RequestParam(required = false) Integer teamId,
                                                                   @RequestParam(required = false) String email, @RequestHeader(required = false) String Token) {
        //change later to required = true and delete this line
        String UserName = "API";
        if (Token != null && Token != "")
            UserName = jwtToken.getUsernameFromToken(Token);

        if (teamId == null && memberId == null && email == null)
            throw new ValidationException("Not enough data provided");

        return ResponseEntity.ok(
                teammembersService.findByTeammemberProperties(memberId, teamId, email)
        );
    }

    @GetMapping("/WorkingTree")
    public ResponseEntity<WorkingTreeListRequest> getWorkingTree(@RequestParam(required = false) String email, @RequestHeader(required = false) String Token) {
        if (Token != null && Token != "")
            email = jwtToken.getUsernameFromToken(Token);

        if (email == null || email.equals(""))
            throw new ValidationException("Not enough data provided");

        WorkingTreeListRequest response = new WorkingTreeListRequest();
        TeammembersListRequest teammembersListRequest = teammembersService.findByTeammemberProperties(null, null, email);
        for (TeammembersRequest teammember : teammembersListRequest.getTeammembersRequestList()) {
            WorkingTreeRequest workingTreeRequest = new WorkingTreeRequest();
            workingTreeRequest.setEmail(email);
            workingTreeRequest.setMemberid(teammember.getMemberid());
            workingTreeRequest.setTeamid(teammember.getTeamid());
            TeamRequest team = teamService.findByTeamId(teammember.getTeamid());
            workingTreeRequest.setTeamname(team.getTeamname());
            workingTreeRequest.setTeamdescription(team.getDescription());
            workingTreeRequest.setCompanyid(team.getCompanyid());
            CompanyRequest company = companyService.findByCompanyId(team.getCompanyid());
            workingTreeRequest.setCompanyname(company.getCompanyname());
            workingTreeRequest.setProjectID(team.getProjectID());
            ProjectRequest project = projectService.getById(team.getProjectID());
            workingTreeRequest.setProjectname(project.getName());

            response.addWorkingTreeRequest(workingTreeRequest);
        }

        return ResponseEntity.ok(response);
    }
}
