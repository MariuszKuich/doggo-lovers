import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/select/src/vaadin-select.js';
import '@vaadin/date-picker/src/vaadin-date-picker.js';
import '@vaadin/button/src/vaadin-button.js';

@customElement('dog-save-edit-view')
export class DogSaveEditView extends LitElement {
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
<vaadin-vertical-layout style="width: 100%; height: 100%; justify-content: flex-start; align-items: center;">
 <h1>Add or edit data about your dog</h1>
 <vaadin-text-field id="name" type="text" required label="Name"></vaadin-text-field>
 <vaadin-select id="breed" label="Breed" required></vaadin-select>
 <vaadin-date-picker id="dateOfBirth" label="Date of birth" required></vaadin-date-picker>
 <vaadin-button id="btnSave" style="margin-top: var(--lumo-space-l);" tabindex="0" theme="primary">
  Confirm
 </vaadin-button>
 <vaadin-button id="btnCancel" tabindex="0" theme="error secondary">
  Cancel
 </vaadin-button>
</vaadin-vertical-layout>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}
