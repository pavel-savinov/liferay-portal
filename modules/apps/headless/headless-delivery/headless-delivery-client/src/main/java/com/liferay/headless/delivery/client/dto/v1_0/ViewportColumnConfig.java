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

package com.liferay.headless.delivery.client.dto.v1_0;

import com.liferay.headless.delivery.client.function.UnsafeSupplier;
import com.liferay.headless.delivery.client.serdes.v1_0.ViewportColumnConfigSerDes;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class ViewportColumnConfig implements Cloneable {

	public static ViewportColumnConfig toDTO(String json) {
		return ViewportColumnConfigSerDes.toDTO(json);
	}

	public ViewportColumnConfigDefinition getLandscapeMobile() {
		return landscapeMobile;
	}

	public void setLandscapeMobile(
		ViewportColumnConfigDefinition landscapeMobile) {

		this.landscapeMobile = landscapeMobile;
	}

	public void setLandscapeMobile(
		UnsafeSupplier<ViewportColumnConfigDefinition, Exception>
			landscapeMobileUnsafeSupplier) {

		try {
			landscapeMobile = landscapeMobileUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ViewportColumnConfigDefinition landscapeMobile;

	public ViewportColumnConfigDefinition getPortraitMobile() {
		return portraitMobile;
	}

	public void setPortraitMobile(
		ViewportColumnConfigDefinition portraitMobile) {

		this.portraitMobile = portraitMobile;
	}

	public void setPortraitMobile(
		UnsafeSupplier<ViewportColumnConfigDefinition, Exception>
			portraitMobileUnsafeSupplier) {

		try {
			portraitMobile = portraitMobileUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ViewportColumnConfigDefinition portraitMobile;

	public ViewportColumnConfigDefinition getTablet() {
		return tablet;
	}

	public void setTablet(ViewportColumnConfigDefinition tablet) {
		this.tablet = tablet;
	}

	public void setTablet(
		UnsafeSupplier<ViewportColumnConfigDefinition, Exception>
			tabletUnsafeSupplier) {

		try {
			tablet = tabletUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ViewportColumnConfigDefinition tablet;

	@Override
	public ViewportColumnConfig clone() throws CloneNotSupportedException {
		return (ViewportColumnConfig)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ViewportColumnConfig)) {
			return false;
		}

		ViewportColumnConfig viewportColumnConfig =
			(ViewportColumnConfig)object;

		return Objects.equals(toString(), viewportColumnConfig.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ViewportColumnConfigSerDes.toJSON(this);
	}

}