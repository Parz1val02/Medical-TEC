package com.example.medicaltec.funciones;

import com.auth0.jwt.JWT;

import com.example.medicaltec.Entity.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JsonWebToken {

    public String generateToken() {
        /** Create new JaaSJwtBuilder and setup the claims. */
        /** Using method chaining we create a new JaaSJwtBuilder that will setup all the claims.
         Before signing the JWT is created with all the specified claims. */
        new JWT();
        //new JaaSJwtBuilder();

        /*String token = JaaSJwtBuilder.builder() // Creates a new instance of JaaSJwtBuilder
                .withDefaults() // This sets default/most common values
                .withApiKey("My api key here") // Set the api key, see https://jaas.8x8.vc/#/apikeys for more info.
                .withUserName("My name here") // Set the user name
                .withUserEmail("My email here") // Set the user email
                .withModerator(true) // Enable user as moderator
                .withOutboundEnabled(true) // Enable outbound calls
                .withTranscriptionEnabled(true) // Enable transcription
                .withAppID("My AppID here") // Set the AppID
                .withUserAvatar("https://avatarurl.com/avatar/url") // Set the user avatar
                .signWith(rsaPrivateKey); /** Finally the JWT is signed with the private key */

        //System.out.println(token); // Write to standard output the signed token.
        return "";
    }


    public String generateScheduledMeetingLink(String appId, String roomName, Date startTime, int durationMinutes, String secretKey) {
        Date endTime = new Date(startTime.getTime() + durationMinutes * 60000L);

        Map<String, Object> claims = new HashMap<>();
        claims.put("context", generateContextClaim(roomName, startTime, endTime));

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String link = "https://your-jitsi-meet-domain.com/" + roomName + "#" + token;

        return link;
    }

    private Map<String, Object> generateContextClaim(String roomName, Date startTime, Date endTime) {
        Map<String, Object> context = new HashMap<>();
        context.put("user", generateUserClaim());
        context.put("group", roomName);
        context.put("start_time", startTime);
        context.put("end_time", endTime);

        return context;
    }

    private Map<String, Object> generateUserClaim() {
        Map<String, Object> user = new HashMap<>();
        // Customize user claim if needed
        return user;
    }

}
