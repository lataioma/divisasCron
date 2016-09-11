package Jobs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaseConfig {
	
	private static FirebaseConfig instance;
	private FileInputStream archivoConfiguracion;
	private FirebaseOptions options;
	
	
	private FirebaseConfig(){
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
	}
	
	public static FirebaseConfig getInstance(){
		
		if (instance == null){
			instance = new FirebaseConfig();
		}
		return instance;
		
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
}
