// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

import javax.swing.ImageIcon;

public class Search implements Runnable
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public void run() {
        Crosslexic.app_setEnabled(false);
        this.wordSearch();
        Crosslexic.app_setEnabled(true);
    }
    
    protected void wordSearch() {
        int n = 0;
        int n2 = 0;
        final char[] value = new char[Crosslexic.getWordlength()];
        for (int i = 0; i < Crosslexic.getWordlength(); ++i) {
            if (Crosslexic.getLetterText()[i].getText().trim().length() > 0) {
                value[i] = Crosslexic.getLetterText()[i].getText().trim().toCharArray()[0];
            }
            else {
                value[i] = '.';
            }
        }
        if (this.valid_text() && Dictionary.open()) {
            Crosslexic.addtolist(null, "Searching on pattern: " + new String(value));
            String nextWord;
            while ((nextWord = Dictionary.getNextWord()) != "End-Of-File" && Crosslexic.getSearchThread() != null) {
                boolean b = true;
                if (++n2 % 500 == 0) {
                    Crosslexic.getProgressBar().setValue(n2 * 100 / 431000);
                }
                if (nextWord.length() == Crosslexic.getWordlength()) {
                    for (int j = 0; j < Crosslexic.getWordlength(); ++j) {
                        if (Crosslexic.getLetterText()[j].getText().trim().length() > 0 && Crosslexic.getLetterText()[j].getText().trim().toCharArray()[0] != nextWord.toLowerCase().toCharArray()[j]) {
                            b = false;
                        }
                    }
                }
                else {
                    b = false;
                }
                if (b) {
                    Crosslexic.addtolist(Crosslexic.iconMatch, "   " + nextWord);
                    ++n;
                }
            }
            if (Crosslexic.getSearchThread() != null) {
                Crosslexic.addtolist(null, "Results: " + n + " matches.");
                Crosslexic.getProgressBar().setValue(100);
            }
            else {
                Crosslexic.addtolist(null, "Results so far: " + n + " matches.");
            }
            Dictionary.close();
        }
    }
    
    private boolean valid_text() {
        boolean b = false;
        for (int i = 0; i < Crosslexic.getWordlength(); ++i) {
            if (Crosslexic.getLetterText()[i].getText().trim().length() > 0) {
                b = true;
                break;
            }
        }
        if (!b) {
            Crosslexic.addtolist_now(Crosslexic.iconAlert, "ERROR: Please enter some letters.");
        }
        return b;
    }
}
