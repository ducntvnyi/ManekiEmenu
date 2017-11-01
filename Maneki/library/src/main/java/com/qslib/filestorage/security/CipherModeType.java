package com.qslib.filestorage.security;

/**
 * <em>https://en.wikipedia.org/wiki/Block_cipher_modes_of_operation</em>
 *
 * @author Roman Kushnarenko - sromku (sromku@gmail.com)
 */
public enum CipherModeType {
    /**
     * Cipher Block Chaining Mode
     */
    CBC("CBC"),

    /**
     * Electronic Codebook Mode
     */
    ECB("ECB");

    private final String mName;

    CipherModeType(String name) {
        mName = name;
    }

    /**
     * Get the algorithm name of the enum value.
     *
     * @return The algorithm name
     */
    public String getAlgorithmName() {
        return mName;
    }
}