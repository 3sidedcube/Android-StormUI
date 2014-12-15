package com.cube.storm.ui.view.holder.list;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cube.storm.UiSettings;
import com.cube.storm.ui.R;
import com.cube.storm.ui.model.list.ButtonListItem;
import com.cube.storm.ui.model.property.LinkProperty;
import com.cube.storm.ui.view.holder.ViewHolder;
import com.cube.storm.ui.view.holder.ViewHolderController;

/**
 * View holder for {@link com.cube.storm.ui.model.list.ButtonListItem} in the adapter
 *
 * @author Alan Le Fournis
 * @project Storm
 */
public class ButtonListItemHolder extends ViewHolderController
{
	@Override public ViewHolder createViewHolder(ViewGroup parent)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_list_item_view, parent, false);
		mViewHolder = new ButtonListItemViewHolder(view);

		return mViewHolder;
	}

	private class ButtonListItemViewHolder extends ViewHolder<ButtonListItem>
	{
		protected TextView title;
		protected Button button;
		protected LinearLayout embeddedLinksContainer;

		public ButtonListItemViewHolder(View view)
		{
			super(view);

			title = (TextView)view.findViewById(R.id.title);
			button = (Button)view.findViewById(R.id.button);
			embeddedLinksContainer = (LinearLayout)view.findViewById(R.id.embedded_links_container);
		}

		@Override public void populateView(ButtonListItem model)
		{
			title.setVisibility(View.GONE);
			button.setVisibility(View.GONE);

			if (model.getTitle() != null)
			{
				String titleContent = UiSettings.getInstance().getTextProcessor().process(model.getTitle().getContent());

				if (!TextUtils.isEmpty(titleContent))
				{
					title.setText(titleContent);
					title.setVisibility(View.VISIBLE);
				}
				else
				{
					title.setVisibility(View.GONE);
				}
			}

			if (model.getButton() != null && model.getButton().getTitle() != null)
			{
				String buttonContent = UiSettings.getInstance().getTextProcessor().process(model.getButton().getTitle().getContent());

				if (!TextUtils.isEmpty(buttonContent))
				{
					button.setText(UiSettings.getInstance().getTextProcessor().process(model.getButton().getTitle().getContent()));
					button.setVisibility(View.VISIBLE);
				}
				else
				{
					button.setVisibility(View.GONE);
				}
			}

			if (model.getEmbeddedLinks() != null)
			{
				embeddedLinksContainer.removeAllViews();

				for (LinkProperty linkProperty : model.getEmbeddedLinks())
				{
					final LinkProperty property = linkProperty;
					View embeddedLinkView = LayoutInflater.from(embeddedLinksContainer.getContext()).inflate(R.layout.button_embedded_link, embeddedLinksContainer, false);

					if (embeddedLinkView != null)
					{
						Button button = (Button)embeddedLinkView.findViewById(R.id.button);
						button.setVisibility(View.GONE);
						String embeddedContent = UiSettings.getInstance().getTextProcessor().process(linkProperty.getTitle().getContent());

						if (!TextUtils.isEmpty(embeddedContent))
						{
							button.setText(embeddedContent);
							button.setVisibility(View.VISIBLE);
						}

						button.setOnClickListener(new OnClickListener()
						{
							@Override public void onClick(View v)
							{
								UiSettings.getInstance().getLinkHandler().handleLink(v.getContext(), property);
							}
						});

						embeddedLinksContainer.setVisibility(View.VISIBLE);
						embeddedLinksContainer.addView(button);
					}
				}
			}
		}
	}
}