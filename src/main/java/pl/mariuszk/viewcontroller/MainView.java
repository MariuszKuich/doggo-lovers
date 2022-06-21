package pl.mariuszk.viewcontroller;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.userdetails.UserDetails;
import pl.mariuszk.service.security.SecurityService;

import javax.annotation.security.PermitAll;
import java.util.Optional;

@Route("")
@PageTitle("Doggo Lovers")
@PermitAll
@Tag("main-view")
@JsModule("./src/views/main-view.ts")
public class MainView extends LitTemplate {

	@Id("lblUsername")
	private TextField lblUsername;
	@Id("btnLogout")
	private Button btnLogout;

    public MainView(SecurityService securityService) {
		Optional<UserDetails> loggedUser = Optional.ofNullable(securityService.getAuthenticatedUser());

		loggedUser.ifPresent(lu -> lblUsername.setValue(lu.getUsername()));

		btnLogout.addClickListener(e -> {
			loggedUser.ifPresent(lu -> securityService.logout());
		});
    }
}
