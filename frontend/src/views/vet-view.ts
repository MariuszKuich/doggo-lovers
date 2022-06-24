import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/date-picker/src/vaadin-date-picker.js';
import '@vaadin/text-field/src/vaadin-text-field.js';
import '@vaadin/text-area/src/vaadin-text-area.js';
import '@vaadin/grid/src/vaadin-grid.js';
import '@vaadin/button/src/vaadin-button.js';

@customElement('vet-view')
export class VetView extends LitElement {
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
 <h1>History of visits to the vet</h1>
 <h3 id="hHeader">{Placeholder}'s visits</h3>
 <vaadin-date-picker id="visitDate" label="Visit date" required></vaadin-date-picker>
 <vaadin-text-field id="cause" style="flex-shrink: 0; width: 30%;" type="text" required label="Visit cause"></vaadin-text-field>
 <vaadin-text-area id="recommendations" style="flex-shrink: 0; width: 30%; height: 20%;" required label="Recommendations"></vaadin-text-area>
 <vaadin-button theme="primary" id="btnAdd" tabindex="0">
   Add visit 
 </vaadin-button>
 <vaadin-button theme="secondary error" id="btnGoBack" style="margin-top: var(--lumo-space-l);" tabindex="0">
   Go back 
 </vaadin-button>
 <h3>Visits history</h3>
 <vaadin-grid id="gridHistory" style="flex-shrink: 0; flex-grow: 0; height: 40%;" is-attached></vaadin-grid>
</vaadin-vertical-layout>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}
