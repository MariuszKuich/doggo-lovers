import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/date-picker/src/vaadin-date-picker.js';
import '@vaadin/time-picker/src/vaadin-time-picker.js';
import '@vaadin/select/src/vaadin-select.js';
import '@vaadin/button/src/vaadin-button.js';
import '@vaadin/grid/src/vaadin-grid.js';

@customElement('scheduler-view')
export class SchedulerView extends LitElement {
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
 <h1>Schedule walking your dog</h1>
 <h3 id="hDogsName">Walking {placeholder}</h3>
 <vaadin-date-picker label="Walk date" placeholder="Pick a date" id="walkDate" required></vaadin-date-picker>
 <vaadin-time-picker id="walkTime" label="Walk time" required></vaadin-time-picker>
 <vaadin-select id="assignee" label="Person" required></vaadin-select>
 <vaadin-button id="btnSubmit" style="margin: var(--lumo-space-l);" tabindex="0" theme="primary">
  Send request for walk
 </vaadin-button>
 <h2>Walks scheduled by you</h2>
 <vaadin-grid id="gridUsersWalks" is-attached style="height: 20%; flex-shrink: 0; flex-grow: 0;"></vaadin-grid>
 <h2>Walks assigned to you</h2>
 <vaadin-grid id="gridWalksAssigned" is-attached style="height: 20%; padding: var(--lumo-space-l); flex-shrink: 0; flex-grow: 0;"></vaadin-grid>
 <vaadin-button id="btnGoBack" tabindex="0" theme="error secondary" style="flex-shrink: 0; margin: var(--lumo-space-l);">
   Go back 
 </vaadin-button>
</vaadin-vertical-layout>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}
