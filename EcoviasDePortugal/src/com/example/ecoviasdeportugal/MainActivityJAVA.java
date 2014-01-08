package com.example.ecoviasdeportugal;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivityJAVA extends Activity implements LocationListener {
	
	private LocationManager locationManager; //acede aos servi�os de localiza��o do android\
	private TextView latitude; //vari�vel para colocar a latitude
	private TextView longitude; //vari�vel para colocar a longitude
	private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // primeiro m�todo que � SEMPRE chamado. Aproveita-se para inicializar vari�veis. Neste caso temos 3: locationamanager, latitude e longitude
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_xml); //faz o inflate do layout sobre a avtivity
        locationManager = (LocationManager) getSystemService (Context.LOCATION_SERVICE); // se tiver, d� a ultima coordenada conhecida de um fornencer do servi�o de GPS
        latitude = (TextView) findViewById (R.id.latitudevalor); //permite aceder �s propriedades do objecto textview latitudevalor e alter�las para mostrar outro valor
        longitude = (TextView) findViewById (R.id.longitudevalor); //permite aceder �s propriedades do objecto textview latitudevalor e alter�las para mostrar outro valor
        provider = LocationManager.GPS_PROVIDER;
        
        Button botao = (Button) findViewById (R.id.botaolocalizacao);
        botao.setOnClickListener(new OnClickListener() {
        	public void onClick (View vista) {
        		if(locationManager.isProviderEnabled(provider)) {
        			onLocationChanged(locationManager.getLastKnownLocation(provider));
        		}
        		
        		else {
        			latitude.setText (getString (R.string.sem_valor)); //coloca a textview da varia�vel latitude igual � string da vari�vel sem_valor
        			longitude.setText (getString (R.string.sem_valor));
        		}
        	}
        }
        
        		); 
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_jav, menu);
        return true;
           
        
        
    }


    @Override
    protected void onResume() { //sempre que a nossa ativity ficar "em cima" o android executa a consulta ao fornecer de dados gps
    	super.onResume();
    	  	if(locationManager.isProviderEnabled(provider))
    		locationManager.requestLocationUpdates(provider, 1000, 1, this); // Se o provider fornecer dados, a cada segundo ou a cada metro ele vai ler uma coordenada de localiza��o ao fornecedor
    	 	 	
    	
    }
    
    @Override
    protected void onDestroy () { //quanda fecha a app deixa de receber dados do GPS
    	super.onDestroy ();
    	locationManager.removeUpdates (this); // this � a pr�pria class onde n�s estamos, o locationListener, que queremos fechar
    }
    
    
	@Override
	public void onLocationChanged(Location localizacao) { //possivel localiza��o. Depois temos de a ir buscar ao fornecedor
		// TODO Auto-generated method stub
		
		if (localizacao == null) {
			latitude.setText (getString (R.string.sem_valor)); //coloca a textview da varia�vel latitude igual � string da vari�vel sem_valor
			longitude.setText (getString (R.string.sem_valor));
						 
		}
		else
			
		{
			latitude.setText (String.valueOf(localizacao.getLatitude())); // A classe String tem o m�todo valueOf que converte o par|ametro latitude de location em string, dado que a vari�vel latitude s� recebe strings pois � do tipo string
			longitude.setText((String.valueOf(localizacao.getLongitude())));
			
			
			
		}
		
	}


	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
    
}
