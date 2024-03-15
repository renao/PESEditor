package pes.editor;

public class PlayerAttribute {

	public int attributeType;
	public int offset;
	public int shift;
	public int mask;
	public String name;
	
	public PlayerAttribute(int attributeType, int offset, int shift, int mask, String name) {
		this.attributeType = attributeType;
		this.offset = offset;
		this.shift = shift;
		this.mask = mask;
		this.name = name;
	}
}
