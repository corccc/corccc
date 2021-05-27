package org.zz.gmhelper;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.util.Pack;
import org.bouncycastle.util.encoders.Hex;

public class SM2SplitAlgUtil implements ECConstants {

    public static final int SM3_DIGEST_LENGTH = 32;
    
    public static final int SM2_CURVE_LENGTH = 32;
    
    public static final int SM2_N_BITLENGTH = 256;
    
    //客户端部分签名和完整签名时使用的随机数k1
    private BigInteger k1;
    
    //客户端部分解密和完整解密时使用的随机数k
    private BigInteger k;
    
    /**
     * @description: <生成随机数，私钥d范围[1,n-2],签名中随机数k范围[1,n-2]，为了方便简化操作，本随机数范围[1,n-2]>
     * @return 域内的随机数
     */
    private  BigInteger getBigRand()
    {	
        BigInteger b;
        do
        {
            b = new BigInteger(SM2_N_BITLENGTH, new SecureRandom());
        }
        while (b.equals(ZERO) || b.compareTo(SM2Util.SM2_ECC_N.subtract(ONE)) >= 0);
        return b;
    }
    
    /**
     * @description: <根据X和Y转换成点Point>
     * @param xBytes x坐标
     * @param yBytes y坐标
     * @return Point点
     */
    public  ECPoint xyToPoint(byte[] xBytes,byte[] yBytes){
    	ECPoint point;
    	BigInteger x = new BigInteger(1, xBytes);
    	BigInteger y = new BigInteger(1, yBytes);
    	point = SM2Util.CURVE.createPoint(x, y);
    	return point;
    }
    
    private  byte[] fixTo32Bytes(byte[] src) {
        final int fixLen = 32;
        if (src.length == fixLen) {
            return src;
        }

        byte[] result = new byte[fixLen];
        if (src.length > fixLen) {
            System.arraycopy(src, src.length - result.length, result, 0, result.length);
        } else {
//            System.arraycopy(src, result.length - src.length, result, 0, src.length);
        	System.arraycopy(src, 0, result, result.length - src.length, src.length);
        	
        }
        return result;
    }
    
//    private byte[] concatenate(byte[]... input)
//    {
//    	int len = input.length;
//    	int bufLen = 0;
//    	for (int i = 0; i < len; i++) {
//			bufLen += input[i].length;
//		}
//    	byte[] rv = new byte[bufLen];
//    	int destPos = 0;
//       	for (int i = 0; i < len; i++) {
//    		System.arraycopy(input[i], 0, rv, destPos, input[i].length);
//    		destPos +=input[i].length;
//    	}
//       	return rv;
//    }
    
    private byte[] concatenate32BytesBuf(byte[]... input)
    {
    	int len = input.length;
    	byte[] rv = new byte[32*input.length];
    	int destPos = 0;
       	for (int i = 0; i < len; i++) {
    		System.arraycopy(fixTo32Bytes(input[i]), 0, rv, destPos, 32);
    		destPos +=32;
    	}
       	return rv;
    }
    

    /**
     * @description: <产生服务端部分公私钥对（D2，P2），D2为32字节，P2点共64字节，该接口为服务端接口>
     * @return 产生部分公私钥对，格式为：D2（32字节）|P2的X坐标点（32字节）|P2的Y坐标点（32字节）
     */
    public  byte[] generatePartKeyPair(){
    	BigInteger D2 = getBigRand();    	//generate server partPrikey D2
    	ECPoint P2 = ((new FixedPointCombMultiplier()).multiply(SM2Util.G_POINT, D2.modInverse(SM2Util.SM2_ECC_N))).normalize(); //P2=(1/D2)*G
    	byte[] partKeyPair = concatenate32BytesBuf(D2.toByteArray(),P2.getAffineXCoord().getEncoded(),P2.getAffineYCoord().getEncoded()); //partKeyPair=D2|P2X|P2Y 		
    	return partKeyPair;
    }
    
    /**
     * @description: <根据服务端部分公钥，生成完成公钥P，该接口为客户端接口>
     * @param P2  服务端部分公钥
     * @return 客户端部分私钥+完整公钥，格式为：D1（32字节）|P的X坐标点（32字节）|P的Y坐标点（32字节）
     */
    public  byte[] generatePubkey(byte[] P2){
    	BigInteger D1 = getBigRand();    	//generate client partPrikey D1
    	byte[] P2x = org.bouncycastle.util.Arrays.copyOfRange(P2, 0, 32);
    	byte[] P2y = org.bouncycastle.util.Arrays.copyOfRange(P2, 32, 64);
    	
    	ECPoint pointP2 = xyToPoint(P2x,P2y);
    	ECPoint pointP = ((pointP2.multiply(D1.modInverse(SM2Util.SM2_ECC_N))).subtract(SM2Util.G_POINT)).normalize(); // P=(1/D1)*P2-G
    	byte[] key = concatenate32BytesBuf(D1.toByteArray(),pointP.getAffineXCoord().getEncoded(),pointP.getAffineYCoord().getEncoded());  //key=D1|PX|PY 
    	return key;
    }
    
    /**
     * @description: <根据完整的公私钥对，拆分成服务端部分公私钥对及客户端部分公私钥对，该接口为服务端接口>
     * @param priKey 完整私钥
     * @return 客户端部分私钥+服务端部分私钥 格式为：D1（32字节）|D2（32字节）
     */
    public  byte[] divideKeyPair(byte[] priKeyBytes){
      	BigInteger d = new BigInteger(1, priKeyBytes); 	//(1+d)^-1=D1*D2; D1=[(1+d)D2]^-1
    	BigInteger D2 = getBigRand();      
    	BigInteger D1 = d.add(ONE).multiply(D2).modInverse(SM2Util.SM2_ECC_N);	
    	byte[] key = concatenate32BytesBuf(D1.toByteArray(),D2.toByteArray()); 	
    	return key;
    }
    
    public  byte[] jointKeyPair(byte[] d1,byte[] d2){
    	BigInteger D1 = new BigInteger(1, d1);
    	BigInteger D2 = new BigInteger(1, d2);
    	BigInteger d = D1.multiply(D2).modInverse(SM2Util.SM2_ECC_N).subtract(ONE);
    	byte[] key = fixTo32Bytes(d.toByteArray());	
    	return key;
    }
    
    
    /**
     * @description: <客户端生成部分签名，该接口为客户端接口>
     * @param msg 消息原文
     * @param P 完整公钥P
     * @return e+Q1  格式为：e（32字节）|Q1的x坐标（32字节）|Q1的Y坐标（32字节）
     */
    public  byte[] clientPartSign(byte[] msg,byte[] P){
    	byte[] px = org.bouncycastle.util.Arrays.copyOfRange(P, 0, 32);
    	byte[] py = org.bouncycastle.util.Arrays.copyOfRange(P, 32, 64);
    	
    	byte[] e = calculateE(msg, px, py);
    	k1 = getBigRand();
    	ECPoint pointQ1 = new FixedPointCombMultiplier().multiply(SM2Util.G_POINT, k1).normalize(); //Q1=k1*G
    	byte[] out = concatenate32BytesBuf(e,pointQ1.getAffineXCoord().getEncoded(),pointQ1.getAffineYCoord().getEncoded()); //out=e|Q1X|Q1Y   	
    	return out;
    }
    
    /**
     * @description: <method description，该接口为服务端接口>
     * @param eHash 客户端发送给服务端的摘要e
     * @param Q1 客户端发给服务端的Q1
     * @param D2 服务器侧的部分私钥D2
     * @return r，s2，s3， 格式为：r（32字节）|s2（32字节）|s3（32字节）
     */
    public  byte[] serverPartSign(byte[] eHash ,byte[] Q1,byte[] D2){
    	BigInteger k2 = getBigRand();
    	BigInteger k3 = getBigRand();
    	BigInteger e = new BigInteger(1, eHash);	
    	BigInteger d2 = new BigInteger(1,D2);
    	byte[] Q1x = org.bouncycastle.util.Arrays.copyOfRange(Q1, 0, 32);
    	byte[] Q1y = org.bouncycastle.util.Arrays.copyOfRange(Q1, 32, 64);
    	ECPoint pointQ1 = xyToPoint(Q1x,Q1y);
    	  	
    	ECPoint x1y1 = ECAlgorithms.sumOfTwoMultiplies(pointQ1, k2,SM2Util.G_POINT, k3).normalize(); //(x1,y1)=k2*Q1+k3*G
    	BigInteger r = e.add(x1y1.getAffineXCoord().toBigInteger()).mod(SM2Util.SM2_ECC_N); //r = (x1+e)modN
    	BigInteger s2 = d2.multiply(k2).mod(SM2Util.SM2_ECC_N);
    	BigInteger s3 = (d2.multiply(r.add(k3))).mod(SM2Util.SM2_ECC_N);
    	
    	byte[] out = concatenate32BytesBuf(r.toByteArray(),s2.toByteArray(),s3.toByteArray());
    	return out;
    }
    
    /**
     * @description: <根据r，s2，s3，D1，k1生成完整签名，该接口为客户端接口>
     * @param r 服务端返回给客户端的r
     * @param s2 服务端返回给客户端的s2
     * @param s3 服务端返回给客户端的s3
     * @param D1 客户端部分私钥D1
     * @param k1 客户端计算部分签名时使用的k1
     * @return 完整签名值（r，s），格式为：r（32字节）|s（32字节）
     */
    public  byte[] clientFullSign(byte[] r,byte[] s2,byte[] s3,byte[] D1){
//    	byte[] out = new byte[64];
    	BigInteger R = new BigInteger(1, r);	
    	BigInteger S2 = new BigInteger(1, s2);
    	BigInteger S3 = new BigInteger(1, s3);
    	BigInteger d1 = new BigInteger(1, D1);
    	BigInteger s = ((d1.multiply(k1).multiply(S2)).add((d1.multiply(S3))).subtract(R)).mod(SM2Util.SM2_ECC_N);//s=(D1*k1)*S2+D1*s3-r
	
    	byte[] out = concatenate32BytesBuf(r,s.toByteArray());	    	
    	return out;
    }
    
    /**
     * @description: <客户端产生部分签名T1，该接口为客户端接口>
     * @param sm2Cipher SM2密文值
     * @return T1，格式为：T1的x坐标（32字节）|T1的Y坐标（32字节）
     */
    public  byte[] clientPartDecrypt(byte[] sm2Cipher){
//    	byte[] out = new byte[96];
//    	byte[] C1X = new byte[32];
//    	byte[] C1Y = new byte[32];
//    	System.arraycopy(sm2Cipher, 1, C1X, 0, 32);
//    	System.arraycopy(sm2Cipher, 32+1, C1Y, 0, 32);
    	byte[] C1X = org.bouncycastle.util.Arrays.copyOfRange(sm2Cipher, 1, 32+1);
    	byte[] C1Y = org.bouncycastle.util.Arrays.copyOfRange(sm2Cipher, 32+1, 32+1+32);
    	ECPoint pointC1 = xyToPoint(C1X,C1Y);
    	
    	k = getBigRand();
    	ECPoint pointT1 = pointC1.multiply(k).normalize(); // T1=k*C1
    	byte[] out = concatenate32BytesBuf(pointT1.getAffineXCoord().getEncoded(),pointT1.getAffineYCoord().getEncoded());
    	return out;
    }
    
    /**
     * @description: <生成服务端部分签名T2，此接口为服务端接口>
     * @param T1 客户端发送给服务端的部分签名
     * @param D2 服务端部分私钥
     * @return 服务端部分签名T2，格式为：T2X（32字节）|T2Y（32字节）
     */
    public  byte[] serverPartDecrypt(byte[] T1,byte[] D2){
//    	byte[] out = new byte[64];
//    	byte[] T1X = new byte[32];
//    	byte[] T1Y = new byte[32];
//    	System.arraycopy(T1, 0, T1X, 0, 32);
//    	System.arraycopy(T1, 32, T1Y, 0, 32);
    	
    	byte[] T1X = org.bouncycastle.util.Arrays.copyOfRange(T1, 0, 32);
    	byte[] T1Y = org.bouncycastle.util.Arrays.copyOfRange(T1, 32, 64);
    	ECPoint pointT1 = xyToPoint(T1X,T1Y);
    	
    	BigInteger d2 = new BigInteger(1, D2);
    	ECPoint pointT2 = pointT1.multiply(d2.modInverse(SM2Util.SM2_ECC_N)).normalize(); // T2=(D2^-1)*T1
    	byte[] out = concatenate32BytesBuf(pointT2.getAffineXCoord().getEncoded(),pointT2.getAffineYCoord().getEncoded());
    	
//    	System.arraycopy(PointT2.getAffineXCoord().getEncoded(), 0, out, 0, 32);
//    	System.arraycopy(PointT2.getAffineYCoord().getEncoded(), 0, out, 32, 32);  
    	return out;
    }
    
    
    /**
     * @description: <method description>
     * @param sm2Cipher 密文数据
     * @param T2 服务端发送给客户端的部分解密T2值
     * @param D1 客户端部分私钥
     * @return 解密出的消息明文
     * @throws InvalidCipherTextException
     */
    public  byte[] clientFullDecrypt(byte[] sm2Cipher, byte[] T2,byte[] D1) throws InvalidCipherTextException{
    	byte[] c1 = new byte[SM2_CURVE_LENGTH * 2 + 1];
        System.arraycopy(sm2Cipher, 0, c1, 0, c1.length);
        ECPoint c1P = SM2Util.CURVE.decodePoint(c1);
    	
        ECPoint s = c1P.multiply(SM2Util.DOMAIN_PARAMS.getH());
        if (s.isInfinity())
        {
            throw new InvalidCipherTextException("[h]C1 at infinity");
        }     
        
//    	byte[] T2X = new byte[32];
//    	byte[] T2Y = new byte[32];
//    	System.arraycopy(T2, 0, T2X, 0, 32);
//    	System.arraycopy(T2, 32, T2Y, 0, 32);
    	
    	byte[] T2X = org.bouncycastle.util.Arrays.copyOfRange(T2, 0, 32);
    	byte[] T2Y = org.bouncycastle.util.Arrays.copyOfRange(T2, 32, 64);
    	ECPoint PointT2 = xyToPoint(T2X,T2Y);
    	
    	BigInteger d1 = new BigInteger(1, D1);
    	BigInteger temp = ((k.modInverse(SM2Util.SM2_ECC_N)).multiply(d1.modInverse(SM2Util.SM2_ECC_N))).mod(SM2Util.SM2_ECC_N);
    	ECPoint x2y2P = PointT2.multiply(temp).subtract(c1P).normalize(); // (x2,y2)=(1/k)*(1/D1)*T2-C1
    	
    	byte[] c2 = new byte[sm2Cipher.length - c1.length - SM3_DIGEST_LENGTH];
    	System.arraycopy(sm2Cipher, c1.length, c2, 0, c2.length);
    	
    	byte[] x2y2 = concatenate32BytesBuf(x2y2P.getAffineXCoord().getEncoded(), x2y2P.getAffineYCoord().getEncoded());
    	
    	
    	byte[] c3 = new byte[SM3_DIGEST_LENGTH];
    	System.arraycopy(sm2Cipher, c1.length+c2.length, c3, 0, c3.length);
    	
    	byte[] t =kdf(x2y2, c2.length);
    	
    	byte[] msg = new byte[c2.length];
    	for (int i = 0; i < msg.length; i++) {
			msg[i] = (byte) (c2[i]^t[i]);
		}
    	
    	byte[] u = new byte[SM3_DIGEST_LENGTH];
    	SM3Digest digest = new SM3Digest();
    	digest.update(fixTo32Bytes(x2y2P.getAffineXCoord().getEncoded()),0,32);
    	digest.update(msg, 0, msg.length);
    	digest.update(fixTo32Bytes(x2y2P.getAffineYCoord().getEncoded()), 0, 32);
    	digest.doFinal(u, 0);    	
    	
    	if (!Arrays.equals(u, c3)){
    		throw new InvalidCipherTextException("invalid cipher text");
    	}
    	
    	return msg;
    }
    
    private  byte[] calculateE(byte[] msg,byte[] px,byte[] py){
    	byte[] buf = new byte[SM3_DIGEST_LENGTH];
    	SM3Digest digest = new SM3Digest();	
    	byte[] userID = Hex.decode("31323334353637383132333435363738");
    	int len = userID.length * 8;
    	digest.update((byte)(len >> 8 & 0xFF));
        digest.update((byte)(len & 0xFF));
        digest.update(userID, 0, userID.length);
        digest.update(SM2Util.CURVE.getA().getEncoded(), 0, SM2Util.CURVE.getA().getEncoded().length);
        digest.update(SM2Util.CURVE.getB().getEncoded(), 0, SM2Util.CURVE.getB().getEncoded().length);
        digest.update(SM2Util.G_POINT.getAffineXCoord().getEncoded(), 0, SM2Util.G_POINT.getAffineXCoord().getEncoded().length);
        digest.update(SM2Util.G_POINT.getAffineYCoord().getEncoded(), 0, SM2Util.G_POINT.getAffineYCoord().getEncoded().length);
        digest.update(px, 0, px.length);
        digest.update(py, 0, py.length);
        digest.doFinal(buf, 0);
        
        digest.update(buf, 0, buf.length);
        digest.update(msg, 0, msg.length);
        digest.doFinal(buf, 0);
        return buf;
        
    }
    
    
    private  byte[] kdf(byte[] z,int klen)
    {
    	
    	int off = 0;
    	int ct = 0;
    	byte[] buf = new byte[SM3_DIGEST_LENGTH];
    	byte[] out = new byte[klen];
    	
        SM3Digest digest = new SM3Digest();	
    	while (off <klen) {
    		digest.update(z, 0, z.length);
    		Pack.intToBigEndian(++ct, buf, 0);
            digest.update(buf, 0, 4);
            digest.doFinal(buf, 0);
             
            int length = Math.min(SM3_DIGEST_LENGTH, klen - off);
			System.arraycopy(buf, 0, out, off, length);
            off += length;   
		}
    	return out;
    }
    
//    public static void main(String[] args) throws Exception {
//    	
//    }
    
}