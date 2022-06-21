package pl.mariuszk.viewcontroller;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pl.mariuszk.service.NotificationService;

import java.util.List;
import java.util.Map;

import static pl.mariuszk.enums.QueryParamKeys.CREATED;
import static pl.mariuszk.enums.QueryParamKeys.ERROR;
import static pl.mariuszk.enums.Route.SIGN_IN;

@Route("login")
@PageTitle("Login")
@Tag("login-view")
@JsModule("./src/views/login-view.ts")
public class LoginView extends LitTemplate implements BeforeEnterObserver {

    @Id("loginForm")
    private LoginForm loginForm;
	@Id("btnSignIn")
	private Button btnSignIn;
	@Id("btnLogInGoogle")
	private Button btnLogInGoogle;

	private final NotificationService notificationService;

	public LoginView(NotificationService notificationService) {
		this.notificationService = notificationService;

        loginForm.setAction("login");
		btnSignIn.addClickListener(e -> UI.getCurrent().navigate(SIGN_IN.getUrl()));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		Map<String, List<String>> params = beforeEnterEvent.getLocation()
				.getQueryParameters()
				.getParameters();

		if (params.containsKey(ERROR.getValue())) {
			loginForm.setError(true);
		}
        if (params.containsKey(CREATED.getValue())) {
			notificationService.displayNotification("Signed in successfully", NotificationVariant.LUMO_SUCCESS);
        }
    }
}
