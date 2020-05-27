package io.github.greyq.eventposter.receiver;

import io.github.greyq.eventposter.EventManager;

import java.lang.reflect.Method;

/**
 * This is a class used to store and keep track of methods and their parent classes
 *
 * @author greyq
 * @since May 26, 2020
 */
public class ReceiverClass {

    /**
     * determines the receiver is listening
     * checked in {@link EventManager}, if false it does not dispatch the event
     */
    private boolean receiving;

    /**
     * The method that this class is connected with
     * {@link EventManager} assigns this variable when a class is registered
     */
    private Method method;

    /**
     * the parent object of the method
     */
    private Object parent;

    /**
     * the priority of the event
     * assigned in declaration of {@link Receiver}
     */
    private final long priority;

    public ReceiverClass(Object parent, Method method, long priority) {
        this.parent = parent;
        this.method = method;
        this.priority = priority;
    }


    /**
     * shouldn't ever throw these, but this makes it happy
     * @param event any object that you decide to pass as an event
     */
    public void sendEvent(Object event) {
        Class<?>[] parameters = this.method.getParameterTypes();
        if (parameters.length == 1) {
            if (event.getClass().isAssignableFrom(parameters[0])) {
                try {
                    this.method.invoke(this.parent, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Just getters and setters, nothing fun down here

    public Method getMethod() {
        return this.method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public boolean isReceiving() {
        return this.receiving;
    }

    public void setReceiving(boolean receiving) {
        this.receiving = receiving;
    }

    public Object getParent() {
        return this.parent;
    }

    public long getPriority() {
        return this.priority;
    }
}
