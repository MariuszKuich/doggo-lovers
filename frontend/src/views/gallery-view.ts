import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/upload/src/vaadin-upload.js';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/button/src/vaadin-button.js';

@customElement('gallery-view')
export class GalleryView extends LitElement {
  static get styles() {
    return css`
      :host {
          display: block;
          height: 100%;
      }
      `;
  }

  render() {
    return html`
<vaadin-vertical-layout style="width: 100%; height: 100%; align-items: center;">
 <h1>Dogs photos</h1>
 <vaadin-upload id="uploadFile" max-file-size="5 000 000" max-files="1" style="flex-shrink: 0;"></vaadin-upload>
 <vaadin-button theme="primary" id="btnSend" style="margin-top: var(--lumo-space-l);" tabindex="0">
   Send Photo 
 </vaadin-button>
 <vaadin-button theme="secondary error" id="btnGoBack" style="margin: var(--lumo-space-l);" tabindex="0">
   Go Back 
 </vaadin-button>
 <vaadin-vertical-layout id="vLayoutPhotos" style="width: 70%; flex-grow: 0; align-self: center; flex-shrink: 0; height: 60%; align-items: center;"></vaadin-vertical-layout>
</vaadin-vertical-layout>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}
