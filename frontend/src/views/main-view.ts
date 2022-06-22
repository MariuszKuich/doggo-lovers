import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/horizontal-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';

@customElement('main-view')
export class MainView extends LitElement {
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
 <h1 id="hWelcome">Hello, {placeholder}!</h1>
 <vaadin-vertical-layout id="btnDogData" style="align-items: center;" theme="spacing-xs">
  <vaadin-horizontal-layout theme="spacing-s">
   <vaadin-button id="vaadinButton" style="padding: var(--lumo-space-l);" tabindex="0" theme="secondary">
    <iron-icon icon="lumo:plus" slot="prefix"></iron-icon>Add/Edit Dog Data 
   </vaadin-button>
   <vaadin-button style="padding: var(--lumo-space-l);" tabindex="0" id="btnScheduler" theme="secondary success">
    <iron-icon icon="lumo:calendar" slot="prefix"></iron-icon>Schedule Walking The Dog
   </vaadin-button>
  </vaadin-horizontal-layout>
  <vaadin-horizontal-layout theme="spacing-s">
   <vaadin-button style="padding: var(--lumo-space-l);" tabindex="0" id="btnGallery" theme="secondary error">
    <iron-icon icon="lumo:eye" slot="prefix"></iron-icon>Dogs Gallery
   </vaadin-button>
   <vaadin-button style="padding: var(--lumo-space-l);" tabindex="0" id="btnVet" theme="secondary contrast">
    <iron-icon icon="lumo:search" slot="prefix"></iron-icon>History Of Visits To The Vet
   </vaadin-button>
  </vaadin-horizontal-layout>
 </vaadin-vertical-layout>
 <vaadin-button theme="primary" id="btnLogout" tabindex="0" style="margin: var(--lumo-space-xl);">
   Logout 
 </vaadin-button>
</vaadin-vertical-layout>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}
