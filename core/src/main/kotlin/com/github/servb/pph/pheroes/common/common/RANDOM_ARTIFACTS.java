package com.github.servb.pph.pheroes.common.common;

import com.github.servb.pph.util.helpertype.EnumC;

/**
 * RANDOM_ARTIFACTS. TODO: Provide documentation.
 *
 * @author SerVB
 */
public enum RANDOM_ARTIFACTS implements EnumC {

    ARTT_RAND(0xFF00),
    ARTT_RAND_L1(0xFF01),
    ARTT_RAND_L2(0xFF02),
    ARTT_RAND_L3(0xFF03),
    ARTT_RAND_L4(0xFF04);

    //<editor-fold defaultstate="collapsed" desc="C-like enum (for indexing and count)">
    /** The value of the element. */
    private int value;

    /** Constructs a new element with the next value. */
    private RANDOM_ARTIFACTS() {
        this(NextValueHolder.nextValue); // Call the constructor with the next value
    }

    /**
     * Constructs a new element with the specified value.
     *
     * @param value The specified value.
     */
    private RANDOM_ARTIFACTS(final int value) {
        this.value = value;                     // Set the specified value to this value
        NextValueHolder.nextValue = value + 1;  // Increment the next value for a next element
    }

    /**
     * Returns the value of the element.
     *
     * @return The value of the element.
     */
    @Override
    public final int getValue() {
        return value;
    }

    /** Object that holds the next value. */
    private final static class NextValueHolder {

        /** The next value. */
        private static int nextValue = 0;

    }
    //</editor-fold>

}
