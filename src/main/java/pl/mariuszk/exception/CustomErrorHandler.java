package pl.mariuszk.exception;

import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mariuszk.service.NotificationService;

import static com.vaadin.flow.component.notification.NotificationVariant.LUMO_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomErrorHandler implements ErrorHandler {

	private final NotificationService notificationService;

	@Override
	public void error(ErrorEvent errorEvent) {
		String errMessage = errorEvent.getThrowable().getMessage();
		log.error("Something wrong happened: {}", errMessage);
		notificationService.displayNotification("An internal error has occurred: " + errMessage, LUMO_ERROR);
	}
}