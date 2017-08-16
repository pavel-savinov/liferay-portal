import {isString} from 'metal';
import {toElement} from 'metal-dom';
import Component from 'metal-component';
import Soy from 'metal-soy';
import SourceEditor from './SourceEditor.es';
import FragmentPreview from './FragmentPreview.es';
import templates from './FragmentEditor.soy';
import {debounce, decodeHTMLEntities} from './utils.es';

/**
 * Component that allows editing an existing or new Fragment
 * It integrates three <SourceEditor /> components for each part of
 * the fragment and a <FragmentPreview /> component for the preview
 */
class FragmentEditor extends Component {
	/** @inheritdoc */
	attached() {
		this._htmlInput = toElement(`#${this.namespace}htmlContent`);
		this._cssInput = toElement(`#${this.namespace}cssContent`);
		this._jsInput = toElement(`#${this.namespace}jsContent`);

		this.refs.htmlEditor.setContent(decodeHTMLEntities(this._htmlInput.value));
		this.refs.cssEditor.setContent(decodeHTMLEntities(this._cssInput.value));
		this.refs.jsEditor.setContent(decodeHTMLEntities(this._jsInput.value));

		this._updatePreview = debounce(this._updatePreview.bind(this), 100);
		this.refs.htmlEditor.on('change', this._updatePreview);
		this.refs.cssEditor.on('change', this._updatePreview);
		this.refs.jsEditor.on('change', this._updatePreview);

		this._updatePreview();
	}

	/**
	 * Gets the code added to each <SourceEditor /> and updates the
	 * preview calling it's update method
	 */
	_updatePreview() {
		const html = this.refs.htmlEditor.getContent();
		const css = this.refs.cssEditor.getContent();
		const js = this.refs.jsEditor.getContent();

		this._htmlInput.value = html;
		this._cssInput.value = css;
		this._jsInput.value = js;

		this.refs.preview.update(html, css, js);
	}
}

FragmentEditor.STATE = {
	/**
	 * Namespace of the portlet being used.
	 * Necesary for getting the real inputs which interact with the server.
	 * @type {string}
	 */
	namespace: {
		validator: isString,
	},
};

Soy.register(FragmentEditor, templates);

export default FragmentEditor;