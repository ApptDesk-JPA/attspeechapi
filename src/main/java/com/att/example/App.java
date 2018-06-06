package com.att.example;
// Import the relevant code kit parts

import com.att.api.config.AppConfig;
import com.att.api.oauth.OAuthService;
import com.att.api.oauth.OAuthToken;
import com.att.api.speech.model.SpeechResponse;
import com.att.api.speech.service.SpeechService;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class App {
    private static void setProxySettings() {
        // set any proxy settings
        //RESTConfig.setDefaultProxy("proxy.host", 8080);
    }

    public static AppConfig getConfig() throws IOException {
        return AppConfig.getInstance();
    }
    public static void main(String[] args) {
        try {
            setProxySettings();
            // Use the app settings from developer.att.com for the following
            // values. Make sure Speech is enabled for the app key/secret.

//            final String fqdn = "https://api.att.com";
            final String fqdn = getConfig().getProperty("FQDN");
            // Enter the value from 'App Key' field
//            final String clientId = "odtuqw4cmkvgbhe3a2qlabsn6lutk1hh";
            final String clientId = getConfig().getProperty("clientId");
            // Enter the value from 'Secret' field
            final String clientSecret = getConfig().getProperty("clientSecret");
//            final String clientSecret = "vexxli6w3rmeokvppojid9imgnkczjps";
            // Create service for requesting an OAuth token
            OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);
            // Get OAuth token using the Speech scope
            OAuthToken token = osrvc.getToken(getConfig().getProperty("scope"));
//            OAuthToken token = osrvc.getToken("SPEECH");
            // Create service for interacting with the Speech api
            SpeechService speechSrvc = new SpeechService(fqdn, token);
            // Set this to a single channel audio file
//            final File AUDIO_FILE = new File("E:\\Work2\\itfrontdesk\\testUpload\\johnsmith.wav");
//            final File AUDIO_FILE = new File("E:\\Work2\\itfrontdesk\\testUpload\\johndoe.wav");
            final File AUDIO_FILE = new File("E:\\Work2\\itfrontdesk\\testUpload\\audio\\johnsmith.wav");
            final String context = "";
            final String subcontext = "";
            final String xarg = "13 kbps";
            final SpeechResponse response = speechSrvc.sendRequest(AUDIO_FILE, xarg, context, subcontext);
            // display our results

            List<String> words = response.getNbests().get(0).getWords();
            System.out.println("Out put: = " + words);
        } catch (Exception re) {
            // handle exceptions here
            re.printStackTrace();
        } finally {
            // perform any clean up here
        }
    }
}