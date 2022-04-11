package com.ultrafastappconductor.ultrafastconductor.Providers;


import com.ultrafastappconductor.ultrafastconductor.Models.FCMBody;
import com.ultrafastappconductor.ultrafastconductor.Models.IFCMResponse;
import com.ultrafastappconductor.ultrafastconductor.retrofi.IFCMApi;
import com.ultrafastappconductor.ultrafastconductor.retrofi.Retrofitcliente;

import retrofit2.Call;


public class NotificationProvider {

    private String url = "https://fcm.googleapis.com";

    public NotificationProvider() {
    }

    public Call<IFCMResponse> sendNotification(FCMBody body) {
        return Retrofitcliente.getclienteObjet(url).create(IFCMApi.class).sendt(body);
    }
}
