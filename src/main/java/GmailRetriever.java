import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import com.google.api.services.gmail.Gmail;

import java.awt.*;
import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;

public class GmailRetriever {

    private static final String APPLICATION_NAME =
            "Bayesian_Filter";

    private static  java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/gmail-java-Bayesian_Filter"); //soy crack  "Documents/BayesianFilter"

    private static FileDataStoreFactory DATA_STORE_FACTORY;

    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    private static HttpTransport HTTP_TRANSPORT;

    private static final List<String> SCOPES =
            Arrays.asList(GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_READONLY, GmailScopes.GMAIL_COMPOSE, GmailScopes.GMAIL_MODIFY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
    

    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                GmailRetriever.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    public static Gmail getGmailService() throws IOException {
        Credential credential = authorize();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static List<Message> getMessages(String query) throws IOException {

        Gmail gSpam = getGmailService();
        String user = "me"; //Para que agarre las cosas del usuario
        //String query = "in:Spam"; //Para que agarre el spam
        String pageToken; // Para dividir

        String body; //El cuerpo del corrreo
        byte[] emailBytes; // Un array con letras del correo

        ListMessagesResponse response = gSpam.users().messages().list(user).setQ(query).execute();
        List<Message> messages = new ArrayList<Message>();
        //Message message = new Message();


        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                pageToken = response.getNextPageToken();
                response = gSpam.users().messages().list(user).setQ(query).setPageToken(pageToken).execute();
            } else {
                break;
            }
        }

        /*Document doc;
        Base64 base = new Base64(true);

        for(Message mID : messages){

                    message = getGmailService().users().messages().get(user, mID.getId()).setFormat("full").execute();

                    if(message.getPayload().getMimeType().equals("text/html")) {

                        emailBytes = base.decodeBase64(message.getPayload().getBody().getData());
                        body = new String(emailBytes);
                        doc = Jsoup.parse(body);
                        System.out.println(doc.body().text());


                    } else if (message.getPayload().getMimeType().equals("text/plain")){

                        emailBytes = base.decodeBase64(message.getPayload().getBody().getData());
                        body = new String(emailBytes);
                        System.out.println(body);


                    } else if (message.getPayload().getMimeType().equals("multipart/alternative")){

                        List<MessagePart> parts = message.getPayload().getParts();

                        for (MessagePart parte : parts) {

                    emailBytes = base.decodeBase64(parte.getBody().getData());

                    if (parte.getMimeType().equals("text/html")) {

                        body = new String(emailBytes);
                        doc = Jsoup.parse(body);
                        System.out.println(doc.body().text());

                    } else if (parte.getMimeType().equals("text/plain")) {
                        body = new String(emailBytes);
                        System.out.println(body);
                    }
                }
            }

        }*/
        return messages;
    }


}



