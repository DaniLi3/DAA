package com.ultrafastappconductor.ultrafastconductor.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ultrafastappconductor.ultrafastconductor.R;
import com.ultrafastappconductor.ultrafastconductor.Receiber.AceptarNotificacion;
import com.ultrafastappconductor.ultrafastconductor.Receiber.CancelarNotificacion;
import com.ultrafastappconductor.ultrafastconductor.channel.NotificationHelper;

import java.util.Map;

public class MyFirebaseMessagingClient extends FirebaseMessagingService {


    private static final int NOTIFICATION_CODE=100;
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        RemoteMessage.Notification mnotification = remoteMessage.getNotification();
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String body = data.get("body");




        //AQUI PUEDA QUE HAYA UN ERROR, EN LA VERSION DE OREO

        if (title!=null)
        {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
            {
                if (title.contains("SOLICITUD DE SERVICIO"))
                {
                    String id = data.get("idnoty");
                    showNotificationApliOreoaction(title,body,id);/*
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.cancel(2);
                    showNotificationApliOreo(title,body);*/

                }
                else
                {
                     showNotificationApliOreo(title,body);

                }

            }
            else {
                if (title.contains("SOLICITUD DE SERVICIO"))
                {
                    String id = data.get("idnoty");
                    shoNotificationActions(title,body,id);


                }
                else {
                    shoNotification(title, body);
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void shoNotification(String title, String body) {


         PendingIntent intent= PendingIntent.getActivity(getBaseContext(),0,new Intent(),PendingIntent.FLAG_ONE_SHOT);
        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationHelper notificationHelper = new NotificationHelper(getBaseContext());
        NotificationCompat.Builder builder= notificationHelper.getNotificationOld(title,body,intent,sound);
        notificationHelper.getMmanager().notify(1,builder.build());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotificationApliOreo(String title,String body) {
        PendingIntent intent= PendingIntent.getActivity(getBaseContext(),0,new Intent(),PendingIntent.FLAG_ONE_SHOT);
        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationHelper notificationHelper = new NotificationHelper(getBaseContext());
        Notification.Builder builder= notificationHelper.getNotification(title,body,intent,sound);
        notificationHelper.getMmanager().notify(1,builder.build());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void shoNotificationActions(String title, String body, String idCliente) {

        //ACEPTAR
        Intent accepaction=new Intent(this, AceptarNotificacion.class);
        accepaction.putExtra("idnoty",idCliente);

        PendingIntent acceppendingIntent=PendingIntent.getBroadcast(this,NOTIFICATION_CODE,accepaction,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Action acceptaccion= new NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "SOLICITUDES",
                acceppendingIntent

        ).build();

        //CANCELAR
        Intent cancelction=new Intent(this, CancelarNotificacion.class);
        cancelction.putExtra("idnoty",idCliente);

       /* PendingIntent cancelpendingIntent=PendingIntent.getBroadcast(this,NOTIFICATION_CODE,cancelction,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action cancelaccion= new NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "CANCELAR",
                cancelpendingIntent

        ).build();*/

        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationHelper notificationHelper = new NotificationHelper(getBaseContext());
        NotificationCompat.Builder builder= notificationHelper.getNotificationOldAction(title,body,sound ,acceptaccion);
        notificationHelper.getMmanager().notify(2,builder.build());

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void  showNotificationApliOreoaction(String title,String body,String idCliente) {

        Intent accepaction=new Intent(this, AceptarNotificacion.class);
        accepaction.putExtra("idnoty",idCliente);


        PendingIntent acceppendingIntent=PendingIntent.getBroadcast(this,NOTIFICATION_CODE,accepaction,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Action acceptaccion= new Notification.Action.Builder(
                R.mipmap.ic_launcher,
                "VER SOLICITUDES",
                acceppendingIntent

        ).build();

       /* Intent cancelaction=new Intent(this, CancelarNotificacion.class);
        cancelaction.putExtra("idnoty",idCliente);


                PendingIntent cancelpendingIntent=PendingIntent.getBroadcast(this,NOTIFICATION_CODE,cancelaction,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Action cancelactionn= new Notification.Action.Builder(
                R.mipmap.ic_launcher,
                "CANCELAR",
                cancelpendingIntent

        ).build();*/

// aqui los cancelation



        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationHelper notificationHelper = new NotificationHelper(getBaseContext());
        Notification.Builder builder= notificationHelper.getNotificationAction(title,body,sound,acceptaccion);
        notificationHelper.getMmanager().notify(2,builder.build());
    }


}
