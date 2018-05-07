import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import templates from './LayoutActions.soy';

/**
 * LayoutActions
 */

class LayoutActions extends Component {}

Soy.register(LayoutActions, templates);

export {LayoutActions};
export default LayoutActions;