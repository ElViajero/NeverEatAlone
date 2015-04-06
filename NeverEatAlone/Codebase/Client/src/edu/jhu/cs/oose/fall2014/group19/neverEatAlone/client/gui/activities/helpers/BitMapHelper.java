package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class BitMapHelper {

	/**
	 * This method converts bitmap to string.
	 * 
	 * @param avatar
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String BitMapToString(Bitmap avatar) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		avatar.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		byte[] b = byteArrayOutputStream.toByteArray();
		String bitmapString = Base64.encodeToString(b, Base64.DEFAULT);
		return bitmapString;
	}

	/**
	 * This method converts string to bitmap.
	 * 
	 * @param bitmapString
	 * @return
	 */
	@SuppressWarnings("unused")
	public static Bitmap StringToBitMap(String bitmapString) {
		try {
			byte[] byteArray = Base64.decode(bitmapString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
					byteArray.length);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}
}
