import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/login/src/vaadin-login-form.js';
import '@vaadin/icon/src/vaadin-icon.js';
import '@polymer/iron-icon/iron-icon.js';
import '@vaadin/button/src/vaadin-button.js';

@customElement('login-view')
export class LoginView extends LitElement {
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
<vaadin-vertical-layout style="width: 100%; height: 100%; justify-content: flex-start; flex-direction: column;">
 <h1 style="align-self: center;">Doggo Lovers</h1>
 <vaadin-login-form id="loginForm" style="align-self: center;" no-forgot-password></vaadin-login-form>
 <vaadin-button theme="primary" id="btnSignIn" style="align-self: center; width: 22%;" tabindex="0">
   Sign in 
 </vaadin-button>
 <a href="/oauth2/authorization/google" id="aGoogleLogin" style="align-self: center;" router-ignore>
  <iron-icon icon="lumo:user" slot="prefix"></iron-icon>Log in with Google</a>
</vaadin-vertical-layout>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}
