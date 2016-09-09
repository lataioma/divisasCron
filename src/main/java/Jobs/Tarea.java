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

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import models.Divisa;



public class Tarea implements Job{
	
	private final String API="cf9ec84e98574bb7bd65d94434162332";
	FirebaseOptions options = null;
	 FileInputStream archivoConfiguracion;
	
    @SuppressWarnings("unused")
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
    	System.out.printf("INICIAMOS EJECUCION\n");
    	 
			try {
				archivoConfiguracion = new FileInputStream(getFileFirebase());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			System.out.printf("antes de options\n");
					options = new FirebaseOptions.Builder()
							  .setServiceAccount(archivoConfiguracion)
							  .setDatabaseUrl("https://hipoteca-multidivisa.firebaseio.com/")
							  .build();
					
					
		
    			FirebaseApp.initializeApp(options);
    			System.out.printf("despues de options\n");
    			
    			 try {
						archivoConfiguracion.close();
						System.out.println("CERRADO INPUT STREAM");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			
       	
        System.out.printf(new Locale("es", "ESP"), "%tc Ejecutando tarea...%n", new java.util.Date());
         Divisa resultado = conexionGET("https://openexchangerates.org/api/latest.json?app_id=cf9ec84e98574bb7bd65d94434162332&base=USD","HTTPS");
        
         
         
        System.out.printf("vamos a ver "+resultado.getRates().EUR+"\n");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference ref = database.getReference("server/historico");
		
       
        DatabaseReference usersRef = ref.child(getDate(resultado.getTimestamp()));

        Map<String, Divisa> divisa = new HashMap<String, Divisa>();
        divisa.put(getDate(resultado.getTimestamp()) , resultado);

        usersRef.setValue(divisa);
            
        System.out.println("hemos llegado al final");
       
        
       
         
    }
    
 
    private String getDate(long timestamp) {
    	long timeMilisegons = timestamp* 1000; 
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    	String dateString  = dateFormat.format(timeMilisegons);
    	System.out.println(dateString);
		return dateString;
	}



	private String getFileFirebase() {
    	String uno="Hipoteca Multidivisa-c09bbaf59b2f.json";
    	String dos="hola.json";
    	
    	System.out.printf("iniciamos lectura\n");
    	ClassLoader classLoader = getClass().getClassLoader();
    	File file = new File(classLoader.getResource(dos).getFile());
    	 System.out.printf(file.toString()+"\n");
    	
    	return file.toString(); 
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
