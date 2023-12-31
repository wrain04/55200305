package com.xyw.product.goods.util;

import com.xyw.product.common.core.util.BASE64DecodedMultipartFile;
import com.xyw.product.common.core.util.ToolsUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ImageSizeUtil {

	private final static Logger logger = LoggerFactory.getLogger(ImageSizeUtil.class);

	public static int getImageLengthOfSide(MultipartFile params) {
		int lengthSize = 0;
		Map<String, Integer> result = new HashMap<>();
		long beginTime = new Date().getTime();
		// 获取图片格式
		String suffixName = getSuffixNameInfo(params);
		try {
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(suffixName);
			ImageReader reader = (ImageReader) readers.next();
			ImageInputStream iis = ImageIO.createImageInputStream(params.getInputStream());
			reader.setInput(iis, true);
			result.put("width", reader.getWidth(0));
			result.put("height", reader.getHeight(0));
			if (reader.getWidth(0) > reader.getHeight(0)) {
				lengthSize = reader.getWidth(0);
			}
			else {
				lengthSize = reader.getHeight(0);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return lengthSize;
	}

	public static File multipartFileToFile(MultipartFile file) throws Exception {

		File toFile = null;
		if (file.equals("") || file.getSize() <= 0) {
			file = null;
		}
		else {
			InputStream ins = null;
			ins = file.getInputStream();
			toFile = new File(file.getOriginalFilename());
			inputStreamToFile(ins, toFile);
			ins.close();
		}
		return toFile;
	}

	private static void inputStreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getFileType(MultipartFile file) throws IOException {
		BufferedInputStream bis;
		/**
		 * 根据文件名称，获取后缀名的方式，但是不保险
		 */
		String extension = getSuffixNameInfo(file);
		bis = (BufferedInputStream) file.getInputStream();
		byte[] bs = new byte[1];
		bis.read(bs);
		String type = Integer.toHexString(bs[0] & 0xFF);
		if ("ff".equalsIgnoreCase(type))
			extension = "jpg";
		if ("89".equalsIgnoreCase(type))
			extension = "png";
		return extension;
	}

	/**
	 * 获取图片格式
	 * @param params
	 * @return
	 */
	public static String getSuffixNameInfo(MultipartFile params) {
		String result = "";
		// 图片后缀
		String suffixName = params.getOriginalFilename().substring(params.getOriginalFilename().lastIndexOf("."));
		if (suffixName.indexOf("png") > 0) {
			result = "png";
		}
		else if (suffixName.indexOf("jpg") > 0) {
			result = "jpg";
		}
		else if (suffixName.indexOf("jpeg") > 0) {
			result = "jpeg";
		}

		return result;
	}

	/**
	 * 根据指定大小压缩图片
	 * @param imageBytes 源图片字节数组
	 * @param desFileSize 指定图片大小，单位kb
	 * @return 压缩质量后的图片字节数组
	 */
	public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize) {
		if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
			return imageBytes;
		}
		long srcSize = imageBytes.length;
		double accuracy = getAccuracy(srcSize / 1024);
		// double accuracy = 0.85;
		try {
			while (imageBytes.length > desFileSize * 1024) {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
				Thumbnails.of(inputStream).scale(accuracy).outputQuality(accuracy).toOutputStream(outputStream);
				imageBytes = outputStream.toByteArray();
			}
			logger.info("【图片压缩】imageId={} | 图片原大小={}kb | 压缩后大小={}kb", "", srcSize / 1024, imageBytes.length / 1024);
		}
		catch (Exception e) {
			logger.error("【图片压缩】msg=图片压缩失败!", e);
		}
		return imageBytes;
	}

	/**
	 * 自动调节精度(经验数值)
	 * @param size 源图片大小，单位kb
	 * @return 图片压缩质量比
	 */
	private static double getAccuracy(long size) {
		double accuracy;
		if (size < 900) {
			accuracy = 0.85;
		}
		else if (size < 2047) {
			// accuracy = 0.6;
			accuracy = 0.8;
		}
		else if (size < 3275) {
			// accuracy = 0.44;
			accuracy = 0.7;
		}
		else {
			accuracy = 0.4;
		}
		return accuracy;
	}

	/**
	 * base64 转MultipartFile
	 * @param base64
	 * @return
	 */
	public static MultipartFile base64ToMultipart(String base64) {
		try {
			// 注意base64是否有头信息，如：data:image/jpeg;base64,。。。
			String[] baseStrs = base64.split(",");

			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b;
			b = decoder.decodeBuffer(baseStrs[1]);

			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}

			return new BASE64DecodedMultipartFile(b, baseStrs[0]);
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 压缩图片
	 * @return
	 */
	public static MultipartFile[] byte2Base64StringFun(MultipartFile... fileImg) {
		MultipartFile[] result = fileImg;
		// 获取图片最长边
		int imageLengthSize = ImageSizeUtil.getImageLengthOfSide(fileImg[0]);
		Long swd = fileImg[0].getSize();
		if (imageLengthSize > 4096 || swd > 2500000) {
			BASE64Encoder encoder = new BASE64Encoder();
			String imgData1;
			try {
				InputStream inputStream = fileImg[0].getInputStream();
				byte[] imgData = ImageSizeUtil.compressPicForScale(ToolsUtil.getByteArray(inputStream), 2000);
				imgData1 = "data:" + fileImg[0].getContentType() + ";base64," + encoder.encode(imgData);
				MultipartFile def = ImageSizeUtil.base64ToMultipart(imgData1);
				result[0] = def;
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}