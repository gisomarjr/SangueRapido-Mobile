package br.com.laboratorio.hemope.Paciente;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.View.SlidingTabLayout;


public class PacienteActivity extends AppCompatActivity implements AoClicarNoPacienteListener {

    ViewPager viewPager;
    SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new PacientePageAdapter(
                getSupportFragmentManager()));

        mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(viewPager);

        getSupportActionBar().setElevation(0);

    }

    @Override
    public void onClick(Paciente paciente) {

        if (getResources().getBoolean(R.bool.isPhone)) {
            Intent it = new Intent(this, DetalhePacienteActivity.class);
            it.putExtra("paciente", paciente);
            startActivity(it);

        } else {
            DetalhePacienteFragment detalhePacienteFragment =
                    DetalhePacienteFragment.novaInstancia(paciente);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, detalhePacienteFragment, "detalhe")
                    .commit();
        }

    }


    private class PacientePageAdapter extends FragmentPagerAdapter {
        public PacientePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(position == 0 ? R.string.tab_paciente : R.string.tab_paciente);
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0){
                return new ListaPacientesFragment();
            } else {
                return new ListaPacientesFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
