package common.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by zipdoc on 2016. 11. 16..
 */

public abstract class FcmReceiver extends FirebaseMessagingService {

    private static final String tag = FcmReceiver.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String message = remoteMessage.getFrom(); //sender id

        if(remoteMessage == null) {
            return;
        }

        onFcmReceiver(remoteMessage, remoteMessage.getData());
    }

    protected abstract void onFcmReceiver(RemoteMessage remoteMessage, Map<String, String> data);
}
