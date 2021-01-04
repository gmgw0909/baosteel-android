package com.meetutech.chatkit.commons.models;

/*
 * Created by troy379 on 28.03.17.
 */

import com.meetutech.chatkit.messages.MessageHolders;

/**
 * Interface used to mark messages as custom content types. For its representation see {@link MessageHolders}
 */

public interface MessageContentType extends IMessage {

    interface Image extends IMessage {
        String getImageUrl();
    }

    interface Audio extends IMessage {
        String getAudioUrl();
    }

    interface Video extends IMessage {
        String getVideoUrl();
    }

}
