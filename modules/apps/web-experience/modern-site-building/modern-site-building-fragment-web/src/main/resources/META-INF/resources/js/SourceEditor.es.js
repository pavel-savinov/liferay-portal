/* global AUI */

import Component from 'metal-component';
import Soy from 'metal-soy';
import SourceEditorToolbar from './SourceEditorToolbar.es';
import templates from './SourceEditor.soy';
import {isOneOf} from './validators.es';

/**
 * Component that creates an instance of Ace editor
 * to allow code editing.
 */
class SourceEditor extends Component {
	/** @inheritdoc */
	attached() {
		this._editor = null;
		this._editorDocument = null;
		this._initialContent = null;
		this._onChange = this._onChange.bind(this);

		AUI().use('aui-ace-editor', (A) => { // eslint-disable-line new-cap
			const editor = new A.AceEditor(
				{
					boundingBox: this.refs.editorWrapper,
					mode: this.syntax,
					tabSize: 2,
					highlightActiveLine: false,
				}
			);

			this.refs.toolbar.aceEditor = editor.getEditor();

			this._editorDocument = editor.getSession().getDocument();

			if (this._initialContent) {
				this.setContent(this._initialContent);
				this._initialContent = null;
			}

			this.refs.editorWrapper.style.height = '';
			this.refs.editorWrapper.style.width = '';
			this._editorDocument.on('change', this._onChange);
		});
	}

	/**
	 * Sets the content of the editor or queues the given content
	 * until the editor has been initialized.
	 * @param {string} content
	 */
	setContent(content) {
		if (this._editorDocument) {
			this._editorDocument.setValue(content);
		} else {
			this._initialContent = content;
		}
	}

	/**
	 * Gets the content of the editor.
	 * An empty string will be returned if the editor has
	 * not been initialized.
	 * @return {string} Editor content
	 */
	getContent() {
		return this._editorDocument
			? this._editorDocument.getValue()
			: this._initialContent;
	}

	/**
	 * Callback executed when the internal Ace editor has been
	 * modified. It simply propagates the event.
	 */
	_onChange() {
		this.emit('change');
	}
}

SourceEditor.STATE = {
	/**
	 * Syntax used for the editor.
	 * It will be used for Ace and rendered on the interface.
	 * @type {string}
	 */
	syntax: {
		validator: isOneOf(['html', 'css', 'javascript']),
	},

	/**
	 * Array of items that should be rendered inside the toolbar,
	 * each item will be placed inside the toolbar, and receive
	 * the existing editor as parameter.
	 * @type {Array<string>}
	 */
	toolbarItems: {
		value: [],
	},
};

Soy.register(SourceEditor, templates);

export default SourceEditor;