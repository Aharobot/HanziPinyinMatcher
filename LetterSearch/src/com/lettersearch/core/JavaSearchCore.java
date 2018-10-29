package com.lettersearch.core;

/**
 * ����һ���򵥵��㷨,ʵ���˶�������ĸ����,����������ƴ��/T9ƥ��,���ļ�����,��ϵ�������ȵ���Ҫ�õ����ֵ�App
 * ���ʵ������Э����,�����뱾���޸ĵ�HanziToPinyin���໥Э�����.
 * 
 * <pre>
 * Э��: ��֤���д�����ַ�Ϊ��д
 * </pre>
 * 
 * <pre>
 *  0.2 �汾��������: ֧���˶�����ɸѡ
 * </pre>
 * 
 * @author Andme
 * @version 0.2
 * @category search,arithmetic
 */
public class JavaSearchCore {

	private static final String TAG = JavaSearchCore.class.getSimpleName();

	/**
	 * �����������㷨
	 * 
	 * @param source
	 *            ������˳���,����:���� -> ���� [LIU,ZANG CANG]-> ƴ��,,���Ѿ�����ʵ��,�ο�
	 *            {@link HanziToPinyin}��
	 * @param input
	 * @param colors
	 * @return
	 * @see HanziToPinyin
	 */
	public static boolean searchMorePinYin(Object[] source, String input, int[] colors) {
		int sourceLetter = 0, letterIndex = 0, inputIndex = 0, start = -1, end = 0, saveInputEnd = -1,
				saveInputStart = 0, saveLetter = -1;

		char[] inputchars = input.toCharArray();
		int inputjarray_length = inputchars.length;

		String source_element = source[sourceLetter].toString();
		char[] sourcechars = source_element.toCharArray();

		int source_length = source.length;
		int sourcechars_length = sourcechars.length;

		A: while (inputIndex > -1 && inputIndex < inputjarray_length && sourceLetter < source_length) {
			// ���������ַ�
			if (sourcechars_length == 1 && sourcechars[0] == ' ') {
				sourceLetter++;
				if (sourceLetter >= source_length) {
					break;
				}
				// ���ڵײ�Ľṹ,Ϊ�˽��ڴ潵�����
				source_element = source[sourceLetter].toString();
				sourcechars = source_element.toCharArray();
				sourcechars_length = sourcechars.length;
			} else if (inputchars[inputIndex] == sourcechars[letterIndex]) {
				if (letterIndex == 0) {
					saveInputStart = inputIndex;
				}
				if (saveLetter == -1) {
					saveLetter = sourceLetter + 1;
				}
				letterIndex++;
				inputIndex++;
				if (start == -1) {
					start = sourceLetter;
				}
				end = sourceLetter;
				if (inputIndex == inputjarray_length) {
					break;
				} else if (letterIndex == sourcechars_length || sourcechars[letterIndex] == ' ') {
					letterIndex = 0;
					sourceLetter++;
					if (sourceLetter >= source_length) {
						break;
					}

					source_element = source[sourceLetter].toString();
					sourcechars = source_element.toCharArray();
					sourcechars_length = sourcechars.length;
				}
			} else if (letterIndex == 0 && inputIndex > 0) {
				while (letterIndex < sourcechars_length) {
					if (sourcechars[letterIndex] == ' ') {
						if (sourcechars[letterIndex + 1] == inputchars[inputIndex]) {
							letterIndex++;
							continue A;
						}
					}
					letterIndex++;
				}
				letterIndex = 0;
				inputIndex--;
				if (inputIndex == saveInputStart && saveLetter != -1 && saveLetter < source_length - 1) {
					inputIndex = 0;
					start = -1;
					sourceLetter = saveLetter;
					source_element = source[sourceLetter].toString();
					sourcechars = source_element.toCharArray();
					sourcechars_length = sourcechars.length;
					saveLetter = -1;
					continue;
				}
				if (inputIndex <= 0) {
					start = -1;
				}
			} else {
				if (letterIndex > 0 && inputIndex > 0 && letterIndex < sourcechars_length - 1
						&& sourcechars[letterIndex] == sourcechars[letterIndex - 1]) {
					letterIndex++;
					continue;
				}
				if (sourcechars_length > 1) {
					while (letterIndex < sourcechars_length) {
						if (sourcechars[letterIndex] == ' '
								&& sourcechars[letterIndex + 1] == inputchars[saveInputStart]) {
							if (inputIndex > saveInputEnd) {
								saveInputEnd = inputIndex;
							}
							letterIndex += 2;
							inputIndex = saveInputStart + 1;
							continue A;
						}
						letterIndex++;
					}
					if (saveInputEnd > inputIndex) {
						inputIndex = saveInputEnd;
					}
					saveInputEnd = -1;
				}
				letterIndex = 0;
				sourceLetter++;
				if (sourceLetter >= source_length) {
					break;
				}
				source_element = source[sourceLetter].toString();
				sourcechars = source_element.toCharArray();
				sourcechars_length = sourcechars.length;
			}
		}

		if (inputIndex == inputjarray_length) {
			if (colors != null) {
				int csize = colors.length;
				if (csize >= 2) {
					int[] cs = colors;
					cs[0] = start;
					cs[1] = end + 1;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * ��׼:��ͨ�����㷨
	 * 
	 * @param source
	 * @param input
	 * @param colors
	 * @return
	 */
	public static boolean searchPinYin(Object[] source, String input, int[] colors) {
		int inputIndex = 0;
		int sourceLetter = 0;
		int letterIndex = 0;
		int start = -1;
		int end = 0;

		char[] inputbyte_carray = input.toCharArray();
		char[] sourcebyte_carray = source[sourceLetter].toString().toCharArray();

		while (inputIndex > -1 && inputIndex < inputbyte_carray.length && sourceLetter < source.length) {

			if (sourcebyte_carray.length == 0 || (sourcebyte_carray.length == 1 && sourcebyte_carray[0] == ' ')) {
				sourceLetter++;
				if (sourceLetter >= source.length) {
					break;
				}
				sourcebyte_carray = source[sourceLetter].toString().toCharArray();
				// ���ϴ˴���ѡ,ֻ���������Ϊ�յ��ַ�
			} else if (inputbyte_carray[inputIndex] == sourcebyte_carray[letterIndex]) {
				letterIndex++;
				inputIndex++;
				end = sourceLetter;
				if (start == -1) {
					start = sourceLetter;
				}
				if (inputIndex == inputbyte_carray.length) {
					break;
				} else if (letterIndex == sourcebyte_carray.length) {
					letterIndex = 0;
					sourceLetter++;
					if (sourceLetter >= source.length) {
						break;
					}
					sourcebyte_carray = source[sourceLetter].toString().toCharArray();
				}
			} else if (letterIndex == 0 && inputIndex > 0) {
				inputIndex--;
				if (inputIndex <= 0) {
					start = -1;
				}
			} else {
				if (letterIndex > 0 && inputIndex > 0
						&& sourcebyte_carray[letterIndex] == sourcebyte_carray[letterIndex - 1]) {
					if (letterIndex < sourcebyte_carray.length - 1) {
						letterIndex++;
						continue;
					}
				}
				letterIndex = 0;
				sourceLetter++;
				if (sourceLetter >= source.length) {
					break;
				}
				sourcebyte_carray = source[sourceLetter].toString().toCharArray();
			}
		}
		if (inputIndex == input.length()) {
			if (colors != null) {
				if (colors.length >= 2) {
					colors[0] = start;
					colors[1] = end + 1;
				}
			}
			return true;
		}
		return false;
	}
}
