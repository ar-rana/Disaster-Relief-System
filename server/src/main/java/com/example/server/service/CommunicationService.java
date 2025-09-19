package com.example.server.service;

import org.springframework.stereotype.Service;

@Service
public class CommunicationService {

    public boolean sendSMS() {
//        String connectionString = https://<resource-name>.communication.azure.com/accesskey=<access-key>;

//        SmsClient smsClient = new SmsClientBuilder()
//                    .connectionString(connectionString)
//                    .buildClient();
//
//        smsClient.send(
//            "<from_phone_number>",
//            "<to_phone_number>",
//            """Hello World 👋🏻 via SMS"""
//        );

        int otp = 808080;
        return true;
    }
}
