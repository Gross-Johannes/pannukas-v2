package net.teymm.pannukas.config;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.context.ApplicationListener;
import org.springframework.validation.ObjectError;

public class StartupFailureListener implements ApplicationListener<ApplicationFailedEvent> {

    private static <T extends Throwable> T findCause(Throwable throwable, Class<T> type) {
        Throwable current = throwable;

        while (current != null) {
            if (type.isInstance(current)) {
                return type.cast(current);
            }

            current = current.getCause();
        }

        return null;
    }

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        BindValidationException exception = findCause(event.getException(), BindValidationException.class);

        if (exception == null) {
            return;
        }

        System.err.println();
        System.err.println("CONFIGURATION ERROR");
        System.err.println();

        for (ObjectError error : exception.getValidationErrors().getAllErrors()) {
            System.err.println("- " + error.getDefaultMessage());
        }

        System.err.println();
        System.err.println("Application failed to start due to invalid configuration.");
        System.err.println();

        System.exit(1);
    }
}
