package pl.mariuszk.viewcontroller;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.ErrorEvent;
import pl.mariuszk.exception.CustomErrorHandler;
import pl.mariuszk.model.entity.DogEntity;
import pl.mariuszk.model.entity.UserEntity;
import pl.mariuszk.model.entity.WalkEntity;
import pl.mariuszk.model.frontend.WalkDto;
import pl.mariuszk.service.DogService;
import pl.mariuszk.service.NotificationService;
import pl.mariuszk.service.UserService;
import pl.mariuszk.service.WalkService;

import javax.annotation.security.PermitAll;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static pl.mariuszk.enums.Route.MAIN_VIEW;

@Route("scheduler")
@PageTitle("Scheduler")
@PermitAll
@Tag("scheduler-view")
@JsModule("./src/views/scheduler-view.ts")
public class SchedulerView extends LitTemplate {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");;

    @Id("walkDate")
    private DatePicker walkDate;
    @Id("walkTime")
    private TimePicker walkTime;
    @Id("hDogsName")
    private H3 hDogsName;
    @Id("assignee")
    private Select<UserEntity> assignee;
    @Id("btnSubmit")
    private Button btnSubmit;
    @Id("btnGoBack")
    private Button btnGoBack;
    @Id("gridWalksAssigned")
    private Grid<WalkEntity> gridWalksAssigned;
    @Id("gridUsersWalks")
    private Grid<WalkEntity> gridUsersWalks;

    private final WalkService walkService;

    Binder<WalkDto> binderForm = new BeanValidationBinder<>(WalkDto.class);
    private final WalkDto walkDto = new WalkDto();

    public SchedulerView(DogService dogService, UserService userService, WalkService walkService, CustomErrorHandler errorHandler,
                         NotificationService notificationService) {
        this.walkService = walkService;

        UserEntity userEntity = userService.getUserFromSecurityContext();
        DogEntity dogEntity = dogService.findByOwnerIdOrThrow(userEntity.getId());
        List<UserEntity> otherUsers = userService.getAllUsersExcept(userEntity.getId());

        assignee.setItems(otherUsers);
        assignee.setItemLabelGenerator(user -> String.format("%s (%s)", user.getName(), user.getUsername()));

        hDogsName.setText("Walking " + dogEntity.getName());

        binderForm.addStatusChangeListener(e -> btnSubmit.setEnabled(binderForm.isValid()));
        binderForm.bindInstanceFields(this);

        btnSubmit.addClickListener(e -> {
            try {
                binderForm.writeBean(walkDto);
                if (walkService.dogIsAlreadyWalkedAtGivenTime(dogEntity.getId(), walkDto.getWalkDate(), walkDto.getWalkTime())) {
                    notificationService.displayNotification("Your dog is already walked at given time",
                            NotificationVariant.LUMO_ERROR);
                    return;
                }
                if (walkService.userHasWalkScheduledAtGivenTime(walkDto.getAssignee().getId(), walkDto.getWalkDate(), walkDto.getWalkTime())) {
                    notificationService.displayNotification("This user already has a walk planned at this time",
                            NotificationVariant.LUMO_ERROR);
                    return;
                }
                walkService.save(walkDto, userEntity, dogEntity);
                updateUsersWalks(userEntity.getId());
            } catch (ValidationException ex) {
                errorHandler.error(new ErrorEvent(ex));
            }
        });
        btnGoBack.addClickListener(e -> UI.getCurrent().navigate(MAIN_VIEW.getUrl()));

        gridUsersWalks.addColumn(e ->
                String.format("%s (%s)", e.getAssignee().getName(), e.getAssignee().getUsername())).setHeader("Assignee");
        gridUsersWalks.addColumn(e -> DATE_TIME_FORMATTER.format(e.getWalkDate())).setHeader("Walk date and time");
        gridUsersWalks.addColumn(e -> e.isConfirmed() ? "Yes" : "No").setHeader("User confirmed the walk");
        gridUsersWalks.addColumn(e -> DATE_TIME_FORMATTER.format(e.getCreatedDate())).setHeader("Created at");
        gridUsersWalks.getColumns().forEach(col -> col.setAutoWidth(true));
        updateUsersWalks(userEntity.getId());

        gridWalksAssigned.addColumn(e ->
                String.format("%s (%s)", e.getAssigner().getName(), e.getAssigner().getUsername())).setHeader("Assigner");
        gridWalksAssigned.addColumn(e -> DATE_TIME_FORMATTER.format(e.getWalkDate())).setHeader("Walk date and time");
        gridWalksAssigned.addColumn(e -> e.isConfirmed() ? "Yes" : "No").setHeader("You confirmed the walk");
        gridWalksAssigned.addColumn(e -> e.getDog().getName()).setHeader("Dog to walk");
        gridWalksAssigned.addColumn(e -> DATE_TIME_FORMATTER.format(e.getCreatedDate())).setHeader("Created at");
        gridWalksAssigned.addColumn(new ComponentRenderer<>(gridItem -> {
            Button button = new Button("Confirm", e -> confirmWalk(gridItem, userEntity.getId()));
            button.setEnabled(!gridItem.isConfirmed());
            return new HorizontalLayout(button);
        })).setHeader("Confirm Walk");
        gridWalksAssigned.getColumns().forEach(col -> col.setAutoWidth(true));
        updateWalksAssigned(userEntity.getId());
    }

    private void updateUsersWalks(Long userId) {
        gridUsersWalks.setItems(walkService.getWalksAssignedByUser(userId));
    }

    private void updateWalksAssigned(Long userId) {
        gridWalksAssigned.setItems(walkService.getWalksAssignedToUser(userId));
    }

    private void confirmWalk(WalkEntity walkEntity, Long userId) {
        walkEntity.setConfirmed(true);
        walkService.updateWalk(walkEntity);
        updateWalksAssigned(userId);
    }
}
