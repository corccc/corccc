package org.zz.gmhelper;

public class CPKMatrix {
	private byte[] priKeyMatrix;
	private byte[] pubKeyMatrix;
	public CPKMatrix(byte[] priKeyMatrix,byte[] pubKeyMatrix) {
		this.priKeyMatrix = priKeyMatrix;
		this.pubKeyMatrix = pubKeyMatrix;
	}
	public byte[] getPriKeyMatrix() {
		return priKeyMatrix;
	}
	public void setPriKeyMatrix(byte[] priKeyMatrix) {
		this.priKeyMatrix = priKeyMatrix;
	}
	public byte[] getPubKeyMatrix() {
		return pubKeyMatrix;
	}
	public void setPubKeyMatrix(byte[] pubKeyMatrix) {
		this.pubKeyMatrix = pubKeyMatrix;
	}
}
