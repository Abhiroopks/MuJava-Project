// 
// Decompiled by Procyon v0.5.36
// 

package openjava.tools.parser;

import java.io.IOException;
import java.io.PrintStream;

public class ParserTokenManager implements ParserConstants
{
    public PrintStream debugStream;
    static final long[] jjbitVec0;
    static final long[] jjbitVec2;
    static final long[] jjbitVec3;
    static final long[] jjbitVec4;
    static final long[] jjbitVec5;
    static final long[] jjbitVec6;
    static final long[] jjbitVec7;
    static final long[] jjbitVec8;
    static final int[] jjnextStates;
    public static final String[] jjstrLiteralImages;
    public static final String[] lexStateNames;
    public static final int[] jjnewLexState;
    static final long[] jjtoToken;
    static final long[] jjtoSkip;
    static final long[] jjtoSpecial;
    static final long[] jjtoMore;
    protected JavaCharStream input_stream;
    private final int[] jjrounds;
    private final int[] jjstateSet;
    private final StringBuilder jjimage;
    private StringBuilder image;
    private int jjimageLen;
    private int lengthOfMatch;
    protected char curChar;
    int curLexState;
    int defaultLexState;
    int jjnewStateCnt;
    int jjround;
    int jjmatchedPos;
    int jjmatchedKind;
    
    public void setDebugStream(final PrintStream ds) {
        this.debugStream = ds;
    }
    
    private final int jjStopStringLiteralDfa_0(final int pos, final long active0, final long active1, final long active2) {
        switch (pos) {
            case 0: {
                if ((active1 & 0x800000000800000L) != 0x0L) {
                    return 80;
                }
                if ((active0 & 0xFFFFFFFFFFFFF000L) != 0x0L || (active1 & 0x3L) != 0x0L) {
                    this.jjmatchedKind = 76;
                    return 19;
                }
                if ((active0 & 0x80L) != 0x0L || (active1 & 0x8040000000000L) != 0x0L) {
                    return 23;
                }
                return -1;
            }
            case 1: {
                if ((active0 & 0xFFFFFFF7FCFFF000L) != 0x0L || (active1 & 0x3L) != 0x0L) {
                    if (this.jjmatchedPos != 1) {
                        this.jjmatchedKind = 76;
                        this.jjmatchedPos = 1;
                    }
                    return 19;
                }
                if ((active0 & 0x80L) != 0x0L) {
                    return 21;
                }
                if ((active0 & 0x803000000L) != 0x0L) {
                    return 19;
                }
                return -1;
            }
            case 2: {
                if ((active0 & 0xEFFFF675FEFFF000L) != 0x0L || (active1 & 0x3L) != 0x0L) {
                    if (this.jjmatchedPos != 2) {
                        this.jjmatchedKind = 76;
                        this.jjmatchedPos = 2;
                    }
                    return 19;
                }
                if ((active0 & 0x1000098200000000L) != 0x0L) {
                    return 19;
                }
                return -1;
            }
            case 3: {
                if ((active0 & 0xC77FE571F2F4F000L) != 0x0L || (active1 & 0x3L) != 0x0L) {
                    this.jjmatchedKind = 76;
                    this.jjmatchedPos = 3;
                    return 19;
                }
                if ((active0 & 0x288012040C0B0000L) != 0x0L) {
                    return 19;
                }
                return -1;
            }
            case 4: {
                if ((active0 & 0x446BE57012C07000L) != 0x0L || (active1 & 0x3L) != 0x0L) {
                    if (this.jjmatchedPos != 4) {
                        this.jjmatchedKind = 76;
                        this.jjmatchedPos = 4;
                    }
                    return 19;
                }
                if ((active0 & 0x83140001E0348000L) != 0x0L) {
                    return 19;
                }
                return -1;
            }
            case 5: {
                if ((active0 & 0x4440E15090C05000L) != 0x0L || (active1 & 0x3L) != 0x0L) {
                    this.jjmatchedKind = 76;
                    this.jjmatchedPos = 5;
                    return 19;
                }
                if ((active0 & 0x22B042002002000L) != 0x0L) {
                    return 19;
                }
                return -1;
            }
            case 6: {
                if ((active0 & 0x4440815000401000L) != 0x0L || (active1 & 0x3L) != 0x0L) {
                    this.jjmatchedKind = 76;
                    this.jjmatchedPos = 6;
                    return 19;
                }
                if ((active0 & 0x600090804000L) != 0x0L) {
                    return 19;
                }
                return -1;
            }
            case 7: {
                if ((active0 & 0x440815000000000L) != 0x0L || (active1 & 0x3L) != 0x0L) {
                    this.jjmatchedKind = 76;
                    this.jjmatchedPos = 7;
                    return 19;
                }
                if ((active0 & 0x4000000000401000L) != 0x0L) {
                    return 19;
                }
                return -1;
            }
            case 8: {
                if ((active0 & 0x40005000000000L) != 0x0L || (active1 & 0x2L) != 0x0L) {
                    this.jjmatchedKind = 76;
                    this.jjmatchedPos = 8;
                    return 19;
                }
                if ((active0 & 0x400810000000000L) != 0x0L || (active1 & 0x1L) != 0x0L) {
                    return 19;
                }
                return -1;
            }
            case 9: {
                if ((active0 & 0x40000000000000L) != 0x0L || (active1 & 0x2L) != 0x0L) {
                    this.jjmatchedKind = 76;
                    this.jjmatchedPos = 9;
                    return 19;
                }
                if ((active0 & 0x5000000000L) != 0x0L) {
                    return 19;
                }
                return -1;
            }
            case 10: {
                if ((active0 & 0x40000000000000L) != 0x0L || (active1 & 0x2L) != 0x0L) {
                    this.jjmatchedKind = 76;
                    this.jjmatchedPos = 10;
                    return 19;
                }
                return -1;
            }
            default: {
                return -1;
            }
        }
    }
    
    private final int jjStartNfa_0(final int pos, final long active0, final long active1, final long active2) {
        return this.jjMoveNfa_0(this.jjStopStringLiteralDfa_0(pos, active0, active1, active2), pos + 1);
    }
    
    private int jjStopAtPos(final int pos, final int kind) {
        this.jjmatchedKind = kind;
        return (this.jjmatchedPos = pos) + 1;
    }
    
    private int jjMoveStringLiteralDfa0_0() {
        switch (this.curChar) {
            case '\u001a': {
                return this.jjStopAtPos(0, 127);
            }
            case '!': {
                this.jjmatchedKind = 91;
                return this.jjMoveStringLiteralDfa1_0(0L, 17179869184L);
            }
            case '%': {
                this.jjmatchedKind = 110;
                return this.jjMoveStringLiteralDfa1_0(0L, 36028797018963968L);
            }
            case '&': {
                this.jjmatchedKind = 107;
                return this.jjMoveStringLiteralDfa1_0(0L, 4503668346847232L);
            }
            case '(': {
                return this.jjStopAtPos(0, 79);
            }
            case ')': {
                return this.jjStopAtPos(0, 80);
            }
            case '*': {
                this.jjmatchedKind = 105;
                return this.jjMoveStringLiteralDfa1_0(0L, 1125899906842624L);
            }
            case '+': {
                this.jjmatchedKind = 103;
                return this.jjMoveStringLiteralDfa1_0(0L, 281612415664128L);
            }
            case ',': {
                return this.jjStopAtPos(0, 86);
            }
            case '-': {
                this.jjmatchedKind = 104;
                return this.jjMoveStringLiteralDfa1_0(0L, 563224831328256L);
            }
            case '.': {
                this.jjmatchedKind = 87;
                return this.jjMoveStringLiteralDfa1_0(0L, 576460752303423488L);
            }
            case '/': {
                this.jjmatchedKind = 106;
                return this.jjMoveStringLiteralDfa1_0(128L, 2251799813685248L);
            }
            case ':': {
                return this.jjStopAtPos(0, 94);
            }
            case ';': {
                return this.jjStopAtPos(0, 85);
            }
            case '<': {
                this.jjmatchedKind = 90;
                return this.jjMoveStringLiteralDfa1_0(0L, 72198335821250560L);
            }
            case '=': {
                this.jjmatchedKind = 89;
                return this.jjMoveStringLiteralDfa1_0(0L, 2147483648L);
            }
            case '>': {
                this.jjmatchedKind = 126;
                return this.jjMoveStringLiteralDfa1_0(0L, 3891110086638043136L);
            }
            case '?': {
                return this.jjStopAtPos(0, 93);
            }
            case '@': {
                return this.jjStopAtPos(0, 88);
            }
            case '[': {
                return this.jjStopAtPos(0, 83);
            }
            case ']': {
                return this.jjStopAtPos(0, 84);
            }
            case '^': {
                this.jjmatchedKind = 109;
                return this.jjMoveStringLiteralDfa1_0(0L, 18014398509481984L);
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa1_0(12288L, 0L);
            }
            case 'b': {
                return this.jjMoveStringLiteralDfa1_0(114688L, 0L);
            }
            case 'c': {
                return this.jjMoveStringLiteralDfa1_0(8257536L, 0L);
            }
            case 'd': {
                return this.jjMoveStringLiteralDfa1_0(58720256L, 0L);
            }
            case 'e': {
                return this.jjMoveStringLiteralDfa1_0(469762048L, 0L);
            }
            case 'f': {
                return this.jjMoveStringLiteralDfa1_0(16642998272L, 0L);
            }
            case 'g': {
                return this.jjMoveStringLiteralDfa1_0(17179869184L, 0L);
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa1_0(2164663517184L, 2L);
            }
            case 'l': {
                return this.jjMoveStringLiteralDfa1_0(2199023255552L, 0L);
            }
            case 'm': {
                return this.jjMoveStringLiteralDfa1_0(0L, 1L);
            }
            case 'n': {
                return this.jjMoveStringLiteralDfa1_0(30786325577728L, 0L);
            }
            case 'p': {
                return this.jjMoveStringLiteralDfa1_0(527765581332480L, 0L);
            }
            case 'r': {
                return this.jjMoveStringLiteralDfa1_0(562949953421312L, 0L);
            }
            case 's': {
                return this.jjMoveStringLiteralDfa1_0(34902897112121344L, 0L);
            }
            case 't': {
                return this.jjMoveStringLiteralDfa1_0(2269814212194729984L, 0L);
            }
            case 'v': {
                return this.jjMoveStringLiteralDfa1_0(6917529027641081856L, 0L);
            }
            case 'w': {
                return this.jjMoveStringLiteralDfa1_0(Long.MIN_VALUE, 0L);
            }
            case '{': {
                return this.jjStopAtPos(0, 81);
            }
            case '|': {
                this.jjmatchedKind = 108;
                return this.jjMoveStringLiteralDfa1_0(0L, 9007233614479360L);
            }
            case '}': {
                return this.jjStopAtPos(0, 82);
            }
            case '~': {
                return this.jjStopAtPos(0, 92);
            }
            default: {
                return this.jjMoveNfa_0(0, 0);
            }
        }
    }
    
    private int jjMoveStringLiteralDfa1_0(final long active0, final long active1) {
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(0, active0, active1, 0L);
            return 1;
        }
        switch (this.curChar) {
            case '&': {
                if ((active1 & 0x1000000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 100);
                }
                break;
            }
            case '*': {
                if ((active0 & 0x80L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(1, 7, 21);
                }
                break;
            }
            case '+': {
                if ((active1 & 0x2000000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 101);
                }
                break;
            }
            case '-': {
                if ((active1 & 0x4000000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 102);
                }
                break;
            }
            case '.': {
                return this.jjMoveStringLiteralDfa2_0(active0, 0L, active1, 576460752303423488L);
            }
            case '<': {
                if ((active1 & 0x800000000000L) != 0x0L) {
                    this.jjmatchedKind = 111;
                    this.jjmatchedPos = 1;
                }
                return this.jjMoveStringLiteralDfa2_0(active0, 0L, active1, 72057594037927936L);
            }
            case '=': {
                if ((active1 & 0x80000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 95);
                }
                if ((active1 & 0x100000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 96);
                }
                if ((active1 & 0x200000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 97);
                }
                if ((active1 & 0x400000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 98);
                }
                if ((active1 & 0x1000000000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 112);
                }
                if ((active1 & 0x2000000000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 113);
                }
                if ((active1 & 0x4000000000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 114);
                }
                if ((active1 & 0x8000000000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 115);
                }
                if ((active1 & 0x10000000000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 116);
                }
                if ((active1 & 0x20000000000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 117);
                }
                if ((active1 & 0x40000000000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 118);
                }
                if ((active1 & 0x80000000000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 119);
                }
                break;
            }
            case '>': {
                if ((active1 & 0x2000000000000000L) != 0x0L) {
                    this.jjmatchedKind = 125;
                    this.jjmatchedPos = 1;
                }
                return this.jjMoveStringLiteralDfa2_0(active0, 0L, active1, 1585267068834414592L);
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa2_0(active0, 39582955864064L, active1, 0L);
            }
            case 'b': {
                return this.jjMoveStringLiteralDfa2_0(active0, 4096L, active1, 0L);
            }
            case 'e': {
                return this.jjMoveStringLiteralDfa2_0(active0, 571746054832128L, active1, 1L);
            }
            case 'f': {
                if ((active0 & 0x800000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(1, 35, 19);
                }
                break;
            }
            case 'h': {
                return this.jjMoveStringLiteralDfa2_0(active0, -8970044557814661120L, active1, 0L);
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa2_0(active0, 3221225472L, active1, 0L);
            }
            case 'l': {
                return this.jjMoveStringLiteralDfa2_0(active0, 4363124736L, active1, 0L);
            }
            case 'm': {
                return this.jjMoveStringLiteralDfa2_0(active0, 206158430208L, active1, 0L);
            }
            case 'n': {
                return this.jjMoveStringLiteralDfa2_0(active0, 1924279566336L, active1, 2L);
            }
            case 'o': {
                if ((active0 & 0x1000000L) != 0x0L) {
                    this.jjmatchedKind = 24;
                    this.jjmatchedPos = 1;
                }
                return this.jjMoveStringLiteralDfa2_0(active0, 6917531252474003456L, active1, 0L);
            }
            case 'r': {
                return this.jjMoveStringLiteralDfa2_0(active0, 2017823739294547968L, active1, 0L);
            }
            case 's': {
                return this.jjMoveStringLiteralDfa2_0(active0, 8192L, active1, 0L);
            }
            case 't': {
                return this.jjMoveStringLiteralDfa2_0(active0, 2251799813685248L, active1, 0L);
            }
            case 'u': {
                return this.jjMoveStringLiteralDfa2_0(active0, 4802666790125568L, active1, 0L);
            }
            case 'w': {
                return this.jjMoveStringLiteralDfa2_0(active0, 9007199254740992L, active1, 0L);
            }
            case 'x': {
                return this.jjMoveStringLiteralDfa2_0(active0, 268435456L, active1, 0L);
            }
            case 'y': {
                return this.jjMoveStringLiteralDfa2_0(active0, 18014398509547520L, active1, 0L);
            }
            case '|': {
                if ((active1 & 0x800000000L) != 0x0L) {
                    return this.jjStopAtPos(1, 99);
                }
                break;
            }
        }
        return this.jjStartNfa_0(0, active0, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa2_0(final long old0, long active0, final long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0x0L) {
            return this.jjStartNfa_0(0, old0, old1, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(1, active0, active1, 0L);
            return 2;
        }
        switch (this.curChar) {
            case '.': {
                if ((active1 & 0x800000000000000L) != 0x0L) {
                    return this.jjStopAtPos(2, 123);
                }
                break;
            }
            case '=': {
                if ((active1 & 0x100000000000000L) != 0x0L) {
                    return this.jjStopAtPos(2, 120);
                }
                if ((active1 & 0x200000000000000L) != 0x0L) {
                    return this.jjStopAtPos(2, 121);
                }
                break;
            }
            case '>': {
                if ((active1 & 0x1000000000000000L) != 0x0L) {
                    this.jjmatchedKind = 124;
                    this.jjmatchedPos = 2;
                }
                return this.jjMoveStringLiteralDfa3_0(active0, 0L, active1, 288230376151711744L);
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa3_0(active0, 290482175966969856L, active1, 0L);
            }
            case 'b': {
                return this.jjMoveStringLiteralDfa3_0(active0, 281474976710656L, active1, 0L);
            }
            case 'c': {
                return this.jjMoveStringLiteralDfa3_0(active0, 35184372088832L, active1, 0L);
            }
            case 'e': {
                return this.jjMoveStringLiteralDfa3_0(active0, 32768L, active1, 0L);
            }
            case 'f': {
                return this.jjMoveStringLiteralDfa3_0(active0, 8388608L, active1, 0L);
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa3_0(active0, -6872422662623199232L, active1, 0L);
            }
            case 'l': {
                return this.jjMoveStringLiteralDfa3_0(active0, 4611703611150303232L, active1, 0L);
            }
            case 'n': {
                return this.jjMoveStringLiteralDfa3_0(active0, 18016600760254464L, active1, 0L);
            }
            case 'o': {
                return this.jjMoveStringLiteralDfa3_0(active0, 1266641690181632L, active1, 0L);
            }
            case 'p': {
                return this.jjMoveStringLiteralDfa3_0(active0, 4503805785800704L, active1, 0L);
            }
            case 'r': {
                if ((active0 & 0x200000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(2, 33, 19);
                }
                return this.jjMoveStringLiteralDfa3_0(active0, 216172782113783808L, active1, 0L);
            }
            case 's': {
                return this.jjMoveStringLiteralDfa3_0(active0, 274945159168L, active1, 2L);
            }
            case 't': {
                if ((active0 & 0x8000000000L) != 0x0L) {
                    this.jjmatchedKind = 39;
                    this.jjmatchedPos = 2;
                }
                return this.jjMoveStringLiteralDfa3_0(active0, 568464960192512L, active1, 1L);
            }
            case 'u': {
                return this.jjMoveStringLiteralDfa3_0(active0, 576460752471195648L, active1, 0L);
            }
            case 'w': {
                if ((active0 & 0x80000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(2, 43, 19);
                }
                break;
            }
            case 'y': {
                if ((active0 & 0x1000000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(2, 60, 19);
                }
                break;
            }
        }
        return this.jjStartNfa_0(1, active0, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa3_0(final long old0, long active0, final long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0x0L) {
            return this.jjStartNfa_0(1, old0, old1, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(2, active0, active1, 0L);
            return 3;
        }
        switch (this.curChar) {
            case '=': {
                if ((active1 & 0x400000000000000L) != 0x0L) {
                    return this.jjStopAtPos(3, 122);
                }
                break;
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa4_0(active0, 4611686025952002048L, active1, 1L);
            }
            case 'b': {
                return this.jjMoveStringLiteralDfa4_0(active0, 33554432L, active1, 0L);
            }
            case 'c': {
                return this.jjMoveStringLiteralDfa4_0(active0, 18014398509744128L, active1, 0L);
            }
            case 'd': {
                if ((active0 & 0x2000000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(3, 61, 19);
                }
                break;
            }
            case 'e': {
                if ((active0 & 0x10000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(3, 16, 19);
                }
                if ((active0 & 0x20000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(3, 17, 19);
                }
                if ((active0 & 0x4000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(3, 26, 19);
                }
                if ((active0 & 0x800000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(3, 59, 19);
                }
                return this.jjMoveStringLiteralDfa4_0(active0, 4504699407441920L, active1, 0L);
            }
            case 'g': {
                if ((active0 & 0x20000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(3, 41, 19);
                }
                break;
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa4_0(active0, 4398046511104L, active1, 0L);
            }
            case 'k': {
                return this.jjMoveStringLiteralDfa4_0(active0, 35184372088832L, active1, 0L);
            }
            case 'l': {
                if ((active0 & 0x100000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(3, 44, 19);
                }
                return this.jjMoveStringLiteralDfa4_0(active0, -9223090493158572032L, active1, 0L);
            }
            case 'm': {
                if ((active0 & 0x8000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(3, 27, 19);
                }
                break;
            }
            case 'n': {
                return this.jjMoveStringLiteralDfa4_0(active0, 288230376151711744L, active1, 0L);
            }
            case 'o': {
                if ((active0 & 0x400000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(3, 34, 19);
                }
                return this.jjMoveStringLiteralDfa4_0(active0, 216172919552737280L, active1, 0L);
            }
            case 'r': {
                if ((active0 & 0x80000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(3, 19, 19);
                }
                return this.jjMoveStringLiteralDfa4_0(active0, 1125899906842624L, active1, 0L);
            }
            case 's': {
                if ((active0 & 0x80000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(3, 55, 19);
                }
                return this.jjMoveStringLiteralDfa4_0(active0, 540016640L, active1, 0L);
            }
            case 't': {
                return this.jjMoveStringLiteralDfa4_0(active0, 11400011438886912L, active1, 2L);
            }
            case 'u': {
                return this.jjMoveStringLiteralDfa4_0(active0, 562949953421312L, active1, 0L);
            }
            case 'v': {
                return this.jjMoveStringLiteralDfa4_0(active0, 70368744177664L, active1, 0L);
            }
        }
        return this.jjStartNfa_0(2, active0, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa4_0(final long old0, long active0, final long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0x0L) {
            return this.jjStartNfa_0(2, old0, old1, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(3, active0, active1, 0L);
            return 4;
        }
        switch (this.curChar) {
            case 'a': {
                return this.jjMoveStringLiteralDfa5_0(active0, 105827994173440L, active1, 2L);
            }
            case 'c': {
                return this.jjMoveStringLiteralDfa5_0(active0, 9007199254740992L, active1, 1L);
            }
            case 'e': {
                if ((active0 & 0x20000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(4, 29, 19);
                }
                if ((active0 & Long.MIN_VALUE) != 0x0L) {
                    return this.jjStartNfaWithStates_0(4, 63, 19);
                }
                return this.jjMoveStringLiteralDfa5_0(active0, 140806207848448L, active1, 0L);
            }
            case 'h': {
                if ((active0 & 0x40000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(4, 18, 19);
                }
                return this.jjMoveStringLiteralDfa5_0(active0, 18014398509481984L, active1, 0L);
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa5_0(active0, 2533274794590208L, active1, 0L);
            }
            case 'k': {
                if ((active0 & 0x8000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(4, 15, 19);
                }
                break;
            }
            case 'l': {
                if ((active0 & 0x40000000L) != 0x0L) {
                    this.jjmatchedKind = 30;
                    this.jjmatchedPos = 4;
                }
                return this.jjMoveStringLiteralDfa5_0(active0, 2181038080L, active1, 0L);
            }
            case 'n': {
                return this.jjMoveStringLiteralDfa5_0(active0, 268435456L, active1, 0L);
            }
            case 'r': {
                if ((active0 & 0x10000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(4, 52, 19);
                }
                return this.jjMoveStringLiteralDfa5_0(active0, 564186904014848L, active1, 0L);
            }
            case 's': {
                if ((active0 & 0x100000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(4, 20, 19);
                }
                return this.jjMoveStringLiteralDfa5_0(active0, 288230376151711744L, active1, 0L);
            }
            case 't': {
                if ((active0 & 0x200000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(4, 21, 19);
                }
                if ((active0 & 0x100000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(4, 32, 19);
                }
                if ((active0 & 0x4000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(4, 50, 19);
                }
                return this.jjMoveStringLiteralDfa5_0(active0, 4611686018427387904L, active1, 0L);
            }
            case 'u': {
                return this.jjMoveStringLiteralDfa5_0(active0, 8388608L, active1, 0L);
            }
            case 'v': {
                return this.jjMoveStringLiteralDfa5_0(active0, 4398046511104L, active1, 0L);
            }
            case 'w': {
                if ((active0 & 0x100000000000000L) != 0x0L) {
                    this.jjmatchedKind = 56;
                    this.jjmatchedPos = 4;
                }
                return this.jjMoveStringLiteralDfa5_0(active0, 144115188075855872L, active1, 0L);
            }
        }
        return this.jjStartNfa_0(3, active0, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa5_0(final long old0, long active0, final long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0x0L) {
            return this.jjStartNfa_0(3, old0, old1, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(4, active0, active1, 0L);
            return 5;
        }
        switch (this.curChar) {
            case 'a': {
                return this.jjMoveStringLiteralDfa6_0(active0, 20480L, active1, 0L);
            }
            case 'c': {
                if ((active0 & 0x1000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(5, 48, 19);
                }
                if ((active0 & 0x8000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(5, 51, 19);
                }
                return this.jjMoveStringLiteralDfa6_0(active0, 140737488355328L, active1, 0L);
            }
            case 'd': {
                return this.jjMoveStringLiteralDfa6_0(active0, 268435456L, active1, 0L);
            }
            case 'e': {
                if ((active0 & 0x2000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(5, 25, 19);
                }
                if ((active0 & 0x40000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(5, 42, 19);
                }
                break;
            }
            case 'f': {
                return this.jjMoveStringLiteralDfa6_0(active0, 1099511627776L, active1, 0L);
            }
            case 'g': {
                return this.jjMoveStringLiteralDfa6_0(active0, 35184372088832L, active1, 0L);
            }
            case 'h': {
                if ((active0 & 0x20000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(5, 53, 19);
                }
                break;
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa6_0(active0, 4899916394579099648L, active1, 0L);
            }
            case 'l': {
                return this.jjMoveStringLiteralDfa6_0(active0, 2155872256L, active1, 1L);
            }
            case 'm': {
                return this.jjMoveStringLiteralDfa6_0(active0, 68719476736L, active1, 0L);
            }
            case 'n': {
                if ((active0 & 0x2000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(5, 49, 19);
                }
                return this.jjMoveStringLiteralDfa6_0(active0, 274882101248L, active1, 2L);
            }
            case 'r': {
                return this.jjMoveStringLiteralDfa6_0(active0, 18014398509481984L, active1, 0L);
            }
            case 's': {
                if ((active0 & 0x200000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(5, 57, 19);
                }
                break;
            }
            case 't': {
                if ((active0 & 0x2000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(5, 13, 19);
                }
                if ((active0 & 0x2000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(5, 37, 19);
                }
                return this.jjMoveStringLiteralDfa6_0(active0, 70368744177664L, active1, 0L);
            }
        }
        return this.jjStartNfa_0(4, active0, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa6_0(final long old0, long active0, final long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0x0L) {
            return this.jjStartNfa_0(4, old0, old1, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(5, active0, active1, 0L);
            return 6;
        }
        switch (this.curChar) {
            case 'a': {
                return this.jjMoveStringLiteralDfa7_0(active0, 1099511627776L, active1, 1L);
            }
            case 'c': {
                return this.jjMoveStringLiteralDfa7_0(active0, 274877911040L, active1, 0L);
            }
            case 'e': {
                if ((active0 & 0x200000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(6, 45, 19);
                }
                if ((active0 & 0x400000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(6, 46, 19);
                }
                return this.jjMoveStringLiteralDfa7_0(active0, 288230444871188480L, active1, 0L);
            }
            case 'l': {
                return this.jjMoveStringLiteralDfa7_0(active0, 4611686018427387904L, active1, 0L);
            }
            case 'n': {
                if ((active0 & 0x4000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(6, 14, 19);
                }
                break;
            }
            case 'o': {
                return this.jjMoveStringLiteralDfa7_0(active0, 18014398509481984L, active1, 0L);
            }
            case 's': {
                if ((active0 & 0x10000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(6, 28, 19);
                }
                break;
            }
            case 't': {
                if ((active0 & 0x800000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(6, 23, 19);
                }
                return this.jjMoveStringLiteralDfa7_0(active0, 140737488355328L, active1, 2L);
            }
            case 'u': {
                return this.jjMoveStringLiteralDfa7_0(active0, 4194304L, active1, 0L);
            }
            case 'y': {
                if ((active0 & 0x80000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(6, 31, 19);
                }
                break;
            }
        }
        return this.jjStartNfa_0(5, active0, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa7_0(final long old0, long active0, final long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0x0L) {
            return this.jjStartNfa_0(5, old0, old1, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(6, active0, active1, 0L);
            return 7;
        }
        switch (this.curChar) {
            case 'c': {
                return this.jjMoveStringLiteralDfa8_0(active0, 1099511627776L, active1, 0L);
            }
            case 'e': {
                if ((active0 & 0x400000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(7, 22, 19);
                }
                if ((active0 & 0x4000000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(7, 62, 19);
                }
                return this.jjMoveStringLiteralDfa8_0(active0, 141012366262272L, active1, 0L);
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa8_0(active0, 0L, active1, 2L);
            }
            case 'n': {
                return this.jjMoveStringLiteralDfa8_0(active0, 306244843380670464L, active1, 0L);
            }
            case 's': {
                return this.jjMoveStringLiteralDfa8_0(active0, 0L, active1, 1L);
            }
            case 't': {
                if ((active0 & 0x1000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(7, 12, 19);
                }
                break;
            }
        }
        return this.jjStartNfa_0(6, active0, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa8_0(final long old0, long active0, final long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0x0L) {
            return this.jjStartNfa_0(6, old0, old1, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(7, active0, active1, 0L);
            return 8;
        }
        switch (this.curChar) {
            case 'a': {
                return this.jjMoveStringLiteralDfa9_0(active0, 0L, active1, 2L);
            }
            case 'd': {
                if ((active0 & 0x800000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(8, 47, 19);
                }
                break;
            }
            case 'e': {
                if ((active0 & 0x10000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(8, 40, 19);
                }
                break;
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa9_0(active0, 18014398509481984L, active1, 0L);
            }
            case 'o': {
                return this.jjMoveStringLiteralDfa9_0(active0, 274877906944L, active1, 0L);
            }
            case 's': {
                if ((active1 & 0x1L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(8, 64, 19);
                }
                break;
            }
            case 't': {
                if ((active0 & 0x400000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(8, 58, 19);
                }
                return this.jjMoveStringLiteralDfa9_0(active0, 68719476736L, active1, 0L);
            }
        }
        return this.jjStartNfa_0(7, active0, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa9_0(final long old0, long active0, final long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0x0L) {
            return this.jjStartNfa_0(7, old0, old1, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(8, active0, active1, 0L);
            return 9;
        }
        switch (this.curChar) {
            case 'f': {
                if ((active0 & 0x4000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(9, 38, 19);
                }
                break;
            }
            case 's': {
                if ((active0 & 0x1000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(9, 36, 19);
                }
                break;
            }
            case 't': {
                return this.jjMoveStringLiteralDfa10_0(active0, 0L, active1, 2L);
            }
            case 'z': {
                return this.jjMoveStringLiteralDfa10_0(active0, 18014398509481984L, active1, 0L);
            }
        }
        return this.jjStartNfa_0(8, active0, active1, 0L);
    }
    
    private int jjMoveStringLiteralDfa10_0(final long old0, long active0, final long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0x0L) {
            return this.jjStartNfa_0(8, old0, old1, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(9, active0, active1, 0L);
            return 10;
        }
        switch (this.curChar) {
            case 'e': {
                return this.jjMoveStringLiteralDfa11_0(active0, 18014398509481984L, active1, 2L);
            }
            default: {
                return this.jjStartNfa_0(9, active0, active1, 0L);
            }
        }
    }
    
    private int jjMoveStringLiteralDfa11_0(final long old0, long active0, final long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0x0L) {
            return this.jjStartNfa_0(9, old0, old1, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(10, active0, active1, 0L);
            return 11;
        }
        switch (this.curChar) {
            case 'd': {
                if ((active0 & 0x40000000000000L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(11, 54, 19);
                }
                break;
            }
            case 's': {
                if ((active1 & 0x2L) != 0x0L) {
                    return this.jjStartNfaWithStates_0(11, 65, 19);
                }
                break;
            }
        }
        return this.jjStartNfa_0(10, active0, active1, 0L);
    }
    
    private int jjStartNfaWithStates_0(final int pos, final int kind, final int state) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return pos + 1;
        }
        return this.jjMoveNfa_0(state, pos + 1);
    }
    
    private int jjMoveNfa_0(final int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 80;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            if (++this.jjround == Integer.MAX_VALUE) {
                this.ReInitRounds();
            }
            if (this.curChar < '@') {
                final long l = 1L << this.curChar;
                do {
                    switch (this.jjstateSet[--i]) {
                        case 80: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                if (kind > 72) {
                                    kind = 72;
                                }
                                this.jjCheckNAddStates(0, 2);
                            }
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddStates(3, 5);
                                continue;
                            }
                            continue;
                        }
                        case 0: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddStates(6, 19);
                            }
                            else if (this.curChar == '.') {
                                this.jjCheckNAddTwoStates(34, 39);
                            }
                            else if (this.curChar == '/') {
                                this.jjAddStates(20, 21);
                            }
                            else if (this.curChar == '$') {
                                if (kind > 76) {
                                    kind = 76;
                                }
                                this.jjCheckNAdd(19);
                            }
                            else if (this.curChar == '\"') {
                                this.jjCheckNAddStates(22, 24);
                            }
                            else if (this.curChar == '\'') {
                                this.jjAddStates(25, 26);
                            }
                            if ((0x3FE000000000000L & l) != 0x0L) {
                                if (kind > 66) {
                                    kind = 66;
                                }
                                this.jjCheckNAddStates(27, 29);
                                continue;
                            }
                            if (this.curChar == '0') {
                                if (kind > 66) {
                                    kind = 66;
                                }
                                this.jjCheckNAddStates(30, 34);
                                continue;
                            }
                            continue;
                        }
                        case 23: {
                            if (this.curChar == '/') {
                                if (kind > 8) {
                                    kind = 8;
                                }
                                this.jjCheckNAddStates(35, 37);
                                continue;
                            }
                            if (this.curChar == '*') {
                                this.jjstateSet[this.jjnewStateCnt++] = 21;
                                continue;
                            }
                            continue;
                        }
                        case 1: {
                            if ((0xFFFFFF7FFFFFDBFFL & l) != 0x0L) {
                                this.jjCheckNAdd(2);
                                continue;
                            }
                            continue;
                        }
                        case 2: {
                            if (this.curChar == '\'' && kind > 74) {
                                kind = 74;
                                continue;
                            }
                            continue;
                        }
                        case 4: {
                            if ((0x8400000000L & l) != 0x0L) {
                                this.jjCheckNAdd(2);
                                continue;
                            }
                            continue;
                        }
                        case 5: {
                            if ((0xFF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(6, 2);
                                continue;
                            }
                            continue;
                        }
                        case 6: {
                            if ((0xFF000000000000L & l) != 0x0L) {
                                this.jjCheckNAdd(2);
                                continue;
                            }
                            continue;
                        }
                        case 7: {
                            if ((0xF000000000000L & l) != 0x0L) {
                                this.jjstateSet[this.jjnewStateCnt++] = 8;
                                continue;
                            }
                            continue;
                        }
                        case 8: {
                            if ((0xFF000000000000L & l) != 0x0L) {
                                this.jjCheckNAdd(6);
                                continue;
                            }
                            continue;
                        }
                        case 9: {
                            if (this.curChar == '\"') {
                                this.jjCheckNAddStates(22, 24);
                                continue;
                            }
                            continue;
                        }
                        case 10: {
                            if ((0xFFFFFFFBFFFFDBFFL & l) != 0x0L) {
                                this.jjCheckNAddStates(22, 24);
                                continue;
                            }
                            continue;
                        }
                        case 12: {
                            if ((0x8400000000L & l) != 0x0L) {
                                this.jjCheckNAddStates(22, 24);
                                continue;
                            }
                            continue;
                        }
                        case 13: {
                            if (this.curChar == '\"' && kind > 75) {
                                kind = 75;
                                continue;
                            }
                            continue;
                        }
                        case 14: {
                            if ((0xFF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddStates(38, 41);
                                continue;
                            }
                            continue;
                        }
                        case 15: {
                            if ((0xFF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddStates(22, 24);
                                continue;
                            }
                            continue;
                        }
                        case 16: {
                            if ((0xF000000000000L & l) != 0x0L) {
                                this.jjstateSet[this.jjnewStateCnt++] = 17;
                                continue;
                            }
                            continue;
                        }
                        case 17: {
                            if ((0xFF000000000000L & l) != 0x0L) {
                                this.jjCheckNAdd(15);
                                continue;
                            }
                            continue;
                        }
                        case 18: {
                            if (this.curChar != '$') {
                                continue;
                            }
                            if (kind > 76) {
                                kind = 76;
                            }
                            this.jjCheckNAdd(19);
                            continue;
                        }
                        case 19: {
                            if ((0x3FF001000000000L & l) == 0x0L) {
                                continue;
                            }
                            if (kind > 76) {
                                kind = 76;
                            }
                            this.jjCheckNAdd(19);
                            continue;
                        }
                        case 20: {
                            if (this.curChar == '/') {
                                this.jjAddStates(20, 21);
                                continue;
                            }
                            continue;
                        }
                        case 21: {
                            if (this.curChar == '*') {
                                this.jjstateSet[this.jjnewStateCnt++] = 22;
                                continue;
                            }
                            continue;
                        }
                        case 22: {
                            if ((0xFFFF7FFFFFFFFFFFL & l) != 0x0L && kind > 6) {
                                kind = 6;
                                continue;
                            }
                            continue;
                        }
                        case 24: {
                            if (this.curChar != '/') {
                                continue;
                            }
                            if (kind > 8) {
                                kind = 8;
                            }
                            this.jjCheckNAddStates(35, 37);
                            continue;
                        }
                        case 25: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0x0L) {
                                continue;
                            }
                            if (kind > 8) {
                                kind = 8;
                            }
                            this.jjCheckNAddStates(35, 37);
                            continue;
                        }
                        case 26: {
                            if ((0x2400L & l) != 0x0L && kind > 8) {
                                kind = 8;
                                continue;
                            }
                            continue;
                        }
                        case 27: {
                            if (this.curChar == '\n' && kind > 8) {
                                kind = 8;
                                continue;
                            }
                            continue;
                        }
                        case 28: {
                            if (this.curChar == '\r') {
                                this.jjstateSet[this.jjnewStateCnt++] = 27;
                                continue;
                            }
                            continue;
                        }
                        case 29: {
                            if ((0x3FE000000000000L & l) == 0x0L) {
                                continue;
                            }
                            if (kind > 66) {
                                kind = 66;
                            }
                            this.jjCheckNAddStates(27, 29);
                            continue;
                        }
                        case 30: {
                            if ((0x3FF000000000000L & l) == 0x0L) {
                                continue;
                            }
                            if (kind > 66) {
                                kind = 66;
                            }
                            this.jjCheckNAdd(30);
                            continue;
                        }
                        case 31: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(31, 32);
                                continue;
                            }
                            continue;
                        }
                        case 33: {
                            if (this.curChar == '.') {
                                this.jjCheckNAddTwoStates(34, 39);
                                continue;
                            }
                            continue;
                        }
                        case 34: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddStates(3, 5);
                                continue;
                            }
                            continue;
                        }
                        case 36: {
                            if ((0x280000000000L & l) != 0x0L) {
                                this.jjCheckNAdd(37);
                                continue;
                            }
                            continue;
                        }
                        case 37: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(37, 38);
                                continue;
                            }
                            continue;
                        }
                        case 39: {
                            if ((0x3FF000000000000L & l) == 0x0L) {
                                continue;
                            }
                            if (kind > 72) {
                                kind = 72;
                            }
                            this.jjCheckNAddStates(0, 2);
                            continue;
                        }
                        case 41: {
                            if ((0x280000000000L & l) != 0x0L) {
                                this.jjCheckNAdd(42);
                                continue;
                            }
                            continue;
                        }
                        case 42: {
                            if ((0x3FF000000000000L & l) == 0x0L) {
                                continue;
                            }
                            if (kind > 72) {
                                kind = 72;
                            }
                            this.jjCheckNAddTwoStates(42, 43);
                            continue;
                        }
                        case 44: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddStates(6, 19);
                                continue;
                            }
                            continue;
                        }
                        case 45: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(45, 46);
                                continue;
                            }
                            continue;
                        }
                        case 46: {
                            if (this.curChar != '.') {
                                continue;
                            }
                            if (kind > 71) {
                                kind = 71;
                            }
                            this.jjCheckNAddStates(42, 44);
                            continue;
                        }
                        case 47: {
                            if ((0x3FF000000000000L & l) == 0x0L) {
                                continue;
                            }
                            if (kind > 71) {
                                kind = 71;
                            }
                            this.jjCheckNAddStates(42, 44);
                            continue;
                        }
                        case 49: {
                            if ((0x280000000000L & l) != 0x0L) {
                                this.jjCheckNAdd(50);
                                continue;
                            }
                            continue;
                        }
                        case 50: {
                            if ((0x3FF000000000000L & l) == 0x0L) {
                                continue;
                            }
                            if (kind > 71) {
                                kind = 71;
                            }
                            this.jjCheckNAddTwoStates(50, 38);
                            continue;
                        }
                        case 51: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(51, 52);
                                continue;
                            }
                            continue;
                        }
                        case 53: {
                            if ((0x280000000000L & l) != 0x0L) {
                                this.jjCheckNAdd(54);
                                continue;
                            }
                            continue;
                        }
                        case 54: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(54, 38);
                                continue;
                            }
                            continue;
                        }
                        case 55: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddStates(45, 47);
                                continue;
                            }
                            continue;
                        }
                        case 57: {
                            if ((0x280000000000L & l) != 0x0L) {
                                this.jjCheckNAdd(58);
                                continue;
                            }
                            continue;
                        }
                        case 58: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(58, 38);
                                continue;
                            }
                            continue;
                        }
                        case 59: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(59, 60);
                                continue;
                            }
                            continue;
                        }
                        case 60: {
                            if (this.curChar == '.') {
                                this.jjCheckNAddStates(48, 50);
                                continue;
                            }
                            continue;
                        }
                        case 61: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddStates(48, 50);
                                continue;
                            }
                            continue;
                        }
                        case 63: {
                            if ((0x280000000000L & l) != 0x0L) {
                                this.jjCheckNAdd(64);
                                continue;
                            }
                            continue;
                        }
                        case 64: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(64, 43);
                                continue;
                            }
                            continue;
                        }
                        case 65: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(65, 66);
                                continue;
                            }
                            continue;
                        }
                        case 67: {
                            if ((0x280000000000L & l) != 0x0L) {
                                this.jjCheckNAdd(68);
                                continue;
                            }
                            continue;
                        }
                        case 68: {
                            if ((0x3FF000000000000L & l) == 0x0L) {
                                continue;
                            }
                            if (kind > 72) {
                                kind = 72;
                            }
                            this.jjCheckNAddTwoStates(68, 43);
                            continue;
                        }
                        case 69: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddStates(51, 53);
                                continue;
                            }
                            continue;
                        }
                        case 71: {
                            if ((0x280000000000L & l) != 0x0L) {
                                this.jjCheckNAdd(72);
                                continue;
                            }
                            continue;
                        }
                        case 72: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(72, 43);
                                continue;
                            }
                            continue;
                        }
                        case 73: {
                            if (this.curChar != '0') {
                                continue;
                            }
                            if (kind > 66) {
                                kind = 66;
                            }
                            this.jjCheckNAddStates(30, 34);
                            continue;
                        }
                        case 75: {
                            if ((0x3FF000000000000L & l) == 0x0L) {
                                continue;
                            }
                            if (kind > 66) {
                                kind = 66;
                            }
                            this.jjstateSet[this.jjnewStateCnt++] = 75;
                            continue;
                        }
                        case 76: {
                            if ((0xFF000000000000L & l) == 0x0L) {
                                continue;
                            }
                            if (kind > 66) {
                                kind = 66;
                            }
                            this.jjCheckNAdd(76);
                            continue;
                        }
                        case 78: {
                            if ((0x3FF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(78, 32);
                                continue;
                            }
                            continue;
                        }
                        case 79: {
                            if ((0xFF000000000000L & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(79, 32);
                                continue;
                            }
                            continue;
                        }
                        default: {
                            continue;
                        }
                    }
                } while (i != startsAt);
            }
            else if (this.curChar < '\u0080') {
                final long l = 1L << (this.curChar & '?');
                do {
                    switch (this.jjstateSet[--i]) {
                        case 0:
                        case 19: {
                            if ((0x7FFFFFE87FFFFFEL & l) == 0x0L) {
                                continue;
                            }
                            if (kind > 76) {
                                kind = 76;
                            }
                            this.jjCheckNAdd(19);
                            continue;
                        }
                        case 1: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) != 0x0L) {
                                this.jjCheckNAdd(2);
                                continue;
                            }
                            continue;
                        }
                        case 3: {
                            if (this.curChar == '\\') {
                                this.jjAddStates(54, 56);
                                continue;
                            }
                            continue;
                        }
                        case 4: {
                            if ((0x14404410000000L & l) != 0x0L) {
                                this.jjCheckNAdd(2);
                                continue;
                            }
                            continue;
                        }
                        case 10: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) != 0x0L) {
                                this.jjCheckNAddStates(22, 24);
                                continue;
                            }
                            continue;
                        }
                        case 11: {
                            if (this.curChar == '\\') {
                                this.jjAddStates(57, 59);
                                continue;
                            }
                            continue;
                        }
                        case 12: {
                            if ((0x14404410000000L & l) != 0x0L) {
                                this.jjCheckNAddStates(22, 24);
                                continue;
                            }
                            continue;
                        }
                        case 22: {
                            if (kind > 6) {
                                kind = 6;
                                continue;
                            }
                            continue;
                        }
                        case 32: {
                            if ((0x100000001000L & l) != 0x0L && kind > 67) {
                                kind = 67;
                                continue;
                            }
                            continue;
                        }
                        case 35: {
                            if ((0x2000000020L & l) != 0x0L) {
                                this.jjAddStates(60, 61);
                                continue;
                            }
                            continue;
                        }
                        case 38: {
                            if ((0x1000000010L & l) != 0x0L && kind > 71) {
                                kind = 71;
                                continue;
                            }
                            continue;
                        }
                        case 40: {
                            if ((0x2000000020L & l) != 0x0L) {
                                this.jjAddStates(62, 63);
                                continue;
                            }
                            continue;
                        }
                        case 43: {
                            if ((0x4000000040L & l) != 0x0L && kind > 72) {
                                kind = 72;
                                continue;
                            }
                            continue;
                        }
                        case 48: {
                            if ((0x2000000020L & l) != 0x0L) {
                                this.jjAddStates(64, 65);
                                continue;
                            }
                            continue;
                        }
                        case 52: {
                            if ((0x2000000020L & l) != 0x0L) {
                                this.jjAddStates(66, 67);
                                continue;
                            }
                            continue;
                        }
                        case 56: {
                            if ((0x2000000020L & l) != 0x0L) {
                                this.jjAddStates(68, 69);
                                continue;
                            }
                            continue;
                        }
                        case 62: {
                            if ((0x2000000020L & l) != 0x0L) {
                                this.jjAddStates(70, 71);
                                continue;
                            }
                            continue;
                        }
                        case 66: {
                            if ((0x2000000020L & l) != 0x0L) {
                                this.jjAddStates(72, 73);
                                continue;
                            }
                            continue;
                        }
                        case 70: {
                            if ((0x2000000020L & l) != 0x0L) {
                                this.jjAddStates(74, 75);
                                continue;
                            }
                            continue;
                        }
                        case 74: {
                            if ((0x100000001000000L & l) != 0x0L) {
                                this.jjCheckNAdd(75);
                                continue;
                            }
                            continue;
                        }
                        case 75: {
                            if ((0x7E0000007EL & l) == 0x0L) {
                                continue;
                            }
                            if (kind > 66) {
                                kind = 66;
                            }
                            this.jjCheckNAdd(75);
                            continue;
                        }
                        case 77: {
                            if ((0x100000001000000L & l) != 0x0L) {
                                this.jjCheckNAdd(78);
                                continue;
                            }
                            continue;
                        }
                        case 78: {
                            if ((0x7E0000007EL & l) != 0x0L) {
                                this.jjCheckNAddTwoStates(78, 32);
                                continue;
                            }
                            continue;
                        }
                        default: {
                            continue;
                        }
                        case 25: {
                            if (kind > 8) {
                                kind = 8;
                            }
                            this.jjAddStates(35, 37);
                            continue;
                        }
                    }
                } while (i != startsAt);
            }
            else {
                final int hiByte = this.curChar >> 8;
                final int i2 = hiByte >> 6;
                final long l2 = 1L << (hiByte & 0x3F);
                final int i3 = (this.curChar & '\u00ff') >> 6;
                final long l3 = 1L << (this.curChar & '?');
                do {
                    switch (this.jjstateSet[--i]) {
                        case 0:
                        case 19: {
                            if (!jjCanMove_1(hiByte, i2, i3, l2, l3)) {
                                continue;
                            }
                            if (kind > 76) {
                                kind = 76;
                            }
                            this.jjCheckNAdd(19);
                            continue;
                        }
                        case 1: {
                            if (jjCanMove_0(hiByte, i2, i3, l2, l3)) {
                                this.jjstateSet[this.jjnewStateCnt++] = 2;
                                continue;
                            }
                            continue;
                        }
                        case 10: {
                            if (jjCanMove_0(hiByte, i2, i3, l2, l3)) {
                                this.jjAddStates(22, 24);
                                continue;
                            }
                            continue;
                        }
                        case 22: {
                            if (jjCanMove_0(hiByte, i2, i3, l2, l3) && kind > 6) {
                                kind = 6;
                                continue;
                            }
                            continue;
                        }
                        case 25: {
                            if (!jjCanMove_0(hiByte, i2, i3, l2, l3)) {
                                continue;
                            }
                            if (kind > 8) {
                                kind = 8;
                            }
                            this.jjAddStates(35, 37);
                            continue;
                        }
                        default: {
                            continue;
                        }
                    }
                } while (i != startsAt);
            }
            if (kind != Integer.MAX_VALUE) {
                this.jjmatchedKind = kind;
                this.jjmatchedPos = curPos;
                kind = Integer.MAX_VALUE;
            }
            ++curPos;
            final int n = i = this.jjnewStateCnt;
            final int n2 = 80;
            final int jjnewStateCnt = startsAt;
            this.jjnewStateCnt = jjnewStateCnt;
            if (n == (startsAt = n2 - jjnewStateCnt)) {
                break;
            }
            try {
                this.curChar = this.input_stream.readChar();
            }
            catch (IOException e) {
                return curPos;
            }
        }
        return curPos;
    }
    
    private int jjMoveStringLiteralDfa0_2() {
        switch (this.curChar) {
            case '*': {
                return this.jjMoveStringLiteralDfa1_2(1024L);
            }
            default: {
                return 1;
            }
        }
    }
    
    private int jjMoveStringLiteralDfa1_2(final long active0) {
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return 1;
        }
        switch (this.curChar) {
            case '/': {
                if ((active0 & 0x400L) != 0x0L) {
                    return this.jjStopAtPos(1, 10);
                }
                return 2;
            }
            default: {
                return 2;
            }
        }
    }
    
    private int jjMoveStringLiteralDfa0_1() {
        switch (this.curChar) {
            case '*': {
                return this.jjMoveStringLiteralDfa1_1(512L);
            }
            default: {
                return 1;
            }
        }
    }
    
    private int jjMoveStringLiteralDfa1_1(final long active0) {
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return 1;
        }
        switch (this.curChar) {
            case '/': {
                if ((active0 & 0x200L) != 0x0L) {
                    return this.jjStopAtPos(1, 9);
                }
                return 2;
            }
            default: {
                return 2;
            }
        }
    }
    
    private static final boolean jjCanMove_0(final int hiByte, final int i1, final int i2, final long l1, final long l2) {
        switch (hiByte) {
            case 0: {
                return (ParserTokenManager.jjbitVec2[i2] & l2) != 0x0L;
            }
            default: {
                return (ParserTokenManager.jjbitVec0[i1] & l1) != 0x0L;
            }
        }
    }
    
    private static final boolean jjCanMove_1(final int hiByte, final int i1, final int i2, final long l1, final long l2) {
        switch (hiByte) {
            case 0: {
                return (ParserTokenManager.jjbitVec4[i2] & l2) != 0x0L;
            }
            case 48: {
                return (ParserTokenManager.jjbitVec5[i2] & l2) != 0x0L;
            }
            case 49: {
                return (ParserTokenManager.jjbitVec6[i2] & l2) != 0x0L;
            }
            case 51: {
                return (ParserTokenManager.jjbitVec7[i2] & l2) != 0x0L;
            }
            case 61: {
                return (ParserTokenManager.jjbitVec8[i2] & l2) != 0x0L;
            }
            default: {
                return (ParserTokenManager.jjbitVec3[i1] & l1) != 0x0L;
            }
        }
    }
    
    public ParserTokenManager(final JavaCharStream stream) {
        this.debugStream = System.out;
        this.jjrounds = new int[80];
        this.jjstateSet = new int[160];
        this.jjimage = new StringBuilder();
        this.image = this.jjimage;
        this.curLexState = 0;
        this.defaultLexState = 0;
        this.input_stream = stream;
    }
    
    public ParserTokenManager(final JavaCharStream stream, final int lexState) {
        this(stream);
        this.SwitchTo(lexState);
    }
    
    public void ReInit(final JavaCharStream stream) {
        final int n = 0;
        this.jjnewStateCnt = n;
        this.jjmatchedPos = n;
        this.curLexState = this.defaultLexState;
        this.input_stream = stream;
        this.ReInitRounds();
    }
    
    private void ReInitRounds() {
        this.jjround = -2147483647;
        int i = 80;
        while (i-- > 0) {
            this.jjrounds[i] = Integer.MIN_VALUE;
        }
    }
    
    public void ReInit(final JavaCharStream stream, final int lexState) {
        this.ReInit(stream);
        this.SwitchTo(lexState);
    }
    
    public void SwitchTo(final int lexState) {
        if (lexState >= 3 || lexState < 0) {
            throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
        }
        this.curLexState = lexState;
    }
    
    protected Token jjFillToken() {
        final String im = ParserTokenManager.jjstrLiteralImages[this.jjmatchedKind];
        final String curTokenImage = (im == null) ? this.input_stream.GetImage() : im;
        final int beginLine = this.input_stream.getBeginLine();
        final int beginColumn = this.input_stream.getBeginColumn();
        final int endLine = this.input_stream.getEndLine();
        final int endColumn = this.input_stream.getEndColumn();
        final Token t = MyToken.newToken(this.jjmatchedKind, curTokenImage);
        t.beginLine = beginLine;
        t.endLine = endLine;
        t.beginColumn = beginColumn;
        t.endColumn = endColumn;
        return t;
    }
    
    public Token getNextToken() {
        Token specialToken = null;
        int curPos = 0;
    Label_0004_Outer:
        while (true) {
        Label_0004:
            while (true) {
                try {
                    this.curChar = this.input_stream.BeginToken();
                }
                catch (IOException e) {
                    this.jjmatchedKind = 0;
                    final Token matchedToken = this.jjFillToken();
                    matchedToken.specialToken = specialToken;
                    return matchedToken;
                }
                (this.image = this.jjimage).setLength(0);
                this.jjimageLen = 0;
                while (true) {
                    switch (this.curLexState) {
                        case 0: {
                            try {
                                this.input_stream.backup(0);
                                while (this.curChar <= ' ' && (0x100003600L & 1L << this.curChar) != 0x0L) {
                                    this.curChar = this.input_stream.BeginToken();
                                }
                            }
                            catch (IOException e2) {
                                continue Label_0004;
                            }
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            curPos = this.jjMoveStringLiteralDfa0_0();
                            if (this.jjmatchedPos == 0 && this.jjmatchedKind > 128) {
                                this.jjmatchedKind = 128;
                                break;
                            }
                            break;
                        }
                        case 1: {
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            curPos = this.jjMoveStringLiteralDfa0_1();
                            if (this.jjmatchedPos == 0 && this.jjmatchedKind > 11) {
                                this.jjmatchedKind = 11;
                                break;
                            }
                            break;
                        }
                        case 2: {
                            this.jjmatchedKind = Integer.MAX_VALUE;
                            this.jjmatchedPos = 0;
                            curPos = this.jjMoveStringLiteralDfa0_2();
                            if (this.jjmatchedPos == 0 && this.jjmatchedKind > 11) {
                                this.jjmatchedKind = 11;
                                break;
                            }
                            break;
                        }
                    }
                    if (this.jjmatchedKind == Integer.MAX_VALUE) {
                        break Label_0004_Outer;
                    }
                    if (this.jjmatchedPos + 1 < curPos) {
                        this.input_stream.backup(curPos - this.jjmatchedPos - 1);
                    }
                    if ((ParserTokenManager.jjtoToken[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 0x3F)) != 0x0L) {
                        final Token matchedToken = this.jjFillToken();
                        matchedToken.specialToken = specialToken;
                        this.TokenLexicalActions(matchedToken);
                        if (ParserTokenManager.jjnewLexState[this.jjmatchedKind] != -1) {
                            this.curLexState = ParserTokenManager.jjnewLexState[this.jjmatchedKind];
                        }
                        return matchedToken;
                    }
                    if ((ParserTokenManager.jjtoSkip[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 0x3F)) == 0x0L) {
                        this.MoreLexicalActions();
                        if (ParserTokenManager.jjnewLexState[this.jjmatchedKind] != -1) {
                            this.curLexState = ParserTokenManager.jjnewLexState[this.jjmatchedKind];
                        }
                        curPos = 0;
                        this.jjmatchedKind = Integer.MAX_VALUE;
                        try {
                            this.curChar = this.input_stream.readChar();
                            continue Label_0004_Outer;
                        }
                        catch (IOException ex) {}
                        break Label_0004_Outer;
                    }
                    if ((ParserTokenManager.jjtoSpecial[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 0x3F)) != 0x0L) {
                        final Token matchedToken = this.jjFillToken();
                        if (specialToken == null) {
                            specialToken = matchedToken;
                        }
                        else {
                            matchedToken.specialToken = specialToken;
                            final Token token = specialToken;
                            final Token next = matchedToken;
                            token.next = next;
                            specialToken = next;
                        }
                        this.SkipLexicalActions(matchedToken);
                    }
                    else {
                        this.SkipLexicalActions(null);
                    }
                    if (ParserTokenManager.jjnewLexState[this.jjmatchedKind] != -1) {
                        this.curLexState = ParserTokenManager.jjnewLexState[this.jjmatchedKind];
                        break;
                    }
                    break;
                }
                break;
            }
        }
        int error_line = this.input_stream.getEndLine();
        int error_column = this.input_stream.getEndColumn();
        String error_after = null;
        boolean EOFSeen = false;
        try {
            this.input_stream.readChar();
            this.input_stream.backup(1);
        }
        catch (IOException e3) {
            EOFSeen = true;
            error_after = ((curPos <= 1) ? "" : this.input_stream.GetImage());
            if (this.curChar == '\n' || this.curChar == '\r') {
                ++error_line;
                error_column = 0;
            }
            else {
                ++error_column;
            }
        }
        if (!EOFSeen) {
            this.input_stream.backup(1);
            error_after = ((curPos <= 1) ? "" : this.input_stream.GetImage());
        }
        throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
    }
    
    void SkipLexicalActions(final Token matchedToken) {
        final int jjmatchedKind = this.jjmatchedKind;
    }
    
    void MoreLexicalActions() {
        final int jjimageLen = this.jjimageLen;
        final int lengthOfMatch = this.jjmatchedPos + 1;
        this.lengthOfMatch = lengthOfMatch;
        this.jjimageLen = jjimageLen + lengthOfMatch;
        switch (this.jjmatchedKind) {
            case 6: {
                this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
                this.jjimageLen = 0;
                this.input_stream.backup(1);
                break;
            }
        }
    }
    
    void TokenLexicalActions(final Token matchedToken) {
        switch (this.jjmatchedKind) {
            case 124: {
                this.image.append(ParserTokenManager.jjstrLiteralImages[124]);
                this.lengthOfMatch = ParserTokenManager.jjstrLiteralImages[124].length();
                matchedToken.kind = 126;
                ((MyToken)matchedToken).realKind = 124;
                this.input_stream.backup(2);
                matchedToken.image = ">";
                break;
            }
            case 125: {
                this.image.append(ParserTokenManager.jjstrLiteralImages[125]);
                this.lengthOfMatch = ParserTokenManager.jjstrLiteralImages[125].length();
                matchedToken.kind = 126;
                ((MyToken)matchedToken).realKind = 125;
                this.input_stream.backup(1);
                matchedToken.image = ">";
                break;
            }
        }
    }
    
    private void jjCheckNAdd(final int state) {
        if (this.jjrounds[state] != this.jjround) {
            this.jjstateSet[this.jjnewStateCnt++] = state;
            this.jjrounds[state] = this.jjround;
        }
    }
    
    private void jjAddStates(int start, final int end) {
        do {
            this.jjstateSet[this.jjnewStateCnt++] = ParserTokenManager.jjnextStates[start];
        } while (start++ != end);
    }
    
    private void jjCheckNAddTwoStates(final int state1, final int state2) {
        this.jjCheckNAdd(state1);
        this.jjCheckNAdd(state2);
    }
    
    private void jjCheckNAddStates(int start, final int end) {
        do {
            this.jjCheckNAdd(ParserTokenManager.jjnextStates[start]);
        } while (start++ != end);
    }
    
    static {
        jjbitVec0 = new long[] { -2L, -1L, -1L, -1L };
        jjbitVec2 = new long[] { 0L, 0L, -1L, -1L };
        jjbitVec3 = new long[] { 2301339413881290750L, -16384L, 4294967295L, 432345564227567616L };
        jjbitVec4 = new long[] { 0L, 0L, 0L, -36028797027352577L };
        jjbitVec5 = new long[] { 0L, -1L, -1L, -1L };
        jjbitVec6 = new long[] { -1L, -1L, 65535L, 0L };
        jjbitVec7 = new long[] { -1L, -1L, 0L, 0L };
        jjbitVec8 = new long[] { 70368744177663L, 0L, 0L, 0L };
        jjnextStates = new int[] { 39, 40, 43, 34, 35, 38, 45, 46, 51, 52, 55, 56, 38, 59, 60, 65, 66, 69, 70, 43, 23, 24, 10, 11, 13, 1, 3, 30, 31, 32, 74, 76, 77, 79, 32, 25, 26, 28, 10, 11, 15, 13, 47, 48, 38, 55, 56, 38, 61, 62, 43, 69, 70, 43, 4, 5, 7, 12, 14, 16, 36, 37, 41, 42, 49, 50, 53, 54, 57, 58, 63, 64, 67, 68, 71, 72 };
        jjstrLiteralImages = new String[] { "", null, null, null, null, null, null, null, null, null, null, null, "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "void", "volatile", "while", "metaclass", "instantiates", null, null, null, null, null, null, null, null, null, null, null, null, null, "(", ")", "{", "}", "[", "]", ";", ",", ".", "@", "=", "<", "!", "~", "?", ":", "==", "<=", ">=", "!=", "||", "&&", "++", "--", "+", "-", "*", "/", "&", "|", "^", "%", "<<", "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=", "<<=", ">>=", ">>>=", "...", ">>>", ">>", ">", "\u001a", null };
        lexStateNames = new String[] { "DEFAULT", "IN_FORMAL_COMMENT", "IN_MULTI_LINE_COMMENT" };
        jjnewLexState = new int[] { -1, -1, -1, -1, -1, -1, 1, 2, -1, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
        jjtoToken = new long[] { -4095L, -25201L, 1L };
        jjtoSkip = new long[] { 1854L, 0L, 0L };
        jjtoSpecial = new long[] { 1792L, 0L, 0L };
        jjtoMore = new long[] { 2240L, 0L, 0L };
    }
}
