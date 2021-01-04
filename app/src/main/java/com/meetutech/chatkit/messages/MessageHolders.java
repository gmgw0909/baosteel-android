package com.meetutech.chatkit.messages;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.meetutech.baosteel.R;
import com.meetutech.baosteel.ui.chat.ChatGroupActivity;
import com.meetutech.baosteel.widget.ImageShowClickListener;
import com.meetutech.chatkit.commons.ImageLoader;
import com.meetutech.chatkit.commons.MediaManager;
import com.meetutech.chatkit.commons.ViewHolder;
import com.meetutech.chatkit.commons.models.IMessage;
import com.meetutech.chatkit.commons.models.MessageContentType;
import com.meetutech.chatkit.utils.DateFormatter;
import com.meetutech.chatkit.utils.RoundedImageView;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Created by troy379 on 31.03.17.
 */
public class MessageHolders {

  private static final short VIEW_TYPE_DATE_HEADER = 130;
  private static final short VIEW_TYPE_TEXT_MESSAGE = 131;
  private static final short VIEW_TYPE_IMAGE_MESSAGE = 132;
  private static final short VIEW_TYPE_AUDIO_MESSAGE = 133;
  private static final short VIEW_TYPE_VIDEO_MESSAGE = 134;
  private Class<? extends ViewHolder<Date>> dateHeaderHolder;
  private int dateHeaderLayout;

  private HolderConfig<IMessage> incomingTextConfig;
  private HolderConfig<IMessage> outcomingTextConfig;
  private HolderConfig<MessageContentType.Image> incomingImageConfig;
  private HolderConfig<MessageContentType.Image> outcomingImageConfig;
  private HolderConfig<MessageContentType.Audio> incomingAudioConfig;
  private HolderConfig<MessageContentType.Audio> outcomingAudioConfig;
  private HolderConfig<MessageContentType.Video> incomingVideoConfig;
  private HolderConfig<MessageContentType.Video> outcomingVideoConfig;

  private List<ContentTypeConfig> customContentTypes = new ArrayList<>();
  private ContentChecker contentChecker;

  public MessageHolders() {
    this.dateHeaderHolder = DefaultDateHeaderViewHolder.class;
    this.dateHeaderLayout = R.layout.item_date_header;

    this.incomingTextConfig = new HolderConfig<>(DefaultIncomingTextMessageViewHolder.class,
        R.layout.item_incoming_text_message);
    this.outcomingTextConfig = new HolderConfig<>(DefaultOutcomingTextMessageViewHolder.class,
        R.layout.item_outcoming_text_message);
    this.incomingImageConfig = new HolderConfig<>(DefaultIncomingImageMessageViewHolder.class,
        R.layout.item_incoming_image_message);
    this.outcomingImageConfig = new HolderConfig<>(DefaultOutcomingImageMessageViewHolder.class,
        R.layout.item_outcoming_image_message);
    this.incomingAudioConfig = new HolderConfig<>(DefaultIncomingAudioMessageViewHolder.class,
        R.layout.item_incoming_audio_message);
    this.outcomingAudioConfig = new HolderConfig<>(DefaultOutcomingAudioMessageViewHolder.class,
        R.layout.item_outcoming_audio_message);
    this.incomingVideoConfig = new HolderConfig<>(DefaultIncomingVideoMessageViewHolder.class,
        R.layout.item_incoming_video_message);
    this.outcomingVideoConfig = new HolderConfig<>(DefaultOutcomingVideoMessageViewHolder.class,
        R.layout.item_outcoming_video_message);
  }

  /**
   * Sets both of custom view holder class and layout resource for incoming text message.
   *
   * @param holder holder class.
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setIncomingTextConfig(
      @NonNull Class<? extends BaseMessageViewHolder<? extends IMessage>> holder,
      @LayoutRes int layout) {
    this.incomingTextConfig.holder = holder;
    this.incomingTextConfig.layout = layout;
    return this;
  }

  /**
   * Sets custom view holder class for incoming text message.
   *
   * @param holder holder class.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setIncomingTextHolder(
      @NonNull Class<? extends BaseMessageViewHolder<? extends IMessage>> holder) {
    this.incomingTextConfig.holder = holder;
    return this;
  }

  /**
   * Sets custom layout resource for incoming text message.
   *
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setIncomingTextLayout(@LayoutRes int layout) {
    this.incomingTextConfig.layout = layout;
    return this;
  }

  /**
   * Sets both of custom view holder class and layout resource for outcoming text message.
   *
   * @param holder holder class.
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setOutcomingTextConfig(
      @NonNull Class<? extends BaseMessageViewHolder<? extends IMessage>> holder,
      @LayoutRes int layout) {
    this.outcomingTextConfig.holder = holder;
    this.outcomingTextConfig.layout = layout;
    return this;
  }

  /**
   * Sets custom view holder class for outcoming text message.
   *
   * @param holder holder class.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setOutcomingTextHolder(
      @NonNull Class<? extends BaseMessageViewHolder<? extends IMessage>> holder) {
    this.outcomingTextConfig.holder = holder;
    return this;
  }

  /**
   * Sets custom layout resource for outcoming text message.
   *
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setOutcomingTextLayout(@LayoutRes int layout) {
    this.outcomingTextConfig.layout = layout;
    return this;
  }

  /**
   * Sets both of custom view holder class and layout resource for incoming image message.
   *
   * @param holder holder class.
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setIncomingImageConfig(
      @NonNull Class<? extends BaseMessageViewHolder<? extends MessageContentType.Image>> holder,
      @LayoutRes int layout) {
    this.incomingImageConfig.holder = holder;
    this.incomingImageConfig.layout = layout;
    return this;
  }

  /**
   * Sets custom view holder class for incoming image message.
   *
   * @param holder holder class.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setIncomingImageHolder(
      @NonNull Class<? extends BaseMessageViewHolder<? extends MessageContentType.Image>> holder) {
    this.incomingImageConfig.holder = holder;
    return this;
  }

  /**
   * Sets custom layout resource for incoming image message.
   *
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setIncomingImageLayout(@LayoutRes int layout) {
    this.incomingImageConfig.layout = layout;
    return this;
  }

  /**
   * Sets both of custom view holder class and layout resource for outcoming image message.
   *
   * @param holder holder class.
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setOutcomingImageConfig(
      @NonNull Class<? extends BaseMessageViewHolder<? extends MessageContentType.Image>> holder,
      @LayoutRes int layout) {
    this.outcomingImageConfig.holder = holder;
    this.outcomingImageConfig.layout = layout;
    return this;
  }

  /**
   * Sets custom view holder class for outcoming image message.
   *
   * @param holder holder class.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setOutcomingImageHolder(
      @NonNull Class<? extends BaseMessageViewHolder<? extends MessageContentType.Image>> holder) {
    this.outcomingImageConfig.holder = holder;
    return this;
  }

  /**
   * Sets custom layout resource for outcoming image message.
   *
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setOutcomingImageLayout(@LayoutRes int layout) {
    this.outcomingImageConfig.layout = layout;
    return this;
  }

  /**
   * Sets both of custom view holder class and layout resource for incoming audio message.
   *
   * @param holder holder class.
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setIncomingAudioConfig(
      @NonNull Class<? extends BaseMessageViewHolder<? extends MessageContentType.Audio>> holder,
      @LayoutRes int layout) {
    this.incomingAudioConfig.holder = holder;
    this.incomingAudioConfig.layout = layout;
    return this;
  }

  /**
   * Sets custom view holder class for incoming audio message.
   *
   * @param holder holder class.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setIncomingAudioHolder(
      @NonNull Class<? extends BaseMessageViewHolder<? extends MessageContentType.Audio>> holder) {
    this.incomingAudioConfig.holder = holder;
    return this;
  }

  /**
   * Sets custom layout resource for incoming audio message.
   *
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setIncomingAudioLayout(@LayoutRes int layout) {
    this.incomingAudioConfig.layout = layout;
    return this;
  }

  /**
   * Sets both of custom view holder class and layout resource for outcoming audio message.
   *
   * @param holder holder class.
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setOutcomingAudioConfig(
      @NonNull Class<? extends BaseMessageViewHolder<? extends MessageContentType.Audio>> holder,
      @LayoutRes int layout) {
    this.outcomingAudioConfig.holder = holder;
    this.outcomingAudioConfig.layout = layout;
    return this;
  }

  /**
   * Sets custom view holder class for outcoming audio message.
   *
   * @param holder holder class.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setOutcomingAudioHolder(
      @NonNull Class<? extends BaseMessageViewHolder<? extends MessageContentType.Audio>> holder) {
    this.outcomingAudioConfig.holder = holder;
    return this;
  }

  /**
   * Sets custom layout resource for outcoming image message.
   *
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setOutcomingAudioLayout(@LayoutRes int layout) {
    this.outcomingAudioConfig.layout = layout;
    return this;
  }

  /**
   * Video Config
   */
  /**
   * Sets both of custom view holder class and layout resource for incoming Video message.
   *
   * @param holder holder class.
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setIncomingVideoConfig(
      @NonNull Class<? extends BaseMessageViewHolder<? extends MessageContentType.Video>> holder,
      @LayoutRes int layout) {
    this.incomingVideoConfig.holder = holder;
    this.incomingVideoConfig.layout = layout;
    return this;
  }

  /**
   * Sets custom view holder class for incoming audio message.
   *
   * @param holder holder class.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setIncomingVideoHolder(
      @NonNull Class<? extends BaseMessageViewHolder<? extends MessageContentType.Video>> holder) {
    this.incomingVideoConfig.holder = holder;
    return this;
  }

  /**
   * Sets custom layout resource for incoming Video message.
   *
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setIncomingVideoLayout(@LayoutRes int layout) {
    this.incomingVideoConfig.layout = layout;
    return this;
  }

  /**
   * Sets both of custom view holder class and layout resource for outcoming Video message.
   *
   * @param holder holder class.
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setOutcomingVideoConfig(
      @NonNull Class<? extends BaseMessageViewHolder<? extends MessageContentType.Video>> holder,
      @LayoutRes int layout) {
    this.outcomingVideoConfig.holder = holder;
    this.outcomingVideoConfig.layout = layout;
    return this;
  }

  /**
   * Sets custom view holder class for outcoming Video message.
   *
   * @param holder holder class.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setOutcomingVideoHolder(
      @NonNull Class<? extends BaseMessageViewHolder<? extends MessageContentType.Video>> holder) {
    this.outcomingVideoConfig.holder = holder;
    return this;
  }

  /**
   * Sets custom layout resource for outcoming Video message.
   *
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setOutcomingVideoLayout(@LayoutRes int layout) {
    this.outcomingVideoConfig.layout = layout;
    return this;
  }
  //END

  /**
   * Sets both of custom view holder class and layout resource for date header.
   *
   * @param holder holder class.
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setDateHeaderConfig(@NonNull Class<? extends ViewHolder<Date>> holder,
      @LayoutRes int layout) {
    this.dateHeaderHolder = holder;
    this.dateHeaderLayout = layout;
    return this;
  }

  /**
   * Sets custom view holder class for date header.
   *
   * @param holder holder class.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setDateHeaderHolder(@NonNull Class<? extends ViewHolder<Date>> holder) {
    this.dateHeaderHolder = holder;
    return this;
  }

  /**
   * Sets custom layout reource for date header.
   *
   * @param layout layout resource.
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public MessageHolders setDateHeaderLayout(@LayoutRes int layout) {
    this.dateHeaderLayout = layout;
    return this;
  }

  /**
   * Registers custom content type (e.g. multimedia, events etc.)
   *
   * @param type unique id for content type
   * @param holder holder class for incoming and outcoming messages
   * @param incomingLayout layout resource for incoming message
   * @param outcomingLayout layout resource for outcoming message
   * @param contentChecker {@link ContentChecker} for registered type
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public <TYPE extends MessageContentType> MessageHolders registerContentType(byte type,
      @NonNull Class<? extends BaseMessageViewHolder<TYPE>> holder, @LayoutRes int incomingLayout,
      @LayoutRes int outcomingLayout, @NonNull ContentChecker contentChecker) {

    return registerContentType(type, holder, incomingLayout, holder, outcomingLayout,
        contentChecker);
  }

  /**
   * Registers custom content type (e.g. multimedia, events etc.)
   *
   * @param type unique id for content type
   * @param incomingHolder holder class for incoming message
   * @param outcomingHolder holder class for outcoming message
   * @param incomingLayout layout resource for incoming message
   * @param outcomingLayout layout resource for outcoming message
   * @param contentChecker {@link ContentChecker} for registered type
   * @return {@link MessageHolders} for subsequent configuration.
   */
  public <TYPE extends MessageContentType> MessageHolders registerContentType(byte type,
      @NonNull Class<? extends BaseMessageViewHolder<TYPE>> incomingHolder,
      @LayoutRes int incomingLayout,
      @NonNull Class<? extends BaseMessageViewHolder<TYPE>> outcomingHolder,
      @LayoutRes int outcomingLayout, @NonNull ContentChecker contentChecker) {

    if (type == 0) {
      throw new IllegalArgumentException("content type must be greater or less than '0'!");
    }

    customContentTypes.add(
        new ContentTypeConfig<>(type, new HolderConfig<>(incomingHolder, incomingLayout),
            new HolderConfig<>(outcomingHolder, outcomingLayout)));
    this.contentChecker = contentChecker;
    return this;
  }

    /*
    * INTERFACES
    * */

  /**
   * The interface, which contains logic for checking the availability of content.
   */
  public interface ContentChecker<MESSAGE extends IMessage> {

    /**
     * Checks the availability of content.
     *
     * @param message current message in list.
     * @param type content type, for which content availability is determined.
     * @return weather the message has content for the current message.
     */
    boolean hasContentFor(MESSAGE message, byte type);
  }

    /*
    * PRIVATE METHODS
    * */

  ViewHolder getHolder(ViewGroup parent, int viewType, MessagesListStyle messagesListStyle) {
    switch (viewType) {
      case VIEW_TYPE_DATE_HEADER:
        return getHolder(parent, dateHeaderLayout, dateHeaderHolder, messagesListStyle);
      case VIEW_TYPE_TEXT_MESSAGE:
        return getHolder(parent, incomingTextConfig, messagesListStyle);
      case -VIEW_TYPE_TEXT_MESSAGE:
        return getHolder(parent, outcomingTextConfig, messagesListStyle);
      case VIEW_TYPE_IMAGE_MESSAGE:
        return getHolder(parent, incomingImageConfig, messagesListStyle);
      case -VIEW_TYPE_IMAGE_MESSAGE:
        return getHolder(parent, outcomingImageConfig, messagesListStyle);
      case VIEW_TYPE_AUDIO_MESSAGE:
        return getHolder(parent, incomingAudioConfig, messagesListStyle);
      case -VIEW_TYPE_AUDIO_MESSAGE:
        return getHolder(parent, outcomingAudioConfig, messagesListStyle);
      case VIEW_TYPE_VIDEO_MESSAGE:
        return getHolder(parent, incomingVideoConfig, messagesListStyle);
      case -VIEW_TYPE_VIDEO_MESSAGE:
        return getHolder(parent, outcomingVideoConfig, messagesListStyle);
      default:
        for (ContentTypeConfig typeConfig : customContentTypes) {
          if (Math.abs(typeConfig.type) == Math.abs(viewType)) {
            if (viewType > 0) {
              return getHolder(parent, typeConfig.incomingConfig, messagesListStyle);
            } else {
              return getHolder(parent, typeConfig.outcomingConfig, messagesListStyle);
            }
          }
        }
    }
    return null;
  }

  @SuppressWarnings("unchecked") void bind(ViewHolder holder, Object item, boolean isSelected,
      ImageLoader imageLoader, View.OnClickListener onMessageClickListener,
      View.OnLongClickListener onMessageLongClickListener,
      DateFormatter.Formatter dateHeadersFormatter) {

    if (item instanceof IMessage) {
      ((MessageHolders.BaseMessageViewHolder) holder).isSelected = isSelected;
      ((MessageHolders.BaseMessageViewHolder) holder).imageLoader = imageLoader;
      holder.itemView.setOnLongClickListener(onMessageLongClickListener);
      holder.itemView.setOnClickListener(onMessageClickListener);
    } else if (item instanceof Date) {
      ((MessageHolders.DefaultDateHeaderViewHolder) holder).dateHeadersFormatter =
          dateHeadersFormatter;
    }

    holder.onBind(item);
  }

  int getViewType(Object item, String senderId) {
    boolean isOutcoming = false;
    int viewType;

    if (item instanceof IMessage) {
      IMessage message = (IMessage) item;
      isOutcoming = message.getUser().getId().contentEquals(senderId);
      viewType = getContentViewType(message);
    } else {
      viewType = VIEW_TYPE_DATE_HEADER;
    }

    return isOutcoming ? viewType * -1 : viewType;
  }

  private ViewHolder getHolder(ViewGroup parent, HolderConfig holderConfig,
      MessagesListStyle style) {
    return getHolder(parent, holderConfig.layout, holderConfig.holder, style);
  }

  private <HOLDER extends ViewHolder> ViewHolder getHolder(ViewGroup parent, @LayoutRes int layout,
      Class<HOLDER> holderClass, MessagesListStyle style) {

    View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    try {
      Constructor<HOLDER> constructor = holderClass.getDeclaredConstructor(View.class);
      constructor.setAccessible(true);
      HOLDER holder = constructor.newInstance(v);
      if (holder instanceof DefaultMessageViewHolder && style != null) {
        ((DefaultMessageViewHolder) holder).applyStyle(style);
      }
      return holder;
    } catch (Exception e) {
      return null;
    }
  }

  @SuppressWarnings("unchecked") private short getContentViewType(IMessage message) {
    if (message instanceof MessageContentType.Image
        && ((MessageContentType.Image) message).getImageUrl() != null) {
      return VIEW_TYPE_IMAGE_MESSAGE;
    }

    if (message instanceof MessageContentType.Audio
        && ((MessageContentType.Audio) message).getAudioUrl() != null) {
      return VIEW_TYPE_AUDIO_MESSAGE;
    }
    if (message instanceof MessageContentType.Video
        && ((MessageContentType.Video) message).getVideoUrl() != null) {
      return VIEW_TYPE_VIDEO_MESSAGE;
    }

    if (message instanceof MessageContentType) {
      for (int i = 0; i < customContentTypes.size(); i++) {
        ContentTypeConfig config = customContentTypes.get(i);
        if (contentChecker == null) {
          throw new IllegalArgumentException(
              "ContentChecker cannot be null when using custom content types!");
        }
        boolean hasContent = contentChecker.hasContentFor(message, config.type);
        if (hasContent) return config.type;
      }
    }

    return VIEW_TYPE_TEXT_MESSAGE;
  }

    /*
    * HOLDERS
    * */

  /**
   * The base class for view holders for incoming and outcoming message.
   * You can extend it to create your own holder in conjuction with custom layout or even using
   * default layout.
   */
  public static abstract class BaseMessageViewHolder<MESSAGE extends IMessage>
      extends ViewHolder<MESSAGE> {

    boolean isSelected;

    /**
     * Callback for implementing images loading in message list
     */
    ImageLoader imageLoader;

    public BaseMessageViewHolder(View itemView) {
      super(itemView);
    }

    /**
     * Returns whether is item selected
     *
     * @return weather is item selected.
     */
    public boolean isSelected() {
      return isSelected;
    }

    /**
     * Returns weather is selection mode enabled
     *
     * @return weather is selection mode enabled.
     */
    public boolean isSelectionModeEnabled() {
      return MessagesListAdapter.isSelectionModeEnabled;
    }

    /**
     * Getter for {@link #imageLoader}
     *
     * @return image loader interface.
     */
    public ImageLoader getImageLoader() {
      return imageLoader;
    }

    protected void configureLinksBehavior(final TextView text) {
      text.setLinksClickable(false);
      text.setMovementMethod(new LinkMovementMethod() {
        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
          boolean result = false;
          if (!MessagesListAdapter.isSelectionModeEnabled) {
            result = super.onTouchEvent(widget, buffer, event);
          }
          itemView.onTouchEvent(event);
          return result;
        }
      });
    }
  }

  /**
   * Default view holder implementation for incoming text message
   */
  public static class IncomingTextMessageViewHolder<MESSAGE extends IMessage>
      extends BaseIncomingMessageViewHolder<MESSAGE> {

    protected ViewGroup bubble;
    protected TextView text;

    public IncomingTextMessageViewHolder(View itemView) {
      super(itemView);
      bubble = (ViewGroup) itemView.findViewById(R.id.bubble);
      text = (TextView) itemView.findViewById(R.id.messageText);
    }

    @Override public void onBind(MESSAGE message) {
      super.onBind(message);
      if (bubble != null) {
        bubble.setSelected(isSelected());
      }

      if (text != null) {
        text.setText(message.getText());
      }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN) @Override
    public void applyStyle(MessagesListStyle style) {
      super.applyStyle(style);
      if (bubble != null) {
        bubble.setPadding(style.getIncomingDefaultBubblePaddingLeft(),
            style.getIncomingDefaultBubblePaddingTop(),
            style.getIncomingDefaultBubblePaddingRight(),
            style.getIncomingDefaultBubblePaddingBottom());
        bubble.setBackground(style.getIncomingBubbleDrawable());
      }

      if (text != null) {
        text.setTextColor(style.getIncomingTextColor());
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingTextSize());
        text.setTypeface(text.getTypeface(), style.getIncomingTextStyle());
        text.setAutoLinkMask(style.getTextAutoLinkMask());
        text.setLinkTextColor(style.getIncomingTextLinkColor());
        configureLinksBehavior(text);
      }
    }
  }

  /**
   * Default view holder implementation for outcoming text message
   */
  public static class OutcomingTextMessageViewHolder<MESSAGE extends IMessage>
      extends BaseOutcomingMessageViewHolder<MESSAGE> {

    protected ViewGroup bubble;
    protected TextView text;

    public OutcomingTextMessageViewHolder(View itemView) {
      super(itemView);
      bubble = (ViewGroup) itemView.findViewById(R.id.bubble);
      text = (TextView) itemView.findViewById(R.id.messageText);
    }

    @Override public void onBind(MESSAGE message) {
      super.onBind(message);
      if (bubble != null) {
        bubble.setSelected(isSelected());
      }

      if (text != null) {
        text.setText(message.getText());
      }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN) @Override
    public final void applyStyle(MessagesListStyle style) {
      super.applyStyle(style);
      if (bubble != null) {
        bubble.setPadding(style.getOutcomingDefaultBubblePaddingLeft(),
            style.getOutcomingDefaultBubblePaddingTop(),
            style.getOutcomingDefaultBubblePaddingRight(),
            style.getOutcomingDefaultBubblePaddingBottom());
        bubble.setBackground(style.getOutcomingBubbleDrawable());
      }

      if (text != null) {
        text.setTextColor(style.getOutcomingTextColor());
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getOutcomingTextSize());
        text.setTypeface(text.getTypeface(), style.getOutcomingTextStyle());
        text.setAutoLinkMask(style.getTextAutoLinkMask());
        text.setLinkTextColor(style.getOutcomingTextLinkColor());
        configureLinksBehavior(text);
      }
    }
  }

  /**
   * Default view holder implementation for incoming image message
   */
  public static class IncomingImageMessageViewHolder<MESSAGE extends MessageContentType.Image>
      extends BaseIncomingMessageViewHolder<MESSAGE> {

    protected ImageView image;
    protected View imageOverlay;

    public IncomingImageMessageViewHolder(View itemView) {
      super(itemView);
      image = (ImageView) itemView.findViewById(R.id.image);
      imageOverlay = itemView.findViewById(R.id.imageOverlay);

      if (image != null && image instanceof RoundedImageView) {
        ((RoundedImageView) image).setCorners(R.dimen.message_bubble_corners_radius,
            R.dimen.message_bubble_corners_radius, R.dimen.message_bubble_corners_radius, 0);
      }
    }

    @Override public void onBind(MESSAGE message) {
      super.onBind(message);
      if (image != null && imageLoader != null) {
        imageLoader.loadImage(image, message.getImageUrl());
      }
      image.setOnClickListener(new ImageShowClickListener(message.getImageUrl(),ChatGroupActivity.instance));
      if (imageOverlay != null) {
        imageOverlay.setSelected(isSelected());
      }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN) @Override
    public final void applyStyle(MessagesListStyle style) {
      super.applyStyle(style);
      if (time != null) {
        time.setTextColor(style.getIncomingImageTimeTextColor());
        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingImageTimeTextSize());
        time.setTypeface(time.getTypeface(), style.getIncomingImageTimeTextStyle());
      }

      if (imageOverlay != null) {
        imageOverlay.setBackground(style.getIncomingImageOverlayDrawable());
      }
    }
  }

  /**
   * Default view holder implementation for outcoming image message
   */
  public static class OutcomingImageMessageViewHolder<MESSAGE extends MessageContentType.Image>
      extends BaseOutcomingMessageViewHolder<MESSAGE> {

    protected ImageView image;
    protected View imageOverlay;

    public OutcomingImageMessageViewHolder(View itemView) {
      super(itemView);
      image = (ImageView) itemView.findViewById(R.id.image);
      imageOverlay = itemView.findViewById(R.id.imageOverlay);

      if (image != null && image instanceof RoundedImageView) {
        ((RoundedImageView) image).setCorners(R.dimen.message_bubble_corners_radius,
            R.dimen.message_bubble_corners_radius, 0, R.dimen.message_bubble_corners_radius);
      }
    }

    @Override public void onBind(MESSAGE message) {
      super.onBind(message);
      if (image != null && imageLoader != null) {
        imageLoader.loadImage(image, message.getImageUrl());
      }
      image.setOnClickListener(new ImageShowClickListener(message.getImageUrl(),ChatGroupActivity.instance));
      if (imageOverlay != null) {
        imageOverlay.setSelected(isSelected());
      }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN) @Override
    public final void applyStyle(MessagesListStyle style) {
      super.applyStyle(style);
      if (time != null) {
        time.setTextColor(style.getOutcomingImageTimeTextColor());
        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getOutcomingImageTimeTextSize());
        time.setTypeface(time.getTypeface(), style.getOutcomingImageTimeTextStyle());
      }

      if (imageOverlay != null) {
        imageOverlay.setBackground(style.getOutcomingImageOverlayDrawable());
      }
    }
  }

  /**
   * Default view holder implementation for incoming audio message
   */
  public static class IncomingAudioMessageViewHolder<MESSAGE extends MessageContentType.Audio>
      extends BaseIncomingMessageViewHolder<MESSAGE> {

    protected View mainView;
    protected TextView messageText;
    protected ViewGroup bubble;

    public IncomingAudioMessageViewHolder(View itemView) {
      super(itemView);
      mainView = itemView.findViewById(R.id.mainView);
      bubble = (ViewGroup) itemView.findViewById(R.id.bubble);
      messageText = (TextView) itemView.findViewById(R.id.messageText);
    }

    @Override public void onBind(MESSAGE message) {
      super.onBind(message);
      if (mainView != null) {
        mainView.setOnClickListener(new AudioPlayClickListener(message.getAudioUrl()));
      }
      if (messageText != null) {
        messageText.setText(String.format("{fa-music}  %s",
            MediaManager.getDuration(ChatGroupActivity.instance, message.getAudioUrl())));
      }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN) @Override
    public final void applyStyle(MessagesListStyle style) {
      super.applyStyle(style);
      if (bubble != null) {
        bubble.setPadding(style.getIncomingDefaultBubblePaddingLeft(),
            style.getIncomingDefaultBubblePaddingTop(),
            style.getIncomingDefaultBubblePaddingRight(),
            style.getIncomingDefaultBubblePaddingBottom());
        bubble.setBackground(style.getIncomingBubbleDrawable());
      }

      if (messageText != null) {
        messageText.setTextColor(style.getIncomingTextColor());
        messageText.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingTextSize());
        messageText.setTypeface(messageText.getTypeface(), style.getIncomingTextStyle());
        messageText.setAutoLinkMask(style.getTextAutoLinkMask());
        messageText.setLinkTextColor(style.getIncomingTextLinkColor());
        configureLinksBehavior(messageText);
      }
    }
  }

  /**
   * Default view holder implementation for outcoming audio message
   */
  public static class OutcomingAudioMessageViewHolder<MESSAGE extends MessageContentType.Audio>
      extends BaseOutcomingMessageViewHolder<MESSAGE> {

    protected View mainView;
    protected TextView messageText;
    protected ViewGroup bubble;

    public OutcomingAudioMessageViewHolder(View itemView) {
      super(itemView);
      mainView = itemView.findViewById(R.id.mainView);
      bubble = (ViewGroup) itemView.findViewById(R.id.bubble);
      messageText = (TextView) itemView.findViewById(R.id.messageText);
    }

    @Override public void onBind(MESSAGE message) {
      super.onBind(message);
      if (mainView != null) {
        mainView.setOnClickListener(new AudioPlayClickListener(message.getAudioUrl()));
      }
      if (messageText != null) {
        messageText.setText(String.format("{fa-music}  %s",
            MediaManager.getDuration(ChatGroupActivity.instance, message.getAudioUrl())));
        //messageText.setText("  ");

      }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN) @Override
    public final void applyStyle(MessagesListStyle style) {
      super.applyStyle(style);
      if (bubble != null) {
        bubble.setPadding(style.getOutcomingDefaultBubblePaddingLeft(),
            style.getOutcomingDefaultBubblePaddingTop(),
            style.getOutcomingDefaultBubblePaddingRight(),
            style.getOutcomingDefaultBubblePaddingBottom());
        bubble.setBackground(style.getOutcomingBubbleDrawable());
      }

      if (messageText != null) {
        messageText.setTextColor(style.getOutcomingTextColor());
        messageText.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getOutcomingTextSize());
        messageText.setTypeface(messageText.getTypeface(), style.getOutcomingTextStyle());
        messageText.setAutoLinkMask(style.getTextAutoLinkMask());
        messageText.setLinkTextColor(style.getOutcomingTextLinkColor());
        configureLinksBehavior(messageText);
      }
    }
  }


  /**
   * Default view holder implementation for incoming Video message
   */
  public static class IncomingVideoMessageViewHolder<MESSAGE extends MessageContentType.Video>
      extends BaseIncomingMessageViewHolder<MESSAGE> {

    protected View mainView;
    protected TextView messageText;
    protected ViewGroup bubble;

    public IncomingVideoMessageViewHolder(View itemView) {
      super(itemView);
      mainView = itemView.findViewById(R.id.mainView);
      bubble = (ViewGroup) itemView.findViewById(R.id.bubble);
      messageText = (TextView) itemView.findViewById(R.id.messageText);
    }

    @Override public void onBind(MESSAGE message) {
      super.onBind(message);
      if (mainView != null) {
        mainView.setOnClickListener(new VideoPlayClickListener(message.getVideoUrl()));
      }
      if (messageText != null) {
        messageText.setText(String.format("{fa-video-camera}  %s",
            MediaManager.getDuration(ChatGroupActivity.instance, message.getVideoUrl())));
      }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN) @Override
    public final void applyStyle(MessagesListStyle style) {
      super.applyStyle(style);
      if (bubble != null) {
        bubble.setPadding(style.getIncomingDefaultBubblePaddingLeft(),
            style.getIncomingDefaultBubblePaddingTop(),
            style.getIncomingDefaultBubblePaddingRight(),
            style.getIncomingDefaultBubblePaddingBottom());
        bubble.setBackground(style.getIncomingBubbleDrawable());
      }

      if (messageText != null) {
        messageText.setTextColor(style.getIncomingTextColor());
        messageText.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingTextSize());
        messageText.setTypeface(messageText.getTypeface(), style.getIncomingTextStyle());
        messageText.setAutoLinkMask(style.getTextAutoLinkMask());
        messageText.setLinkTextColor(style.getIncomingTextLinkColor());
        configureLinksBehavior(messageText);
      }
    }
  }

  /**
   * Default view holder implementation for outcoming Video message
   */
  public static class OutcomingVideoMessageViewHolder<MESSAGE extends MessageContentType.Video>
      extends BaseOutcomingMessageViewHolder<MESSAGE> {

    protected View mainView;
    protected TextView messageText;
    protected ViewGroup bubble;

    public OutcomingVideoMessageViewHolder(View itemView) {
      super(itemView);
      mainView = itemView.findViewById(R.id.mainView);
      bubble = (ViewGroup) itemView.findViewById(R.id.bubble);
      messageText = (TextView) itemView.findViewById(R.id.messageText);
    }

    @Override public void onBind(MESSAGE message) {
      super.onBind(message);
      if (mainView != null) {
        mainView.setOnClickListener(new VideoPlayClickListener(message.getVideoUrl()));
      }
      if (messageText != null) {
        messageText.setText(String.format("{fa-video-camera}  %s",
            MediaManager.getDuration(ChatGroupActivity.instance, message.getVideoUrl())));
        //messageText.setText("  ");

      }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN) @Override
    public final void applyStyle(MessagesListStyle style) {
      super.applyStyle(style);
      if (bubble != null) {
        bubble.setPadding(style.getOutcomingDefaultBubblePaddingLeft(),
            style.getOutcomingDefaultBubblePaddingTop(),
            style.getOutcomingDefaultBubblePaddingRight(),
            style.getOutcomingDefaultBubblePaddingBottom());
        bubble.setBackground(style.getOutcomingBubbleDrawable());
      }

      if (messageText != null) {
        messageText.setTextColor(style.getOutcomingTextColor());
        messageText.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getOutcomingTextSize());
        messageText.setTypeface(messageText.getTypeface(), style.getOutcomingTextStyle());
        messageText.setAutoLinkMask(style.getTextAutoLinkMask());
        messageText.setLinkTextColor(style.getOutcomingTextLinkColor());
        configureLinksBehavior(messageText);
      }
    }
  }


  /**
   * Default view holder implementation for date header
   */
  public static class DefaultDateHeaderViewHolder extends ViewHolder<Date>
      implements DefaultMessageViewHolder {

    protected TextView text;
    protected String dateFormat;
    protected DateFormatter.Formatter dateHeadersFormatter;

    public DefaultDateHeaderViewHolder(View itemView) {
      super(itemView);
      text = (TextView) itemView.findViewById(R.id.messageText);
    }

    @Override public void onBind(Date date) {
      if (text != null) {
        String formattedDate = null;
        if (dateHeadersFormatter != null) formattedDate = dateHeadersFormatter.format(date);
        text.setText(
            formattedDate == null ? DateFormatter.format(date, dateFormat) : formattedDate);
      }
    }

    @Override public void applyStyle(MessagesListStyle style) {
      if (text != null) {
        text.setTextColor(style.getDateHeaderTextColor());
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getDateHeaderTextSize());
        text.setTypeface(text.getTypeface(), style.getDateHeaderTextStyle());
        text.setPadding(style.getDateHeaderPadding(), style.getDateHeaderPadding(),
            style.getDateHeaderPadding(), style.getDateHeaderPadding());
      }
      dateFormat = style.getDateHeaderFormat();
      dateFormat =
          dateFormat == null ? DateFormatter.Template.STRING_DAY_MONTH_YEAR.get() : dateFormat;
    }
  }

  /**
   * Base view holder for incoming message
   */
  public abstract static class BaseIncomingMessageViewHolder<MESSAGE extends IMessage>
      extends BaseMessageViewHolder<MESSAGE> implements DefaultMessageViewHolder {

    protected TextView time;
    protected ImageView userAvatar;
    protected TextView messageUserName;

    public BaseIncomingMessageViewHolder(View itemView) {
      super(itemView);
      time = (TextView) itemView.findViewById(R.id.messageTime);
      userAvatar = (ImageView) itemView.findViewById(R.id.messageUserAvatar);
      messageUserName = (TextView) itemView.findViewById(R.id.messageUserName);
    }

    @Override public void onBind(MESSAGE message) {
      if (time != null) {
        time.setText(DateFormatter.format(message.getCreatedAt(), DateFormatter.Template.TIME));
      }

      if (userAvatar != null) {
        boolean isAvatarExists =
            imageLoader != null && message.getUser().getAvatar() != null && !message.getUser()
                .getAvatar()
                .isEmpty();

        userAvatar.setVisibility(isAvatarExists ? View.VISIBLE : View.GONE);
        if (isAvatarExists) {
          imageLoader.loadImage(userAvatar, message.getUser().getAvatar());
        }
      }
      if (messageUserName != null) {
        messageUserName.setText(message.getUser().getName());
      }
    }

    @Override public void applyStyle(MessagesListStyle style) {
      if (time != null) {
        time.setTextColor(style.getIncomingTimeTextColor());
        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getIncomingTimeTextSize());
        time.setTypeface(time.getTypeface(), style.getIncomingTimeTextStyle());
      }

      if (userAvatar != null) {
        userAvatar.getLayoutParams().width = style.getIncomingAvatarWidth();
        userAvatar.getLayoutParams().height = style.getIncomingAvatarHeight();
      }
    }
  }

  /**
   * Base view holder for outcoming message
   */
  public abstract static class BaseOutcomingMessageViewHolder<MESSAGE extends IMessage>
      extends BaseMessageViewHolder<MESSAGE> implements DefaultMessageViewHolder {

    protected TextView time;

    public BaseOutcomingMessageViewHolder(View itemView) {
      super(itemView);
      time = (TextView) itemView.findViewById(R.id.messageTime);
    }

    @Override public void onBind(MESSAGE message) {
      if (time != null) {
        time.setText(DateFormatter.format(message.getCreatedAt(), DateFormatter.Template.TIME));
      }
    }

    @Override public void applyStyle(MessagesListStyle style) {
      if (time != null) {
        time.setTextColor(style.getOutcomingTimeTextColor());
        time.setTextSize(TypedValue.COMPLEX_UNIT_PX, style.getOutcomingTimeTextSize());
        time.setTypeface(time.getTypeface(), style.getOutcomingTimeTextStyle());
      }
    }
  }

    /*
    * DEFAULTS
    * */

  interface DefaultMessageViewHolder {
    void applyStyle(MessagesListStyle style);
  }

  private static class DefaultIncomingTextMessageViewHolder
      extends IncomingTextMessageViewHolder<IMessage> {

    public DefaultIncomingTextMessageViewHolder(View itemView) {
      super(itemView);
    }
  }

  private static class DefaultOutcomingTextMessageViewHolder
      extends OutcomingTextMessageViewHolder<IMessage> {

    public DefaultOutcomingTextMessageViewHolder(View itemView) {
      super(itemView);
    }
  }

  private static class DefaultIncomingImageMessageViewHolder
      extends IncomingImageMessageViewHolder<MessageContentType.Image> {

    public DefaultIncomingImageMessageViewHolder(View itemView) {
      super(itemView);
    }
  }

  private static class DefaultOutcomingImageMessageViewHolder
      extends OutcomingImageMessageViewHolder<MessageContentType.Image> {

    public DefaultOutcomingImageMessageViewHolder(View itemView) {
      super(itemView);
    }
  }

  private static class DefaultIncomingAudioMessageViewHolder
      extends IncomingAudioMessageViewHolder<MessageContentType.Audio> {

    public DefaultIncomingAudioMessageViewHolder(View itemView) {
      super(itemView);
    }
  }

  private static class DefaultOutcomingAudioMessageViewHolder
      extends OutcomingAudioMessageViewHolder<MessageContentType.Audio> {

    public DefaultOutcomingAudioMessageViewHolder(View itemView) {
      super(itemView);
    }
  }

  private static class DefaultIncomingVideoMessageViewHolder
      extends IncomingVideoMessageViewHolder<MessageContentType.Video> {

    public DefaultIncomingVideoMessageViewHolder(View itemView) {
      super(itemView);
    }
  }

  private static class DefaultOutcomingVideoMessageViewHolder
      extends OutcomingVideoMessageViewHolder<MessageContentType.Video> {

    public DefaultOutcomingVideoMessageViewHolder(View itemView) {
      super(itemView);
    }
  }

  private static class ContentTypeConfig<TYPE extends MessageContentType> {

    private byte type;
    private HolderConfig<TYPE> incomingConfig;
    private HolderConfig<TYPE> outcomingConfig;

    private ContentTypeConfig(byte type, HolderConfig<TYPE> incomingConfig,
        HolderConfig<TYPE> outcomingConfig) {

      this.type = type;
      this.incomingConfig = incomingConfig;
      this.outcomingConfig = outcomingConfig;
    }
  }

  private class HolderConfig<T extends IMessage> {

    Class<? extends BaseMessageViewHolder<? extends T>> holder;
    int layout;

    HolderConfig(Class<? extends BaseMessageViewHolder<? extends T>> holder, int layout) {
      this.holder = holder;
      this.layout = layout;
    }
  }
}
