import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.google.api.services.gmail.model.MessagePart;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
        //System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
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
        String pageToken; // Para dividir

        String body; //El cuerpo del corrreo
        byte[] emailBytes; // Un array con letras del correo

        ListMessagesResponse response = gSpam.users().messages().list(user).setQ(query).execute();
        List<Message> messages = new ArrayList<Message>();

        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                pageToken = response.getNextPageToken();
                response = gSpam.users().messages().list(user).setQ(query).setPageToken(pageToken).execute();
            } else {
                break;
            }
        }
        return messages;
    }

    public String getBody(String query) throws IOException, MessagingException {

        Gmail ser = getGmailService();
        String body = ""; //El cuerpo del corrreo
        byte[] emailBytes; // Un array con letras del correo

        ListMessagesResponse response = ser.users().messages().list("me").setQ(query).execute();
        List<Message> messages = response.getMessages();
        Message message = new Message();
        Document doc;
        Base64 base = new Base64(true);
        String bodyDef = "";

        for(Message mID : messages){

            message = getGmailService().users().messages().get("me", mID.getId()).setFormat("full").execute();

            if(message.getPayload().getMimeType().equals("text/html")) {

                emailBytes = base.decodeBase64(message.getPayload().getBody().getData());
                body = new String(emailBytes);
                doc = Jsoup.parse(body);
                bodyDef += doc.body().text() + '\245';
                //System.out.println(doc.body().text());


            } else if (message.getPayload().getMimeType().equals("text/plain")){

                emailBytes = base.decodeBase64(message.getPayload().getBody().getData());
                bodyDef += new String(emailBytes) +   '\245';
                //System.out.println(body);


            } else if (message.getPayload().getMimeType().equals("multipart/alternative")){

                List<MessagePart> parts = message.getPayload().getParts();

                for (MessagePart parte : parts) {

                    emailBytes = base.decodeBase64(parte.getBody().getData());

                    if (parte.getMimeType().equals("text/html")) {

                        body = new String(emailBytes);
                        doc = Jsoup.parse(body);
                        bodyDef += doc.body().text() +  '\245';
                        //System.out.println(doc.body().text());

                    } else if (parte.getMimeType().equals("text/plain")) {
                        bodyDef += new String(emailBytes) +  '\245';
                        //System.out.println(body);
                    }
                }
            }

        }
    //System.out.print(bodyDef);
        return bodyDef;
    }
}




