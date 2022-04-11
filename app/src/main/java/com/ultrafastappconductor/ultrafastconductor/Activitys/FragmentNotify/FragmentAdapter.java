package com.ultrafastappconductor.ultrafastconductor.Activitys.FragmentNotify;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.SolicitudesAceptadasFrakment;
import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.SolicitudesCanceladasFrakment;
import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.SolicitudesDeUsuariosFrakmen;

public class FragmentAdapter  extends FragmentStateAdapter {


    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1: return new SolicitudesAceptadasFrakment();
            case 2: return new SolicitudesCanceladasFrakment();

        }

        return new SolicitudesDeUsuariosFrakmen();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
