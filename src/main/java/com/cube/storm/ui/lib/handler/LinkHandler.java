package com.cube.storm.ui.lib.handler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.model.descriptor.VideoPageDescriptor;
import com.cube.storm.ui.model.descriptor.WebPageDescriptor;
import com.cube.storm.ui.model.property.DestinationLinkProperty;
import com.cube.storm.ui.model.property.ExternalLinkProperty;
import com.cube.storm.ui.model.property.InternalLinkProperty;
import com.cube.storm.ui.model.property.LinkProperty;
import com.cube.storm.ui.model.property.UriLinkProperty;

import java.util.Locale;

/**
 * Link handler class used when a link is triggered in a holder
 *
 * @author Callum Taylor
 * @project StormUI
 */
public class LinkHandler
{
	/**
	 * Called to handle when a link is clicked
	 *
	 * @param context The context of the view clicked
	 * @param link The link property that was clicked
	 */
	public void handleLink(Context context, LinkProperty link)
	{
		if (link instanceof DestinationLinkProperty && TextUtils.isEmpty(((DestinationLinkProperty)link).getDestination()))
		{
			return;
		}

		if (link instanceof InternalLinkProperty)
		{
			if (isYoutubeVideo(Uri.parse(((InternalLinkProperty)link).getDestination())) || isVideo(Uri.parse(((InternalLinkProperty)link).getDestination())))
			{
				VideoPageDescriptor page = new VideoPageDescriptor();
				page.setSrc(((InternalLinkProperty)link).getDestination());
				page.setType("content");

				Intent toLoad = UiSettings.getInstance().getIntentFactory().getIntentForPageDescriptor(context, page);

				if (toLoad != null)
				{
					context.startActivity(toLoad);
				}
			}
			else
			{
				Intent toLoad = UiSettings.getInstance().getIntentFactory().geIntentForPageUri(context, Uri.parse(((InternalLinkProperty)link).getDestination()));

				if (toLoad != null)
				{
					context.startActivity(toLoad);
				}
			}
		}
		else if (link instanceof UriLinkProperty)
		{
			String destination = ((UriLinkProperty)link).getDestination();

			if (destination.startsWith("tel://"))
			{
				destination = destination.replace("//", "");
			}

			Intent uriIntent = new Intent(Intent.ACTION_VIEW);
			uriIntent.setData(Uri.parse(destination));
			context.startActivity(uriIntent);
		}
		else if (link instanceof ExternalLinkProperty)
		{
			if (isYoutubeVideo(Uri.parse(((ExternalLinkProperty)link).getDestination())) || isVideo(Uri.parse(((ExternalLinkProperty)link).getDestination())))
			{
				VideoPageDescriptor page = new VideoPageDescriptor();
				page.setSrc(((ExternalLinkProperty)link).getDestination());
				page.setType("content");

				Intent toLoad = UiSettings.getInstance().getIntentFactory().getIntentForPageDescriptor(context, page);

				if (toLoad != null)
				{
					context.startActivity(toLoad);
				}
			}
			else
			{
				WebPageDescriptor page = new WebPageDescriptor();
				page.setSrc(((ExternalLinkProperty)link).getDestination());
				page.setType("content");

				Intent toLoad = UiSettings.getInstance().getIntentFactory().getIntentForPageDescriptor(context, page);

				if (toLoad != null)
				{
					context.startActivity(toLoad);
				}
			}
		}
	}

	public boolean isYoutubeVideo(@Nullable Uri uri)
	{
		if (uri == null || uri.getHost() == null)
		{
			return false;
		}

		return (uri.getHost().endsWith("youtube.com") && uri.getQueryParameter("v") != null) || (uri.getHost().endsWith("youtu.be") && uri.getPathSegments().size() > 0);
	}

	public boolean isVideo(@Nullable Uri uri)
	{
		if (uri == null || uri.getHost() == null)
		{
			return false;
		}

		return (uri.getHost().toLowerCase(Locale.US).endsWith("mp4") || (uri.getHost().toLowerCase(Locale.US).endsWith("m4v")));
	}
}
