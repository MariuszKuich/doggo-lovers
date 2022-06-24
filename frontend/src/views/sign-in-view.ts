import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/button/src/vaadin-button.js';
import '@vaadin/password-field/src/vaadin-password-field.js';
import '@vaadin/email-field/src/vaadin-email-field.js';
import '@vaadin/text-field/src/vaadin-text-field.js';

@customElement('sign-in-view')
export class SignInView extends LitElement {
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
<vaadin-vertical-layout style="width: 100%; height: 100%; align-items: center; justify-content: flex-start;">
 <h1 style="align-self: center;">Sign in</h1>
 <vaadin-email-field id="username" label="Email" type="email" required></vaadin-email-field>
 <vaadin-text-field label="Name" id="name" type="text" required></vaadin-text-field>
 <vaadin-password-field id="password" type="password" required label="Password"></vaadin-password-field>
 <vaadin-password-field id="passwordConfirm" type="password" label="Confirm password" invalid required></vaadin-password-field>
 <vaadin-button theme="primary" id="btnSubmit" style="margin: var(--lumo-space-m);" tabindex="0">
   Sign in 
 </vaadin-button>
 <vaadin-button theme="secondary error" id="btnCancel" tabindex="0">
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
