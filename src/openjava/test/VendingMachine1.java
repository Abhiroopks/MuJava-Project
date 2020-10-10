// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

import java.util.LinkedList;

public class VendingMachine1
{
    private int credit;
    private LinkedList stock;
    private static final int MAX = 10;
    
    VendingMachine1() {
        this.credit = 0;
        this.stock = new LinkedList();
    }
    
    public void coin(final int coin) {
        if (coin != 10 && coin != 25 && coin != 100) {
            return;
        }
        if (this.credit >= 90) {
            return;
        }
        this.credit += coin;
    }
    
    public int getChoc(final StringBuffer choc) {
        if (this.credit < 90 || this.stock.size() <= 0) {
            final int change = 0;
            choc.replace(0, choc.length(), "");
            return change;
        }
        final int change = this.credit - 90;
        choc.replace(this.credit = 0, choc.length(), (String) this.stock.removeFirst());
        return change;
    }
    
    public void addChoc(final String choc) {
        if (this.stock.size() >= 10) {
            return;
        }
        this.stock.add(choc);
    }
    
    public int getCredit() {
        return this.credit;
    }
    
    public LinkedList getStock() {
        final Integer i = new Integer(1);
        return this.stock;
    }
}
