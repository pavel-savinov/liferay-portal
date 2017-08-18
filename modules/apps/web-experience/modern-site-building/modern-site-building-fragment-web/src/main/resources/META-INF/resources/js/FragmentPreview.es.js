import Component from 'metal-component';
import Soy from 'metal-soy';
import {isOneOf} from './validators.es';
import templates from './FragmentPreview.soy';

/**
 * Component that renders the preview of a Fragment.
 * It allows modifying the preview with an update method.
 */
class FragmentPreview extends Component {
	/** @inheritdoc */
	attach() {
		this._previewSizePrefix = this.refs.preview.className;
	}

	/**
	 * Updates the rendered preview with the given content.
	 * Encapsulates the given code inside a frame and renders it.
	 * @param  {string} html HTML content of the preview
	 * @param  {string} css  Styles of the preview
	 * @param  {string} js   Javascript of the preview (popups are disallowed)
	 */
	update(html, css, js) {
		const preview = 'data:text/html;charset=utf-8,' + encodeURI(`
			<!DOCTYPE html>
			<html>
			<head>
				<meta name="viewport" content="width=device-width, initial-scale=1">
				<meta charset="utf-8">
				<style>${css}</style>
			</head>
			<body>
				${html}
				<script>${js}</script>
			</body>
			</html>
		`);

		if (preview !== this.refs.preview.src) {
			this.refs.preview.src = preview;
		}
	}

	/**
	 * Changes the preview size to 'desktop'
	 */
	_setPreviewSizeDesktop() {
		this.size = 'desktop';
	}

	/**
	 * Changes the preview size to 'tablet'
	 */
	_setPreviewSizeTablet() {
		this.size = 'tablet';
	}

	/**
	 * Changes the preview size to 'mobile'
	 */
	_setPreviewSizeMobile() {
		this.size = 'mobile';
	}

	/**
	 * Changes the preview size to 'full'
	 */
	_setPreviewSizeFull() {
		this.size = 'full';
	}
}

FragmentPreview.STATE = {
	/**
	 * Ratio of the preview being rendered.
	 * This property is modified internally with the ui buttons
	 * presented to the user, but it can be safely altered externally.
	 * @type {string}
	 */
	size: {
		validator: isOneOf(['desktop', 'tablet', 'mobile', 'full']),
		value: 'full',
	},
};

Soy.register(FragmentPreview, templates);

export default FragmentPreview;