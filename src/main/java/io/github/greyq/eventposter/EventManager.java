package io.github.greyq.eventposter;

import io.github.greyq.eventposter.receiver.ReceiverClass;
import io.github.greyq.eventposter.receiver.Receiver;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This is where events are dispatched and registered
 *
 * @author greyq
 * @since May 26, 2020
 */
public class EventManager {
    private List<ReceiverClass> receiverClasses = new CopyOnWriteArrayList<ReceiverClass>();

    /**
     * @param event dispatches this event to any receiving {@link ReceiverClass}es
     */
    public void sendEvent(Object event) {
        for (ReceiverClass receiver : this.getReceiverClasses()) {
            // Only sendEvent if the receiver is receiving
            if (receiver.isReceiving()) receiver.sendEvent(event);
        }
    }

    /**
     * this registers a {@link ReceiverClass}, however it does not make it receive
     * @param object an object to register
     */
    public void registerReceiverClass(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {

            Receiver annotation = method.getAnnotation(Receiver.class);

            if (annotation != null) {
                // I was going to add a check to see if the list already contains this
                // However, I realized that in most situations it wouldn't
                // If you need to check, you can check manually within your project, as I don't want to check the list every time I register a listener
                this.getReceiverClasses().add(new ReceiverClass(object, method, annotation.priority()));
            }
        }
        this.getReceiverClasses().sort(Comparator.comparing(ReceiverClass::getPriority));
    }

    /**
     * I'd prefer if we didn't iterate over ALL the {@link ReceiverClass}es, but I can't think of any better alternative
     *
     * this registers a {@link ReceiverClass}, however it does not make it receive
     * @param object an object to register
     */
    public void unregisterReceiverClass(Object object) {
        for (ReceiverClass receiverClass : this.getReceiverClasses()) {
            if (receiverClass.getParent().equals(object)) {
                this.getReceiverClasses().remove(receiverClass);
            }
        }
    }

    /**
     * @param receiver an object inputted
     * @return if the object is receiving events
     */
    public boolean receiving(Object receiver) {
        for (ReceiverClass receiverClass : this.getReceiverClasses()) {
            if (receiverClass.getParent().equals(receiver)) {
                return true;
            }
        }
        return false;
    }

    /**
     * again, I'd prefer if we didn't iterate over ALL the {@link ReceiverClass}es, but I can't think of any better alternative
     *
     * @param receiver the object to set receiving
     * @param state the state to set it in
     */
    public void setReceiving(Object receiver, boolean state) {
        for (ReceiverClass receiverClass : this.getReceiverClasses()) {
            if (receiverClass.getParent().equals(receiver)) {
                receiverClass.setReceiving(state);
            }
        }
    }

    // only getters / setters below

    public List<ReceiverClass> getReceiverClasses() {
        return this.receiverClasses;
    }
}
