// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

import java.util.BitSet;

public class TruthTable
{
    private int rows;
    private int vars;
    private BitSet[] bits;
    
    public TruthTable(final int vars) {
        this.vars = vars;
        this.rows = (int)Math.pow(2.0, vars);
        this.bits = new BitSet[this.rows];
    }
    
    public static void main(final String[] args) {
        final TruthTable table = new TruthTable(3);
        for (int i = 0; i < 8; ++i) {
            final BitSet bit = table.getRow(i);
            for (int j = 0; j < 3; ++j) {
                System.out.print(">" + j + ":" + bit.get(j));
            }
            System.out.println();
        }
    }
    
    public BitSet getRow(int row) {
        if (row >= this.rows) {
            return null;
        }
        row = this.rows - row - 1;
        if (this.bits[row] == null) {
            this.bits[row] = new BitSet();
            final String bitString = Integer.toBinaryString(row);
            final int start = this.vars - bitString.length();
            for (int i = 0; i < bitString.length(); ++i) {
                if (bitString.charAt(i) == '1') {
                    this.bits[row].set(start + i, true);
                }
            }
        }
        return this.bits[row];
    }
    
    public int getRowNum() {
        return this.rows;
    }
    
    public boolean getRowValue(final int row) {
        return this.bits[this.rows - row - 1].get(this.vars);
    }
    
    public boolean getValue(final int row, final int col) {
        return this.bits[this.rows - row - 1].get(col);
    }
    
    public int getVarNum() {
        return this.vars;
    }
    
    public void setRowValue(final int row, final boolean value) {
        this.bits[this.rows - row - 1].set(this.vars, value);
    }
    
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.rows; ++i) {
            result = result + i + " ";
            for (int j = 0; j < this.vars + 1; ++j) {
                result = result + printTF(this.bits[i].get(j)) + " ";
            }
            result += "\n";
        }
        return result;
    }
    
    public static String printTF(final boolean value) {
        if (value) {
            return "T";
        }
        return "F";
    }
    
    public BitSet[] getBits() {
        return this.bits;
    }
    
    public void setBits(final BitSet[] bits) {
        this.bits = bits;
    }
}
