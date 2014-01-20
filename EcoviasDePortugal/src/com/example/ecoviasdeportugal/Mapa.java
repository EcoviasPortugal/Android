package com.example.ecoviasdeportugal;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

public class Mapa extends FragmentActivity {
	
	private PolylineOptions trilho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa_xml);
        
        trilho = new PolylineOptions().geodesic(true);
        

        // Get a handle to the Map Fragment
        FragmentManager janelaFragmento = getSupportFragmentManager(); // Cria o objeto janelaFragmento do tipo FragmentManager e d�-nos o gestor de fragmentos da nossa janela que � s� um
        SupportMapFragment mapa=(SupportMapFragment) janelaFragmento.findFragmentById(R.id.map); // inicializa o nosso fragmento, com cast
        
        GoogleMap map = mapa.getMap();
        double latitudeAqui = getIntent().getDoubleExtra("latitudemapa", 0); //traz da activitymain o conteudo da variavel latitudemapa, com as coordenadas GPS
        double longitudeAqui = getIntent().getDoubleExtra("longitudemapa",0);
        		

        LatLng saldanha = new LatLng(latitudeAqui,longitudeAqui);
        Toast.makeText (this,latitudeAqui+"",Toast.LENGTH_SHORT).show();
        Toast.makeText (this,longitudeAqui+"",Toast.LENGTH_SHORT).show();

        map.setMyLocationEnabled(true);
       map.moveCamera(CameraUpdateFactory.newLatLngZoom(saldanha, 18)); //faz zoom ao ponto de 13 vezes

     lerTrilho (R.xml.saldanha);
     map.addPolyline(trilho);
    
     
       
        /*map.addPolyline(new PolylineOptions().geodesic(true)
        		.add(new LatLng(38.7337835895756,-9.14485824872606)) //Saldanha, in�cio na est�tua
        		.add(new LatLng(38.7339028966889,-9.1448749730243))
        		.add(new LatLng(38.7341993953131,-9.14496458682949))
        		.add(new LatLng(38.7341791180105,-9.14516557413613)) 
        		.add(new LatLng(38.7343657369285,-9.14519889236687))
        		.add(new LatLng(38.7343074130646,-9.14531905021271))
        		.add(new LatLng(38.7341811211403,-9.1454407576991))
        		.add(new LatLng(38.7340535335041,-9.14554317342986))
        		.add(new LatLng(38.7339332045427,-9.1456024036537))
        		.add(new LatLng(38.7338300680863,-9.14563285502205))
        		.add(new LatLng(38.7337411181391,-9.14564202520358))
        		.add(new LatLng(38.7337072093955,-9.14565406454775))
        		.add(new LatLng(38.7336495998184,-9.14597534672012))
        		.add(new LatLng(38.7336069497946,-9.14637200156623))
        		.add(new LatLng(38.7335635864932,-9.14659127141528))
        		.add(new LatLng(38.733511685837,-9.14687085767348))
        		.add(new LatLng(38.7329036849831,-9.14673153186246))
        		.add(new LatLng(38.7325710919906,-9.14576414788587))
        		.add(new LatLng(38.7332656003257,-9.14537097400824))
        		.add(new LatLng(38.7334256008756,-9.14525569120274))
        		.add(new LatLng(38.7333631177536,-9.14508822490222))
        		.add(new LatLng(38.733318917076,-9.14487066977462))
        		.add(new LatLng(38.7331683591125,-9.14484876305851)) //Saldanha, fim na porta do Atrium 
        ); */
    }
    
    private void lerTrilho (int mapa) { //ler o xml, criar as coordenadas, desenhar trilho
    	
    	Resources recursos = getResources ();
    	XmlResourceParser ficheiro = recursos.getXml(mapa);
    	try { //caso n�o consiga aceder ao conteudo do xml dando exception faz a a��o catch, descrita em baixo
			while (ficheiro.getEventType()!=XmlPullParser.END_DOCUMENT) { //enquanto n�o encontrar o fim do XML faz coisas
				if (ficheiro.getEventType()==XmlPullParser.START_TAG && ficheiro.getName().equals("coordinates")) { //quando encontrar uma TAG com o nome coordinates
					ficheiro.next();
					//Toast.makeText (this,ficheiro.getText(),Toast.LENGTH_SHORT).show(); //mostra um toast para cada linha de coordenadas que ler
					String coordenada = ficheiro.getText();
					String [] splitCoord = coordenada.split(",");
					trilho.add(new LatLng(Double.parseDouble(splitCoord[0]),Double.parseDouble(splitCoord[1])));
				}
				ficheiro.next();
				
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    	finally {ficheiro.close();} //fechar o xml que abrimos para n�o consumir recursos
    	
    	
    	
    	
    	
    }
}
