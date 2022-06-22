package pl.mariuszk.viewcontroller;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.ErrorEvent;
import pl.mariuszk.enums.Breed;
import pl.mariuszk.exception.CustomErrorHandler;
import pl.mariuszk.model.entity.DogEntity;
import pl.mariuszk.model.entity.UserEntity;
import pl.mariuszk.service.DogService;
import pl.mariuszk.service.UserService;

import javax.annotation.security.PermitAll;
import java.util.Collections;

import static java.lang.Boolean.TRUE;
import static pl.mariuszk.enums.QueryParamKey.SAVED;
import static pl.mariuszk.enums.Route.MAIN_VIEW;

@Route("dogdata")
@PageTitle("Dog Data")
@PermitAll
@Tag("dog-save-edit-view")
@JsModule("./src/views/dog-save-edit-view.ts")
public class DogSaveEditView extends LitTemplate {


    @Id("name")
    private TextField name;
    @Id("breed")
    private Select<Breed> breed;
    @Id("dateOfBirth")
    private DatePicker dateOfBirth;
    @Id("btnSave")
    private Button btnSave;
    @Id("btnCancel")
    private Button btnCancel;

    Binder<DogEntity> binder = new BeanValidationBinder<>(DogEntity.class);
    private final DogEntity dogEntity;

    public DogSaveEditView(UserService userService, DogService dogService, CustomErrorHandler errorHandler) {
        UserEntity userEntity = userService.getUserFromSecurityContext();
        dogEntity = dogService.findByOwnerId(userEntity.getId()).orElse(new DogEntity());

        breed.setItems(Breed.values());
        breed.setItemLabelGenerator(Breed::getLabel);

        binder.addStatusChangeListener(e -> btnSave.setEnabled(binder.isValid()));
        binder.bindInstanceFields(this);
        binder.readBean(dogEntity);
        btnSave.addClickListener(e -> {
            try {
                binder.writeBean(dogEntity);
                dogEntity.setOwner(userEntity);
                dogService.saveOrUpdateDogData(dogEntity);
                UI.getCurrent().navigate(MAIN_VIEW.getUrl(),
                        QueryParameters.simple(Collections.singletonMap(SAVED.getValue(), TRUE.toString())));
            } catch (ValidationException ex) {
                errorHandler.error(new ErrorEvent(ex));
            }
        });
        btnCancel.addClickListener(e -> UI.getCurrent().navigate(MAIN_VIEW.getUrl()));
    }
}
