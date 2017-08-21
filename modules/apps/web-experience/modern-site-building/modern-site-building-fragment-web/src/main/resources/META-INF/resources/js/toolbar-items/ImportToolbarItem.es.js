import {isObject} from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './ImportToolbarItem.soy';

/**
 * Toolbar item that allows importing content inside the editor.
 */
class ImportToolbarItem extends Component {
	/**
	 * Import the specified file inside the editor.
	 */
	_import() {
		const file = this.refs.inputField.files[0];

		if (file) {
			const reader = new FileReader();

			reader.addEventListener('loadend', () =>
				this.aceEditor.getSession().getDocument().setValue(reader.result)
			);

			reader.readAsText(file, 'utf-8');
		}
	}
}

ImportToolbarItem.STATE = {
	/**
	 * Internal property used for storing Ace editor.
	 * @type {Object}
	 */
	aceEditor: {
		validator: isObject,
	},
};

Soy.register(ImportToolbarItem, templates);

export default ImportToolbarItem;