package com.cube.storm.ui.lib.factory;

import com.cube.storm.ui.view.View;
import com.cube.storm.ui.view.holder.Holder;

/**
 * This is the factory class which is used by Storm to help with getting the correct view holder/controller
 * for a specific view class name.
 *
 * @author Callum Taylor
 * @project StormUI
 */
public abstract class ViewFactory
{
	/**
	 * Gets the view holder class for a specific view name
	 *
	 * @param viewName The name of the view to lookup
	 *
	 * @return The view holder class or null if one was not found.
	 */
	public Class<? extends Holder> getHolderForView(String viewName)
	{
		return View.valueOf(viewName).getHolderClass();
	}
}
