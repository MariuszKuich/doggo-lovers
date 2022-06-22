package pl.mariuszk.viewcontroller;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.userdetails.UserDetails;
import pl.mariuszk.security.CustomOAuthUserPrincipal;
import pl.mariuszk.service.UserService;
import pl.mariuszk.service.security.SecurityService;

import javax.annotation.security.PermitAll;
import java.util.Optional;

@Route("")
@PageTitle("Doggo Lovers")
@PermitAll
@Tag("main-view")
@JsModule("./src/views/main-view.ts")
public class MainView extends LitTemplate {

	@Id("hWelcome")
	private H1 hWelcome;
	@Id("btnLogout")
	private Button btnLogout;
	@Id("vaadinButton")
	private Button vaadinButton;
	@Id("btnScheduler")
	private Button btnScheduler;
	@Id("btnGallery")
	private Button btnGallery;
	@Id("btnVet")
	private Button btnVet;

	public MainView(SecurityService securityService, UserService userService) {
		Optional<UserDetails> loggedUser = Optional.ofNullable(securityService.getAuthenticatedUser());

		loggedUser.ifPresent(lu -> {
			if (lu instanceof CustomOAuthUserPrincipal) {
				userService.registerNewOAuthUserIfNecessary((CustomOAuthUserPrincipal) lu);
			}
		});
		loggedUser.ifPresent(lu -> hWelcome.setText("Welcome, " + lu.getUsername() + "!"));

		btnLogout.addClickListener(e -> loggedUser.ifPresent(lu -> securityService.logout()));
    }
}
