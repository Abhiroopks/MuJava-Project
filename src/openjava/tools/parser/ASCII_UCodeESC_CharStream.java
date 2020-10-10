// 
// Decompiled by Procyon v0.5.36
// 

package openjava.tools.parser;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;

public final class ASCII_UCodeESC_CharStream
{
    public static final boolean staticFlag = false;
    public int bufpos;
    int bufsize;
    int available;
    int tokenBegin;
    private int[] bufline;
    private int[] bufcolumn;
    private int column;
    private int line;
    private Reader inputStream;
    private boolean prevCharIsCR;
    private boolean prevCharIsLF;
    private char[] nextCharBuf;
    private char[] buffer;
    private int maxNextCharInd;
    private int nextCharInd;
    private int inBuf;
    
    static final int hexval(final char c) throws IOException {
        switch (c) {
            case '0': {
                return 0;
            }
            case '1': {
                return 1;
            }
            case '2': {
                return 2;
            }
            case '3': {
                return 3;
            }
            case '4': {
                return 4;
            }
            case '5': {
                return 5;
            }
            case '6': {
                return 6;
            }
            case '7': {
                return 7;
            }
            case '8': {
                return 8;
            }
            case '9': {
                return 9;
            }
            case 'A':
            case 'a': {
                return 10;
            }
            case 'B':
            case 'b': {
                return 11;
            }
            case 'C':
            case 'c': {
                return 12;
            }
            case 'D':
            case 'd': {
                return 13;
            }
            case 'E':
            case 'e': {
                return 14;
            }
            case 'F':
            case 'f': {
                return 15;
            }
            default: {
                throw new IOException();
            }
        }
    }
    
    private final void ExpandBuff(final boolean b) {
        final char[] array = new char[this.bufsize + 2048];
        final int[] array2 = new int[this.bufsize + 2048];
        final int[] array3 = new int[this.bufsize + 2048];
        try {
            if (b) {
                System.arraycopy(this.buffer, this.tokenBegin, array, 0, this.bufsize - this.tokenBegin);
                System.arraycopy(this.buffer, 0, array, this.bufsize - this.tokenBegin, this.bufpos);
                this.buffer = array;
                System.arraycopy(this.bufline, this.tokenBegin, array2, 0, this.bufsize - this.tokenBegin);
                System.arraycopy(this.bufline, 0, array2, this.bufsize - this.tokenBegin, this.bufpos);
                this.bufline = array2;
                System.arraycopy(this.bufcolumn, this.tokenBegin, array3, 0, this.bufsize - this.tokenBegin);
                System.arraycopy(this.bufcolumn, 0, array3, this.bufsize - this.tokenBegin, this.bufpos);
                this.bufcolumn = array3;
                this.bufpos += this.bufsize - this.tokenBegin;
            }
            else {
                System.arraycopy(this.buffer, this.tokenBegin, array, 0, this.bufsize - this.tokenBegin);
                this.buffer = array;
                System.arraycopy(this.bufline, this.tokenBegin, array2, 0, this.bufsize - this.tokenBegin);
                this.bufline = array2;
                System.arraycopy(this.bufcolumn, this.tokenBegin, array3, 0, this.bufsize - this.tokenBegin);
                this.bufcolumn = array3;
                this.bufpos -= this.tokenBegin;
            }
        }
        catch (Throwable t) {
            throw new Error(t.getMessage());
        }
        final int n = this.bufsize + 2048;
        this.bufsize = n;
        this.available = n;
        this.tokenBegin = 0;
    }
    
    private final void FillBuff() throws IOException {
        if (this.maxNextCharInd == 4096) {
            final int n = 0;
            this.nextCharInd = n;
            this.maxNextCharInd = n;
        }
        try {
            final int read;
            if ((read = this.inputStream.read(this.nextCharBuf, this.maxNextCharInd, 4096 - this.maxNextCharInd)) == -1) {
                this.inputStream.close();
                throw new IOException();
            }
            this.maxNextCharInd += read;
        }
        catch (IOException ex) {
            if (this.bufpos != 0) {
                --this.bufpos;
                this.backup(0);
            }
            else {
                this.bufline[this.bufpos] = this.line;
                this.bufcolumn[this.bufpos] = this.column;
            }
            throw ex;
        }
    }
    
    private final char ReadByte() throws IOException {
        if (++this.nextCharInd >= this.maxNextCharInd) {
            this.FillBuff();
        }
        return this.nextCharBuf[this.nextCharInd];
    }
    
    public final char BeginToken() throws IOException {
        if (this.inBuf > 0) {
            --this.inBuf;
            final char[] buffer = this.buffer;
            int n;
            this.tokenBegin = ((this.bufpos == this.bufsize - 1) ? (this.bufpos = (n = 0)) : (this.bufpos = (n = this.bufpos + 1)));
            return buffer[n];
        }
        this.tokenBegin = 0;
        this.bufpos = -1;
        return this.readChar();
    }
    
    private final void AdjustBuffSize() {
        if (this.available == this.bufsize) {
            if (this.tokenBegin > 2048) {
                this.bufpos = 0;
                this.available = this.tokenBegin;
            }
            else {
                this.ExpandBuff(false);
            }
        }
        else if (this.available > this.tokenBegin) {
            this.available = this.bufsize;
        }
        else if (this.tokenBegin - this.available < 2048) {
            this.ExpandBuff(true);
        }
        else {
            this.available = this.tokenBegin;
        }
    }
    
    private final void UpdateLineColumn(final char c) {
        ++this.column;
        if (this.prevCharIsLF) {
            this.prevCharIsLF = false;
            final int line = this.line;
            final int column = 1;
            this.column = column;
            this.line = line + column;
        }
        else if (this.prevCharIsCR) {
            this.prevCharIsCR = false;
            if (c == '\n') {
                this.prevCharIsLF = true;
            }
            else {
                final int line2 = this.line;
                final int column2 = 1;
                this.column = column2;
                this.line = line2 + column2;
            }
        }
        switch (c) {
            case '\r': {
                this.prevCharIsCR = true;
                break;
            }
            case '\n': {
                this.prevCharIsLF = true;
                break;
            }
            case '\t': {
                --this.column;
                this.column += 8 - (this.column & 0x7);
                break;
            }
        }
        this.bufline[this.bufpos] = this.line;
        this.bufcolumn[this.bufpos] = this.column;
    }
    
    public final char readChar() throws IOException {
        if (this.inBuf > 0) {
            --this.inBuf;
            return this.buffer[(this.bufpos == this.bufsize - 1) ? (this.bufpos = 0) : (++this.bufpos)];
        }
        if (++this.bufpos == this.available) {
            this.AdjustBuffSize();
        }
        final char c;
        if ((this.buffer[this.bufpos] = (c = (char)('\u00ff' & this.ReadByte()))) != '\\') {
            this.UpdateLineColumn(c);
            return c;
        }
        this.UpdateLineColumn(c);
        int n = 1;
        while (true) {
            if (++this.bufpos == this.available) {
                this.AdjustBuffSize();
            }
            char c2;
            try {
                if ((this.buffer[this.bufpos] = (c2 = (char)('\u00ff' & this.ReadByte()))) != '\\') {
                    this.UpdateLineColumn(c2);
                    if (c2 == 'u' && (n & 0x1) == 0x1) {
                        if (--this.bufpos < 0) {
                            this.bufpos = this.bufsize - 1;
                        }
                        break;
                    }
                    this.backup(n);
                    return '\\';
                }
            }
            catch (IOException ex) {
                if (n > 1) {
                    this.backup(n);
                }
                return '\\';
            }
            this.UpdateLineColumn(c2);
            ++n;
        }
        char c4;
        try {
            char c3;
            while ((c3 = (char)('\u00ff' & this.ReadByte())) == 'u') {
                ++this.column;
            }
            c4 = (this.buffer[this.bufpos] = (char)(hexval(c3) << 12 | hexval((char)('\u00ff' & this.ReadByte())) << 8 | hexval((char)('\u00ff' & this.ReadByte())) << 4 | hexval((char)('\u00ff' & this.ReadByte()))));
            this.column += 4;
        }
        catch (IOException ex2) {
            throw new Error("Invalid escape character at line " + this.line + " column " + this.column + ".");
        }
        if (n == 1) {
            return c4;
        }
        this.backup(n - 1);
        return '\\';
    }
    
    @Deprecated
    public final int getColumn() {
        return this.bufcolumn[this.bufpos];
    }
    
    @Deprecated
    public final int getLine() {
        return this.bufline[this.bufpos];
    }
    
    public final int getEndColumn() {
        return this.bufcolumn[this.bufpos];
    }
    
    public final int getEndLine() {
        return this.bufline[this.bufpos];
    }
    
    public final int getBeginColumn() {
        return this.bufcolumn[this.tokenBegin];
    }
    
    public final int getBeginLine() {
        return this.bufline[this.tokenBegin];
    }
    
    public final void backup(final int n) {
        this.inBuf += n;
        final int bufpos = this.bufpos - n;
        this.bufpos = bufpos;
        if (bufpos < 0) {
            this.bufpos += this.bufsize;
        }
    }
    
    public ASCII_UCodeESC_CharStream(final Reader inputStream, final int line, final int n, final int n2) {
        this.bufpos = -1;
        this.column = 0;
        this.line = 1;
        this.prevCharIsCR = false;
        this.prevCharIsLF = false;
        this.maxNextCharInd = 0;
        this.nextCharInd = -1;
        this.inBuf = 0;
        this.inputStream = inputStream;
        this.line = line;
        this.column = n - 1;
        this.bufsize = n2;
        this.available = n2;
        this.buffer = new char[n2];
        this.bufline = new int[n2];
        this.bufcolumn = new int[n2];
        this.nextCharBuf = new char[4096];
    }
    
    public ASCII_UCodeESC_CharStream(final Reader reader, final int n, final int n2) {
        this(reader, n, n2, 4096);
    }
    
    public void ReInit(final Reader inputStream, final int line, final int n, final int n2) {
        this.inputStream = inputStream;
        this.line = line;
        this.column = n - 1;
        if (this.buffer == null || n2 != this.buffer.length) {
            this.bufsize = n2;
            this.available = n2;
            this.buffer = new char[n2];
            this.bufline = new int[n2];
            this.bufcolumn = new int[n2];
            this.nextCharBuf = new char[4096];
        }
        final boolean b = false;
        this.prevCharIsCR = b;
        this.prevCharIsLF = b;
        final int tokenBegin = 0;
        this.maxNextCharInd = tokenBegin;
        this.inBuf = tokenBegin;
        this.tokenBegin = tokenBegin;
        final int n3 = -1;
        this.bufpos = n3;
        this.nextCharInd = n3;
    }
    
    public void ReInit(final Reader reader, final int n, final int n2) {
        this.ReInit(reader, n, n2, 4096);
    }
    
    public ASCII_UCodeESC_CharStream(final InputStream in, final int n, final int n2, final int n3) {
        this(new InputStreamReader(in), n, n2, 4096);
    }
    
    public ASCII_UCodeESC_CharStream(final InputStream inputStream, final int n, final int n2) {
        this(inputStream, n, n2, 4096);
    }
    
    public void ReInit(final InputStream in, final int n, final int n2, final int n3) {
        this.ReInit(new InputStreamReader(in), n, n2, 4096);
    }
    
    public void ReInit(final InputStream inputStream, final int n, final int n2) {
        this.ReInit(inputStream, n, n2, 4096);
    }
    
    public final String GetImage() {
        if (this.bufpos >= this.tokenBegin) {
            return new String(this.buffer, this.tokenBegin, this.bufpos - this.tokenBegin + 1);
        }
        return new String(this.buffer, this.tokenBegin, this.bufsize - this.tokenBegin) + new String(this.buffer, 0, this.bufpos + 1);
    }
    
    public final char[] GetSuffix(final int n) {
        final char[] array = new char[n];
        if (this.bufpos + 1 >= n) {
            System.arraycopy(this.buffer, this.bufpos - n + 1, array, 0, n);
        }
        else {
            System.arraycopy(this.buffer, this.bufsize - (n - this.bufpos - 1), array, 0, n - this.bufpos - 1);
            System.arraycopy(this.buffer, 0, array, n - this.bufpos - 1, this.bufpos + 1);
        }
        return array;
    }
    
    public void Done() {
        this.nextCharBuf = null;
        this.buffer = null;
        this.bufline = null;
        this.bufcolumn = null;
    }
    
    public void adjustBeginLineColumn(int n, final int n2) {
        int tokenBegin = this.tokenBegin;
        int n3;
        if (this.bufpos >= this.tokenBegin) {
            n3 = this.bufpos - this.tokenBegin + this.inBuf + 1;
        }
        else {
            n3 = this.bufsize - this.tokenBegin + this.bufpos + 1 + this.inBuf;
        }
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7;
        while (n4 < n3 && this.bufline[n5 = tokenBegin % this.bufsize] == this.bufline[n7 = ++tokenBegin % this.bufsize]) {
            this.bufline[n5] = n;
            final int n8 = n6 + this.bufcolumn[n7] - this.bufcolumn[n5];
            this.bufcolumn[n5] = n2 + n6;
            n6 = n8;
            ++n4;
        }
        if (n4 < n3) {
            this.bufline[n5] = n++;
            this.bufcolumn[n5] = n2 + n6;
            while (n4++ < n3) {
                if (this.bufline[n5 = tokenBegin % this.bufsize] != this.bufline[++tokenBegin % this.bufsize]) {
                    this.bufline[n5] = n++;
                }
                else {
                    this.bufline[n5] = n;
                }
            }
        }
        this.line = this.bufline[n5];
        this.column = this.bufcolumn[n5];
    }
}
