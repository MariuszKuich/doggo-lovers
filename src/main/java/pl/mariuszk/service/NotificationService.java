package pl.mariuszk.service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	public void displayNotification(String message, NotificationVariant variant) {
		if(UI.getCurrent() != null) {
			UI.getCurrent().access(() -> {
				Notification notification = Notification.show(message);
				notification.addThemeVariants(variant);
			});
		}
	}
}
