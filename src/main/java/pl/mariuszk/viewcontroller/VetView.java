package pl.mariuszk.viewcontroller;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import pl.mariuszk.model.entity.DogEntity;
import pl.mariuszk.model.entity.UserEntity;
import pl.mariuszk.model.entity.VetHistoryEntity;
import pl.mariuszk.service.DogService;
import pl.mariuszk.service.UserService;
import pl.mariuszk.service.VetHistoryService;

import javax.annotation.security.PermitAll;

import static pl.mariuszk.enums.Route.MAIN_VIEW;

@Route("vet")
@PageTitle("Vet History")
@PermitAll
@Tag("vet-view")
@JsModule("./src/views/vet-view.ts")
public class VetView extends LitTemplate {

	@Id("visitDate")
	private DatePicker visitDate;
	@Id("cause")
	private TextField cause;
	@Id("recommendations")
	private TextArea recommendations;
	@Id("hHeader")
	private H3 h3;
	@Id("btnAdd")
	private Button btnAdd;
	@Id("gridHistory")
	private Grid<VetHistoryEntity> gridHistory;
	@Id("btnGoBack")
	private Button btnGoBack;

	private final VetHistoryService vetHistoryService;

	Binder<VetHistoryEntity> binder = new BeanValidationBinder<>(VetHistoryEntity.class);
	private VetHistoryEntity vetHistoryEntity;

	public VetView(VetHistoryService vetHistoryService, UserService userService, DogService dogService, ErrorHandler errorHandler) {
		this.vetHistoryService = vetHistoryService;

		UserEntity userEntity = userService.getUserFromSecurityContext();
		DogEntity dogEntity = dogService.findByOwnerIdOrThrow(userEntity.getId());

		h3.setText(dogEntity.getName() + "'s visits");

		binder.addStatusChangeListener(e -> btnAdd.setEnabled(binder.isValid()));
		binder.bindInstanceFields(this);

		gridHistory.addColumn(VetHistoryEntity::getVisitDate).setHeader("Visit date").setWidth("15%");
		gridHistory.addColumn(VetHistoryEntity::getCause).setHeader("Visit cause").setWidth("20%");
		gridHistory.addColumn(VetHistoryEntity::getRecommendations).setHeader("Recommendations").setWidth("65%");
		gridHistory.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
		updateVisits(dogEntity.getId());

		btnAdd.addClickListener(e -> {
			try {
				vetHistoryEntity = new VetHistoryEntity();
				binder.writeBean(vetHistoryEntity);
				vetHistoryEntity.setDog(dogEntity);
				vetHistoryService.addVisit(vetHistoryEntity);
				updateVisits(dogEntity.getId());
				clearControls();
			} catch (ValidationException ex) {
				errorHandler.error(new ErrorEvent(ex));
			}
		});
		btnGoBack.addClickListener(e -> UI.getCurrent().navigate(MAIN_VIEW.getUrl()));
    }

	private void updateVisits(Long dogId) {
		gridHistory.setItems(vetHistoryService.getDogsVisits(dogId));
	}

	private void clearControls() {
		visitDate.clear();
		visitDate.setInvalid(false);
		cause.clear();
		cause.setInvalid(false);
		recommendations.clear();
		recommendations.setInvalid(false);
	}
}
