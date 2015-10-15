package br.com.laboratorio.hemope.Freezer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;
import br.com.laboratorio.hemope.View.SlidingTabLayout;

public class ListarFreezerActivity extends AppCompatActivity {

    ViewPager viewPager;
    SlidingTabLayout mSlidingTabLayout;
    static Integer countTab = 0;
    Itens itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itens = (Itens) getIntent().getExtras().getSerializable("itens");
        if(itens.isSuccess){
            countTab = itens.freezers.size();
        }else{
            Util.exibirMensagem("Erro", itens.errorMessage, this);
        }

        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new FreezerPageAdapter(
                getSupportFragmentManager()));

        mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(viewPager);

        getSupportActionBar().setElevation(1);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listar_freezer, menu);
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


    private class FreezerPageAdapter extends FragmentPagerAdapter {
        public FreezerPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {

                return itens.freezers.get(position).codigo;

        }

        @Override
        public Fragment getItem(int i) {
            Toast.makeText(getApplicationContext(),"getItem" + i,Toast.LENGTH_SHORT).show();
            return  new ListarFreezerFragment();
        }

        @Override
        public int getCount() {

            return countTab;
        }
    }

}
