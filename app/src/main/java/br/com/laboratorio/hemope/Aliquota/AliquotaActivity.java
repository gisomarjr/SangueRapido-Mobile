package br.com.laboratorio.hemope.Aliquota;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import br.com.laboratorio.hemope.Paciente.ListaPacientesFragment;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.View.SlidingTabLayout;

/**
 *  Atividade responsável por listar a Aliquota
 */
public class AliquotaActivity extends AppCompatActivity {

    ViewPager viewPager;
    SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliquota);
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AliquotaFragment())
                    .commit();
        }*/

        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new AliquotaPageAdapter(
                getSupportFragmentManager()));

        mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(viewPager);

        getSupportActionBar().setElevation(0);

    }

    private class AliquotaPageAdapter extends FragmentPagerAdapter {
        public AliquotaPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){

                case 0:

                    return "Aliquota";

                case 1:

                    return "Amostra";

                case 2:

                    return "Material";

                case 3:

                    return "Alocação";

            }

            return getString(position == 0 ? R.string.tab_paciente : R.string.tab_paciente);
        }

        @Override
        public Fragment getItem(int i) {
             if (i == 0) {
                 return new AliquotaFragment();
             }else{
                     return new ListaPacientesFragment();
             }
        }


        @Override
        public int getCount() {
            return 4;
        }
    }


}
