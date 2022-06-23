package pl.mariuszk.viewcontroller;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;
import pl.mariuszk.exception.CustomErrorHandler;
import pl.mariuszk.model.entity.PhotoEntity;
import pl.mariuszk.model.entity.UserEntity;
import pl.mariuszk.service.NotificationService;
import pl.mariuszk.service.PhotoService;
import pl.mariuszk.service.UserService;

import javax.annotation.security.PermitAll;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static pl.mariuszk.enums.Route.MAIN_VIEW;

@Route("gallery")
@PageTitle("Dogs Photos")
@PermitAll
@Tag("gallery-view")
@JsModule("./src/views/gallery-view.ts")
public class GalleryView extends LitTemplate {

    private static final int PHOTO_MAX_WIDTH_PIXELS = 600;
    private static final int PHOTO_MAX_HEIGHT_PIXELS = 600;
    private static final int GALLERY_COLUMNS_COUNT = 2;

    @Id("uploadFile")
    private Upload uploadFile;
    @Id("btnGoBack")
    private Button btnGoBack;
    @Id("vLayoutPhotos")
    private VerticalLayout vLayoutPhotos;
    @Id("btnSend")
    private Button btnSend;

    private final PhotoService photoService;

    private byte[] imageBytes;

    public GalleryView(UserService userService, PhotoService photoService, CustomErrorHandler errorHandler,
                       NotificationService notificationService) {
        this.photoService = photoService;

        UserEntity userEntity = userService.getUserFromSecurityContext();

        btnSend.setEnabled(false);

        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        uploadFile.setReceiver(buffer);
        uploadFile.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        uploadFile.addSucceededListener(event -> {
            try {
                imageBytes = IOUtils.toByteArray(buffer.getInputStream(event.getFileName()));
                btnSend.setEnabled(true);
            } catch (IOException ex) {
                errorHandler.error(new ErrorEvent(ex));
                btnSend.setEnabled(false);
            }
        });

        btnSend.addClickListener(e -> {
            if (imageBytes == null) {
                return;
            }
            PhotoEntity photoEntity = PhotoEntity.builder()
                    .image(imageBytes)
                    .user(userEntity)
                    .build();
            photoService.save(photoEntity);

            notificationService.displayNotification("Dog uploaded successfully", NotificationVariant.LUMO_SUCCESS);
            btnSend.setEnabled(false);
            uploadFile.clearFileList();

            loadPhotos();
        });

        btnGoBack.addClickListener(e -> UI.getCurrent().navigate(MAIN_VIEW.getUrl()));

        loadPhotos();
    }



    private void loadPhotos() {
        vLayoutPhotos.removeAll();

        List<PhotoEntity> photos = photoService.getAllPhotos();

        int currentRowIndex = 0;
        int currentColumnIndex = 0;

        vLayoutPhotos.addComponentAtIndex(currentRowIndex, new HorizontalLayout());
        for (PhotoEntity photo : photos) {
            if (currentColumnIndex == GALLERY_COLUMNS_COUNT) {
                vLayoutPhotos.addComponentAtIndex(++currentRowIndex, new HorizontalLayout());
                currentColumnIndex = 0;
            }
            Image image = getImage(photo);
            HorizontalLayout hLayout = (HorizontalLayout) vLayoutPhotos.getComponentAt(currentRowIndex);
            hLayout.addComponentAtIndex(currentColumnIndex++, image);
        }
    }

    private Image getImage(PhotoEntity photo) {
        StreamResource streamResource = new StreamResource(photo.getId() + ".png", () -> new ByteArrayInputStream(photo.getImage()));

        Image image = new Image(streamResource, "Dog" + photo.getId());
        image.setMaxHeight(PHOTO_MAX_HEIGHT_PIXELS, Unit.PIXELS);
        image.setMaxWidth(PHOTO_MAX_WIDTH_PIXELS, Unit.PIXELS);

        return image;
    }
}
