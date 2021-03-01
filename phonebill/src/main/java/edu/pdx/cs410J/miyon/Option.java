package edu.pdx.cs410J.miyon;

/**
 * The option class for the CS410J Phone Bill Project
 */
class Option {
    private boolean printOption;
    private boolean fileOption;
    private boolean pPrintOption;
    private String pPrintTo;
    private int nOptions;
    private String fileName;


    /**
     * Creates a new <code>Option</code>
     *
     * @param pPrintOption
     *        pretty print option(T/F)
     * @param pPrintTo
     *        pretty print destination
     * @param printOption
     *        print option(T/F)
     * @param fileOption
     *        file option(T/F)
     * @param nOptions
     *        number of option
     * @param fileName
     *        file name from the command line option part
     *
     */
    Option (boolean pPrintOption, String pPrintTo, boolean printOption, boolean fileOption, int nOptions, String fileName) {
        this.pPrintOption = pPrintOption;
        this.pPrintTo = pPrintTo;
        this.printOption = printOption;
        this.fileOption = fileOption;
        this.nOptions = nOptions;
        this.fileName = fileName;
    }
    /**
     * @return a <code>boolean</code> of pretty print option
     */
    boolean getPPrintOption() {
        return this.pPrintOption;
    }
    /**
     * @return a <code>String</code> of pretty print to
     */
    String getPPrintTo() {
        return this.pPrintTo;
    }
    /**
     * @return a <code>boolean</code> of print option
     */
    boolean getPrintOption() {
        return this.printOption;
    }
    /**
     * @return a <code>boolean</code> of file option
     */
    boolean getFileOption() {
        return this.fileOption;
    }
    /**
     * @return a <code>int</code> of number of options
     */
    int getNOptions() {
        return this.nOptions;
    }
    /**
     * @return a <code>String</code> of file name
     */
    String getFileName() {
        return this.fileName;
    }

    /**
     * @param b
     *       T/F value of pretty print option
     */
    void setpPrintOption(boolean b) {
        this.pPrintOption = b;
    }
    /**
     * @param s
     *       Where to pretty print
     */
    void setPPrintTo(String s) {
        this.pPrintTo = s;
    }
    /**
     * @param b
     *       T/F value of print option
     */
    void setPrintOption(boolean b) {
        this.printOption = b;
    }

    /**
     * @param b
     *       T/F value of file option
     */
    void setFileOption(boolean b) {
        this.fileOption = b;
    }
    /**
     * @param num
     *        number of options
     */
    void setNOptions(int num) {
        this.nOptions = num;
    }
    /**
     * @param fName
     *        file name
     */
    void setFileName(String fName) {
        this.fileName = fName;
    }
}
