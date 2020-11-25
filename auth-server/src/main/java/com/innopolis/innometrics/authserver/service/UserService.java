package com.innopolis.innometrics.authserver.service;


import com.innopolis.innometrics.authserver.DTO.*;
import com.innopolis.innometrics.authserver.entitiy.Permission;
import com.innopolis.innometrics.authserver.entitiy.TemporalToken;
import com.innopolis.innometrics.authserver.repository.RoleRepository;
import com.innopolis.innometrics.authserver.repository.TemporalTokenRepository;
import com.innopolis.innometrics.authserver.repository.UserRepository;
import com.innopolis.innometrics.authserver.entitiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.*;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TemporalTokenRepository temporalTokenRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public UserListResponse getActiveUsers(String request) {

        request = request == "" ? null : request;
        List<User> result = userRepository.findAllActive(request);

        UserListResponse response = new UserListResponse();
        for (User u : result) {
            UserResponse temp = new UserResponse();
            temp.setName(u.getName());
            temp.setEmail(u.getEmail());
            temp.setSurname(u.getSurname());
            temp.setIsactive(u.getIsactive());
            temp.setRole(u.getRole().getName());
            response.getUserList().add(temp);
        }
        return response;
    }

    public UserRequest fromUserToUserRequest(User myUser){
        UserRequest myUserRq = new UserRequest();
        myUserRq.setName(myUser.getName());
        myUserRq.setSurname(myUser.getSurname());
        myUserRq.setEmail(myUser.getEmail());
        myUserRq.setPassword(myUser.getPassword());

        myUserRq.setBirthday(myUser.getBirthday());
        myUserRq.setGender(myUser.getGender());
        myUserRq.setFacebook_alias(myUser.getFacebook_alias());
        myUserRq.setTelegram_alias(myUser.getTelegram_alias());
        myUserRq.setTwitter_alias(myUser.getTwitter_alias());
        myUserRq.setLinkedin_alias(myUser.getLinkedin_alias());

        myUserRq.setIsactive(myUser.getIsactive());
        myUserRq.setConfirmed_at(myUser.getConfirmed_at());
        myUserRq.setRole(myUser.getRole().getName());

        return myUserRq;
    }

    public UserResponse fromUserToUserResponse(User myUser){
        UserResponse myUserRq = new UserResponse();
        myUserRq.setName(myUser.getName());
        myUserRq.setSurname(myUser.getSurname());

        myUserRq.setBirthday(myUser.getBirthday());
        myUserRq.setGender(myUser.getGender());
        myUserRq.setFacebook_alias(myUser.getFacebook_alias());
        myUserRq.setTelegram_alias(myUser.getTelegram_alias());
        myUserRq.setTwitter_alias(myUser.getTwitter_alias());
        myUserRq.setLinkedin_alias(myUser.getLinkedin_alias());

        myUserRq.setEmail(myUser.getEmail());
        myUserRq.setIsactive(myUser.getIsactive());
        myUserRq.setRole(myUser.getRole().getName());

        List<PageResponse> pages =  new ArrayList<>();

        for (Permission permission : myUser.getRole().getPermissions()) {
            PageResponse temp = new PageResponse(permission.getPage().getPage(), permission.getPage().getIcon());
            pages.add(temp);
        }
        myUserRq.setPages(pages);

        return myUserRq;
    }

    public void sendRessetPassordEmail(String email, String BackUrl){
        TemporalToken temporalToken = generateNewTokenEnrty(email);

        MimeMessage message = mailSender.createMimeMessage();

        String subject = "InnoMetrics reset password link";


        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setFrom("innometrics-notification@innopolis.university");
            helper.setSubject(subject);

            String htmlStr = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org=\n" +
                    "/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                    "<html xmlns=3D\"http://www.w3.org/1999/xhtml\" xml:lang=3D\"en\" lang=3D\"en\">\n" +
                    "  <head>\n" +
                    "    <title></title>\n" +
                    "    <style type=3D\"text/css\">\n" +
                    "      body {margin: 10px; padding: 10px; text-align: left; background-color=\n" +
                    ": #ffffff; font: 13px Arial, Tahoma, Verdana, Helvetica, sans-serif; color:=\n" +
                    " #24272b}\n" +
                    "      td, th {padding: 0; margin: 0; border-bottom: 0px solid #bfd0e6;text-=\n" +
                    "align: left}\n" +
                    "      h4 {display: inline}\n" +
                    "      .size1{width:450px}\n" +
                    "      .size2{width:80px}\n" +
                    "    </style>\n" +
                    "  </head>\n" +
                    "  <body>\n";

            htmlStr += "<h2> Please follow the link to reset password. Be noticed that it will expire in 5 minutes:</h2>\n";


            htmlStr+= "<p><br /></p>\n"+
                    "<div> <a href=" + BackUrl +"=" + temporalToken.getTemporalToken() + "> Reset password </a>\n" +
                    "   </div>\n" +
                    "   <div>" +
                    "    <br/>\n" +
                    "    Innometrics Team\n"  +
                    "    <br/>\n" +
                    "   </div>\n" +
                    "  </body>\n" +
                    "</html>";

            helper.setText(htmlStr, true);
            message.setSender(new InternetAddress("innometrics-notification@innopolis.university"));
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public boolean checkTemporalToken(String email, String tempToken){
        TemporalToken temporalToken = temporalTokenRepository.findByEmailAndTemporalToken(email,tempToken);
        //Date currentTime =new Date(Calendar.getInstance().getTimeInMillis());
        Timestamp dateNow = new Timestamp(System.currentTimeMillis());
        if(temporalToken!=null && temporalToken.getExpirationDate().after(dateNow)){
            temporalTokenRepository.delete(temporalToken);
            return true;
        } else {
            if(temporalToken!=null)
                temporalTokenRepository.delete(temporalToken);
            return false;
        }
    }


    private TemporalToken generateNewTokenEnrty(String email){
        TemporalToken temporalToken = temporalTokenRepository.findByEmail(email);
        if(temporalToken!=null){
            temporalTokenRepository.delete(temporalToken);
        }

        Timestamp dateAfterFiveMinsFromNow = new Timestamp(System.currentTimeMillis() + (5 * ONE_MINUTE_IN_MILLIS));


        temporalToken = new TemporalToken(email, generateNewToken(), dateAfterFiveMinsFromNow);

        return temporalTokenRepository.save(temporalToken);
    }

    private static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    private static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
