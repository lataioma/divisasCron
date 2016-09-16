package Jobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import models.Divisa;
import models.LiborReference;

public class TareaLibor implements Job {

	final String euroUrl = "https://data.import.io/extractor/be2d3616-e967-4750-9a59-8b922ffbb5ce/json/latest?_apikey=50007d54bb854cdf84d9bb24a3b21e0132f77534a16fb461572b33bf69353a2879bcaf40e28719e37318914bbabcb7ce0553609f2477e3017247b2ed7b3570e4765ad962d7d9743167048e7e1a1ed144";

	final String usdUrl = "https://data.import.io/extractor/47f5aa1e-7ebc-4aa0-a875-6d612c33933f/json/latest?_apikey=50007d54bb854cdf84d9bb24a3b21e0132f77534a16fb461572b33bf69353a2879bcaf40e28719e37318914bbabcb7ce0553609f2477e3017247b2ed7b3570e4765ad962d7d9743167048e7e1a1ed144";

	final String francUrl = "https://data.import.io/extractor/773e885a-cde7-4492-b861-8c5a25157c1d/json/latest?_apikey=50007d54bb854cdf84d9bb24a3b21e0132f77534a16fb461572b33bf69353a2879bcaf40e28719e37318914bbabcb7ce0553609f2477e3017247b2ed7b3570e4765ad962d7d9743167048e7e1a1ed144";

	final String yenUrl ="https://data.import.io/extractor/1e1d96e6-fad5-4f7b-b3e1-f77e7cf7ae2b/json/latest?_apikey=50007d54bb854cdf84d9bb24a3b21e0132f77534a16fb461572b33bf69353a2879bcaf40e28719e37318914bbabcb7ce0553609f2477e3017247b2ed7b3570e4765ad962d7d9743167048e7e1a1ed144";
	
	final String poundUrl = "https://data.import.io/extractor/ca60e7dd-d1ec-46c9-91cc-d4eaf2203689/json/latest?_apikey=50007d54bb854cdf84d9bb24a3b21e0132f77534a16fb461572b33bf69353a2879bcaf40e28719e37318914bbabcb7ce0553609f2477e3017247b2ed7b3570e4765ad962d7d9743167048e7e1a1ed144";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.printf("INICIAMOS EJECUCION DEL LIBOR\n");

		FirebaseConfig inicioFirebase = FirebaseConfig.getInstance();

		System.out.printf(new Locale("es", "ESP"), "%tc Ejecutando tareaLIBOR...%n", new java.util.Date());
		LiborReference datosFinales = new LiborReference();
		
		try {
			System.out.println("LECTURA DE USD LIBOR");
			recuperaDatosUsd(usdUrl, datosFinales);
			System.out.println("LECTURA DE USD EURO");
			recuperaDatosEuro(euroUrl, datosFinales);
			System.out.println("LECTURA DE USD FRANCO");
			recuperaDatosFrancoSuizo(francUrl, datosFinales);
			System.out.println("LECTURA DE USD YEN");
			recuperaDatosYen(yenUrl, datosFinales);
			System.out.println("LECTURA DE USD POUND");
			recuperaDatosPound(poundUrl, datosFinales);
			
			//Historico
			
			FirebaseDatabase database = FirebaseDatabase.getInstance();
			DatabaseReference ref = database.getReference("server/historical/libor/");
			DatabaseReference usersRef = ref.child(datosFinales.getDateLibor());
			usersRef.setValue(datosFinales);
			System.out.println("GRABADO HISTORICO LIBOR");
			
			//LAST
			DatabaseReference referenciaSpot = database.getReference("server/last/libor/");
			referenciaSpot.setValue(datosFinales);
			System.out.println("GRABADO ULTIMO LIBOR");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.print(
				"DATOS FINALES /n" + datosFinales.getUsdLibor1Month() + "\n" + datosFinales.getEuroLibor1Month());

	}

	private void recuperaDatosUsd(String url, LiborReference datosFinales) throws IOException {
		try {

			JSONObject objeto = busquedaAnidadaJson(lecturaStreamDatosClient(url, "HTTPS"));
			// fecha
			JSONArray datelibor = objeto.getJSONArray("dateLibor");
			
			datosFinales.setDateLibor(getDateString(datelibor.getJSONObject(0).getString("text")));
			// Spot
			String spot = libor(objeto, "usdLiborSpot");
			datosFinales.setUsdLiborSpot(Float.parseFloat(spot.substring(0, spot.length() - 1)));
			// 1 semana
			String week = libor(objeto, "usdLibor1Week");
			datosFinales.setUsdLibor1Week(Float.parseFloat(week.substring(0, week.length() - 1)));
			// 1 Mes
			String month = libor(objeto, "usdLibor1Month");
			datosFinales.setUsdLibor1Month(Float.parseFloat(month.substring(0, month.length() - 1)));
			// 2 meses
			String month2 = libor(objeto, "usdLibor2Month");
			datosFinales.setUsdLibor2Month(Float.parseFloat(month2.substring(0, month2.length() - 1)));
			// 3 meses
			String month3 = libor(objeto, "usdLibor3Month");
			datosFinales.setUsdLibor3Month(Float.parseFloat(month3.substring(0, month3.length() - 1)));
			// 6 meses
			String month6 = libor(objeto, "usdLibor6Month");
			datosFinales.setUsdLibor6Month(Float.parseFloat(month6.substring(0, month6.length() - 1)));
			// Year
			String year = libor(objeto, "usdLibor1Year");
			datosFinales.setUsdLibor1Year(Float.parseFloat(year.substring(0, year.length() - 1)));

			System.out.println("LECTURA\n" + datosFinales.getDateLibor() + "\n " + datosFinales.getUsdLiborSpot()
					+ "\n " + datosFinales.getUsdLibor1Week() + "\n " + datosFinales.getUsdLibor1Month() + "\n "
					+ datosFinales.getUsdLibor3Month() + "\n " + datosFinales.getUsdLibor6Month() + "\n "
					+ datosFinales.getUsdLibor1Year());

		} catch (JSONException e) {
			e.printStackTrace();
			System.out.print("se ha producido una excepcion en el JSON");
		}

	}

	private String getDateString(String string) {
		
    	try {
    		
    		Date fecha=new SimpleDateFormat("MM-dd-yyyy").parse(string);
    		SimpleDateFormat nuevaFecha = new SimpleDateFormat("dd-MM-yyyy");
    		String fechaFinal = nuevaFecha.format(fecha);
    		
			return fechaFinal; 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    			
		
	}

	private String lecturaStreamDatosClient(String url, String string) throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(url).get().addHeader("cache-control", "no-cache")
				// .addHeader("postman-token",
				// "77b0d168-33dc-1bd3-39b7-bb15f5f00f46")
				.build();

		Response response = client.newCall(request).execute();

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.body().byteStream()));
		StringBuilder responseStrBuilder = new StringBuilder();
		String inputStr;
		while ((inputStr = rd.readLine()) != null)
			responseStrBuilder.append(inputStr);

		rd.close();
		return responseStrBuilder.toString();
	}

	private JSONObject busquedaAnidadaJson(String streamdatos) throws JSONException {
		JSONObject primero = new JSONObject(streamdatos);
		JSONObject hola = primero.getJSONObject("result");

		JSONObject otro = hola.getJSONObject("extractorData");

		JSONArray data = otro.getJSONArray("data");
		JSONObject group = data.getJSONObject(0);
		JSONArray group2 = group.getJSONArray("group");
		JSONObject objeto = group2.getJSONObject(0);
		return objeto;
	}

	private String libor(JSONObject object, String campo) throws JSONException {
		JSONArray arrayLibor = object.getJSONArray(campo);
		String liborOver = arrayLibor.getJSONObject(0).getString("text");
		return liborOver;
	}

	private LiborReference conexionGET(String sendUrl, String protocolo) {
		String responce = "";
		Divisa divisa = null;

		BufferedReader rd = null;

		try {

			URL url = new URL(sendUrl);

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

			JSONObject primero = new JSONObject(responseStrBuilder.toString());
			JSONObject result = primero.getJSONObject("result");
			JSONObject extractorData = result.getJSONObject("extractorData");
			JSONArray data = extractorData.getJSONArray("data");

		} catch (Exception e) {

			System.out.println("Web request failed");

		} finally {
			if (rd != null) {
				try {
					rd.close();
				} catch (IOException ex) {
					System.out.println("Problema al cerrar el objeto lector");

				}

			}

		}
		return null;
	}

	// Euro
	private void recuperaDatosEuro(String url, LiborReference datosFinales) throws IOException {
		try {
			JSONObject objeto = busquedaAnidadaJson(lecturaStreamDatosClient(url, "HTTPS"));
			// fecha
			
			// Spot
			String spot = libor(objeto, "euroLiborSpot");
			datosFinales.setEuroLiborSpot(Float.parseFloat(spot.substring(0, spot.length() - 1)));
			// 1 semana
			String week = libor(objeto, "euroLibor1Week");
			datosFinales.setEuroLibor1Week(Float.parseFloat(week.substring(0, week.length() - 1)));
			// 1 Mes
			String month = libor(objeto, "euroLibor1Month");
			datosFinales.setEuroLibor1Month(Float.parseFloat(month.substring(0, month.length() - 1)));
			// 2 meses
			String month2 = libor(objeto, "euroLibor2Month");
			datosFinales.setEuroLibor2Month(Float.parseFloat(month2.substring(0, month2.length() - 1)));
			// 3 meses
			String month3 = libor(objeto, "euroLibor3Month");
			datosFinales.setEuroLibor3Month(Float.parseFloat(month3.substring(0, month3.length() - 1)));
			// 6 meses
			String month6 = libor(objeto, "euroLibor6Month");
			datosFinales.setEuroLibor6Month(Float.parseFloat(month6.substring(0, month6.length() - 1)));
			// Year
			String year = libor(objeto, "euroLibor1Year");
			datosFinales.setEuroLibor1Year(Float.parseFloat(year.substring(0, year.length() - 1)));

			System.out.println("LECTURA\n" + datosFinales.getDateLibor() + "\n " + datosFinales.getEuroLiborSpot()
					+ "\n " + datosFinales.getEuroLibor1Week() + "\n " + datosFinales.getEuroLibor1Month() + "\n "
					+ datosFinales.getEuroLibor3Month() + "\n " + datosFinales.getEuroLibor6Month() + "\n "
					+ datosFinales.getEuroLibor1Year());

		} catch (JSONException e) {
			e.printStackTrace();
			System.out.print("se ha producido una excepcion en el JSON");
		}

	}

	// FrancoSuizo
	private void recuperaDatosFrancoSuizo(String url, LiborReference datosFinales) throws IOException {
		try {
			JSONObject objeto = busquedaAnidadaJson(lecturaStreamDatosClient(url, "HTTPS"));
			// fecha
			
			// Spot
			String spot = libor(objeto, "francLiborSpot");
			datosFinales.setFrancLiborSpot(Float.parseFloat(spot.substring(0, spot.length() - 1)));
			// 1 semana
			String week = libor(objeto, "francLibor1Week");
			datosFinales.setFrancLibor1Week(Float.parseFloat(week.substring(0, week.length() - 1)));
			// 1 Mes
			String month = libor(objeto, "francLibor1Month");
			datosFinales.setFrancLibor1Month(Float.parseFloat(month.substring(0, month.length() - 1)));
			// 2 meses
			String month2 = libor(objeto, "francLibor2Month");
			datosFinales.setFrancLibor2Month(Float.parseFloat(month2.substring(0, month2.length() - 1)));
			// 3 meses
			String month3 = libor(objeto, "francLibor3Month");
			datosFinales.setFrancLibor3Month(Float.parseFloat(month3.substring(0, month3.length() - 1)));
			// 6 meses
			String month6 = libor(objeto, "francLibor6Month");
			datosFinales.setFrancLibor6Month(Float.parseFloat(month6.substring(0, month6.length() - 1)));
			// Year
			String year = libor(objeto, "francLibor1Year");
			datosFinales.setFrancLibor1year(Float.parseFloat(year.substring(0, year.length() - 1)));

			System.out.println("LECTURA\n" + datosFinales.getDateLibor() + "\n " + datosFinales.getEuroLiborSpot()
					+ "\n " + datosFinales.getEuroLibor1Week() + "\n " + datosFinales.getEuroLibor1Month() + "\n "
					+ datosFinales.getEuroLibor3Month() + "\n " + datosFinales.getEuroLibor6Month() + "\n "
					+ datosFinales.getEuroLibor1Year());

		} catch (JSONException e) {
			e.printStackTrace();
			System.out.print("se ha producido una excepcion en el JSON");
		}

	}

	// YEN
	private void recuperaDatosYen(String url, LiborReference datosFinales) throws IOException {
		try {
			JSONObject objeto = busquedaAnidadaJson(lecturaStreamDatosClient(url, "HTTPS"));
			// fecha
			
			// Spot
			String spot = libor(objeto, "yenLiborSpot");
			datosFinales.setYenLiborSpot(Float.parseFloat(spot.substring(0, spot.length() - 1)));
			// 1 semana
			String week = libor(objeto, "yenLibor1Week");
			datosFinales.setYenLibor1Week(Float.parseFloat(week.substring(0, week.length() - 1)));
			// 1 Mes
			String month = libor(objeto, "yenLibor1Month");
			datosFinales.setYenLibor1Month(Float.parseFloat(month.substring(0, month.length() - 1)));
			// 2 meses
			String month2 = libor(objeto, "yenLibor2Month");
			datosFinales.setYenLibor2Month(Float.parseFloat(month2.substring(0, month2.length() - 1)));
			// 3 meses
			String month3 = libor(objeto, "yenLibor3Month");
			datosFinales.setYenLibor3Month(Float.parseFloat(month3.substring(0, month3.length() - 1)));
			// 6 meses
			String month6 = libor(objeto, "yenLibor6Month");
			datosFinales.setYenLibor6Month(Float.parseFloat(month6.substring(0, month6.length() - 1)));
			// Year
			String year = libor(objeto, "yenLibor1Year");
			datosFinales.setYenLibor1Year(Float.parseFloat(year.substring(0, year.length() - 1)));

			System.out.println("LECTURA\n" + datosFinales.getDateLibor() + "\n " + datosFinales.getEuroLiborSpot()
					+ "\n " + datosFinales.getEuroLibor1Week() + "\n " + datosFinales.getEuroLibor1Month() + "\n "
					+ datosFinales.getEuroLibor3Month() + "\n " + datosFinales.getEuroLibor6Month() + "\n "
					+ datosFinales.getEuroLibor1Year());

		} catch (JSONException e) {
			e.printStackTrace();
			System.out.print("se ha producido una excepcion en el JSON");
		}
	}

	// LIBRA ESTERLINA
	private void recuperaDatosPound(String url, LiborReference datosFinales) throws IOException {
		try {
			JSONObject objeto = busquedaAnidadaJson(lecturaStreamDatosClient(url, "HTTPS"));
			// fecha
			
			// Spot
			String spot = libor(objeto, "poundLiborSpot");
			datosFinales.setPoundLiborSpot(Float.parseFloat(spot.substring(0, spot.length() - 1)));
			// 1 semana
			String week = libor(objeto, "poundLibor1Week");
			datosFinales.setPoundLibor1Week(Float.parseFloat(week.substring(0, week.length() - 1)));
			// 1 Mes
			String month = libor(objeto, "poundLibor1Month");
			datosFinales.setPoundLibor1Month(Float.parseFloat(month.substring(0, month.length() - 1)));
			// 2 meses
			String month2 = libor(objeto, "poundLibor2Month");
			datosFinales.setPoundLibor2Month(Float.parseFloat(month2.substring(0, month2.length() - 1)));
			// 3 meses
			String month3 = libor(objeto, "poundLibor3Month");
			datosFinales.setPoundLibor3Month(Float.parseFloat(month3.substring(0, month3.length() - 1)));
			// 6 meses
			String month6 = libor(objeto, "poundLibor6Month");
			datosFinales.setPoundLibor6Month(Float.parseFloat(month6.substring(0, month6.length() - 1)));
			// Year
			String year = libor(objeto, "poundLibor1Year");
			datosFinales.setPoundLibor1Year(Float.parseFloat(year.substring(0, year.length() - 1)));

			System.out.println("LECTURA\n" + datosFinales.getDateLibor() + "\n " + datosFinales.getEuroLiborSpot()
					+ "\n " + datosFinales.getEuroLibor1Week() + "\n " + datosFinales.getEuroLibor1Month() + "\n "
					+ datosFinales.getEuroLibor3Month() + "\n " + datosFinales.getEuroLibor6Month() + "\n "
					+ datosFinales.getEuroLibor1Year());

		} catch (JSONException e) {
			e.printStackTrace();
			System.out.print("se ha producido una excepcion en el JSON");
		}

	}

}
