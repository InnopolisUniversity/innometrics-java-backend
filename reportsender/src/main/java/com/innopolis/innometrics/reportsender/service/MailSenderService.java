package com.innopolis.innometrics.reportsender.service;

import com.innopolis.innometrics.reportsender.model.*;
import com.innopolis.innometrics.reportsender.repository.ClAppsCategoriesRepository;
import com.innopolis.innometrics.reportsender.repository.ClCategoriesRepository;
import com.innopolis.innometrics.reportsender.repository.CumlativeRepActivityRepository;
import com.innopolis.innometrics.reportsender.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.stringtemplate.v4.*;

//import org.apache.commons.text.StringEscapeUtils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MailSenderService{

    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    private CumlativeRepActivityRepository cumlativeRepActivityRepository;

    @Autowired
    private ClCategoriesRepository clCategoriesRepository;

    @Autowired
    private ClAppsCategoriesRepository clAppsCategoriesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment env;


    @Autowired
    EntityManager em;

    Timestamp dateNow = new Timestamp(System.currentTimeMillis());

    public int sendNotification() {

        long DAY_IN_MS = 1000 * 60 * 60 * 24;

        Timestamp dateNow = new Timestamp(System.currentTimeMillis());
        Timestamp dateWeekAgo = new Timestamp(System.currentTimeMillis() - (1 * DAY_IN_MS));
        List<UserModel> userModelList = userRepository.findAll();



        Query q;
        List<Object[]> records = null;
        String query;
        List<Object[]> mac_addresses = null;
        for (UserModel userModel : userModelList) {
            query = String.format("select executable_name, extract(epoch from sum(used_time)),catname,mac_address  \n" +
                            "from innometrics.cumlativerepactivity\n" +
                            "join innometricsconfig.cl_apps_categories\n" +
                            "on cumlativerepactivity.executable_name = cl_apps_categories.executablefile\n" +
                            "join innometricsconfig.cl_categories\n" +
                            "on cl_apps_categories.catid = cl_categories.catid\n" +
                            "where email = '%s'\n" +
                            "and cumlativerepactivity.creationdate between '%s' AND '%s'\n" +
                            "group by executable_name, catname,mac_address \n",
                    userModel.getEmail(),
                    dateWeekAgo,
                    dateNow);

            q = em.createNativeQuery(query);
            records = q.getResultList();

            // getting mac_addresses
            query = String.format("select mac_address \n" +
                                    "from innometrics.cumlativerepactivity \n" +
                                    "where email = '%s' \n" +
                                    "and cumlativerepactivity.creationdate between '%s' AND '%s' \n" +
                                    "group by mac_address\n",
                    userModel.getEmail(),
                    dateWeekAgo,
                    dateNow);


            q = em.createNativeQuery(query);
            mac_addresses = q.getResultList();

            Map<String, Map<String, Integer>> overallTimeSpent = new HashMap<String, Map<String, Integer>>();

            for (Object mac : mac_addresses){

                Map<String, Integer> overallTimeSpentForMac = clCategoriesRepository.findAll().stream().collect(Collectors.toMap(ClCategoriesModel::getCatname, cat -> 0));

                for (Object[] record : records) {
                    if(record[3].equals(mac)){
                        String appName = (String) record[0];
                        Double timeSpent = (Double) record[1];
                        String catName = (String) record[2];
                        overallTimeSpentForMac.put(catName, (int) (overallTimeSpentForMac.get(catName) + timeSpent));
                    }

                }

                overallTimeSpent.put((String) mac, overallTimeSpentForMac);
            }

//                messageSender(userModel.getEmail(), overallTimeSpent);

        }

        System.out.println("emails sent");
        return 0;
    }


    public int sendNotificationToAdmins() {

        long DAY_IN_MS = 1000 * 60 * 60 * 24;

        Timestamp dateNow = new Timestamp(System.currentTimeMillis());
        Timestamp dateWeekAgo = new Timestamp(System.currentTimeMillis() - (1 * DAY_IN_MS));
        List<UserModel> userModelList = userRepository.findAll();


        Map<String[], Integer> listOfUsersWithOverallTimeSpent = new HashMap<String[], Integer>();


        Query q;
        List<Object[]> records = null;
        String query;

        query = String.format("select a.email, a.mac_address, extract(epoch from sum(a.dailysum))\n" +
                "           from ( select email, executable_name, mac_address , max(dailysum) dailysum\n" +
                "           from innometrics.cumlativerepactivity \n" +
                "           where date_trunc('day', captureddate) = date('%s')\n" +
                "           group by email, executable_name, mac_address ) as a\n" +
                "           group by email, mac_address",
                    dateNow.toString().substring(0, 10));


        q = em.createNativeQuery(query);
        records = q.getResultList();

        for (Object[] record : records) {
            String[] emailWithMac = new String[2];
            emailWithMac[0] = (String) record[0];
            emailWithMac[1] = (String) record[1];
            listOfUsersWithOverallTimeSpent.put(emailWithMac, ((Double) record[2]).intValue());
        }

        messageSenderForAdmins("a.kruglov@innopolis.ru", listOfUsersWithOverallTimeSpent, 1);
        messageSenderForAdmins("g.succi@innopolis.ru", listOfUsersWithOverallTimeSpent, 1);
        messageSenderForAdmins("x.vasquez@innopolis.university", listOfUsersWithOverallTimeSpent, 1);


        System.out.println("emails sent");
        return 0;
    }


    //flag  0 for users, 1 for admins
    public void messageSenderForAdmins(String to, Map<String[], Integer> overallTimeSpent, int flag) {

        MimeMessage message = mailSender.createMimeMessage();

        String subject = "InnoMetrics daily activity report";


        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
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

            if (flag == 1) {
                htmlStr += "    <h2>Users activity tracked today " + dateNow.toString().substring(0, 10);
            } else {
                htmlStr += " <h2>Your activity tracked today " + dateNow.toString().substring(0, 10);
            }


            htmlStr += "</h2>\n" +
                    "    <p><br /></p>\n" +
                    "    <table width=\"700\" border=\"0\" cellspacing=3D\"0\" cellpadding=3D\"0\">\n" +
                    "      <thead>\n" +
                    "        <tr>\n";

            if (flag == 1) {
                htmlStr += "          <th colspan=\"3\"><h4>User</h4></th>\n";
            } else {
                htmlStr += "          <th colspan=\"3\"><h4>Category</h4></th>\n";
            }


            htmlStr += " <th class=3D\"size2\">Time Spent</th>\n" +
                    "        </tr>\n" +
                    "      </thead>\n";


            Iterator<Map.Entry<String[], Integer>> entries = overallTimeSpent.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String[], Integer> entry = entries.next();

                if (entry.getValue() != 0) {
                    int hours = (int) entry.getValue() / 3600;
                    int remainder = (int) entry.getValue() - hours * 3600;
                    int mins = remainder / 60;
                    remainder = remainder - mins * 60;
                    int secs = remainder;

                    htmlStr += "        <tr>\n" +
                            "          <td colspan=3D\"3\"><h4>" + entry.getKey()[0] +" with macAddress: "+ entry.getKey()[1] +"</h4></td>\n" +
                            "          <td><h4>" + hours + ":" + mins + ":" + secs + "</h4></td>\n" +
                            "        </tr>\n";
                }

            }

            htmlStr += "    </table>\n" +
                    "    <p>\n" +
                    "      <br/>\n" +
                    "    </p>\n" +
                    "    <div>\n" +
                    "      <br/>\n" +
                    "      Innometrics Team\n" +
                    "      <br/>\n" +
                    "      If you don't want to receive daily report please click <a href=\n";

            htmlStr+=
                    env.getProperty("mail.sender.host")
                            + "/unsubscribe/" + to +">here</a>\n" +
                    "    </div>\n" +
                    "\n" +
                    "  </body>\n" +
                    "</html>";

            helper.setText(htmlStr, true);
            message.setSender(new InternetAddress("innometrics-notification@innopolis.university"));
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void messageSender(String to, Map<String, Map<String, Integer>> overallTimeSpent) {

        MimeMessage message = mailSender.createMimeMessage();

        String subject = "InnoMetrics daily activity report";


        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
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


            Iterator<Map.Entry<String, Map<String, Integer>>> entries = overallTimeSpent.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, Map<String, Integer>> entry = entries.next();

                htmlStr += " <h2>Your activity tracked today " + dateNow.toString().substring(0, 10);

                htmlStr += " for mac " + entry.getKey() + "</h2>\n" +
                        "    <p><br /></p>\n" +
                        "    <table width=\"700\" border=\"0\" cellspacing=3D\"0\" cellpadding=3D\"0\">\n" +
                        "      <thead>\n" +
                        "        <tr>\n";

                htmlStr += "          <th colspan=\"3\"><h4>Category</h4></th>\n";

                htmlStr += " <th class=3D\"size2\">Time Spent</th>\n" +
                        "        </tr>\n" +
                        "      </thead>\n";

                Iterator<Map.Entry<String, Integer>> сatTimes = entry.getValue().entrySet().iterator();
                while (сatTimes.hasNext()) {
                    Map.Entry<String, Integer> сatTime = сatTimes.next();

                    if (сatTime.getValue() != 0) {
                        int hours = (int) сatTime.getValue() / 3600;
                        int remainder = (int) сatTime.getValue() - hours * 3600;
                        int mins = remainder / 60;
                        remainder = remainder - mins * 60;
                        int secs = remainder;

                        htmlStr += "        <tr>\n" +
                                "          <td colspan=3D\"3\"><h4>" + сatTime.getKey()  +"</h4></td>\n" +
                                "          <td><h4>" + hours + ":" + mins + ":" + secs + "</h4></td>\n" +
                                "        </tr>\n";
                    }

                }
                htmlStr += "    </table>\n";

            }


            htmlStr +="    <p>\n" +
                    "      <br/>\n" +
                    "    </p>\n" +
                    "    <div>\n" +
                    "      <br/>\n" +
                    "      Innometrics Team\n" +
                    "      <br/>\n" +
                    "      If you don't want to receive daily report please click <a href=\n";

            htmlStr+=
                    env.getProperty("mail.sender.host")
                            + "/unsubscribe/" + to +">here</a>\n" +
                            "    </div>\n" +
                            "\n" +
                            "  </body>\n" +
                            "</html>";

            helper.setText(htmlStr, true);
            message.setSender(new InternetAddress("innometrics-notification@innopolis.university"));
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

}
