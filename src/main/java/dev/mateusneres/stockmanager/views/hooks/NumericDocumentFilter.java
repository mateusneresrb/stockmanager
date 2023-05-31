package dev.mateusneres.stockmanager.views.hooks;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumericDocumentFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
        String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
        if (isNumeric(newText)) {
            super.insertString(fb, offset, text, attrs);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
        if (isNumeric(newText)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean isNumeric(String text) {
        try {
            if (text.isEmpty()) return true;
            double value = Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}