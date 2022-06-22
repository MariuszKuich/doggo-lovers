package pl.mariuszk.viewcontroller;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import pl.mariuszk.exception.CustomErrorHandler;
import pl.mariuszk.model.frontend.UserDto;
import pl.mariuszk.service.NotificationService;
import pl.mariuszk.service.UserService;

import java.util.Collections;

import static java.lang.Boolean.TRUE;
import static pl.mariuszk.enums.QueryParamKeys.CREATED;
import static pl.mariuszk.enums.Route.LOG_IN;

@Route("signin")
@PageTitle("Sign in")
@AnonymousAllowed
@Tag("sign-in-view")
@JsModule("./src/views/sign-in-view.ts")
public class SignInView extends LitTemplate {

	@Id("username")
	private TextField username;
	@Id("name")
	private TextField name;
	@Id("password")
	private PasswordField password;
	@Id("btnSubmit")
	private Button btnSubmit;
	@Id("passwordConfirm")
	private PasswordField passwordConfirm;

	Binder<UserDto> binder = new BeanValidationBinder<>(UserDto.class);
	private final UserDto userDto = new UserDto();

	public SignInView(UserService userService, CustomErrorHandler errorHandler, NotificationService notificationService) {
		binder.addStatusChangeListener(e -> btnSubmit.setEnabled(binder.isValid()));

		binder.bindInstanceFields(this);

        btnSubmit.addClickListener(e -> {
			if (!passwordConfirm.getValue().equals(password.getValue())) {
				notificationService.displayNotification("Passwords do not match", NotificationVariant.LUMO_CONTRAST);
				return;
			}
			try {
				binder.writeBean(userDto);
				userService.registerNewUser(userDto);
				UI.getCurrent().navigate(LOG_IN.getUrl(),
						QueryParameters.simple(Collections.singletonMap(CREATED.getValue(), TRUE.toString())));
			} catch (Exception ex) {
				errorHandler.error(new ErrorEvent(ex));
			}
		});
    }

}
