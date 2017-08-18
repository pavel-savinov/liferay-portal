import {isBoolean} from 'metal';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './SourceEditorToolbar.soy';
import {isOneOf} from './validators.es';
import ExportToolbarItem from './toolbar-items/ExportToolbarItem.es';
import ImportToolbarItem from './toolbar-items/ImportToolbarItem.es';
import SelectAllToolbarItem from './toolbar-items/SelectAllToolbarItem.es';

/**
 * Toolbar item mapping used for creating functionality instances
 * @type {Object}
 */
const ITEM_MAP = {
	export: ExportToolbarItem,
	import: ImportToolbarItem,
	selectAll: SelectAllToolbarItem,
};

/**
 * Component that adds functionality to an ace editor
 * with a toolbar. Each item implements certain actions.
 */
class SourceEditorToolbar extends Component {
	/** @inheritdoc */
	rendered() {
		if (this.showToolbar && this.aceEditor) {
			this.items.forEach((itemName) => {
				const ItemComponent = ITEM_MAP[itemName];
				const itemWrapper = this.refs[itemName];

				new ItemComponent({
					aceEditor: this.aceEditor,
				}, itemWrapper);
			});
		}
	}

	/**
	 * Toggles the editor toolbar
	 */
	_toggleToolbar() {
		this.showToolbar = !this.showToolbar;
	}
}

SourceEditorToolbar.STATE = {
	/**
	 * Syntax used for the editor.
	 * It will be shown inside the toolbar as toogle button.
	 * @type {string}
	 */
	syntax: {
		validator: isOneOf(['html', 'css', 'javascript']),
	},

	/**
	 * Wherever or not show the editor toolbar
	 * @type {boolean}
	 */
	showToolbar: {
		value: true,
		validator: isBoolean,
	},

	/**
	 * Internal property used for storing Ace editor.
	 * @type {Object}
	 */
	aceEditor: {
		value: null,
	},

	/**
	 * Array of items that should be rendered inside the toolbar,
	 * each item will be placed inside the toolbar, and receive
	 * the existing editor as parameter.
	 * @type {Array<string>}
	 */
	items: {
		value: [],
	},
};

Soy.register(SourceEditorToolbar, templates);

export default SourceEditorToolbar;