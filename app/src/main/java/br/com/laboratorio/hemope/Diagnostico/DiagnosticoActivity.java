package br.com.laboratorio.hemope.Diagnostico;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import br.com.laboratorio.hemope.Model.Diagnostico;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.View.SlidingTabLayout;


public class DiagnosticoActivity extends AppCompatActivity implements AoClicarNoDiagnosticoListener {

    ViewPager viewPager;
//    SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new DiagnosticoPageAdapter(
                getSupportFragmentManager()));

        /*mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(viewPager);*/

        getSupportActionBar().setElevation(0);

    }

    @Override
    public void onClick(Diagnostico diagnostico) {

        if (getResources().getBoolean(R.bool.isPhone)) {
            Intent it = new Intent(this, DetalheDiagnosticoActivity.class);
            it.putExtra("diagnostico", diagnostico);
            startActivity(it);

        } else {
            DetalheDiagnosticoFragment detalheDiagnosticoFragment =
                    DetalheDiagnosticoFragment.novaInstancia(diagnostico);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, detalheDiagnosticoFragment, "detalhe")
                    .commit();
        }

    }


    private class DiagnosticoPageAdapter extends FragmentPagerAdapter {
        public DiagnosticoPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(position == 0 ? R.string.tab_diagnostico : R.string.tab_diagnostico);
        }

        @Override
        public Fragment getItem(int i) {
           // if (i == 0){
                return new ListaDiagnosticosFragment();
            //} /*else {
              //  return new ListaDiagnosticosFragment();
        }


        @Override
        public int getCount() {
            return 1;
        }
    }
}
