package com.recette.lfm.lfmrecette;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class ChampionnatActivity extends ActionBarActivity {
    public final static String IDNBTICKETTRIBUNE= "com.recette.lfm.lfmrecette.IDNBTICKETTRIBUNE";
    public final static String IDNBTICKETGRADIN= "com.recette.lfm.lfmrecette.IDNBTICKETGRADIN";
    public final static String IDNBTICKETPELOUSE= "com.recette.lfm.lfmrecette.IDNBTICKETPELOUSE";
    public final static String IDLOCATIONTERRAIN= "com.recette.lfm.lfmrecette.IDLOCATIONTERRAIN";
    public final static String IDFRAISECLAIRAGE= "com.recette.lfm.lfmrecette.IDFRAISECLAIRAGE";
    public final static String IDCLUBRECEVEUR= "com.recette.lfm.lfmrecette.IDCLUBRECEVEUR";
    public final static String IDCLUBVISITEUR= "com.recette.lfm.lfmrecette.IDCLUBVISITEUR";

    private String valeurLocation="10";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championnat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_championnat, menu);
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

    public void calculeChampionnat(View view)
    {
        boolean ok=true;
       Intent intent = new Intent(this,CalculeChampionnatRecetteActivity.class);
        //recuperer les edittext
        EditText nbticketTribune=(EditText) findViewById(R.id.nbticketTribune);
        EditText nbticketGradin=(EditText) findViewById(R.id.nbticketGradin);
        EditText nbticketPelouse=(EditText) findViewById(R.id.nbticketPelouse);
        EditText fraiseclairage =(EditText) findViewById(R.id.fraisEclairage);
        EditText clubreceveur=(EditText) findViewById(R.id.clubReceveur);
        EditText clubvisiteur=(EditText) findViewById(R.id.clubVisiteur);


        //verifier si les champs sont vides ou trop gros
        if (nbticketTribune.getText().toString().length() < 10) {
            String valeurnbticketTribune;
            if (nbticketTribune.getText().toString().length() > 0){
                //recuperer la valeur du edittext
                valeurnbticketTribune = nbticketTribune.getText().toString();
            }else{
                valeurnbticketTribune="0";
            }

                //inserer les valeurs des edittext dans le intent pour la vue suivante
            intent.putExtra(IDNBTICKETTRIBUNE, valeurnbticketTribune);
        }else {
            ok=false;
        }
        //verifier si les champs sont vides ou trop gros
        if (nbticketGradin.getText().toString().length() < 10) {
            String valeurnbticketGradin;
            if (nbticketGradin.getText().toString().length()>0){
                //recuperer la valeur du edittext
                valeurnbticketGradin = nbticketGradin.getText().toString();
            }else{
                valeurnbticketGradin="0";
            }

                //inserer les valeurs des edittext dans le intent pour la vue suivante
            intent.putExtra(IDNBTICKETGRADIN,valeurnbticketGradin);
        }else {
            ok=false;
        }
        //verifier si les champs sont vides ou trop gros
        if (nbticketPelouse.getText().toString().length() < 10) {
            String valeurnbticketPelouse;
            //verifier si c vide par defaut c = a 0
            if (nbticketPelouse.getText().toString().length()>0){
                //recuperer la valeur du edittext
                valeurnbticketPelouse = nbticketPelouse.getText().toString();
            }else{
                valeurnbticketPelouse="0";
            }

                //inserer les valeurs des edittext dans le intent pour la vue suivante
            intent.putExtra(IDNBTICKETPELOUSE, valeurnbticketPelouse);
        }else{
            ok=false;
        }

        if (fraiseclairage.getText().toString().length()<10){
            String valeurfraiseclairage;
            if (fraiseclairage.getText().toString().length()>0){
                valeurfraiseclairage=fraiseclairage.getText().toString();
            }else{
                valeurfraiseclairage="0";
            }
            intent.putExtra(IDFRAISECLAIRAGE,valeurfraiseclairage);
        }else{
            ok=false;
        }

        if (clubreceveur.getText().toString().length()<10){
            String valeurclubreceveur;
            if (clubreceveur.getText().toString().length()>0){
                valeurclubreceveur=clubreceveur.getText().toString();
            }else{
                valeurclubreceveur="0";
            }
            intent.putExtra(IDCLUBRECEVEUR,valeurclubreceveur);
        }else{
            ok=false;
        }

        if (clubvisiteur.getText().toString().length()<10){
            String valeurclubvisiteur;
            if (clubvisiteur.getText().toString().length()>0){
                valeurclubvisiteur=clubvisiteur.getText().toString();
            }else{
                valeurclubvisiteur="0";
            }
            intent.putExtra(IDCLUBVISITEUR,valeurclubvisiteur);
        }else{
            ok=false;
        }

        intent.putExtra(IDLOCATIONTERRAIN,valeurLocation);

        //lancer lactivite  si tout est ok
        if (ok==true) {
            startActivity(intent);
        }else{
            //affichier un message d'erreur
            Toast.makeText(this,R.string.erreurSaisie,Toast.LENGTH_SHORT).show();
        }
    }

    //evenement lorsque le bouton radio est valide
    public void onRadioButtonClicked(View view){
        boolean checked= ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.radio10:
                if (checked)
                    valeurLocation="10";
                    break;
            case R.id.radio15:
                if (checked)
                    valeurLocation="15";
                    break;
        }
    }
}
