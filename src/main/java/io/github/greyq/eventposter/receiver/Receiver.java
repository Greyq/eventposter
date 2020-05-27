package io.github.greyq.eventposter.receiver;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to note a method within a {@link ReceiverClass} so we can dispatch it
 *
 * @author greyq
 * @since May 26, 2020
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Receiver {
    /**
     * @return the priority of the {@link ReceiverClass}
     * By doing this, I can send the events in the order of priority
     */
    long priority() default 0;
}
