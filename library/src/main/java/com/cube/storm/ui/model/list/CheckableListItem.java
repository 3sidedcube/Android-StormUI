package com.cube.storm.ui.model.list;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A view model with a boolean property
 *
 * @author Alan Le Fournis
 * @author Callum Taylor
 * @project LightningUi
 */
@NoArgsConstructor @AllArgsConstructor
@Accessors(chain = true) @Data @EqualsAndHashCode(callSuper=false)
public class CheckableListItem extends DescriptionListItem
{
	public static String CLASS_NAME = "CheckableListItem";

	{ this.className = CLASS_NAME; }

	@SerializedName("volatile") protected boolean isVolatile;

	@Override public int describeContents()
	{
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags)
	{

	}
}
