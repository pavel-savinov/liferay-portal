/* global sinon, assert */

'use strict';

import {debounce, decodeHTMLEntities} from '../../src/main/resources/META-INF/resources/js/utils.es';

describe('utils', () => {
	describe('debounce', () => {
		let fn;

		beforeEach(() => {
			fn = debounce(sinon.spy(), 100);
		});

		it('waits a given amount of time until executing a function', (done) => {
			fn();
			assert(fn.callCount === 0);
			setTimeout(() => {
				assert(fn.callCount === 1);
				done();
			});
		});

		it('ignores repeating calls to the function', (done) => {
			fn();
			fn();
			fn();
			assert(fn.callCount === 0);
			setTimeout(() => {
				assert(fn.callCount === 1);
				done();
			});
		});

		it('passes all arguments to the wrapped function', (done) => {
			fn();
			fn('holi');
			fn('holi caracoli');
			setTimeout(() => {
				assert(fn.callCount === 1);
				assert(fn.calledWith('holi caracoli'));
				done();
			});
		});
	});

	describe('decodeHTMLEntities', () => {
		it('removes html entities like &bnsp;', () => {
			const text = '&Eacute;l era un bell&iacute;simo&bnsp;ejemplar';
			const result = 'Él era un bellísimo ejemplar';
			assert.equal(decodeHTMLEntities(text), result);
		});

		it('keeps the remaining text intact', () => {
			const text = `
				<h1>&Eacute;l era un bell&iacute;simo&bnsp;ejemplar</h1>
				<p>
					Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
					eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
					enim ad minim veniam, quis nostrud exercitation ullamco laboris
					nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor.
				</p>
			`;
			const result = `
				<h1>Él era un bellísimo ejemplar</h1>
				<p>
					Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
					eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut
					enim ad minim veniam, quis nostrud exercitation ullamco laboris
					nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor.
				</p>
			`;
			assert.equal(decodeHTMLEntities(text), result);
		});
	});
});