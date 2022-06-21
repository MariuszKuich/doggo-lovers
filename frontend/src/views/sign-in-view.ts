import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/button/src/vaadin-button.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/password-field/src/vaadin-password-field.js';

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
<vaadin-vertical-layout style="width: 100%; height: 100%; align-items: center; justify-content: center;">
 <h1 style="align-self: center;">Sign in</h1>
 <vaadin-text-field label="Username" id="username" minlength="3" type="text" required maxlength="20"></vaadin-text-field>
 <vaadin-password-field id="password" minlength="8" type="password" required maxlength="20" label="Password" invalid></vaadin-password-field>
 <vaadin-password-field id="passwordConfirm" minlength="8" type="password" required maxlength="20" label="Confirm password"></vaadin-password-field>
 <vaadin-button theme="primary" id="btnSubmit" style="margin: var(--lumo-space-m);" tabindex="0">
   Sign in 
 </vaadin-button>
</vaadin-vertical-layout>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}
