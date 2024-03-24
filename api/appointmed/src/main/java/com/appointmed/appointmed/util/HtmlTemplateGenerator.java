package com.appointmed.appointmed.util;

import com.appointmed.appointmed.dto.ContactInfoDto;
import com.appointmed.appointmed.model.Location;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class HtmlTemplateGenerator {
    public static String generateReservationConfirmedTemplate(Instant startTimestamp, int duration, List<ContactInfoDto> contactInfos, String address, String locationName, String notes) {
        StringBuilder contactInfoHtml = new StringBuilder();
        for (ContactInfoDto contactInfo : contactInfos) {
            contactInfoHtml.append("<p><strong>Phone:</strong> ").append(contactInfo.getPhoneNumber()).append("</p>");
            contactInfoHtml.append("<p><strong>Email:</strong> ").append(contactInfo.getEmail()).append("</p>");
            contactInfoHtml.append("<p><strong>Fax:</strong> ").append(contactInfo.getFaxNumber()).append("</p>");
            contactInfoHtml.append("<p><strong>Website:</strong> ").append(contactInfo.getWebsiteUrl()).append("</p>");
        }

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Reservation Confirmed</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f0f0f0;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .container {\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            max-width: 600px;\n" +
                "            background-color: #fff;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .title {\n" +
                "            color: blue;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1 class=\"title\">Reservation Confirmed ✅</h1>\n" +
                "        <p><strong>Location:</strong> " + locationName + "</p>\n" +
                "        <p><strong>Address:</strong> " + address + "</p>\n" +
                "        <div><strong>Contact Information:</strong>" + contactInfoHtml + "</div>\n" +
                "        <p><strong>Start:</strong> " + DateTimeFormatter.ISO_INSTANT.format(startTimestamp.truncatedTo(ChronoUnit.MINUTES)) + "</p>\n" +
                "        <p><strong>Duration:</strong> " + duration + " minutes</p>\n" +
                "        <p><strong>Additional Notes:</strong> " + notes + "</p>\n" +
                "        <p>Thank you!</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }

    public static String generateReservationRejectedTemplate(String locationName, List<ContactInfoDto> contactInfos, String rejectionReason) {
        StringBuilder contactInfoHtml = new StringBuilder();
        for (ContactInfoDto contactInfo : contactInfos) {
            contactInfoHtml.append("<p><strong>Phone:</strong> ").append(contactInfo.getPhoneNumber()).append("</p>");
            contactInfoHtml.append("<p><strong>Email:</strong> ").append(contactInfo.getEmail()).append("</p>");
            contactInfoHtml.append("<p><strong>Fax:</strong> ").append(contactInfo.getFaxNumber()).append("</p>");
            contactInfoHtml.append("<p><strong>Website:</strong> ").append(contactInfo.getWebsiteUrl()).append("</p>");
        }

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Reservation Rejected</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f0f0f0;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .container {\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            max-width: 600px;\n" +
                "            background-color: #fff;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .title {\n" +
                "            color: blue;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1 class=\"title\">Reservation Rejected ❌</h1>\n" +
                "        <p>We regret to inform you that your reservation at <strong>" + locationName + "</strong> has been rejected.</p>\n" +
                "        <p><strong>Reason for Rejection:</strong> " + rejectionReason + "</p>\n" +
                "        <div><strong>Contact Information:</strong>" + contactInfoHtml + "</div>\n" +
                "        <p>Thank you!</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }

    public static String generateReservationPendingTemplate(String locationName) {
        StringBuilder contactInfoHtml = new StringBuilder();

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Reservation Pending</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f0f0f0;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .container {\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            max-width: 600px;\n" +
                "            background-color: #fff;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .title {\n" +
                "            color: blue;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1 class=\"title\">Reservation Pending 🕒</h1>\n" +
                "        <p>Your reservation at <strong>" + locationName + "</strong> is currently pending approval.</p>\n" +
                "        <p>We will notify you once the status is updated.</p>\n" +
                "        <p>Thank you!</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }

    public static String generateAccountConfirmationEmail(String name, String surname, String password) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Account Confirmation</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f0f0f0;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .container {\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            max-width: 600px;\n" +
                "            background-color: #fff;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Welcome to Our Platform!</h1>\n" +
                "        <p>Hello " + name + " " + surname + ",</p>\n" +
                "        <p>Your account has been successfully created.</p>\n" +
                "        <p>Please find below your login credentials:</p>\n" +
                "        <ul>\n" +
                "            <li><strong>Username:</strong> Your email address</li>\n" +
                "            <li><strong>Password:</strong> " + password + " (Temporary - Please change it after logging in)</li>\n" +
                "        </ul>\n" +
                "        <p>Thank you!</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}

