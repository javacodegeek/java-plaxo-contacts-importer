import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PlaxoContactImporter {

    public final static String PLAXOCONTACTSURL = "http://www.plaxo.com/pdata/contacts/@me/@all";
    private String userName = null;
    private String userPassword = null;

    PlaxoContactImporter() {
        this.userName = null;
        this.userPassword = null;
    }

    PlaxoContactImporter(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public String getContactsResponse() {
        String response = null;

        if ((this.userName == null)||(this.userName == null)) {
            return "{status: \"error\"; message: \"Not enough parametrs\"}";
        }
        try {

            //Base64 base64 = new Base64();

            String authString = this.userName + ":" + this.userPassword;

            //String authStringEnc = new String(base64.encode(authString.getBytes()));
            String authStringEnc = DatatypeConverter.printBase64Binary(authString.getBytes("UTF-8"));


            URL url = new URL(this.PLAXOCONTACTSURL);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            response = sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "{status: \"error\"; message: \"Some  MalformedURLException heppend\"}";
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"status\": \"error\"; \"message\": \"Some  IOException heppend\"}";
        }
        return "{\"status\": \"success\"; \"message\": " + response + "}";
    }

    public String toString() {
        String result  = "\nThis is PlaxoContactImporter object:";
        result += "\n plaxocontactUrl: " + this.PLAXOCONTACTSURL;
        result += "\n userName: " + this.userName;
        result += "\n userPassword: " + this.userPassword + "\n";
        return result;
    }

}