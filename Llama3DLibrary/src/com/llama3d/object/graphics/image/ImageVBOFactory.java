package com.llama3d.object.graphics.image;

import java.util.ArrayList;
import java.util.List;

import android.opengl.GLES20;

public class ImageVBOFactory {

	// ===================================================================
	// Private Static Fields
	// ===================================================================

	private static List<ImageVBO> imageVBOs = new ArrayList<ImageVBO>();

	// ===================================================================
	// Public Static Fields
	// ===================================================================

	public static ImageVBO getFreeVBOSpace() {
		if (ImageVBOFactory.imageVBOs.size() > 0) {
			// ======== Get Image VBO From Existing VBOs ========
			for (ImageVBO imageVBO : ImageVBOFactory.imageVBOs) {
				if (imageVBO.hasFreeSpace()) {
					return imageVBO;
				} else {
					ImageVBO newImageVBO = new ImageVBO();
					ImageVBOFactory.imageVBOs.add(newImageVBO);
					return newImageVBO;
				}
			}
		} else {
			// ======== Add A New Image VBO For Usage ========
			ImageVBOFactory.imageVBOs.add(new ImageVBO());
			return ImageVBOFactory.imageVBOs.get(0);
		}
		return null;
	}

	public static void clearVBOSpace() {
		for (ImageVBO imageVBO : imageVBOs) {
			// ======== Free VBOs ========
			int[] vBuffer = new int[]{imageVBO.vBuffer};
			GLES20.glGenBuffers(1, vBuffer, 0);
			GLES20.glDeleteBuffers(1, vBuffer, 0);
		}
		ImageVBOFactory.imageVBOs.clear();
	}
}
