import {isObject} from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './SelectAllToolbarItem.soy';

/**
 * Toolbar item that allows selecting all text
 */
class SelectAllToolbarItem extends Component {
	/**
	 * Select all the text using the given ace editor.
	 */
	_selectAll() {
		this.aceEditor.selectAll();
		this.aceEditor.focus();
	}
}

SelectAllToolbarItem.STATE = {
	/**
	 * Internal property used for storing Ace editor.
	 * @type {Object}
	 */
	aceEditor: {
		validator: isObject,
	},
};

Soy.register(SelectAllToolbarItem, templates);

export default SelectAllToolbarItem;