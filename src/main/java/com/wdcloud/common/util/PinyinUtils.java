/**
 * @(#)PinyinUtils.java 2015年8月12日
 * 
 * wdcloud 版权所有2014~2015。
 */
package com.wdcloud.common.util;

import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CHENYB
 * 
 * @since 2015年8月12日
 */
public class PinyinUtils {

	private static Logger logger = LoggerFactory.getLogger(PinyinUtils.class);

	/**
	 * 传入中文汉字，转换出对应拼音
	 * 
	 * @author CHENYB
	 * 
	 * @param src
	 * @return
	 * @since 2015年8月12日 上午10:45:59
	 */
	public static String getFullSpell(String chinese) {
		if (isChinese(chinese)) {
			logger.warn("传入的汉字为[" + chinese + "]，多音字可能无法准确辨别！");
		}

		StringBuffer pinyin = new StringBuffer();
		try {
			char[] chars = chinese.toCharArray();
			String[] strs = new String[chars.length];
			// 设置汉字拼音输出的格式
			HanyuPinyinOutputFormat fmt = new HanyuPinyinOutputFormat();
			fmt.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			fmt.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			fmt.setVCharType(HanyuPinyinVCharType.WITH_V);

			for (int i = 0; i < chars.length; i++) {
				// 判断能否为汉字字符
				if (Character.toString(chars[i]).matches("[\\u4E00-\\u9FA5]+")) {
					// 将汉字的几种全拼都存到strs数组中
					strs = PinyinHelper.toHanyuPinyinStringArray(chars[i], fmt);
					// 取出该汉字全拼的第一种读音并连接到字符串rs后
					pinyin.append(strs[0]);
				} else {
					// 如果不是汉字字符，直接取出字符并连接到字符串rs后
					pinyin.append(Character.toString(chars[i]));
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return pinyin.toString();
	}

	/**
	 * 判断字符串是否含中文
	 * @author CHENYB
	 * @param str
	 * @return
	 * @since 2015年9月11日 下午6:23:15
	 */
	public static boolean isChinese(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str.trim()).find();
	}

	/**
	 * 获取汉字串拼音首字母，英文字符不变
	 * 
	 * @author CHENYB
	 * @param chinese 汉字串
	 * @return 汉语拼音首字母
	 * @since 2015年8月12日 下午12:17:49
	 */
	public static String getFirstSpell(String chinese) {
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		try {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] > 128) {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
					if (temp != null) {
						pybf.append(temp[0].charAt(0));
					}
				} else {
					pybf.append(arr[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return pybf.toString().replaceAll("\\W", "").trim();
	}

	/**
	 * 
	 * @author CHENYB
	 * 
	 * @param args
	 * @since 2015年8月12日 上午10:45:13
	 */
	public static void main(String[] args) {
		System.out.println(PinyinUtils.getFirstSpell("长江abc123"));
		System.out.println(PinyinUtils.getFirstSpell("长大abc123"));
		System.out.println(PinyinUtils.getFullSpell("长江abc123"));
		System.out.println(PinyinUtils.getFullSpell("长大abc123"));
		System.out.println(isChinese("safa中f华g人h民j共dgw和k国l"));
	}

}
