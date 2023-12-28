# ARCHIVED
This project has been archived, since it's not something I plan on updating, and wasn't intended for general use in the first place.

# Event Poster
Event poster is just a quick event bus I wrote for fun.

## Contributing
If you want to contribute, just make a pull request.
Building should just be importing the maven project into Intellij.

## Using
Create an event, it can be anything, really.
```java
public class Event {
    public String message;
    
    public Event(String message) {
    this.message = message;
    }
}
```


```java
import io.github.greyq.eventposter.EventManager;
import Event;import io.github.greyq.eventposter.receiver.Receiver;

public class Main {
    // Create an instance of the manager, doesn't have to be exactly this
    private static EventManager manager = new EventManager();

    public void main(String[] args) {
        // Register this instance of the main class
        manager.registerReceiverClass(this);

        // Mark the receiver as receiving 
        manager.setReceiving(this, true);        

        // Send an event
        manager.sendEvent(new Event("Hello"));
    }

    @Receiver
    public void event(Event event) {
        System.out.println(event.message);
    }
}
```

Running `main(String[] args)` will output:

```$xslt
Hello
```

You can give priority to an event by declaring it like
`@Receiver(priority = <long>)`
