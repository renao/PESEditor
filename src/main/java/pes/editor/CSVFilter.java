package pes.editor;

import pes.editor.constants.PESConstant;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class CSVFilter extends FileFilter {

	// Accept all directories and all csv files.
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		var extension = PESUtils.getExtension(f);
        return extension != null && extension.equals(PESConstant.CSV_FILE_EXTENSION);

    }

	// The description of this filter
	public String getDescription() {
		return ".csv";
	}
}
