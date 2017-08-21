/* global Liferay */

import {isString, isBoolean} from 'metal';
import {toElement} from 'metal-dom';
import State from 'metal-state';

/**
 * Component that generates a form for setting the Fragment name.
 */
class FragmentNameEditor extends State {
	/** @inheritdoc */
	constructor(...args) {
		super(...args);

		this._dialog = null;
		this._nameForm = null;
		this._nameInput = toElement(`#${this.namespace}name`);
		this.open = !this._nameInput.value;

		if (this.open) {
			this._openDialog();
		}
	}

	/**
	 * Opens a new dialog with the described form.
	 */
	_openDialog() {
		this._handleCancel = this._handleCancel.bind(this);
		this._handleSubmit = this._handleSubmit.bind(this);

		const _template =
			toElement(`#${this.namespace}nameEditorTemplate`)
			.innerHTML;

		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				draggable: false,
				width: 400,
				height: 260,
				resizable: false,
				bodyContent: _template,
				after: {destroy: this._handleCancel},
			},
			title: this.title,
		}, (dialog) => {
			this._dialog = dialog;
			this._nameForm = toElement(`#${this.namespace}fragmentNameFm`);
			this._nameFormInput = toElement(`#${this.namespace}fragmentNameInput`);
			this._nameForm.addEventListener('submit', this._handleSubmit);

			const cancelButton = toElement(`#${this.namespace}fragmentNameCancel`);
			cancelButton.addEventListener('click', this._handleCancel);
		});
	}

	/**
	 * Callback executed when the user closes the dialog or presses the cancel
	 * button. This performs a back navigation if the Fragment has no name.
	 */
	_handleCancel() {
		this._dialog.destroy();

		if (!this._nameInput.value) {
			window.history.href = this.backUrl;
		}
	}

	/**
	 * Callback executed when the generated form is submited.
	 * If the user has written a Fragment name, it closes the dialog,
	 * otherwise it does nothing.
	 * @param  {Event} event Submit event that is prevented.
	 */
	_handleSubmit(event) {
		event.preventDefault();

		if (this._nameFormInput.value) {
			this._nameInput.value = this._nameFormInput.value;
			this._dialog.destroy();
		}
	}
}

FragmentNameEditor.STATE = {
	/**
	 * Namespace of the portlet being used.
	 * Necesary for getting the real inputs which interact with the server.
	 * @type {string}
	 */
	namespace: {
		validator: isString,
	},

	/**
	 * Title of the created dialog.
	 * @type {string}
	 */
	title: {
		validator: isString,
	},

	/**
	 * Url used for going back when the user cancels the name input.
	 * @type {string}
	 */
	backUrl: {
		validator: isString,
	},

	/**
	 * If true, the name editor will be shown.
	 * It is set to true when the fragment has no name.
	 * @type {Boolean}
	 */
	open: {
		validator: isBoolean,
		value: false,
	},
};

export default FragmentNameEditor;