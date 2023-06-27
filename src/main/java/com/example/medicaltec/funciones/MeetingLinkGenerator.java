package com.example.medicaltec.funciones;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class MeetingLinkGenerator {


    /*public static void main(String[] args) {


        /*String appId = "vpaas-magic-cookie-64547877cba34cdb892bd4fb58d11524";
        String roomName = "salapersonal";
        int durationMinutes = 30; // Specify the duration of the meeting in minutes
        String secretKey = "your-secret-key";

        LocalDateTime desiredStartTime = LocalDateTime.now().plusMinutes(5); // Schedule the meeting 5 minutes from now

        Date startTime = new Date();

        ZoneId zoneId = ZoneId.systemDefault();
        Date startTime = Date.from(desiredStartTime.atZone(zoneId).toInstant());
        Date endTime = Date.from(desiredStartTime.plusMinutes(durationMinutes).atZone(zoneId).toInstant());

        String scheduledMeetingLink = generateScheduledMeetingLink(appId, roomName, startTime, endTime, secretKey);
        System.out.println(scheduledMeetingLink);*/


    public static String generateScheduledMeetingLink(String appId, String roomName, Date startTime, Date endTime, String secretKey) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("context", generateContextClaim(roomName, startTime, endTime));

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String link = "https://8x8.vc/" + roomName + "#" + token;

        return link;
    }

    private static Map<String, Object> generateContextClaim(String roomName, Date startTime, Date endTime) {
        Map<String, Object> context = new HashMap<>();
        context.put("user", generateUserClaim());
        context.put("group", roomName);
        context.put("start_time", startTime);
        context.put("end_time", endTime);

        return context;
    }

    private static Map<String, Object> generateUserClaim() {
        Map<String, Object> user = new HashMap<>();
        // Customize user claim if needed
        return user;
    }
}
