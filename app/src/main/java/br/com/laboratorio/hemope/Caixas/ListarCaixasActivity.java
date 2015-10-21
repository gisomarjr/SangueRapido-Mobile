package br.com.laboratorio.hemope.Caixas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;
import br.com.laboratorio.hemope.View.SlidingTabLayout;

public class ListarCaixasActivity extends AppCompatActivity {

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
            countTab = itens.caixas.size();
        }else{
            Util.exibirMensagem("Erro", itens.errorMessage, this);
        }

        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new CaixaPageAdapter(
                getSupportFragmentManager()));

        mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(viewPager);

        getSupportActionBar().setElevation(1);

    }

    private class CaixaPageAdapter extends FragmentPagerAdapter {
        public CaixaPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {

                return ""+itens.caixas.get(position).idCaixa;

        }

        @Override
        public Fragment getItem(int i) {

            return new ListarCaixasFragment().newInstance(i,itens);

        }
        @Override
        public int getCount() {

            return countTab;
        }
    }

}
