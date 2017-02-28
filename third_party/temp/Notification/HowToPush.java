
public class HowToPush {

    private Context mContext;


    private void pushNotification(String title){
        /*
        MainActivity is the activity we want to open.
         */
        Intent intent = new Intent(mContext, MainActivity.class);

        /*
        Have to set action to fix bug in case display multiple notifications,
        click on old notification but action coming after is that of the newest one.
          */
        intent.setAction(Long.toString(System.currentTimeMillis()));

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        makeNewNoti(intent, title);
    }

    private void makeNewNoti(Intent intent, String title) {
        NotificationManager notificationManager = (NotificationManager) mCOntext.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.BigTextStyle notiStyle = new NotificationCompat.BigTextStyle();
        notiStyle.bigText(title);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        /*
        Today, we should support the notification in Android Lolipop. https://material.google.com/patterns/notifications.html
        Each notification should contain two pictures : a small icon and a large icon.
         */
        Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.ic_parrot);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_parrot2)
                        .setColor(getResources().getColor(R.color.app_color_2))
                        .setLargeIcon(picture)
                        .setTicker(getString(R.string.app_name))
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText(title).setAutoCancel(true)
                        .setStyle(notiStyle);

        builder.setContentIntent(pendingIntent);
        /*
        In this case, we want each time pushing notification will make a new notification without replacing any notification.
         */

        // Create an unique id for notification
        Random random = new Random();
        int notificationID = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(notificationID, builder.build());
    }

}