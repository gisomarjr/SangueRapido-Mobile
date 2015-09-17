package br.com.laboratorio.hemope.Amostra;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import br.com.laboratorio.hemope.R;


public class AmostraActivity extends AppCompatActivity {

    ViewPager viewPager;
//    SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new AmostraPageAdapter(
                getSupportFragmentManager()));

        /*mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(viewPager);*/

        getSupportActionBar().setElevation(0);

    }

   /* @Override
    public void onClick(Amostra amostra) {

        if (getResources().getBoolean(R.bool.isPhone)) {
            Intent it = new Intent(this, DetalheAmostraActivity.class);
            it.putExtra("amostra", amostra);
            startActivity(it);
            Log.e("status","phone");
        } else {
            Log.e("status","tablet");
            DetalheAmostraFragment detalheAmostraFragment =
                    DetalheAmostraFragment.novaInstancia(amostra);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, detalheAmostraFragment, "detalhe")
                    .commit();
        }

    }*/


    private class AmostraPageAdapter extends FragmentPagerAdapter {
        public AmostraPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
           // return getString(position == 0 ? "Amostras" : "Amostras");

            return "Amostras";
        }

        @Override
        public Fragment getItem(int i) {
           // if (i == 0){
                return new ListaAmostrasFragment();
            //} /*else {
              //  return new ListaAmostrasFragment();
        }


        @Override
        public int getCount() {
            return 1;
        }
    }
}
