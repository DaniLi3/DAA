package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones;

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

public class TapSolicitudes extends AppCompatActivity {
   TabLayout tabLayout;
   ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_user);
        tabLayout=findViewById(R.id.table);
        viewPager=findViewById(R.id.view_pague);
        ArrayList<String> arrayList=new ArrayList<>();
        tabLayout.setupWithViewPager(viewPager);
        prepareViewPague(viewPager,arrayList);
        //setup view pager

    }

    private void prepareViewPague(ViewPager viewPager,ArrayList<String> arrayList) {
        //iniciar el mainfragment
         MainAdapter adapter=new MainAdapter(getSupportFragmentManager());


        adapter.addFragment(new SolicitudesDeUsuariosFrakmen(),"Pendientes");
        adapter.addFragment(new SolicitudesAceptadasFrakment(),"Aceptadas");
        adapter.addFragment(new SolicitudesCanceladasFrakment(),"Canceladas");

        viewPager.setAdapter(adapter);



    }
    private class MainAdapter extends FragmentPagerAdapter{
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
            // retornamos el tamaño de la lista

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