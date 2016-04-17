package com.recette.lfm.lfmrecette;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import static java.lang.String.*;


public class CalculeChampionnatRecetteActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcule_championnat_recette);

        //instancier variable
        Intent intent=getIntent();
        int stockcalcule;
        //recuperer donner de l ancien activity *******************************************************************************
        String valeurnbticketTribune=intent.getStringExtra(ChampionnatActivity.IDNBTICKETTRIBUNE);
        String valeurnbticketGradin=intent.getStringExtra(ChampionnatActivity.IDNBTICKETGRADIN);
        String valeurnbticketPelouse=intent.getStringExtra(ChampionnatActivity.IDNBTICKETPELOUSE);
        String valeurLocationTerrain=intent.getStringExtra(ChampionnatActivity.IDLOCATIONTERRAIN);
        String valeurFraisEclairage=intent.getStringExtra(ChampionnatActivity.IDFRAISECLAIRAGE);
        String valeurClubReceveur=intent.getStringExtra(ChampionnatActivity.IDCLUBRECEVEUR);
        String valeurClubVisiteur=intent.getStringExtra(ChampionnatActivity.IDCLUBVISITEUR);

        //valeur string convertir **********************************************************************************************
        int NvaleurtkTribune=Integer.valueOf(valeurnbticketTribune);
        int NvaleurtkGradin=Integer.valueOf(valeurnbticketGradin);
        int NvaleurtkPelouse=Integer.valueOf(valeurnbticketPelouse);
        int NvaleurLocationTerrain=Integer.valueOf(valeurLocationTerrain);
        Double NvaleurFraisEclairage=Double.valueOf(valeurFraisEclairage);
        Double NvaleurClubReceveur=Double.valueOf(valeurClubReceveur);
        Double NvaleurClubVisiteur=Double.valueOf(valeurClubVisiteur);

        //calcule pour obtenir la somme de chaque type de ticket******************************************************************
        Double NttTribune = Double.valueOf(NvaleurtkTribune *9);
        Double NttGradin= Double.valueOf(NvaleurtkGradin*7);
        Double NttPelouse= Double.valueOf(NvaleurtkPelouse*6);
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
        //maison du football
        int Nmaisonfoot=1*NvaleurtkTribune;
        //Effort solidarité
        int NeffortSolidarite=1*Nnbspectateur;
        //Recette nette
        Double NrecetteNette=NrecetteBrute-(NtaxeFiscale+Nmaisonfoot+NeffortSolidarite);
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
                NfraisOrganisation+NvaleurFraisEclairage+NvaleurClubReceveur+NvaleurClubVisiteur;
        NfraisGeneraux=Math.round(NfraisGeneraux*100.0)/100.0;
        //Solde a répartir
        Double NsoldeRepartir=NrecetteNette-NfraisGeneraux;
        NsoldeRepartir=Math.round(NsoldeRepartir*100.0)/100.0;
        //quand la recette est insufisente************************************
        if (NsoldeRepartir<0){
            NvaleurClubReceveur=Double.valueOf(0);
            NvaleurClubVisiteur=Double.valueOf(0);
            NfraisGeneraux=NfraisLocationTerrain+NfraisReserveStatutaire+NfraisCaisseSolidarite+
                    NfraisOrganisation+NvaleurFraisEclairage+NvaleurClubReceveur+NvaleurClubVisiteur;
            NfraisGeneraux=Math.round(NfraisGeneraux*100.0)/100.0;
            NsoldeRepartir=NrecetteNette-NfraisGeneraux;
            NsoldeRepartir=Math.round(NsoldeRepartir*100.0)/100.0;
            if (NvaleurFraisEclairage != 0 && NsoldeRepartir<0){
                Double est=NrecetteNette-(NfraisLocationTerrain+NfraisReserveStatutaire+NfraisCaisseSolidarite+NfraisOrganisation);
                est=Math.round(est*100.0)/100.0;
                NvaleurFraisEclairage=est;
                NfraisGeneraux=NfraisLocationTerrain+NfraisReserveStatutaire+NfraisCaisseSolidarite+
                        NfraisOrganisation+NvaleurFraisEclairage+NvaleurClubReceveur+NvaleurClubVisiteur;
                NfraisGeneraux=Math.round(NfraisGeneraux*100.0)/100.0;
                NsoldeRepartir=NrecetteNette-NfraisGeneraux;
                NsoldeRepartir=Math.round(NsoldeRepartir*100.0)/100.0;
            }
        }
        //fin*****************************************************************
        //repartition ligue 25%
        Double NrepartitionLigue=(NsoldeRepartir*25)/100;
        NrepartitionLigue=SRtronquer(NrepartitionLigue);
        //club recevant 50%
        Double NrepartitionClubRecevant=(NsoldeRepartir*50)/100;
        NrepartitionClubRecevant=SRtronquer(NrepartitionClubRecevant);
        //club visiteur 25%
        Double NrepartitionClubVisisteur=(NsoldeRepartir*25)/100;
        NrepartitionClubVisisteur=SRtronquer(NrepartitionClubVisisteur);
        //resoudre le probleme de centime***********
        Double centime=NsoldeRepartir-(NrepartitionLigue+NrepartitionClubRecevant+NrepartitionClubVisisteur);
        centime=Math.round(centime*100.0)/100.0;
        NrepartitionLigue=NrepartitionLigue+centime;
        NrepartitionLigue=Math.round(NrepartitionLigue*100.0)/100.0;
        //fin****************************************************
        //part lfm
        Double NpartLFM=Nmaisonfoot+NeffortSolidarite+NfraisReserveStatutaire+NrepartitionLigue;
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
        TextView calMaisonFoot=(TextView) findViewById(R.id.calMaisonFoot);
        TextView calEffortSolidarite=(TextView) findViewById(R.id.calEffortSolidarite);
        TextView calRecetteNette=(TextView) findViewById(R.id.calRecetteNette);
        TextView calLocationTerrain=(TextView) findViewById(R.id.calLocationTerrain);
        TextView calReserveStatutaire=(TextView) findViewById(R.id.calReserveStatutaire);
        TextView calCaisseSolidarite=(TextView) findViewById(R.id.calCaisseSolidarite);
        TextView calFraisOrganisation=(TextView) findViewById(R.id.calFraisOrganisation);
        TextView calFraisEclairage=(TextView) findViewById(R.id.calFraisEclairage);
        TextView calClubReceveur=(TextView) findViewById(R.id.calClubReceveur);
        TextView calClubVisiteur=(TextView) findViewById(R.id.calClubVisiteur);
        TextView calFraisGeneraux=(TextView) findViewById(R.id.calFraisGeneraux);
        TextView calSoldeRepartir=(TextView) findViewById(R.id.calSoldeRepartir);
        TextView calLigue=(TextView) findViewById(R.id.calLigue);
        TextView calRClubReceveur=(TextView) findViewById(R.id.calRClubReceveur);
        TextView calRClubVisiteur=(TextView) findViewById(R.id.calRClubVisiteur);
        TextView calPartLFM=(TextView) findViewById(R.id.calPartLFM);
        TextView calPartSolidarite=(TextView) findViewById(R.id.calPartSolidarite);


        //modifier les textview *****************************************************************
        calnbticketTribune.setText(valeurnbticketTribune+" tickets Tribune à 9€ => "+NttTribune+"€");
        calnbticketGradin.setText(valeurnbticketGradin+" tickets Gradin à 7€ => "+ NttGradin+"€");
        calnbticketPelouse.setText(valeurnbticketPelouse+" tickets Pelouse à 6€=> "+NttPelouse+"€");
        calnbspectateur.setText(Nnbspectateur+" Spectateurs payants");
        calRecetteBrute.setText("RECETTE BRUTE : "+NrecetteBrute+"€");
        calTaxeFiscale.setText("Taxe Fiscale 8% :"+NtaxeFiscale+"€");
        calMaisonFoot.setText("Maison du Football :"+Nmaisonfoot+"€");
        calEffortSolidarite.setText("Effort Solidarité :"+NeffortSolidarite+"€");
        calRecetteNette.setText("Recette nette :"+NrecetteNette+"€");
        calLocationTerrain.setText("Location terrain "+NvaleurLocationTerrain+"% :"+NfraisLocationTerrain+"€");
        calReserveStatutaire.setText("Réserve Statutaire(10%) :"+NfraisReserveStatutaire+"€");
        calCaisseSolidarite.setText("Caisse de Solidarité (15%) :"+NfraisCaisseSolidarite);
        calFraisOrganisation.setText("Frais d'organisation (30%) :"+NfraisOrganisation);
        calFraisEclairage.setText("Frais d'éclairage :"+NvaleurFraisEclairage);
        calClubReceveur.setText("Club receveur :"+NvaleurClubReceveur);
        calClubVisiteur.setText("Club visiteur :"+NvaleurClubVisiteur);
        calFraisGeneraux.setText("Frais généraux :"+NfraisGeneraux);
        calSoldeRepartir.setText("Solde à répartir :"+NsoldeRepartir);
        calLigue.setText("Ligue 25% :"+NrepartitionLigue);
        calRClubReceveur.setText("Club Recevant 50%:"+NrepartitionClubRecevant);
        calRClubVisiteur.setText("Club Visiteur 25%:"+NrepartitionClubVisisteur);
        calPartLFM.setText("Part LFM :"+NpartLFM);
        calPartSolidarite.setText("Part Solidarité :"+NpartSolidarite);

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
        if (id == R.id.action_settings) {
            return true;
        }

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