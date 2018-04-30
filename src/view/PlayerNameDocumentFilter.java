package view;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Simple document filter implemented to limit user input in the "Player Name"
 * text field to a fixed amount of word-only characters.
 */
public class PlayerNameDocumentFilter extends DocumentFilter {

	private static final int MAX_CHARACTERS = 27;
	private static final String REGEX = "\\w{1," + MAX_CHARACTERS + "}";
	
	public PlayerNameDocumentFilter() {
	}

	/**
	 * Limits text entry to {@link PlayerNameDocumentFilter#MAX_CHARACTERS} word-only characters.
	 * 
	 * @see javax.swing.text.DocumentFilter#insertString(javax.swing.text.DocumentFilter.FilterBypass, int, java.lang.String, javax.swing.text.AttributeSet)
	 */
	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {		
		String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
		if(isValid(newText)) {
			super.insertString(fb, offset, string, attr);
		}    
	}

	/**
	 * Limits text entry to {@link PlayerNameDocumentFilter#MAX_CHARACTERS} word-only characters.
	 * 
	 * @see javax.swing.text.DocumentFilter#replace(javax.swing.text.DocumentFilter.FilterBypass, int, int, java.lang.String, javax.swing.text.AttributeSet)
	 */
	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		String originalText = fb.getDocument().getText(0, fb.getDocument().getLength());
		String newText = originalText.substring(0, offset) + text + originalText.substring(offset + length);
		if(isValid(newText)) {
			super.replace(fb, offset, length, text, attrs);
		}
	}
	
	private boolean isValid(String input) {
		return input.matches(REGEX);
	}
}
