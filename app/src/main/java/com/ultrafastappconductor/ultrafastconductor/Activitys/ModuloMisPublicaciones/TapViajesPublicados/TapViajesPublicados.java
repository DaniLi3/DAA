package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.TapViajesPublicados;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ultrafastappconductor.ultrafastconductor.R;

import java.util.ArrayList;
import java.util.List;

public class    TapViajesPublicados extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_viajes_publicados);

        tabLayout=findViewById(R.id.table);
        viewPager=findViewById(R.id.view_pague);
        ArrayList<String> arrayList=new ArrayList<>();
        prepareViewPague(viewPager,arrayList);
        //setup view pager
        tabLayout.setupWithViewPager(viewPager);
    }
    private void prepareViewPague(ViewPager viewPager,ArrayList<String> arrayList) {
        //iniciar el mainfragment
        TapViajesPublicados.MainAdapter adapter=new TapViajesPublicados.MainAdapter(getSupportFragmentManager());
        adapter.addFragment(new FrakmenPublicados(),"Publicados");
        adapter.addFragment(new FrakmenEncurso(),"En Curso");
        adapter.addFragment(new FrakmenFinalizados(),"Concluidos");
   //     adapter.addFragment(new FrakmenCanceladosViajes(),"Vencidos");
        viewPager.setAdapter(adapter);
    }
    private class MainAdapter extends FragmentPagerAdapter {
//iniciamos el arrarlist

        ArrayList<String> arrayList=new ArrayList<>();
        List<Fragment> fragmentslist=new ArrayList<>();

        //cramos el constructor
        public void addFragment(Fragment fragment,String title)
        {
            arrayList.add(title);
            fragmentslist.add(fragment);


        }
        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            //retornamos la posocion del fragmetn

            return fragmentslist.get(position);
        }

        @Override
        public int getCount() {
            // retornamos el tama??o de la lista

            return fragmentslist.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            //retornamos el array list de posocion,,jjj
            return arrayList.get(position);
        }
    }
}