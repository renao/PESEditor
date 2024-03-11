package pes.editor;

import pes.editor.constants.OptionFileConstants;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;

public class OptionFile {

	public byte[] data = new byte[OptionFileConstants.LENGTH];

	private byte[] headerData;

	public String fileName;

	public String gameID;


	String gameName;

	String saveName;

	String notes;


	byte format = -1;

	int fileCount;

	public boolean readXPS(File of) {
		format = -1;
		boolean done = true;
		String extension = PESUtils.getExtension(of);
		try {
			RandomAccessFile in = new RandomAccessFile(of, "r");
			if (extension != null && extension.equals(PESUtils.xps)) {
				long offSet = in.length() - OptionFileConstants.LENGTH - 4;
				in.seek(21);
				byte[] temp;
				int size = PESUtils.swabInt(in.readInt());
				temp = new byte[size];
				in.read(temp);
				gameName = new String(temp, "ISO-8859-1");
				size = PESUtils.swabInt(in.readInt());
				temp = new byte[size];
				in.read(temp);
				saveName = new String(temp, "ISO-8859-1");
				size = PESUtils.swabInt(in.readInt());
				temp = new byte[size];
				in.read(temp);
				notes = new String(temp, "ISO-8859-1");

				headerData = new byte[(int) offSet - 33 - gameName.length()
						- saveName.length() - notes.length()];
				in.read(headerData);
				gameID = new String(headerData, 6, 19);
				format = 0;
			} else if (extension != null && extension.equals(PESUtils.psu)) {
				headerData = new byte[(int) (in.length() - OptionFileConstants.LENGTH)];
				in.read(headerData);
				gameID = new String(headerData, 64, 19);
				format = 2;
			} else if (extension != null && extension.equals(PESUtils.max)) {
				byte[] temp = new byte[(int) in.length()];
				in.read(temp);
				String magic = new String(temp, 0, 12, "ISO-8859-1");
				if (magic.equals(OptionFileConstants.MAGIC_MAX)) {
					int chk = byteToInt(temp, 12);
					temp[12] = 0;
					temp[13] = 0;
					temp[14] = 0;
					temp[15] = 0;
					//System.out.println(chk);
					CRC32 crc = new CRC32();
					crc.update(temp);
					//System.out.println((int) crc.getValue());
					if ((int) crc.getValue() == chk) {
						temp = new byte[32];
						in.seek(16);
						in.read(temp);
						gameID = new String(temp, "ISO-8859-1");
						gameID = gameID.replace("\0", "");
						in.read(temp);
						gameName = new String(temp, "ISO-8859-1");
						gameName = gameName.replace("\0", "");
						int codeSize = PESUtils.swabInt(in.readInt());
						fileCount = PESUtils.swabInt(in.readInt());
						temp = new byte[codeSize];
						in.read(temp);
						Lzari lz = new Lzari();
						temp = lz.decodeLzari(temp);
						int p = 0;
						for (int i = 0; i < fileCount && format != 3; i++) {
							int size = byteToInt(temp, p);
							String title = new String(temp, p + 4, 19,
									"ISO-8859-1");
							if (size == OptionFileConstants.LENGTH && title.equals(gameID)) {
								p = p + 36;
								headerData = new byte[p];
								System.arraycopy(temp, 0, headerData, 0, p);
								System.arraycopy(temp, p, data, 0, OptionFileConstants.LENGTH);
								format = 3;
							} else {
								p = p + 36 + size;
								p = ((p + 23) / 16 * 16) - 8;
							}
						}
						if (format != 3) {
							done = false;
						}
					} else {
						done = false;
					}
				} else {
					done = false;
				}
			} else {
				if (of.getName().equals("KONAMI-WIN32WEXUOPT")) {
					gameID = "PC_WE";
				} else {
					gameID = "PC_PES";
				}
				format = 1;
			}
			if (done && format != -1) {
				if (format != 3)
					in.read(data);
				if (format == 1) {
					convertData();
				}
				decrypt();
			}
			
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			done = false;
		}
		if (done) {
			fileName = of.getName();
		} else {
			fileName = null;
		}
		return done;
	}

	private void convertData() {
		int k = 0;
		for (int a = 0; a < OptionFileConstants.LENGTH; a++) {
			data[a] = (byte) (data[a] ^ OptionFileConstants.KEY_PC[k]);
			if (k < 255) {
				k++;
			} else {
				k = 0;
			}
		}
	}

	public boolean saveXPS(File of) {
		data[45] = 1;
		data[46] = 1;
		data[5938] = 1;
		data[5939] = 1;
		boolean done = true;
		encrypt();
		checkSums();
		if (format == 1) {
			convertData();
		}
		try {
			RandomAccessFile out = new RandomAccessFile(of, "rw");
			if (format == 0) {
				saveName = of.getName();
				saveName = saveName.substring(0, saveName.length() - 4);
				out.write(OptionFileConstants.SHARKPORT);
				out.writeInt(PESUtils.swabInt(gameName.length()));
				out.writeBytes(gameName);
				out.writeInt(PESUtils.swabInt(saveName.length()));
				out.writeBytes(saveName);
				out.writeInt(PESUtils.swabInt(notes.length()));
				out.writeBytes(notes);
				out.write(headerData);
			}
			if (format == 2) {
				out.write(headerData);
			}
			if (format == 3) {
				int textSize = headerData.length + OptionFileConstants.LENGTH;
				textSize = ((textSize + 23) / 16 * 16) - 8;
				// System.out.println(textSize);
				byte[] temp = new byte[textSize];
				System.arraycopy(headerData, 0, temp, 0, headerData.length);
				System.arraycopy(data, 0, temp, headerData.length, data.length);
				Lzari lz = new Lzari();
				temp = lz.encodeLzari(temp);
				int codeSize = temp.length;				
				
				byte[] temp2 = new byte[88];
				System.arraycopy(OptionFileConstants.MAGIC_MAX.getBytes("ISO-8859-1"), 0, temp2, 0,
						OptionFileConstants.MAGIC_MAX.length());
				System
						.arraycopy(gameID.getBytes("ISO-8859-1"), 0, temp2, 16,
								19);
				System.arraycopy(gameName.getBytes("ISO-8859-1"), 0, temp2, 48,
						gameName.length());
				System.arraycopy(getBytesInt(codeSize), 0, temp2, 80, 4);
				System.arraycopy(getBytesInt(fileCount), 0, temp2, 84, 4);
				
				CRC32 chk = new CRC32();
				chk.update(temp2);
				chk.update(temp);
				//System.out.println((int)chk.getValue());
				System.arraycopy(getBytesInt((int) chk.getValue()), 0, temp2,
						12, 4);
				out.write(temp2);
				out.write(temp);
			} else {
				out.write(data);
			}
			if (format == 0) {
				out.write(0);
				out.write(0);
				out.write(0);
				out.write(0);
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			done = false;
		}
		if (format == 1) {
			convertData();
		}
		decrypt();
		return done;

	}

	public byte toByte(int i) {
		byte b;
		if (i > 127) {
			b = (byte) (i - 256);
		} else {
			b = (byte) i;
		}
		return b;
	}

	public int toInt(byte b) {
		int i = b;
		if (i < 0) {
			i = i + 256;
		}
		return i;
	}

	private void decrypt() {
		for (int i = 1; i < 10; i++) {
			int k = 0;
			for (int a = OptionFileConstants.BLOCKS[i]; a + 4 <= OptionFileConstants.BLOCKS[i] + OptionFileConstants.BLOCK_SIZE[i]; a = a + 4) {
				int c = byteToInt(data, a);
				int p = ((c - OptionFileConstants.KEY[k]) + 0x7ab3684c) ^ 0x7ab3684c;
				data[a] = toByte(p & 0x000000FF);
				data[a + 1] = toByte((p >>> 8) & 0x000000FF);
				data[a + 2] = toByte((p >>> 16) & 0x000000FF);
				data[a + 3] = toByte((p >>> 24) & 0x000000FF);
				k++;
				if (k == 446) {
					k = 0;
				}
			}

		}
	}

	private void encrypt() {
		for (int i = 1; i < 10; i++) {
			int k = 0;
			for (int a = OptionFileConstants.BLOCKS[i]; a + 4 <= OptionFileConstants.BLOCKS[i] + OptionFileConstants.BLOCK_SIZE[i]; a = a + 4) {
				int p = byteToInt(data, a);
				int c = OptionFileConstants.KEY[k] + ((p ^ 0x7ab3684c) - 0x7ab3684c);
				data[a] = toByte(c & 0x000000FF);
				data[a + 1] = toByte((c >>> 8) & 0x000000FF);
				data[a + 2] = toByte((c >>> 16) & 0x000000FF);
				data[a + 3] = toByte((c >>> 24) & 0x000000FF);
				k++;
				if (k == 446) {
					k = 0;
				}
			}
		}
	}

	private int byteToInt(byte[] ba, int a) {
		int[] temp = new int[4];
		temp[0] = toInt(ba[a]);
		temp[1] = toInt(ba[a + 1]);
		temp[2] = toInt(ba[a + 2]);
		temp[3] = toInt(ba[a + 3]);
		int p = temp[0] | (temp[1] << 8) | (temp[2] << 16) | (temp[3] << 24);
		return p;
	}

	public void checkSums() {
		for (int i = 0; i < 10; i++) {
			int chk = 0;
			for (int a = OptionFileConstants.BLOCKS[i]; a + 4 <= OptionFileConstants.BLOCKS[i] + OptionFileConstants.BLOCK_SIZE[i]; a = a + 4) {
				chk = chk + byteToInt(data, a);
			}
			data[OptionFileConstants.BLOCKS[i] - 8] = toByte(chk & 0x000000FF);
			data[OptionFileConstants.BLOCKS[i] - 7] = toByte((chk >>> 8) & 0x000000FF);
			data[OptionFileConstants.BLOCKS[i] - 6] = toByte((chk >>> 16) & 0x000000FF);
			data[OptionFileConstants.BLOCKS[i] - 5] = toByte((chk >>> 24) & 0x000000FF);
		}
	}

	/*
	 * public void saveData() { try { File f = new File("datadump");
	 * RandomAccessFile out = new RandomAccessFile(f, "rw"); out.write(data);
	 * out.close(); } catch (Exception e) { } }
	 */

	public byte[] getBytes(int i) {
		byte[] bytes = new byte[2];
		bytes[0] = toByte(i & 0xFF);
		bytes[1] = toByte((i >>> 8) & 0xFF);
		return bytes;
	}

	public byte[] getBytesInt(int i) {
		byte[] bytes = new byte[4];
		bytes[0] = toByte(i & 0xFF);
		bytes[1] = toByte((i >>> 8) & 0xFF);
		bytes[2] = toByte((i >>> 16) & 0xFF);
		bytes[3] = toByte((i >>> 24) & 0xFF);
		return bytes;
	}

	public static boolean isPS2pes(String s) {
		if (s.startsWith("BESLES-") && s.endsWith("PES6OPT")) {
			return true;
		}
		if (s.equals("BASLUS-21464W2K7OPT")) {
			return true;
		}
		return false;
	}

	public boolean isWE() {
		if (gameID.equals("BASLUS-21464W2K7OPT") || gameID.equals("PC_WE")) {
			return true;
		}
		return false;
	}

}