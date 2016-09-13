package Jobs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import models.Divisa;



public class Tarea implements Job{
	
	private final String API="cf9ec84e98574bb7bd65d94434162332";
	
	
    @SuppressWarnings("unused")
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
    	System.out.printf("INICIAMOS EJECUCION\n");
    	
     	FirebaseConfig inicioFirebase= FirebaseConfig.getInstance();
    			
       	
        System.out.printf(new Locale("es", "ESP"), "%tc Ejecutando tarea...%n", new java.util.Date());
         Divisa resultado = conexionGET("https://openexchangerates.org/api/latest.json?app_id=cf9ec84e98574bb7bd65d94434162332&base=USD","HTTPS");
        
         
         
        System.out.printf("vamos a ver "+resultado.getRates().EUR+"\n");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference ref = database.getReference("server/historico");
		
       
        DatabaseReference usersRef = ref.child(getDate(resultado.getTimestamp()));
        usersRef.setValue(resultado);
        
        irAPagina("http://pruebamanel-lataioma.rhcloud.com/","HTTP");
            
        System.out.println("hemos llegado al final");
       
       
         
    }
    
 
    private void irAPagina(String param, String protocolo) {
    	
    
    	String responce = "";
        Divisa divisa = null;

        BufferedReader rd = null;

        try {

            URL url = new URL(param);

            if (protocolo.equals("HTTPS")) {

                HttpsURLConnection conn1 = (HttpsURLConnection) url.openConnection();


                rd = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
                
                

            } else {

                URLConnection conn2 = url.openConnection();

                rd = new BufferedReader(new InputStreamReader(conn2.getInputStream()));

            }


            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = rd.readLine()) != null)
                responseStrBuilder.append(inputStr);
            
            System.out.print(responseStrBuilder.toString());
            
            
            
            
        } catch (Exception e) {

            System.out.println("Web request failed");

        // Web request failed

        } finally {

            if (rd != null) {

                try {

                    rd.close();

                } catch (IOException ex) {

                    System.out.println("Problema al cerrar el objeto lector");

                }

            }

        }
	}


	private String getDate(long timestamp) {
    	long timeMilisegons = timestamp* 1000; 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    	String dateString  = dateFormat.format(timeMilisegons);
    	System.out.println(dateString);
		return dateString;
	}



	



	private static Divisa  conexionGET(String request, String protocolo) {

        String responce = "";
        Divisa divisa = null;

        BufferedReader rd = null;

        try {

            URL url = new URL(request);

            if (protocolo.equals("HTTPS")) {

                HttpsURLConnection conn1 = (HttpsURLConnection) url.openConnection();


                rd = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
                
                

            } else {

                URLConnection conn2 = url.openConnection();

                rd = new BufferedReader(new InputStreamReader(conn2.getInputStream()));

            }


            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = rd.readLine()) != null)
                responseStrBuilder.append(inputStr);

            Gson gson = new Gson();
             divisa = gson.fromJson(responseStrBuilder.toString(), Divisa.class);

        

            /*String line;



            while ((line = rd.readLine()) != null) {

                //Process line...

               responce += line;

           }
            
            Gson gson = new Gson();
          
            JsonParser jsonParser = new JsonParser();
            jsonArray = (JsonObject) jsonParser.parse(responce);

*/

        } catch (Exception e) {

            System.out.println("Web request failed");

        // Web request failed

        } finally {

            if (rd != null) {

                try {

                    rd.close();

                } catch (IOException ex) {

                    System.out.println("Problema al cerrar el objeto lector");

                }

            }

        }



        return divisa;

    }
    

}
