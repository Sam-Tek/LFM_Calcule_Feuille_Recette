package com.recette.lfm.lfmrecette;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import static java.lang.String.*;


public class CalculeChampionnatRecetteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcule_championnat_recette);

        //instancier variable
        Intent intent=getIntent();
        int stockcalcule;
        //recuperer donner de l ancien activity *******************************************************************************
        String valeurnbticketTribune=intent.getStringExtra(ChampionnatActivity.IDNBTICKETTRIBUNE);
        String valeurprixticketTribune=intent.getStringExtra(ChampionnatActivity.IDPRIXTICKETTRIBUNE);

        String valeurnbticketGradin=intent.getStringExtra(ChampionnatActivity.IDNBTICKETGRADIN);
        String valeurprixticketGradin=intent.getStringExtra(ChampionnatActivity.IDPRIXTICKETGRADIN);

        String valeurnbticketPelouse=intent.getStringExtra(ChampionnatActivity.IDNBTICKETPELOUSE);
        String valeurprixticketPelouse=intent.getStringExtra(ChampionnatActivity.IDPRIXTICKETPELOUSE);

        String valeurLocationTerrain=intent.getStringExtra(ChampionnatActivity.IDLOCATIONTERRAIN);
        String valeurFraisEclairage=intent.getStringExtra(ChampionnatActivity.IDFRAISECLAIRAGE);


        //valeur string convertir **********************************************************************************************
        int NvaleurtkTribune=Integer.valueOf(valeurnbticketTribune);
        Double NvaleurprixtkTribune=Double.valueOf(valeurprixticketTribune);
        int NvaleurtkGradin=Integer.valueOf(valeurnbticketGradin);
        Double NvaleurprixtkGradin=Double.valueOf(valeurprixticketGradin);
        int NvaleurtkPelouse=Integer.valueOf(valeurnbticketPelouse);
        Double NvaleurprixtkPelouse=Double.valueOf(valeurprixticketPelouse);
        int NvaleurLocationTerrain=Integer.valueOf(valeurLocationTerrain);
        Double NvaleurFraisEclairage=Double.valueOf(valeurFraisEclairage);


        //calcule pour obtenir la somme de chaque type de ticket******************************************************************
        Double NttTribune = Double.valueOf(NvaleurtkTribune *NvaleurprixtkTribune);
        Double NttGradin= Double.valueOf(NvaleurtkGradin*NvaleurprixtkGradin);
        Double NttPelouse= Double.valueOf(NvaleurtkPelouse*NvaleurprixtkPelouse);
        NttTribune=Math.round(NttTribune*100.0)/100.0;
        NttGradin=Math.round(NttGradin*100.0)/100.0;
        NttPelouse=Math.round(NttPelouse*100.0)/100.0;
        //calcule nombre spectateur
        int Nnbspectateur=NvaleurtkTribune+NvaleurtkGradin+NvaleurtkPelouse;
        //calcule recette brute
        Double NrecetteBrute=NttTribune+NttPelouse+NttGradin;
        NrecetteBrute=Math.round(NrecetteBrute*100.0)/100.0;
        //Recette imposable et taxe de 8% si recette brute >3040
        Double NrecetteImposable=Double.valueOf(0);
        Double NtaxeFiscale=Double.valueOf(0);
        if (NrecetteBrute > 3040){
            NrecetteImposable=NrecetteBrute-3040;
            NtaxeFiscale=(NrecetteImposable*8)/100;
            NtaxeFiscale=Math.round(NtaxeFiscale*100.0)/100.0;
        }
        //Recette nette
        Double NrecetteNette=NrecetteBrute-NtaxeFiscale;
        NrecetteNette=Math.round(NrecetteNette*100.0)/100.0;
        //frais location terrain
        Double NfraisLocationTerrain=(NrecetteNette*NvaleurLocationTerrain)/100;
        NfraisLocationTerrain=Math.round(NfraisLocationTerrain*100.0)/100.0;
        //reserve statutaire 10%
        Double NfraisReserveStatutaire=(NrecetteNette*10)/100;
        NfraisReserveStatutaire=Math.round(NfraisReserveStatutaire*100.0)/100.0;
        //Caisse Solidarité 15%
        Double NfraisCaisseSolidarite=(NrecetteNette*15)/100;
        NfraisCaisseSolidarite=Math.round(NfraisCaisseSolidarite*100.0)/100.0;
        //FRais d organisation
        Double NfraisOrganisation=(NrecetteNette*30)/100;
        NfraisOrganisation=Math.round(NfraisOrganisation*100.0)/100.0;
        //FRais généraux
        Double NfraisGeneraux=NfraisLocationTerrain+NfraisReserveStatutaire+NfraisCaisseSolidarite+
                NfraisOrganisation+NvaleurFraisEclairage;
        NfraisGeneraux=Math.round(NfraisGeneraux*100.0)/100.0;
        //Solde a répartir
        Double NsoldeRepartir=NrecetteNette-NfraisGeneraux;
        NsoldeRepartir=Math.round(NsoldeRepartir*100.0)/100.0;
        //quand la recette est insufisente**********************************
        if (NvaleurFraisEclairage != 0 && NsoldeRepartir<0){
            Double est=NrecetteNette-(NfraisLocationTerrain+NfraisReserveStatutaire+NfraisCaisseSolidarite+NfraisOrganisation);
            est=Math.round(est*100.0)/100.0;
            NvaleurFraisEclairage=est;
            NfraisGeneraux=NfraisLocationTerrain+NfraisReserveStatutaire+NfraisCaisseSolidarite+
                    NfraisOrganisation+NvaleurFraisEclairage;
            NfraisGeneraux=Math.round(NfraisGeneraux*100.0)/100.0;
            NsoldeRepartir=NrecetteNette-NfraisGeneraux;
            NsoldeRepartir=Math.round(NsoldeRepartir*100.0)/100.0;
        }
        //fin*****************************************************************
        //repartition ligue 25%
        Double NrepartitionLigue=(NsoldeRepartir*25)/100;
        NrepartitionLigue=SRtronquer(NrepartitionLigue);
        //club recevant 75%
        Double NrepartitionClubRecevant=(NsoldeRepartir*75)/100;
        NrepartitionClubRecevant=SRtronquer(NrepartitionClubRecevant);
        //resoudre le probleme de centime***********
        Double centime=NsoldeRepartir-(NrepartitionLigue+NrepartitionClubRecevant);
        centime=Math.round(centime*100.0)/100.0;
        NrepartitionLigue=NrepartitionLigue+centime;
        NrepartitionLigue=Math.round(NrepartitionLigue*100.0)/100.0;
        //fin****************************************************
        //part lfm
        Double NpartLFM=NfraisReserveStatutaire+NrepartitionLigue;
        NpartLFM=Math.round(NpartLFM*100.0)/100.0;
        //Part solidarité
        Double NpartSolidarite=NfraisCaisseSolidarite;
        NpartSolidarite=Math.round(NpartSolidarite*100.0)/100.0;



        //affichier dans la vue les resultats des calcule**************************************************************************
        //recuperer les textview pour les modifier
        TextView calnbticketTribune=(TextView) findViewById(R.id.calnbticketTribune);
        TextView calnbticketGradin=(TextView) findViewById(R.id.calnbticketGradin);
        TextView calnbticketPelouse=(TextView) findViewById(R.id.calnbticketPelouse);
        TextView calnbspectateur=(TextView) findViewById(R.id.calnbspectateur);
        TextView calRecetteBrute=(TextView) findViewById(R.id.calRecetteBrute);
        TextView calTaxeFiscale=(TextView) findViewById(R.id.calTaxeFiscale);
        TextView calRecetteNette=(TextView) findViewById(R.id.calRecetteNette);
        TextView calLocationTerrain=(TextView) findViewById(R.id.calLocationTerrain);
        TextView calReserveStatutaire=(TextView) findViewById(R.id.calReserveStatutaire);
        TextView calCaisseSolidarite=(TextView) findViewById(R.id.calCaisseSolidarite);
        TextView calFraisOrganisation=(TextView) findViewById(R.id.calFraisOrganisation);
        TextView calFraisEclairage=(TextView) findViewById(R.id.calFraisEclairage);
        TextView calFraisGeneraux=(TextView) findViewById(R.id.calFraisGeneraux);
        TextView calSoldeRepartir=(TextView) findViewById(R.id.calSoldeRepartir);
        TextView calLigue=(TextView) findViewById(R.id.calLigue);
        TextView calRClubReceveur=(TextView) findViewById(R.id.calRClubReceveur);
        TextView calRClubVisiteur=(TextView) findViewById(R.id.calRClubVisiteur);
        TextView calPartLFM=(TextView) findViewById(R.id.calPartLFM);
        TextView calPartSolidarite=(TextView) findViewById(R.id.calPartSolidarite);


        //modifier les textview *****************************************************************
        calnbticketTribune.setText(valeurnbticketTribune+" tickets Tribune à "+valeurprixticketTribune+"€ => "+NttTribune+"€");
        calnbticketGradin.setText(valeurnbticketGradin+" tickets Gradin à "+valeurprixticketGradin+"€ => "+ NttGradin+"€");
        calnbticketPelouse.setText(valeurnbticketPelouse+" tickets Pelouse à "+valeurprixticketPelouse+"€=> "+NttPelouse+"€");
        calnbspectateur.setText(Nnbspectateur+" Spectateurs payants");
        calRecetteBrute.setText("RECETTE BRUTE : "+NrecetteBrute+"€");
        calTaxeFiscale.setText("Taxe Fiscale 8% :"+NtaxeFiscale+"€");
        calRecetteNette.setText("Recette nette :"+NrecetteNette+"€");
        calLocationTerrain.setText("Location terrain "+NvaleurLocationTerrain+"% :"+NfraisLocationTerrain+"€");
        calReserveStatutaire.setText("Réserve Statutaire(10%) :"+NfraisReserveStatutaire+"€");
        calCaisseSolidarite.setText("Caisse de Solidarité (15%) :"+NfraisCaisseSolidarite+"€");
        calFraisOrganisation.setText("Frais d'organisation (30%) :"+NfraisOrganisation+"€");
        calFraisEclairage.setText("Frais d'éclairage :"+NvaleurFraisEclairage+"€");
        calFraisGeneraux.setText("Frais généraux :"+NfraisGeneraux+"€");
        calSoldeRepartir.setText("Solde à répartir :"+NsoldeRepartir+"€");
        calLigue.setText("Ligue 25% :"+NrepartitionLigue+"€");
        calRClubReceveur.setText("Club Recevant 75%:"+NrepartitionClubRecevant+"€");
        calPartLFM.setText("Part LFM :"+NpartLFM+"€");
        calPartSolidarite.setText("Part Solidarité :"+NpartSolidarite+"€");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calcule_championnat_recette, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

/*        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    //tronquer un chiffre
    public double SRtronquer(double n)
    {
        n=n*100;
        n=Math.floor(n);
        n=n/100;
        return n;
    }

}
