package com.recette.lfm.lfmrecette;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    //action lorqu'on clic sur le bouton championnat
    public void championnatAction(View view)
    {
        Intent intent= new Intent(this,ChampionnatActivity.class);
        startActivity(intent);
    }
    //action lorqu'on clic sur le bouton coupeM1phase
    public void coupeMartiniqueUnphaseAction(View view) {
        Intent intent= new Intent(this,CoupeMartiniqueUnActivity.class);
        startActivity(intent);
    }
    //action lorqu'on clic sur le bouton coupeM2Phase
    public void coupeMartiniqueDeuxphaseAction(View view) {
        Intent intent= new Intent(this,CoupeMartiniqueDeuxActivity.class);
        startActivity(intent);
    }
    //action lorsqu 'on clic sur le bouton coupe de france
    public void coupeFranceAction(View view){
        Intent intent=new Intent(this,CoupeFranceActivity.class);
        startActivity(intent);
    }

}
