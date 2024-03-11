package pes.editor;

import java.io.File;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PESUtils {
	/*
	 * Get the extension of a file.
	 */
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	public static int swabInt(int v) {
		return (v >>> 24) | (v << 24) | ((v << 8) & 0x00FF0000)
				| ((v >> 8) & 0x0000FF00);
	}
	
	public static void javaUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException
				 | IllegalAccessException
				 | UnsupportedLookAndFeelException
				 | InstantiationException exception) {
			EditorLogger.log(exception);
		}
    }
	
	public static void systemUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException
				 | IllegalAccessException
				 | UnsupportedLookAndFeelException
				 | InstantiationException exception) {
			EditorLogger.log(exception);
		}
	}

}
