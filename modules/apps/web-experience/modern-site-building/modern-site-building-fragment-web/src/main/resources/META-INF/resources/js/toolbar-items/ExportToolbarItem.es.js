import {isObject, isString} from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './ExportToolbarItem.soy';

/**
 * Toolbar item that allows importing content inside the editor.
 */
class ExportToolbarItem extends Component {
	/** @inheritdoc */
	attached() {
		this._document = this.aceEditor.getSession().getDocument();
		this._updateExportLink = this._updateExportLink.bind(this);
		this.aceEditor.on('change', this._updateExportLink);
		this._updateExportLink();
	}

	/** @inheritdoc */
	detached() {
		this.aceEditor.on('change', this._updateExportLink);
	}

	/**
	 * Updates the export link of the button
	 */
	_updateExportLink() {
		this._editorContent = encodeURI(this._document.getValue());
	}
}

ExportToolbarItem.STATE = {
	/**
	 * Internal property used for storing Ace editor.
	 * @type {Object}
	 */
	aceEditor: {
		validator: isObject,
	},

	/**
	 * Internal property used for exporting the document
	 * @type {string}
	 */
	_editorContent: {
		validator: isString,
		value: '',
	},
};

Soy.register(ExportToolbarItem, templates);

export default ExportToolbarItem;