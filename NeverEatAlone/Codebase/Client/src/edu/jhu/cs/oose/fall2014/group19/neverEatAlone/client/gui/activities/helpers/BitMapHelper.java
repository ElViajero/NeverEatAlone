package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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

	/**
	 * This method rounds the corners of the bitmap for use in the ImageView
	 * object.
	 * 
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}
}
