package project.common.messages;

/**
 * Created by rikachka on 21.11.15.
 */
public interface Protocol {
    Message decode(byte[] bytes);

    byte[] encode(Message message);
}
