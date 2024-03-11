package pes.editor;

import pes.editor.constants.PESConstant;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class GIFPNGFilter extends FileFilter {

	// Accept all directories and all png Flag files.
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = PESUtils.getExtension(f);
		if (extension != null) {
			if ((extension.equals(PESConstant.PNG_FILE_EXTENSION) || extension
					.equals(PESConstant.GIF_FILE_EXTENSION))) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	// The description of this filter
	public String getDescription() {
		return "PNG / GIF";
	}

}
