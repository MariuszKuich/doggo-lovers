package pl.mariuszk.viewcontroller;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.mariuszk.model.entity.DogEntity;
import pl.mariuszk.model.entity.UserEntity;
import pl.mariuszk.security.CustomOAuthUserPrincipal;
import pl.mariuszk.security.ICustomUserDetails;
import pl.mariuszk.service.DogService;
import pl.mariuszk.service.NotificationService;
import pl.mariuszk.service.UserService;
import pl.mariuszk.service.security.SecurityService;

import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static pl.mariuszk.enums.QueryParamKey.SAVED;
import static pl.mariuszk.enums.Route.DOG_DATA;

@Route("")
@PageTitle("Doggo Lovers")
@PermitAll
@Tag("main-view")
@JsModule("./src/views/main-view.ts")
public class MainView extends LitTemplate implements BeforeEnterObserver {

	@Id("hWelcome")
	private H1 hWelcome;
	@Id("btnLogout")
	private Button btnLogout;
	@Id("btnEditSave")
	private Button btnEditSave;
	@Id("btnScheduler")
	private Button btnScheduler;
	@Id("btnGallery")
	private Button btnGallery;
	@Id("btnVet")
	private Button btnVet;

	private final SecurityService securityService;
	private final UserService userService;
	private final NotificationService notificationService;

	public MainView(SecurityService securityService, UserService userService, DogService dogService,
					NotificationService notificationService) {
		this.securityService = securityService;
		this.userService = userService;
		this.notificationService = notificationService;

		UserEntity userEntity = getUserInfo();
		Optional<DogEntity> usersDog = dogService.findByOwnerId(userEntity.getId());

		btnScheduler.setEnabled(usersDog.isPresent());
		btnGallery.setEnabled(usersDog.isPresent());
		btnVet.setEnabled(usersDog.isPresent());

		hWelcome.setText("Hello, " + userEntity.getName() + "!");
		btnLogout.addClickListener(e -> securityService.logout());
		btnEditSave.addClickListener(e -> UI.getCurrent().navigate(DOG_DATA.getUrl()));
    }

	private UserEntity getUserInfo() {
		ICustomUserDetails loggedUser = securityService.getAuthenticatedUser();

		if (loggedUser instanceof CustomOAuthUserPrincipal) {
			userService.registerNewOAuthUserIfNecessary((CustomOAuthUserPrincipal) loggedUser);
		}

		return userService.getUserByUsername(loggedUser.getUsername());
	}


	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		Map<String, List<String>> params = beforeEnterEvent.getLocation()
				.getQueryParameters()
				.getParameters();

		if (params.containsKey(SAVED.getValue())) {
			notificationService.displayNotification("Dog data saved", NotificationVariant.LUMO_SUCCESS);
		}
	}
}
