/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {Config} from 'metal-state';
import 'frontend-js-web/liferay/compat/modal/Modal.es';
import Component from 'metal-component';
import Soy from 'metal-soy';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './CreateContentDialog.soy';
import './CreateContentForm.es';
import './MapContentForm.es';
import {setIn} from '../../utils/FragmentsEditorUpdateUtils.es';
import {
	addStructuredContent,
	getContentStructureMappingFields,
	updateEditableValues
} from '../../utils/FragmentsEditorFetchUtils.es';
import {
	COMPATIBLE_TYPES,
	EDITABLE_FRAGMENT_ENTRY_PROCESSOR
} from '../../utils/constants';
import {
	disableSavingChangesStatusAction,
	enableSavingChangesStatusAction
} from '../../actions/saveChanges.es';

/**
 * CreateContentDialog
 */
class CreateContentDialog extends Component {
	/**
	 * @inheritdoc
	 * @param {object} state
	 * @return {object}
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = setIn(
			state,
			['_stepLabel'],
			Liferay.Util.sub(
				Liferay.Language.get('step-x-of-x'),
				state._step,
				'2'
			)
		);

		nextState = setIn(
			nextState,
			['_stepName'],
			Liferay.Language.get('details')
		);

		return nextState;
	}

	/**
	 * Close asset type selection dialog
	 * @private
	 * @review
	 */
	_handleCancelButtonClick() {
		this.dispose();
	}

	/**
	 * Sends mapped content to the server and closes this dialog.
	 * @private
	 * @review
	 */
	_handleSubmitButtonClick() {
		if (this._step === 1) {
			this._step = 2;
		} else {
			this.store.dispatch(enableSavingChangesStatusAction());

			addStructuredContent(
				this._serializedFields,
				this._ddmStructure.id,
				this._title
			)
				.then(response => {
					const promises = [];
					const updateFragmentEntryLinks = {};

					this._fields
						.filter(field => field.fragmentEntryLinkId)
						.forEach(field => {
							const currentValues = this.fragmentEntryLinks[
								field.fragmentEntryLinkId
							].editableValues;

							const updateFragmentEntryLink =
								updateFragmentEntryLinks[
									field.fragmentEntryLinkId
								] || Object.assign({}, currentValues);

							const updateEditableValues =
								updateFragmentEntryLink[
									EDITABLE_FRAGMENT_ENTRY_PROCESSOR
								];

							updateEditableValues[field.editableId].classNameId =
								response.classNameId;
							updateEditableValues[field.editableId].classPK =
								response.classPK;
							updateEditableValues[field.editableId].fieldId =
								field.key;

							updateFragmentEntryLinks[
								field.fragmentEntryLinkId
							] = updateFragmentEntryLink;
						});

					Object.keys(updateFragmentEntryLinks).forEach(
						fragmentEntryLinkId => {
							promises.push(
								updateEditableValues(
									fragmentEntryLinkId,
									updateFragmentEntryLinks[
										fragmentEntryLinkId
									]
								)
							);
						}
					);

					Promise.all(promises).then(() => {
						this.store
							.dispatch(disableSavingChangesStatusAction())
							.done(() => this.dispose());
					});
				})
				.catch(error => {
					this._errorMessage = error.message;
				});
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleCreateContentFormChange(event) {
		this._ddmStructure = event.ddmStructure;
		this._title = event.title;
		this._valid = event.valid;

		if (this._ddmStructure) {
			getContentStructureMappingFields(this._ddmStructure.id).then(
				response => {
					const compatibleTypes = {};

					Object.keys(COMPATIBLE_TYPES).forEach(editableType => {
						const ddmTypes = COMPATIBLE_TYPES[editableType];

						ddmTypes.forEach(ddmType => {
							compatibleTypes[ddmType] = editableType;
						});
					});

					const fields = [
						{
							key: '-',
							label: Liferay.Language.get('unmapped'),
							type: '-'
						}
					];

					response.forEach(field => {
						field.type = compatibleTypes[field.type];

						fields.push(field);
					});

					this._fields = fields;
				}
			);
		}
	}

	/**
	 * @private
	 * @review
	 */
	_handleMapContentFormFieldsChange(event) {
		if (event.fields) {
			this._fields = event.fields;
			this._serializedFields = event.serializedFields;
		}
	}

	/**
	 * Change asset type selection dialog visibility.
	 * @private
	 * @review
	 */
	_handleVisibleChanged() {
		if (!this.visible) {
			this.dispose();
		}
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
CreateContentDialog.STATE = {
	/**
	 * Selected DDM structure
	 * @default undefined
	 * @instance
	 * @memberOf CreateContentDialog
	 * @private
	 * @review
	 * @type {{id: string, label: string}}
	 */
	_ddmStructure: Config.shapeOf({
		id: Config.string().required(),
		label: Config.string().required()
	}),

	/**
	 * Error message
	 * @default ''
	 * @instance
	 * @memberOf CreateContentDialog
	 * @private
	 * @review
	 * @type {string}
	 */
	_errorMessage: Config.string().value(null),

	/**
	 * List of available structure fields
	 * @default null
	 * @instance
	 * @memberOf CreateContentDialog
	 * @private
	 * @review
	 * @type {Array<{
	 *   disabled: boolean,
	 *   editableId: string,
	 *   fragmentEntryLinkId: string,
	 *   key: !string,
	 *   label: !string,
	 *   type: !string
	 * }>}
	 */
	_fields: Config.arrayOf(
		Config.shapeOf({
			disabled: Config.bool().value(false),
			editableId: Config.string().value(''),
			fragmentEntryLinkId: Config.string().value(null),
			key: Config.string().required(),
			label: Config.string().required(),
			type: Config.string().required()
		})
	)
		.internal()
		.value(null),

	/**
	 * Serialized DDM form fields
	 * @default ''
	 * @instance
	 * @memberOf CreateContentDialog
	 * @private
	 * @review
	 * @type {string}
	 */
	_serializedFields: Config.string().value(''),

	/**
	 * Current dialog step
	 * @default 1
	 * @instance
	 * @memberOf CreateContentDialog
	 * @private
	 * @review
	 * @type {number}
	 */
	_step: Config.number().value(1),

	/**
	 * Current content title
	 * @default ''
	 * @instance
	 * @memberOf CreateContentDialog
	 * @private
	 * @review
	 * @type {string}
	 */
	_title: Config.string().value(''),

	/**
	 * Is form valid
	 * @default false
	 * @instance
	 * @memberOf CreateContentForm
	 * @private
	 * @review
	 * @type {boolean}
	 */
	_valid: Config.bool().value(false)
};

const ConnectedCreateContentDialog = getConnectedComponent(
	CreateContentDialog,
	['fragmentEntryLinks', 'portletNamespace', 'savingChanges', 'spritemap']
);

Soy.register(ConnectedCreateContentDialog, templates);

export {ConnectedCreateContentDialog, CreateContentDialog};
export default ConnectedCreateContentDialog;
